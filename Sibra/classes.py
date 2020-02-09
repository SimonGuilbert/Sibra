# -*- coding: utf-8 -*-
"""
Created on Thu Jan 23 10:32:41 2020

@author: guilbers
"""

from datetime import date,datetime,timedelta
from time import sleep
import sibra


# =============================================================================
# Fonctions trouvées sur internet pour simplifier le code
# =============================================================================
# Ces 3 fonctions renvoient respectivement la gauche, la droite ou le milieu d'une mot ou nombre
def left(s, amount):
    return s[:amount]

def right(s, amount):
    return s[-amount:]

def mid(s, offset, amount):
    return s[offset:offset+amount]

# =============================================================================
# Choix de la date
# =============================================================================
print("   _____ _ _                 ____  _\n  / ____(_) |               |  _ \(_)\n | (___  _| |__  _ __ __ _  | |_) |_  ___ _ ____   _____ _ __  _   _  ___\n  \___ \| | '_ \| '__/ _` | |  _ <| |/ _ \ '_ \ \ / / _ \ '_ \| | | |/ _ \ \n  ____) | | |_) | | | (_| | | |_) | |  __/ | | \ V /  __/ | | | |_| |  __/\n |_____/|_|_.__/|_|  \__,_| |____/|_|\___|_| |_|\_/ \___|_| |_|\__,_|\___|\n")                                                                          
print("\n→ Indiquez la date de départ au format jj/mm/aaaa") 
print("\n→ Ou bien entrez le mot 'today' pour voyager aujourd'hui")
         
choixDate = input("Quand souhaitez-vous voyager ? : ")

# Vérification de la bonne saisie de l'utilisateur
testDate = False
while choixDate != "today" and not testDate:
    try:
       if int(right(choixDate,4))>2000 and int(right(choixDate,4))<2050 and \
       int(mid(choixDate,3,2))<=12 and int(left(choixDate,2))<=31 and \
       choixDate[2] == "/" and choixDate[5] == "/" and len(choixDate)==10:
               testDate = True
       else:
           choixDate = input("Saisie incorrecte. Veuillez réessayer : ")   
    except:
        choixDate = input("Saisie incorrecte. Veuillez réessayer : ")    
        
# La variable choixDate prend la valeur d'aujourd'hui au bon format (jj/mm/aaaa)
if choixDate == "today":        
    choixDate = datetime.strptime(str(date.today()),"%Y-%m-%d").strftime("%d/%m/%Y")
# Conversion de la date saisie en datetime
convDate = datetime(
        year = int(right(choixDate,4)),
        month = int(mid(choixDate,3,2)),
        day = int(left(choixDate,2)))
   
#Jour de la semaine de la date choisie (0=lundi, 1=mardi, ..., 6=dimanche)
jour = convDate.weekday()

jour = ("weVacancesFerie" if jour>4 or convDate in sibra.getJoursFeriesVacances() else "semaine")

#jour = "semaine"

# =============================================================================
# Horaires
# =============================================================================
print("  _    _                 _\n | |  | |               (_)\n | |__| | ___  _ __ __ _ _ _ __ ___\n |  __  |/ _ \| '__/ _` | | '__/ _ \ \n | |  | | (_) | | | (_| | | | |  __/\n |_|  |_|\___/|_|  \__,_|_|_|  \___|\n")
          
rappelJour = ("jour normal de semaine" if jour == "semaine" else "dimanche, jour férié ou pendant les vacances scolaires (zone A)")
rappelDate = ("le "+choixDate if choixDate != "today" else "aujourdh'hui\n\n")
print("\n→ Vous avez choisi de voyager",rappelDate,": c'est un",rappelJour)
print("\n→ Saisissez l'heure de départ souhaitée au format hh:mm")
print("\n→ Si vous voulez partir maintenant, écrivez now")
choixHoraire = input("A quelle heure voulez-vous partir ?")

# Vérification de la bonne saisie de l'utilisateur
testHoraire = False
while choixHoraire != "now" and not testHoraire:
    try:
       if int(left(choixHoraire,2))>=0 and int(left(choixHoraire,2))<=23 and \
       int(right(choixHoraire,2))>=0 and int(right(choixHoraire,2))<=59 and \
       choixHoraire[2] == ":" and len(choixHoraire) == 5:
               testHoraire = True
       else:
           choixHoraire = input("Saisie incorrecte. Veuillez réessayer : ")   
    except:
        choixHoraire = input("Saisie incorrecte. Vérifiez la syntaxe de 08:45 et non pas 8:45. Veuillez réessayer : ")    
   
if choixHoraire == "now":
    choixHoraire = left(str(datetime.now().time()),5)
      
## Conversion de la date saisie en datetime
horaireDepart = timedelta(
        hours = int(left(choixHoraire,2)),
        minutes = int(right(choixHoraire,2)))


# =============================================================================
# Shortest, Fastest ou Foremost
# =============================================================================
print("  __  __           _ \n |  \/  |         | |\n | \  / | ___   __| | ___\n | |\/| |/ _ \ / _` |/ _ \ \n | |  | | (_) | (_| |  __/\n |_|  |_|\___/ \__,_|\___|\n")
rappelHoraire = ("à "+choixHoraire if choixHoraire != "now" else "maintenant\n\n")
print("\n→ Vous avez choisi de partir",rappelDate,rappelHoraire)
print("\nComment voulez-vous voyager ?")
print(" ♦ Shortest : le plus court en nombre d'arrêts")
print(" ♦ Fastest : le plus rapide mais avec potentiellement plus d'arrêts")
print(" ♦ Foremost : arrive au plus tôt peu importe le nombre d'arrêts")
modeTransport = input("Écrivez shortest,fastest ou foremost :").lower()

# Vérification de la bonne saisie de l'utilisateur
while modeTransport not in ("shortest","fastest","foremost"):
        modeTransport = input("Saisie incorrecte. Veuillez réessayer : ").lower()
    
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
    
# Un objet de classe Arret n'a initialement pas de fils.
# Ils seront créés avec la fonction setArretsFils() de la classe Reseau             
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
    
    def getHorairesGo(self):
        return self.horairesGo
    
    def getHorairesBack(self):
        return self.horairesBack
    
    def getLigne(self):
        return self.ligne


class Reseau :
    def __init__(self,racine):
        self.racine = racine    
         
    def croisementLigne(self,arret,ligne):
    # Pour savoir si un arret a des arrets suivants sur d'autres lignes
        if arret not in intersections:
            res = []
            for l in listeLignes:
                if l != ligne and l != voyage.racine.getLigne():
                    if arret in l.path:
                        res.append([l.getArretPrecedent(arret),l])
                        res.append([l.getArretSuivant(arret),l])
                        intersections.append(arret)
            return ([[None]] if not res else res)
        return [[None]]
              
    def setArretsFils(self,arretsAAjouter):
    # Création des fils de l'arborescence
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
                Reseau(nouvelArret).setArretsFils(listeProchainsArrets)
                
    def estTerminus(self):
        # Un arret est terminus s'il n'a pas de fils
        return not self.racine.arretSuivants
    
    def sensCirculation(self,listeArr):
        if self.racine.getLigne().getArretPrecedent(self.racine.getIntitule()) == None:
            return "Go"
        elif self.racine.getLigne().getArretSuivant(self.racine.getIntitule()) == None:
            return "Back"
        else:
            if self.racine.getLigne().getArretSuivant(self.racine.getIntitule()) in [listeArr[i].getIntitule() for i in range(len(listeArr)-1)]:
                return "Go"
            return "Back"
    
    def remontee(self,stop,l,sens):
        #Récupération de l'indice du noeud courant dans sa ligne de bus
        i = self.racine.getLigne().path.index(self.racine.getIntitule())
        # La boucle while sert à 'reculer' dans l'arbre jusqu'à une intersection
        while self.racine.getLigne().path[i] not in stop or self.racine.getLigne().path[i] == self.racine.getIntitule():   
            i += (1 if sens == "Go" else -1)
            del l[-1] 
              
    def chemins(self,derniereIntersection,listeArrets = [], listeTemp = []):
        # Reparcourt l'arborescence et liste tous les chemins existants entre le noeud racine et les terminus
        listeArrets.append(self.racine)
        if not self.estTerminus():
            for fils in self.racine.arretSuivants:
                Reseau(fils).chemins((self.racine.getIntitule() if len(self.racine.arretSuivants)>1 else derniereIntersection),listeArrets,listeTemp)
            if self.racine.getIntitule() in intersections and len(self.racine.arretSuivants)>1:
                if self.racine.getIntitule() != depart:
                    self.remontee(intersections,listeArrets,self.sensCirculation(listeArrets))
        else:
            listeTemp.extend(listeArrets)
            self.remontee(derniereIntersection,listeArrets,self.sensCirculation(listeArrets))
        return listeTemp
    
           
# =============================================================================
# Lecture des fichiers d'origine
# =============================================================================      
# Lecture des fichiers relatifs aux lignes de bus, puis insertion des données
# dans un objet de classe Ligne
listeFichiers = ['data/1_Poisy-ParcDesGlaisins.txt','data/2_Piscine-Patinoire_Campus.txt']
listeLignes = []
for fichier in listeFichiers:
    listeLignes.append(Ligne(fichier,mid(fichier,5,len(fichier)-9)))
      
def ajouter(nomFichier):
    # Ajoute un objet de classe Ligne à listeLignes à partir d'un autre fichier .txt
    if nomFichier in listeFichiers:
        print("Attention : le fichier demandé est déjà pris en compte dans le calcul de l'itinéraire")
    elif sibra.lecture(nomFichier) != ['vide']:
        listeFichiers.append(nomFichier)
        listeLignes.append(Ligne(nomFichier,mid(nomFichier,5,len(nomFichier)-9)))
        for fichier in listeFichiers:
            print(" ♦",fichier)
        print("\nListe des arrêts disponibles (ne pas mettre d'accents):")
        affArrets = []
        for liste in listeLignes:
            for arret in liste.path:
                affArrets.append(arret)
        print(list(set(affArrets)))
    else:
        print("\nAttention : le fichier demandé n'a pas été trouvé")

# =============================================================================
# Fonctions utilisées dans le programme principal    
# =============================================================================
def erreurSaisie(saisie):
    # Vérifie la bonne saisie de l'utilisateur pour les noms d'arrêts (sert aussi à créer ligneDepart)
    for ligne in listeLignes:
        if saisie in ligne.path:
            return ligne
    return True  

def shortest(l):
    # Renvoie le plus court chemin d'une liste de chemins
    plusCourt = l[0]
    for chemin in l:
        if len(chemin)<len(plusCourt):
            plusCourt = chemin
    return plusCourt

def fastest(l):
    # Renvoie le chemin le plus rapide d'une liste de chemins
    plusRapide = [l[0],getHoraireArrivee(l[0])-horaireDepart]
    for chemin in l:
        if getHoraireArrivee(chemin)-horaireDepart<plusRapide[1]:
            plusRapide = [chemin,getHoraireArrivee(chemin)-horaireDepart]
    return plusRapide[0]

def foremost(l):
    # Renvoie le chemin qui fait arriver le plus tôt d'une liste de chemins
    plusTot = [l[0],getHoraireArrivee(l[0])]
    for chemin in l:
        if getHoraireArrivee(chemin)<plusTot[1]:
            plusTot = [chemin,getHoraireArrivee(chemin)]
    return plusTot[0]
       
def getHoraireArrivee(l):
    # Parcourt un chemin passé en paramètre et renvoie l'horaire d'arrivée
    horaire = horairePlusProche(horaireDepart,l[0],sensCirculation(l,l[1]))
    i = 1
    chgmLigne = False
    while i != len(l)-1:
        while (l[i-1].getLigne() == l[i].getLigne() or chgmLigne) and i != len(l)-1:
            chgmLigne = False
            horaire = horairePlusProche(horaire,l[i],sensCirculation(l,l[i]))
            i += 1
        if i != len(l)-1 or l[i-1].getLigne() != l[i].getLigne():
            chgmLigne = (True if i != len(l)-1 else False)
            nouvelleLigne = l[i].getLigne()
            if nouvelleLigne.getArretPrecedent(l[i].getIntitule()) == l[i-1].getIntitule():
                horairesTemp = nouvelleLigne.date_go[l[i-1].getIntitule()]
            else:
                horairesTemp = nouvelleLigne.date_back[l[i-1].getIntitule()]
            for hor in horairesTemp:
                if (int(left(hor,len(hor)-3)) >= int(left(str(horaire),len(str(horaire))-6))) and (int(right(hor,2)) >= int(left(right(str(horaire),5),2))):
                    horaire = timedelta(hours = int(left(hor,len(hor)-3)),minutes = int(right(hor,2)))
                    break
    return horairePlusProche(horaire,l[len(l)-1],sensCirculation(l,l[len(l)-1]))

def sensCirculation(liste,arret):
    # Renvoie "Go" ou "Back" qui ont la même signification que dans les fichiers d'origine
    if arret.getLigne().getArretPrecedent(arret.getIntitule()) == liste[liste.index(arret)-1].getIntitule():
        return "Go"
    return "Back"

def memeArretQueDepart(arret):
    # Deux arrets portant le même nom et situés sur la même ligne de bus sont identiques
    return arret.getIntitule() == depart and arret.getLigne() == ligneDepart
            
def getCheminsPossibles(liste,listeFinale = []):
    # Renvoie une liste de listes de chemins commençant tous par le départ et finissant tous par l'arrivée choisis par l'utilisateur
    for arret in liste:
        if arret.getIntitule() == arrivee:
            res = []
            i = liste.index(arret)
            while not memeArretQueDepart(liste[i]):
                i -= 1
            for j in range(i,liste.index(arret)+1):
                res.append(liste[j])
            listeFinale.append(res)
    return listeFinale
    
def horairePlusProche(horaire,arret,goOrBack):
    # Renvoie le premier horaire supérieur à un horaire donné en paramètre pour un arrêt donné aussi en paramètre
    for h in (arret.getHorairesGo() if goOrBack == "Go" else arret.getHorairesBack()):
        if h>=horaire:
            return h
    return "erreur"
       
def nomsArrets(liste):
    # Renvoie les intitulés des arrêts d'une liste d'objets Arret
    res = []
    for arret in liste:
        res.append(arret.getIntitule())
    return res
                   
def detailsItineraire(l):
    # Même principe que getHoraireArrivee() mais en affichant des résultats intermédiaires dans la console
    horaire = horairePlusProche(horaireDepart,l[0],sensCirculation(l,l[1]))
    print("→ Départ de",depart,"à",left(str(horaire),len(str(horaire))-3),"avec le bus n°",ligneDepart.nom[0],
          "en direction de",(ligneDepart.path[0] if ligneDepart.getArretPrecedent(depart) in nomsArrets(l) else ligneDepart.path[-1]))
    i = 1
    chgmLigne = False
    while i != len(l)-1:
        while (l[i-1].getLigne() == l[i].getLigne() or chgmLigne) and i != len(l)-1:
            chgmLigne = False
            horaire = horairePlusProche(horaire,l[i],sensCirculation(l,l[i]))
            print("  ♦",l[i].getIntitule(),"►",left(str(horaire),len(str(horaire))-3))
            i += 1
        if i != len(l)-1 or l[i-1].getLigne() != l[i].getLigne():
            chgmLigne = (True if i != len(l)-1 else False)
            nouvelleLigne = l[i].getLigne()
            if nouvelleLigne.getArretPrecedent(l[i].getIntitule()) == l[i-1].getIntitule():
                horairesTemp = nouvelleLigne.date_go[l[i-1].getIntitule()]
            else:
                horairesTemp = nouvelleLigne.date_back[l[i-1].getIntitule()]
            for hor in horairesTemp:
                if (int(left(hor,len(hor)-3)) >= int(left(str(horaire),len(str(horaire))-6))) and (int(right(hor,2)) >= int(left(right(str(horaire),5),2))):
                    horairePrec = horaire
                    horaire = timedelta(hours = int(left(hor,len(hor)-3)),minutes = int(right(hor,2)))
                    print("→ Descendez à l'arrêt",l[i-1].getIntitule(),"et attendez",(horaire-horairePrec).seconds//60,"minutes")
                    print("→ Prenez le bus n°",left(l[i].getLigne().nom,1),"en direction de",(l[i].getLigne().path[0] if l[i].getLigne().getArretSuivant(l[i].getIntitule()) in nomsArrets(l) else l[i].getLigne().path[-1]),"à",hor)
                    break
    dernierArret = str(horairePlusProche(horaire,l[len(l)-1],sensCirculation(l,l[len(l)-1])))
    print("→ Arrivée à",arrivee,"à",left(dernierArret,len(dernierArret)-3))

# =============================================================================
# Programme principal      
# =============================================================================
    

# =============================================================================
# Accueil - Date du voyage
# =============================================================================

# =============================================================================
# Demande de saisie du lieu de départ ou ajout d'un fichier
# =============================================================================
print("  _____                        _\n |  __ \                      | |\n | |  | | ___ _ __   __ _ _ __| |_\n | |  | |/ _ \ '_ \ / _` | '__| __|\n | |__| |  __/ |_) | (_| | |  | |_\n |_____/ \___| .__/ \__,_|_|   \__|\n             | |\n             |_|\n")
print("Vous avez choisi de voyager en :")
print(" ♦",rappelJour)
print(" ♦",rappelHoraire)
print(" ♦ mode",modeTransport)  
print("\nListe des fichiers déjà pris en compte pour calculer l'itinéraire:")
# Affichage des fichiers déjà pris en compte
for fichier in listeFichiers:
    print(" ♦",fichier)
print("\n→ Si vous voulez ajouter un nouveau fichier (.txt), écrivez son nom dans la console")
print("\n→ Sinon entrez le nom de l'arrêt de bus de départ souhaité")
print("\nListe des arrêts disponibles (ne pas mettre d'accents):")

affArrets = []
for liste in listeLignes:
    for arret in liste.path:
        affArrets.append(arret)
print(list(set(affArrets)))

depart = input("Entrez le nom de l'arrêt de bus de départ ou du fichier .txt à ajouter : ")

while right(depart,4) == ".txt":
    print("\nAjout en cours ...\n")
    sleep(1)
    ajouter(depart)
    depart = input("Entrez le nom de l'arrêt de bus de départ ou du fichier .txt à ajouter : ")
    
depart = depart.lower() 
  
while erreurSaisie(depart) == True:
    depart = input("Le lieu de départ est introuvable. Réessayez : ").lower()
ligneDepart = erreurSaisie(depart)

## =============================================================================
## Demande de saisie du lieu d'arrivée
## =============================================================================
print("                     _\n     /\             (_)\n    /  \   _ __ _ __ ___   _____  ___\n   / /\ \ | '__| '__| \ \ / / _ \/ _ \ \n  / ____ \| |  | |  | |\ V /  __/  __/\n /_/    \_\_|  |_|  |_| \_/ \___|\___|\n")
arrivee = input("Veuillez choisir l'arrêt d'arrivée souhaité : ").lower()
while erreurSaisie(arrivee) == True:
    arrivee = input("Le lieu d'arrivée est introuvable. Réessayez : ").lower()

# =============================================================================
# Création de l'arborescence
# =============================================================================

# Liste initialement constitué du nom de l'arrêt de départ utilisée dans la fonction croisementLigne() de la classe Reseau
intersections = [depart]

# Création du noeud racine de l'arborescence
voyage = Reseau(Arret(depart,
                      ligneDepart,
                      ligneDepart.date_go[depart],
                      ligneDepart.date_back[depart]))

#Ajout de tous les autres arrêts de l'arborescence par la fonction récursive setArretsFils()
voyage.setArretsFils([
        [ligneDepart.getArretPrecedent(depart),ligneDepart],
        [ligneDepart.getArretSuivant(depart),ligneDepart]])
    
# =============================================================================
# Chemins possibles
# =============================================================================

# On sélectionne les chemins contenant le départ et l'arrivée parmi tous les chemins possibles de l'arborescence
listeCheminsPossibles = getCheminsPossibles(voyage.chemins(depart))

print("  _____ _   _                      _ \n |_   _| | (_)                    (_) \n   | | | |_ _ _ __   ___ _ __ __ _ _ _ __ ___\n   | | | __| | '_ \ / _ \ '__/ _` | | '__/ _ \ \n  _| |_| |_| | | | |  __/ | | (_| | | | |  __/\n |_____|\__|_|_| |_|\___|_|  \__,_|_|_|  \___|\n")
if horairePlusProche(horaireDepart,listeCheminsPossibles[0][0],("Go" if ligneDepart.getArretSuivant(depart) in nomsArrets(listeCheminsPossibles[0]) else "Back")) != "erreur":
# =============================================================================
# Shortest
# =============================================================================    
    if modeTransport == "shortest":   
        detailsItineraire(shortest(listeCheminsPossibles))  
# =============================================================================
# Fastest    
# =============================================================================
    elif modeTransport == "fastest":
        detailsItineraire(fastest(listeCheminsPossibles))  
# =============================================================================
# Foremost    
# =============================================================================
    elif modeTransport == "foremost":
        detailsItineraire(foremost(listeCheminsPossibles)) 
else:
    print("Il n'y a plus de bus disponible après "+left(str(horaireDepart),5)+". Réessayez demain")

## =============================================================================
## Fin du programme principal    
## =============================================================================
      
