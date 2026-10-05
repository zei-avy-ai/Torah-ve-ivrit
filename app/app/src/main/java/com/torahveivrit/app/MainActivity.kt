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
import androidx.compose.ui.platform.LocalContext
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
private data class Verse(val number: Int, val hebrew: String, val french: String)
private data class ParashaInfo(
    val hebrew: String,
    val french: String,
    val reference: String,
    val date: String,
    val haftarahSephardic: String
)
private val upcomingParashot5787 = listOf(
    ParashaInfo("בְּרֵאשִׁית", "Bereshit", "Genèse 1:1–6:8", "10 octobre 2026", "Isaïe 42:5–21"),
    ParashaInfo("נֹחַ", "Noa'h", "Genèse 6:9–11:32", "17 octobre 2026", "Isaïe 54:1–10"),
    ParashaInfo("לֶךְ־לְךָ", "Lekh Lekha", "Genèse 12:1–17:27", "24 octobre 2026", "Isaïe 40:27–41:16"),
    ParashaInfo("וַיֵּרָא", "Vayera", "Genèse 18:1–22:24", "31 octobre 2026", "II Rois 4:1–37")
)

private val bereshit1 = listOf(
    Verse(1, "בְּרֵאשִׁית בָּרָא אֱלֹהִים אֵת הַשָּׁמַיִם וְאֵת הָאָרֶץ.", "Au commencement, Dieu avait créé le ciel et la terre."),
    Verse(2, "וְהָאָרֶץ הָיְתָה תֹהוּ וָבֹהוּ, וְחֹשֶׁךְ עַל־פְּנֵי תְהוֹם; וְרוּחַ אֱלֹהִים מְרַחֶפֶת עַל־פְּנֵי הַמָּיִם.", "Or la terre n’était que solitude et chaos ; des ténèbres couvraient la face de l’abîme, et le souffle de Dieu planait sur la face des eaux."),
    Verse(3, "וַיֹּאמֶר אֱלֹהִים: יְהִי אוֹר; וַיְהִי־אוֹר.", "Dieu dit : « Que la lumière soit ! » Et la lumière fut."),
    Verse(4, "וַיַּרְא אֱלֹהִים אֶת־הָאוֹר כִּי־טוֹב; וַיַּבְדֵּל אֱלֹהִים בֵּין הָאוֹר וּבֵין הַחֹשֶׁךְ.", "Dieu considéra que la lumière était bonne, et il établit une distinction entre la lumière et les ténèbres."),
    Verse(5, "וַיִּקְרָא אֱלֹהִים לָאוֹר יוֹם, וְלַחֹשֶׁךְ קָרָא לָיְלָה; וַיְהִי־עֶרֶב וַיְהִי־בֹקֶר, יוֹם אֶחָד.", "Dieu appela la lumière jour, et les ténèbres, il les appela Nuit. Il fut soir, il fut matin, — un jour."),
    Verse(6, "וַיֹּאמֶר אֱלֹהִים, יְהִי רָקִיעַ בְּתוֹךְ הַמָּיִם, וִיהִי מַבְדִּיל, בֵּין מַיִם לָמָיִם.", "Dieu dit : « Qu’un espace s’étende au milieu des eaux, et forme une barrière entre les unes et les autres. »"),
    Verse(7, "וַיַּעַשׂ אֱלֹהִים, אֶת־הָרָקִיעַ, וַיַּבְדֵּל בֵּין הַמַּיִם אֲשֶׁר מִתַּחַת לָרָקִיעַ, וּבֵין הַמַּיִם אֲשֶׁר מֵעַל לָרָקִיעַ; וַיְהִי־כֵן.", "Dieu fit l’espace, opéra une séparation entre les eaux qui sont au-dessous et les eaux qui sont au-dessus, et cela demeura ainsi."),
    Verse(8, "וַיִּקְרָא אֱלֹהִים לָרָקִיעַ, שָׁמָיִם; וַיְהִי־עֶרֶב וַיְהִי־בֹקֶר, יוֹם שֵׁנִי.", "Dieu nomma cet espace le Ciel. Le soir se fit, le matin se fit, — second jour.")
)

private val parashot = listOf(
    "בראשית — Bereshit", "נח — Noa'h", "לך־לך — Lekh Lekha", "וירא — Vayera",
    "חיי שרה — Haye Sarah", "תולדות — Toldot", "ויצא — Vayetze", "וישלח — Vayishlah",
    "וישב — Vayeshev", "מקץ — Mikets", "ויגש — Vayigash", "ויחי — Vayehi",
    "שמות — Shemot", "וארא — Vaera", "בא — Bo", "בשלח — Beshalah",
    "יתרו — Yitro", "משפטים — Mishpatim", "תרומה — Terumah", "תצוה — Tetsaveh",
    "כי תשא — Ki Tissa", "ויקהל — Vayakhel", "פקודי — Pekudei",
    "ויקרא — Vayikra", "צו — Tsav", "שמיני — Shemini", "תזריע — Tazria",
    "מצורע — Metsora", "אחרי מות — Aharei Mot", "קדושים — Kedoshim",
    "אמור — Emor", "בהר — Behar", "בחוקותי — Behoukotaï", "במדבר — Bamidbar",
    "נשא — Nasso", "בהעלותך — Behaalotekha", "שלח לך — Shelah Lekha",
    "קרח — Korah", "חקת — Houkat", "בלק — Balak", "פינחס — Pinhas",
    "מטות — Matot", "מסעי — Massei", "דברים — Devarim", "ואתחנן — Vaethanan",
    "עקב — Ekev", "ראה — Reeh", "שופטים — Shoftim", "כי תצא — Ki Tetse",
    "כי תבוא — Ki Tavo", "נצבים — Nitsavim", "וילך — Vayelekh",
    "האזינו — Haazinou", "וזאת הברכה — Vezot Haberakha"
)

private val studyDays = listOf(
    "Jour 1 — Lecture" to "Lire le passage hébreu avec ניקוד à voix haute.",
    "Jour 2 — Vocabulaire" to "Repérer les mots nouveaux et leurs racines.",
    "Jour 3 — Compréhension" to "Relire le passage et identifier le sens des phrases.",
    "Jour 4 — Traduction" to "Comparer l'hébreu à la traduction française juive.",
    "Jour 5 — Révision" to "Revoir le vocabulaire et les passages étudiés.",
    "Jour 6 — Bilan" to "Révision générale et exercice de la semaine."
)

private fun verseRangeForDay(day: Int, totalVerses: Int): String {
    if (day >= 5 || totalVerses == 0) return "Révision de l'ensemble du passage"
    val studyDaysCount = 5
    val base = totalVerses / studyDaysCount
    val remainder = totalVerses % studyDaysCount
    var start = 1
    for (i in 0 until day) start += base + if (i < remainder) 1 else 0
    val count = base + if (day < remainder) 1 else 0
    val end = start + count - 1
    return "Versets $start–$end"
}

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
    val context = LocalContext.current
    val prefs = remember(context) { context.getSharedPreferences("progress", Context.MODE_PRIVATE) }
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
            2 -> Haftarah(Modifier.padding(p))
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
        item { Info("Cycle des parachiot", parashot.size.toString() + " parachiot référencées dans le cycle annuel.") }
        item {
            val next = upcomingParashot5787.first()
            Info(
                "Prochaine paracha",
                next.hebrew + " — " + next.french + " • " + next.date + "\n" +
                    next.reference + "\nHaftarah séfarade : " + next.haftarahSephardic
            )
        }
        item { Info("Leçon disponible", "Bereshit 1:1–8 est intégré avec ניקוד et traduction française juive. La répartition des versets est calculée automatiquement sur 5 jours d'étude, avec le 6e jour réservé au bilan.") }
        item { Info("Programme du jour", if (done < studyDays.size) studyDays[done].first else "Semaine terminée") }
        item {
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Progression", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    LinearProgressIndicator(progress = done.toFloat() / studyDays.size, modifier = Modifier.fillMaxWidth())
                    Spacer(Modifier.height(6.dp))
                    Text(done.toString() + " / " + studyDays.size + " journées validées")
                }
            }
        }
        item { Info("Sources", "Textes juifs et traductions juives uniquement. La traduction utilisée ici est celle de la Bible du Rabbinat sous la direction de Zadoc Kahn, édition originale 1899.") }
    }
}

@Composable
private fun Study(title: String, m: Modifier, completed: List<Boolean>, onToggle: (Int) -> Unit) {
    LazyColumn(m.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { Text(title, style = MaterialTheme.typography.headlineSmall); Text("Paracha בראשית — programme en 6 jours") }
        item { Info("Répartition automatique", "Les versets actuellement disponibles sont répartis sur 5 journées d’étude, puis le 6e jour est consacré au bilan. La répartition sera recalculée à mesure que le corpus sera complété.") }
        items(studyDays.size) { i ->
            Card(Modifier.fillMaxWidth()) {
                Row(Modifier.fillMaxWidth().padding(16.dp)) {
                    Column(Modifier.weight(1f)) {
                        Text(studyDays[i].first, style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(4.dp))
                        Text(studyDays[i].second)
                        Spacer(Modifier.height(4.dp))
                        Text(verseRangeForDay(i, bereshit1.size), style = MaterialTheme.typography.labelMedium)
                    }
                    Spacer(Modifier.width(8.dp))
                    Checkbox(checked = completed[i], onCheckedChange = { onToggle(i) })
                }
            }
        }
        items(bereshit1.size) { i ->
            val verse = bereshit1[i]
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("בראשית 1:" + verse.number, style = MaterialTheme.typography.labelLarge)
                    Spacer(Modifier.height(6.dp))
                    Text(verse.hebrew, textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth())
                    Spacer(Modifier.height(8.dp))
                    Text(verse.french)
                }
            }
        }
        item { Info("Source", "Bible du Rabbinat, sous la direction de Zadoc Kahn, édition originale 1899. Texte français du domaine public ; source consultée : Wikisource.") }
    }
}

@Composable
private fun Haftarah(m: Modifier) {
    LazyColumn(m.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Text("Haftarah", style = MaterialTheme.typography.headlineSmall)
            Text("Référence séfarade pour la paracha Bereshit")
        }
        item {
            Info(
                "בראשית — Bereshit",
                "Isaïe 42:5–21\n\nLecture séfarade indiquée pour Bereshit. Le texte hébreu et sa traduction juive seront intégrés après vérification de la source et de l'édition."
            )
        }
        item {
            Info(
                "Principe de l'application",
                "Aucun texte non vérifié n'est ajouté. Les contenus doivent provenir de sources juives identifiées et respecter la priorité séfarade tunisienne."
            )
        }
    }
}

@Composable
private fun Exercise(m: Modifier) {
    var answer by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }
    LazyColumn(m.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { Text("Exercice de la semaine", style = MaterialTheme.typography.headlineSmall) }
        item { Info("Lecture", "Lis à voix haute בראשית 1:1–5 avec ניקוד, puis compare avec la traduction française.") }
        item {
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("בְּרֵאשִׁית בָּרָא אֱלֹהִים", textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth())
                    Spacer(Modifier.height(6.dp))
                    Text("Exercice de vocabulaire : identifie le mot אֱלֹהִים et son sens.", textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth())
                    Spacer(Modifier.height(12.dp)); Text("Que signifie « אלהים » ?")
                    OutlinedTextField(value = answer, onValueChange = { answer = it }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = { checked = true }) { Text("Vérifier") }
                    if (checked) { Spacer(Modifier.height(8.dp)); Text(if (answer.trim().lowercase() in listOf("dieu", "god")) "Correct." else "Réponse attendue : Dieu.") }
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
    LazyColumn(m.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { Text("Calendrier", style = MaterialTheme.typography.headlineSmall) }
        item { Info("5787 — prochaines parachiot", "10 oct. — Bereshit\n17 oct. — Noa'h\n24 oct. — Lekh Lekha\n31 oct. — Vayera") }
        item { Info("Cycle de lecture", "La lecture annuelle recommence avec Bereshit après Sim'hat Torah. Pour 5787, Bereshit est lue le 10 octobre 2026.") }
        item { Info("Étude", done.toString() + " / " + studyDays.size + " journées validées.") }
        item { Info("Calendrier hébraïque complet", "La prochaine étape sera d'intégrer les dates hébraïques et les fêtes directement dans l'application, sans dépendance à une connexion réseau.") }
    }
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
