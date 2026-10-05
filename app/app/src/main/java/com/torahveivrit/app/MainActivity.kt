package com.torahveivrit.app

import android.os.Bundle
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

private data class Section(val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)
private val sections = listOf(
    Section("Accueil", Icons.Filled.Home), Section("Torah", Icons.Filled.MenuBook),
    Section("Haftarah", Icons.Filled.Star), Section("Exercice", Icons.Filled.School),
    Section("Bilan", Icons.Filled.CheckCircle), Section("Sidour", Icons.Filled.Translate),
    Section("Calendrier", Icons.Filled.CalendarMonth)
)
private val studyDays = listOf(
    "Jour 1 — Lecture" to "Lire le passage hébreu avec ניקוד à voix haute.",
    "Jour 2 — Vocabulaire" to "Repérer les mots nouveaux et leurs racines.",
    "Jour 3 — Compréhension" to "Relire le passage et identifier le sens des phrases.",
    "Jour 4 — Traduction" to "Comparer l'hébreu à la traduction française juive.",
    "Jour 5 — Révision" to "Revoir le vocabulaire et les passages étudiés.",
    "Jour 6 — Bilan" to "Révision générale et exercice de la semaine."
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { Surface(Modifier.fillMaxSize()) { TorahVeIvritApp() } } }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TorahVeIvritApp() {
    var selected by remember { mutableIntStateOf(0) }
    val prefs = remember { getSharedPreferences("progress", Context.MODE_PRIVATE) }
    var completed by remember { mutableStateOf((0 until studyDays.size).map { prefs.getBoolean("day_" + it, false) }) }
    fun toggleDay(index: Int) {
        val next = completed.toMutableList()
        next[index] = !next[index]
        completed = next
        prefs.edit().putBoolean("day_" + index, next[index]).apply()
    }
    Scaffold(
        topBar = { TopAppBar(title = {
            Column { Text("תורה ועברית"); Text(sections[selected].title, style = MaterialTheme.typography.labelMedium) }
        }) },
        bottomBar = { NavigationBar {
            sections.forEachIndexed { i, s ->
                NavigationBarItem(selected == i, { selected = i }, { Icon(s.icon, s.title) }, label = { Text(s.title) })
            }
        } }
    ) { p ->
        when (selected) {
            0 -> Home(Modifier.padding(p), completed)
            1 -> Study("Torah", Modifier.padding(p), completed, ::toggleDay)
            2 -> Study("Haftarah", Modifier.padding(p), completed, ::toggleDay)
            3 -> Exercise(Modifier.padding(p))
            4 -> Review(Modifier.padding(p), completed)
            5 -> Siddur(Modifier.padding(p))
            else -> Calendar(Modifier.padding(p), completed)
        }
    }
}

@Composable
private fun Home(m: Modifier, completed: List<Boolean>) {
    val done = completed.count { it }
    LazyColumn(m.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { Text("תורה ועברית", style = MaterialTheme.typography.headlineSmall); Text("Apprendre l'hébreu biblique au rythme de la paracha") }
        item { Info("Paracha actuelle", "בראשית — Bereshit") }
        item { Info("Programme du jour", if (done < studyDays.size) studyDays[done].first else "Semaine terminée") }
        item {
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Progression", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    LinearProgressIndicator(progress = { done.toFloat() / studyDays.size }, modifier = Modifier.fillMaxWidth())
                    Spacer(Modifier.height(6.dp))
                    Text(done.toString() + " / " + studyDays.size + " journées validées")
                }
            }
        }
        item { Info("Sources", "Textes juifs et traductions juives uniquement. Le contenu sera enrichi progressivement avec des sources autorisées.") }
    }
}

@Composable
private fun Study(title: String, m: Modifier, completed: List<Boolean>, onToggle: (Int) -> Unit) {
    LazyColumn(m.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { Text(title, style = MaterialTheme.typography.headlineSmall); Text("Paracha בראשית — programme en 6 jours") }
        items(studyDays.size) { i ->
            Card(Modifier.fillMaxWidth()) {
                Row(Modifier.fillMaxWidth().padding(16.dp)) {
                    Column(Modifier.weight(1f)) {
                        Text(studyDays[i].first, style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(4.dp)); Text(studyDays[i].second)
                    }
                    Spacer(Modifier.width(8.dp))
                    Checkbox(checked = completed[i], onCheckedChange = { onToggle(i) })
                }
            }
        }
        item { Info("קטע הלימוד", "Le texte hébreu vocalisé, le vocabulaire et la traduction française seront intégrés dans les données de cours.") }
    }
}

@Composable
private fun Exercise(m: Modifier) {
    var answer by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }
    LazyColumn(m.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { Text("Exercice de la semaine", style = MaterialTheme.typography.headlineSmall) }
        item { Info("Lecture", "Lis à voix haute le début de בראשית.") }
        item {
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("בראשית ברא אלהים את השמים ואת הארץ", textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth())
                    Spacer(Modifier.height(12.dp)); Text("Que signifie « אלהים » ?")
                    OutlinedTextField(value = answer, onValueChange = { answer = it }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = { checked = true }) { Text("Vérifier") }
                    if (checked) { Spacer(Modifier.height(8.dp)); Text(if (answer.trim().lowercase() == "dieu") "Correct." else "Réponse attendue : Dieu.") }
                }
            }
        }
    }
}

@Composable
private fun Review(m: Modifier, completed: List<Boolean>) {
    val done = completed.count { it }
    Page(m, "Bilan et révision", "", listOf(
        "Progression" to (done.toString() + " / " + studyDays.size + " journées validées."),
        "Objectif" to "Terminer les 6 journées puis revoir les passages difficiles.",
        "Lexique" to "Le lexique sera alimenté directement depuis les leçons."
    ))
}

@Composable
private fun Siddur(m: Modifier) {
    LazyColumn(m.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { Text("Sidour", style = MaterialTheme.typography.headlineSmall) }
        item { Info("Minhag", "Priorité au minhag séfarade tunisien, puis séfarade israélien.") }
        item {
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("שמע ישראל ה׳ אלהינו ה׳ אחד", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End)
                    Spacer(Modifier.height(8.dp)); Text("Texte hébreu du rite sélectionné.")
                }
            }
        }
    }
}

@Composable
private fun Calendar(m: Modifier, completed: List<Boolean>) {
    val done = completed.count { it }
    Page(m, "Calendrier", "", listOf(
        "Cycle annuel" to "Le programme suit le cycle des parachiot.",
        "Semaine actuelle" to "בראשית — Bereshit.",
        "Étude" to (done.toString() + " / " + studyDays.size + " journées validées."),
        "Prochaine étape" to "Intégrer le calendrier hébraïque et les dates des parachiot."
    ))
}

@Composable
private fun Page(m: Modifier, title: String, subtitle: String, cards: List<Pair<String, String>>) {
    LazyColumn(m.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { Text(title, style = MaterialTheme.typography.headlineSmall); if (subtitle.isNotBlank()) Text(subtitle) }
        items(cards.size) { i -> Info(cards[i].first, cards[i].second) }
    }
}

@Composable
private fun Info(title: String, body: String) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp)); Text(body)
        }
    }
}
