# -*- coding: utf-8 -*-
"""
Created on Thu Jan 23 10:32:41 2020

@author: guilbers
"""

from datetime import timedelta,date
from time import sleep
import sibra

# =============================================================================
# Fonctions trouvées sur internet pour simplifier le code
# =============================================================================
def left(s, amount):
    return s[:amount]

def right(s, amount):
    return s[-amount:]

def mid(s, offset, amount):
    return s[offset:offset+amount]

# =============================================================================
# Date du jour
# =============================================================================
#Jour de la semaine d'aujoud'hui (0=lundi, 1=mardi, ..., 6=dimanche)
jour = ("semaine" if date.today().weekday() < 5 else "weekend")

# =============================================================================
# Classes
# =============================================================================

# On considère que le + dans la première ligne du fichier txt est une erreur 
# et doit être remplacé par un N. On transforme ensuite la chaîne de caractères
# en liste avec split
class Ligne:
    def __init__(self,nomFichier,nomLigne):
        self.nom = nomLigne
        self.slited_content = sibra.lecture(nomFichier)
        self.path = self.slited_content[0 if jour == "semaine" else 3].replace("+","n").split(" n ")
        self.date_go = sibra.dates2dic(self.slited_content[1 if jour == "semaine" else 4])
        self.date_back = sibra.dates2dic(self.slited_content[2 if jour == "semaine" else 5])
        
    def getArretPrecedent(self,arret):
        if arret == self.path[0]:
            return None
        return self.path[self.path.index(arret)-1]
    
    def getArretSuivant(self,arret):
        if arret == self.path[len(self.path)-1]:
            return None
        return self.path[self.path.index(arret)+1]
        
        
class Arret:
    def __init__(self, intitule, ligne, horairesGo, horairesBack):
        self.intitule = intitule
        self.ligne = ligne
        self.horairesGo = self.setHoraires(horairesGo)
        self.horairesBack = self.setHoraires(horairesBack)
        self.arretSuivants = []
        
    # Retourne la liste des horaires au format timedelta (obtenu avec la bibliotèque datetime)            
    def setHoraires(self,typeHoraires):
        l = []
        for horaire in typeHoraires:
            if horaire != '-' :
                l.append(timedelta(hours = int(left(horaire,len(horaire)-3)),
                                   minutes = int(right(horaire,2))))
        return l
       
    def getIntitule(self):
        return self.intitule
    
    def getHorairesSemaineGo(self):
        return self.horairesSemaineGo
    
    def getHorairesSemaineBack(self):
        return self.horairesSemaineBack
    
    def getHorairesWEGo(self):
        return self.horairesWEGo
    
    def getHorairesWEBack(self):
        return self.horairesWEBack
    
    def getLigne(self):
        return self.ligne


class Reseau :
    def __init__(self,racine):
        self.racine = racine
    
#    def conditionArret(self,listeArrets):
#        for arret in listeArrets:
#            if arret[0] == arrivee:
#                return True
#        for arret in listeArrets:
#            print("ici ",listeArrets)
#            if arret[0] != None:
#                return False
#        return True
        
    def conditionArret(self):
        return self.racine.getIntitule() == arrivee

        
    def croisementLigne(self,arret,ligne):
        if arret not in intersections:
            res = []
            for l in listeLignes:
                if l != ligne:
                    if arret in l.path:
                        res.append([l.getArretPrecedent(arret),l])
                        res.append([l.getArretSuivant(arret),l])
                        intersections.append(arret)
            return ([[None]] if not res else res)
        return [[None]]
        
                
    def setArretsFils(self,arretsAAjouter):
#        print("à ",self.racine.getIntitule())
#        ex=input(self.racine.getLigne().nom)
        for arret in arretsAAjouter:
            if arret[0] != None:
                nouvelArret = Arret(arret[0],
                                    arret[1],
                                    arret[1].date_go[arret[0]],
                                    arret[1].date_back[arret[0]])
                self.racine.arretSuivants.append(nouvelArret)
                # Récursivité           .
                if arret[1].getArretPrecedent(arret[0]) != self.racine.getIntitule():
                    listeProchainsArrets = [[arret[1].getArretPrecedent(arret[0]),arret[1]]]
                else:
                    listeProchainsArrets = [[arret[1].getArretSuivant(arret[0]),arret[1]]]
                listeProchainsArrets.extend(self.croisementLigne(arret[0],arret[1]))
                print(self.racine.getIntitule(),len(self.racine.arretSuivants))
#                print("-------------------------")
#                print("de ",self.racine.getIntitule())
#                print("de ",self.racine.getLigne().nom)
                Reseau(nouvelArret).setArretsFils(listeProchainsArrets)


        
# =============================================================================
# Lecture des fichiers d'origine
# =============================================================================
# Lecture des fichiers relatifs aux lignes de bus, puis insertion des données
# dans un objet de classe Ligne
listeFichiers = ['data/1_Poisy-ParcDesGlaisins.txt','data/2_Piscine-Patinoire_Campus.txt']
listeLignes = []
for fichier in listeFichiers:
    listeLignes.append(Ligne(fichier,mid(fichier,5,len(fichier)-8)))

#Ajoute un objet de classe Ligne à listeLignes à partir d'un autre fichier .txt   
def ajouter(nomFichier):
    if sibra.lecture(nomFichier) != ['vide']:
        listeFichiers.append(nomFichier)
        listeLignes.append(Ligne(nomFichier,mid(nomFichier,5,len(nomFichier)-8)))
        print("Le fichier a bien été ajouté")
    else:
        print("\nAttention : le fichier demandé n'a pas été trouvé")

# =============================================================================
# Fonctions utilisées dans le programme principal    
# =============================================================================
def erreurSaisie(saisie):
    for ligne in listeLignes:
        if saisie in ligne.path:
            return ligne
    return True
     
# =============================================================================
# Programme principal      
# =============================================================================
    
#depart = "vernod"
#arrivee = "ponchy"
#ligneDepart = erreurSaisie(depart)
##mode = input("Comment voulez-vous circuler ? ")
    
# =============================================================================
# Demande de saisie du lieu de départ
# =============================================================================
#"\033[4mChaîneDeCaractères\033[0m" renvoie ChaîneDeCaractères souligné dans la console
print("\n\033[4mListe des fichiers déjà pris en compte:\033[0m")
# Affichage des fichiers déjà pris en compte
for fichier in listeFichiers:
    print(fichier)
print("\nCi-dessus la liste des fichiers qui seront traités pour calculer votre itinéraire. Si vous voulez en ajouter un autre, " + \
    "entrez tout de suite le nom du fichier (.txt) à ajouter dans la console. Si la liste des fichiers pris en compte " + \
    "vous suffisent, entrez le nom du départ souhaité.\n\n\033[4mVoici la liste des arrêts disponibles (ne pas mettre d'accents):\033[0m")

affArrets = []
for liste in listeLignes:
    for arret in liste.path:
        affArrets.append(arret)
print(list(set(affArrets)))

depart = input("Entrez le nom de l'arrêt de bus de départ ou du fichier .txt à ajouter : ").lower()

while right(depart,4) == ".txt":
    print("\nAjout en cours ...\n")
    sleep(1)
    ajouter(depart)
    depart = input("Entrez le nom de l'arrêt de bus de départ ou du fichier .txt à ajouter : ").lower()
    

while erreurSaisie(depart) == True:
    depart = input("Le lieu de départ est introuvable. Réessayez : ").lower()
ligneDepart = erreurSaisie(depart)

# =============================================================================
# Demande de saisie du lieu d'arrivée
# =============================================================================
arrivee = input("Veuillez choisir l'arrêt d'arrivée souhaité : ")
while erreurSaisie(arrivee) == True:
    arrivee = input("Le lieu d'arrivee est introuvable. Réessayez : ").lower()

# =============================================================================
# Création de l'arborescence
# =============================================================================
# Liste initialement vide utilisée dans la fonction croisementLigne() de la classe Reseau
intersections = []
# Création du noeud racine de l'arborescence
voyage = Reseau(Arret(depart,
                      ligneDepart,
                      ligneDepart.date_go[depart],
                      ligneDepart.date_back[depart]))
#Ajout de tous les autres arrêts de l'arborescence par la fonction récursive setArretsFils()
voyage.setArretsFils([
        [ligneDepart.getArretPrecedent(depart),ligneDepart],
        [ligneDepart.getArretSuivant(depart),ligneDepart]])
           
## =============================================================================
## Fin du programme principal    
## =============================================================================
#
#
print("\n\n\n ************** Affichages de tests *******************\n")


