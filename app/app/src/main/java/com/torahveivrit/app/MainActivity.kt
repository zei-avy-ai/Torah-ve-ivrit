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
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

private data class AppSection(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

private val sections = listOf(
    AppSection("Accueil", Icons.Filled.Home),
    AppSection("Torah", Icons.Filled.MenuBook),
    AppSection("Haftarah", Icons.Filled.Star),
    AppSection("Exercice", Icons.Filled.School),
    AppSection("Bilan", Icons.Filled.CheckCircle),
    AppSection("Sidour", Icons.Filled.Translate),
    AppSection("Calendrier", Icons.Filled.CalendarMonth)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TorahVeIvritApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TorahVeIvritApp() {
    var selected by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("תורה ועברית")
                        Text(
                            sections[selected].title,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                sections.forEachIndexed { index, section ->
                    NavigationBarItem(
                        selected = selected == index,
                        onClick = { selected = index },
                        icon = { Icon(section.icon, contentDescription = section.title) },
                        label = { Text(section.title) }
                    )
                }
            }
        }
    ) { padding ->
        when (selected) {
            0 -> HomeScreen(Modifier.padding(padding))
            1 -> StudyScreen("Torah", Modifier.padding(padding))
            2 -> StudyScreen("Haftarah", Modifier.padding(padding))
            3 -> ExerciseScreen(Modifier.padding(padding))
            4 -> ReviewScreen(Modifier.padding(padding))
            5 -> SiddurScreen(Modifier.padding(padding))
            else -> CalendarScreen(Modifier.padding(padding))
        }
    }
}

@Composable
private fun HomeScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                "תורה ועברית",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Apprendre l'hébreu biblique au rythme de la paracha",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        item { InfoCard("Cette semaine", "Paracha et versets du jour") }
        item { InfoCard("Progression", "Ton apprentissage sera suivi jour après jour") }
        item { InfoCard("Révision", "Retrouve ici les notions à revoir") }
    }
}

@Composable
private fun StudyScreen(title: String, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(title, style = MaterialTheme.typography.headlineSmall)
            Text("Étude de la semaine", style = MaterialTheme.typography.titleMedium)
        }
        item { VerseCard("בראשית", "Texte hébreu avec ניקוד") }
        item { VerseCard("לימוד", "Vocabulaire et compréhension") }
        item { VerseCard("תרגום", "Traduction française") }
    }
}

@Composable
private fun ExerciseScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Exercice de la semaine", style = MaterialTheme.typography.headlineSmall)
        InfoCard("Lecture", "Lis le passage en hébreu.")
        InfoCard("Vocabulaire", "Travaille les mots nouveaux.")
        InfoCard("Compréhension", "Vérifie ce que tu as compris.")
    }
}

@Composable
private fun ReviewScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Bilan et révision", style = MaterialTheme.typography.headlineSmall)
        InfoCard("Bilan de la semaine", "Révise avant de passer à la paracha suivante.")
        InfoCard("Lexique", "Le dictionnaire sera accessible depuis les espaces d'étude.")
    }
}

@Composable
private fun SiddurScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { Text("Sidour", style = MaterialTheme.typography.headlineSmall) }
        item { InfoCard("Minhag", "Priorité au minhag séfarade tunisien, puis séfarade israélien.") }
        item {
            Card(Modifier.fillMaxWidth()) {
                Text(
                    "שמע ישראל ה׳ אלהינו ה׳ אחד",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

@Composable
private fun CalendarScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Calendrier", style = MaterialTheme.typography.headlineSmall)
        InfoCard("Semaine", "Paracha, étude et révision")
        InfoCard("Année", "Progression sur le cycle annuel de la Torah")
    }
}

@Composable
private fun InfoCard(title: String, body: String) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text(body)
        }
    }
}

@Composable
private fun VerseCard(title: String, body: String) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Text(body)
        }
    }
}
