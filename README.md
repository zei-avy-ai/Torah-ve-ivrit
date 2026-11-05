# Torah ve Ivrit — תורה ועברית

Application Android d’apprentissage de l’hébreu biblique en français, organisée autour du cycle de lecture de la Torah.

## Objectifs

- Étudier la Torah avec texte hébreu vocalisé et traduction française juive.
- Suivre un programme d’étude en six journées : lecture, vocabulaire, compréhension, traduction, révision et bilan.
- Progresser avec des exercices et un suivi conservé localement sur l’appareil.
- Ajouter les Haftarot, le Sidour et le calendrier hébraïque en respectant la priorité du minhag séfarade tunisien.
- Fonctionner localement, sans compte obligatoire ni permissions superflues.

## État actuel

Le projet contient une première application Jetpack Compose avec sept rubriques : Accueil, Torah, Haftarah, Exercice, Bilan, Sidour et Calendrier. Le texte hébreu et une traduction française de Bereshit sont intégrés comme ressources locales. Plusieurs rubriques restent à enrichir et à vérifier sur le plan des textes et des minhaguim : elles ne doivent pas être considérées comme un sidour ou un corpus liturgique complet.

## Architecture

- Android / Kotlin
- Jetpack Compose et Material 3
- Ressources JSON locales pour les textes
- Préférences locales Android pour le suivi de progression
- Namespace : `com.torahveivrit.app`

## Compilation

Le projet utilise Gradle 8.2 et Android Gradle Plugin 8.2.2. Avec JDK 17 et le SDK Android installé :

```bash
gradle assembleDebug
```

L’APK debug est généré dans :

```
app/app/build/outputs/apk/debug/app-debug.apk
```

## Sources et exigence éditoriale

Les textes doivent toujours être accompagnés d’une référence vérifiable (édition, rite et passage). La priorité est donnée aux sources juives et au minhag séfarade tunisien. Ne pas compléter un texte liturgique par reconstruction ou approximation.

## Licence et attribution

Les données textuelles doivent conserver leur attribution et leur statut de droits. Vérifier les conditions de réutilisation de chaque nouvelle source avant intégration.
