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
# Classes
# =============================================================================

# On considère que le + dans la première ligne du fichier txt est une erreur 
# et doit être remplacé par un N. On transforme ensuite la chaîne de caractères
# en liste avec split
class Ligne:
    def __init__(self,nomFichier,nomLigne):
        self.nom = nomLigne
        self.slited_content = sibra.lecture(nomFichier)
        self.regular_path = self.slited_content[0].replace("+","N").split(" N ")
        self.regular_date_go = sibra.dates2dic(self.slited_content[1])
        self.regular_date_back = sibra.dates2dic(self.slited_content[2])
        self.we_holidays_path = self.slited_content[3].replace("+","N").split(" N ")
        self.we_holidays_date_go = sibra.dates2dic(self.slited_content[4])
        self.we_holidays_date_back = sibra.dates2dic(self.slited_content[5])
        
class Arret:
    def __init__(self, intitule, horairesSemaineGo, horairesSemaineBack, horairesWEGo, horairesWEBack, arretSuivantMemeLigne = None, arretSuivantAutreLigne = None):
        self.intitule = intitule
        self.horairesSemaineGo = self.setHoraires(horairesSemaineGo)
        self.horairesSemaineBack = self.setHoraires(horairesSemaineBack)
        self.horairesWEGo = self.setHoraires(horairesWEGo)
        self.horairesWEBack = self.setHoraires(horairesWEBack)
        self.arretSuivantMemeLigne = arretSuivantMemeLigne
        self.arretSuivantAutreLigne = arretSuivantAutreLigne
        

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
    
    def getarretSuivantMemeLigne(self):
        return self.arretSuivantMemeLigne


class Reseau :
    def __init__(self,racine):
        self.racine = racine
        
    def setArretsFils(self,nomArretAAjouter,compteur,sens):
        compteur += (1 if sens == "Go" else -1)
        self.racine.arretSuivantMemeLigne = Arret(nomArretAAjouter,
                                        regular_date_go[nomArretAAjouter],
                                        regular_date_back[nomArretAAjouter],
                                        we_holidays_date_go[nomArretAAjouter],
                                        we_holidays_date_back[nomArretAAjouter])
        print("actuel : ",self.racine.getIntitule())
        print("suivant : ",self.racine.arretSuivantMemeLigne.getIntitule())
        print("----")
        if nomArretAAjouter == arrivee:
            return "ok"
        return Reseau(self.racine.arretSuivantMemeLigne).setArretsFils(semaineOuWE[compteur],compteur = compteur,sens = sens)
            
# =============================================================================
# Lecture des fichiers d'origine
# =============================================================================
# Lecture des fichiers relatifs aux lignes de bus, puis insertion des données
# dans un objet de classe Ligne
listeFichiers = ['data/1_Poisy-ParcDesGlaisins.txt','data/2_Piscine-Patinoire_Campus.txt']
listeLignes = []
for fichier in listeFichiers:
    listeLignes.append(Ligne(fichier,mid(fichier,5,len(fichier)-8)))
    
def ajout(nomFichier):
    if sibra.lecture(nomFichier) != 'Vide':
        listeFichiers.append(nomFichier)
        print("Le fichier a bien été ajouté")
    else:
        print("Attention : le fichier demandé n'a pas été trouvé")

# =============================================================================
# Fonctions utilisées dans le programme principal    
# =============================================================================
def erreurSaisie(saisie):
    for ligne in listeLignes:
        if saisie in semaineOuWE:
            return [False,ligne]
#    sleep(2)
    return True

        
# =============================================================================
# Programme principal      
# =============================================================================
#Jour de la semaine d'aujoud'hui (0=lundi, 1=mardi, ..., 6=dimanche)
jour = date.today().weekday()
semaineOuWE = ("ligne.regular_path" if jour in (0,1,2,3,4) else "ligne.we_holidays_path" )

messageDeDepart = "Voici la liste des fichiers qui seront traités pour calculer votre itinéraire. Si vous voulez en ajouter un autre, " + \
    "entrez tout de suite la commande ajouter('nomDuFichier') dans la console. Si la liste des fichiers pris en compte " + \
    "vous suffisent, entrez le nom de départ souhaité"
depart = input(messageDeDepart + ', '.join([fichier for fichier in listeFichiers]))
while left(depart,5) == "ajout":
    depart = input(messageDeDepart + ', '.join([fichier for fichier in listeFichiers]))
 
while erreurSaisie(depart)[0] == True:
    depart = (input("Le lieu de départ est introuvable. Réessayez.") if left(depart,5)!="ajout"
              else input("Veuillez maintenant sélectionner votre lieu de départ")) 
#    sleep(2)
ligneDepart = erreurSaisie(depart)[1]

arrivee = input("Veuillez choisir l'arrêt d'arrivée souhaité : ")
while erreurSaisie(arrivee)[0] == True :
    arrivee = input("Le lieu d'arrivee est introuvable. Réessayez.")  
#    sleep(2)
ligneArrivee = erreurSaisie(arrivee)[1]


#depart = "GARE"
#arrivee = "VIGNIERES"
##mode = input("Comment voulez-vous circuler ? ")
#
#
#
#for ligne in listeLignes:
#    if (depart and arrivee) in ligne.semaineOuWE:
#        ligneDepart = ligne
##        ligneArrivee = ligne
#    elif depart in ligne.semaineOuWE:
#        
#        
#
#
#if depart in regular_path:
#    voyage = Reseau(Arret(depart,
#                          regular_date_go[depart],
#                          regular_date_back[depart],
#                          we_holidays_date_go[depart],
#                          we_holidays_date_back[depart]))
#    if arrivee in regular_path:
#        if regular_path.index(depart) < regular_path.index(arrivee):
#            voyage.setArretsFils(regular_path[regular_path.index(depart)+1],regular_path.index(depart)+1,"Go")
#        else:
#            voyage.setArretsFils(regular_path[regular_path.index(depart)-1],regular_path.index(depart)-1,"Back")
#            
#             
## =============================================================================
## Fin du programme principal    
## =============================================================================
#
#
#print("\n\n\n ************** Affichages de tests *******************\n")
#print(regular_path)

                
   
