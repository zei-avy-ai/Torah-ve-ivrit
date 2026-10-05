package com.torahveivrit.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
private val dailyPlan = listOf(
    "Jour 1 — Lecture" to "Lire les premiers versets avec ניקוד.",
    "Jour 2 — Vocabulaire" to "Identifier les mots nouveaux et leur racine.",
    "Jour 3 — Compréhension" to "Relire le texte et comprendre les phrases.",
    "Jour 4 — Traduction" to "Comparer l'hébreu à une traduction juive.",
    "Jour 5 — Révision" to "Relire les mots et passages étudiés.",
    "Jour 6 — Bilan" to "Révision générale et exercice de la semaine."
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { Surface(Modifier.fillMaxSize()) { TorahVeIvritApp() } } }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable private fun TorahVeIvritApp() {
    var selected by remember { mutableIntStateOf(0) }
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
            0 -> Home(Modifier.padding(p)); 1 -> Study("Torah", Modifier.padding(p))
            2 -> Study("Haftarah", Modifier.padding(p)); 3 -> Exercise(Modifier.padding(p))
            4 -> Review(Modifier.padding(p)); 5 -> Siddur(Modifier.padding(p))
            else -> Calendar(Modifier.padding(p))
        }
    }
}

@Composable private fun Home(m: Modifier) = Page(m, "תורה ועברית", "Apprendre l'hébreu biblique au rythme de la paracha",
    listOf("Étude de la semaine" to "Paracha, Torah et Haftarah", "Programme du jour" to "Un objectif simple chaque jour.", "Progression" to "Ton avancement sera conservé localement.", "Sources" to "Textes juifs et traductions juives uniquement."))

@Composable private fun Study(title: String, m: Modifier) {
    LazyColumn(m.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { Text(title, style = MaterialTheme.typography.headlineSmall); Text("Programme hebdomadaire") }
        items(dailyPlan.size) { i -> Info(dailyPlan[i].first, dailyPlan[i].second) }
        item { Info("קטע הלימוד", "Texte hébreu avec ניקוד, vocabulaire et traduction française.") }
    }
}

@Composable private fun Exercise(m: Modifier) = Page(m, "Exercice de la semaine", "",
    listOf("Lecture" to "Lis le passage en hébreu à voix haute.", "Vocabulaire" to "Retrouve les racines et le sens des mots.", "Compréhension" to "Réponds aux questions sur le texte.", "Révision" to "Recommence les exercices difficiles."))

@Composable private fun Review(m: Modifier) = Page(m, "Bilan et révision", "",
    listOf("Semaine" to "Revois les passages et mots appris.", "Lexique" to "Le dictionnaire sera alimenté par les leçons.", "Progression" to "Le suivi sera enregistré localement."))

@Composable private fun Siddur(m: Modifier) {
    LazyColumn(m.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { Text("Sidour", style = MaterialTheme.typography.headlineSmall) }
        item { Info("Minhag", "Priorité au minhag séfarade tunisien, puis séfarade israélien.") }
        item { Card(Modifier.fillMaxWidth()) { Column(Modifier.padding(16.dp)) {
            Text("שמע ישראל ה׳ אלהינו ה׳ אחד", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End)
        } } }
    }
}

@Composable private fun Calendar(m: Modifier) = Page(m, "Calendrier", "",
    listOf("Cycle annuel" to "Le programme suit le cycle des parachiot.", "Semaine" to "5 jours d'apprentissage puis une journée de bilan.", "Prochaine étape" to "Intégrer le calendrier hébraïque et la paracha de chaque semaine."))

@Composable private fun Page(m: Modifier, title: String, subtitle: String, cards: List<Pair<String,String>>) {
    LazyColumn(m.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { Text(title, style = MaterialTheme.typography.headlineSmall); if (subtitle.isNotBlank()) Text(subtitle) }
        items(cards.size) { i -> Info(cards[i].first, cards[i].second) }
    }
}

@Composable private fun Info(title: String, body: String) {
    Card(Modifier.fillMaxWidth()) { Column(Modifier.padding(16.dp)) {
        Text(title, style = MaterialTheme.typography.titleMedium); Spacer(Modifier.height(4.dp)); Text(body)
    } }
}