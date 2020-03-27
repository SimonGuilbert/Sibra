# PROJ631_Projet_Algorithmique
Ce dépôt correspond au travail principal réalisé au cours du module PROJ631 en IDU3 (Informatique Données Usages) à Polytech Annecy-Chambéry. 

Il est divisé en deux projets codés dans des langages différents.
## 1er projet (Python): Gestion d'un Réseau de Bus. 
  
Le programme permet de calculer le meilleur itinéraire entre deux arrêts de bus en fonction de :
* shortest: chemin le plus court
* fastest: chemin le plus rapide
* foremost: chemin qui fait arriver le plus tôt

Les données relatives au réseau de la Sibra à Annecy est pris en exemple dans l'algorithme. Voici le résultat obtenu :
![](https://github.com/SimonGuilbert/PROJ631_Projet_Algorithmique/blob/master/Sibra/data/Resultat.png)
### Mode d'emploi du programme de gestion du réseau de bus
* Télécharger le dossier Sibra de ce dépôt
* Vérifier qu'il contient bien un dossier data, un fichier programme.py et un autre lecture.py
* Télécharger un  logiciel capable de lire un code en Python et qui possède une console. 
Par exemple, on peut utiliser Spyder téléchargeable gratuitement : www.anaconda.com/distribution/
* Exécuter le fichier programme.py
* Suivre les instructions de la console

## 2ème projet (Java): Arbres de Décision - de Id3 à C4.5
Le programme utilise l'algorithme Id3 et C4.5 pour créer un arbre de décision à partir de données d'apprentissage.
L'arbre est ensuite parcouru pour prédire la classe des données d'un ensemble de test.
Les résultats et performances sont données sous forme de matrices de confusion :
![](https://github.com/SimonGuilbert/PROJ631_Projet_Algorithmique/blob/master/ArbresDecision/Code/Data/Matrices.PNG)

### Mode d'emploi du programme sur l'arbre de décision
* 1ère méthode : avec un terminal
  * Télécharger le dossier de ce dépôt
  * Décompresser et enregistrer le dossier appelé ArbreDecision
  * Ouvrir un terminal
  * Avec la commande cd, se déplacer à l'emplacement du dossier ArbreDecision
  * Entrer la commande cd Code
  * Écrire javac Main.java
  * Écrire java Main
  * Si ça ne fonctionne pas, regarder ceci : https://fr.wikihow.com/compiler-et-ex%C3%A9cuter-un-programme-Java-en-ligne-de-commande
  
* 2ème méthode : avec Eclipse
  * Télécharger le dossier de ce dépôt
  * Télecharger Eclipse gratuitement : https://www.eclipse.org/downloads/
  * Décompresser et enregistrer le dossier appelé ArbreDecision 
  * Ouvrir Eclipse
  * File > new > Java Project
  * Décocher Use Default Location
  * Dans la zone de texte Location: écrire le chemin amenant au dossier Code (sous-dossier de ArbreDecision)
  * Finish
  * A gauche, dans l'onglet Package Explorer, clic droit sur Code
  * Run As > Java Application
  

  

  
