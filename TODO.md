# Classes:


## World
* génère background
* liste de chateaux
* liste d'unités sur le terrain

## Joueur (enum ?)
* juste un type pour associer aux soldats/chateaux leur propriétaire

## Entity
* hérite de imageview
* coordonnées world
* propriétaire (joueur)
### Soldat
#### Piquier
#### Chevalier
#### Onagre
### Chateau
* trésor
* niveau
* réserve de troupes
* unité de prod
* ordre courant
* orientation porte

##Settings
* constantes



# Structure:
## Exécution tour par tour toutes les X secondes
## Gestion fin de partie
## mode pause
## gestion des interactions

# Tour de jeu:
## Gestion des chateaux
### Gestion des valeurs:
* revenu
### Gestion prod (amélioration niveau comprise):
### Engagement des unités
## Gestion des unités sur le terrain
### Deplacements (+ assauts)
