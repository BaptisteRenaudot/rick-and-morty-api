# Rick and Morty

Application Kotlin multiplateforme utilisant l'API Rick and Morty pour explorer les personnages, épisodes et lieux de l'univers Rick and Morty.

## Structure du projet

Ce projet Kotlin Multiplateforme cible les plateformes Android et Desktop.

### Architecture

L'application est organisée selon les principes de Clean Architecture et le pattern MVI (Model-View-Intent).

#### Clean Architecture

Le code est divisé en trois couches principales :

1. **UI** : Interface utilisateur et logique de présentation
2. **Domain** : Logique métier, cas d'utilisation et interfaces des repositories
3. **Data** : Gestion des données et implémentation des repositories

### Organisation des dossiers

- **composeApp**
  - `commonMain` : Code commun à toutes les plateformes
  - `androidMain` : Code spécifique à Android
  - `desktopMain` : Code spécifique au Desktop

#### Architecture MVI

L'architecture MVI assure un flux de données unidirectionnel :

1. **Model** : État et logique métier (ViewModel)
2. **View** : Composants d'interface utilisateur
3. **Intent** : Actions utilisateur qui déclenchent des mises à jour
