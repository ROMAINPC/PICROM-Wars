# PICROM-Wars, Jeu de stratégie
NB : Although the code is in English, this read-me and the game are in French.
Réalisé avec Picachoc : https://github.com/picachoc





## Introduction :





## Structure du projet et exécution :
### Lancement :
### IDE :
Ce projet a été édité sous l'IDE Eclipse, il est conseillé de l'exécuter avec Eclipse ou un IDE similaire.
### Installation (nécessite JDK) :
### Java 11+ (JavaFX SDK nécessaire -> <https://openjfx.io/>):
* Se placer à l'intérieur du dossier `src`
* `javac -d ../bin --module-path "path\to\javafx-sdk-X\lib" --add-modules javafx.controls picrom/*.java`
* `cd ..`
* Copier [src/picrom/application.css](src/picrom/application.css) dans [bin/picrom/application.css](bin/picrom/application.css)
* Copier [src/Drawables](src/Drawables) dans [bin/Drawables](bin/Drawables)

### Java 8, 9 ou 10 :
* Retirer `--module-path "path\to\javafx-sdk-X\lib" --add-modules javafx.controls` des commandes précédentes.

### Exécution (nécessite JRE) :
* `cd bin`
* `java picrom.Main`
* OR for java 11+ `java --module-path "path\to\javafx-sdk-X\lib" --add-modules javafx.controls picrom.Main`

### Paramètres :
Ce projet n'as pas encore été conçu pour être exporté en exécutable, il est possible de modifer certains éléments de gameplay depuis la classe [src/picrom/utils/Settings.java](src/picrom/utils/Settings.java)


### Versions disponibles :
#### Version avec sauvegarde :





## Règles du jeu :
### Objectif :
### Contrôles et interface :
### Unités :
### Châteaux :









## Remarques de gameplay :








## Choix et remarques d'implémentation :
### Structure objet :
### Détails de code :


exemple image: (to remove)
<img src="md_src/nom.PNG" width="150">




