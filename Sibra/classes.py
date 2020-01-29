# -*- coding: utf-8 -*-
"""
Created on Thu Jan 23 10:32:41 2020

@author: guilbers
"""

from datetime import timedelta,date
import sibra

# =============================================================================
# Fonctions trouvées sur internet pour simplifier le code
# =============================================================================
def left(s, amount):
    return s[:amount]

def right(s, amount):
    return s[-amount:]

#Jour de la semaine (0=lundi, 1=mardi, ..., 6=dimanche)
jour = date.today().weekday()

#def mid(s, offset, amount):
#    return s[offset:offset+amount]

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
        
#
#class Trajet:
#    def __init__(self,duree,arretDepart,arretArrivee):
#        self.duree
#        self.arretDepart = arretDepart
#        self.arretArrivee = arretArrivee
#        
#    def getDuree(self):
#        return self.duree
#    
#    def getArretDepart(self):
#        return self.arretDepart
#    
#    def getArretArrivee(self):
#        return self.arretArrivee
#        


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
        return Reseau(self.racine.arretSuivantMemeLigne).setArretsFils(regular_path[compteur],compteur = compteur,sens = sens)
            

        
# =============================================================================
# Programme principal      
# =============================================================================
#depart = input("Veuillez chosir l'arrêt de votre départ : ")
#arrivee = input("Veuillez choisir l'arrêt d'arrivée souhaité : ")
depart = "GARE"
arrivee = "VIGNIERES"
#mode = input("Comment voulez-vous circuler ? ")

slited_content = sibra.lecture('data/1_Poisy-ParcDesGlaisins.txt')
regular_path = slited_content[0]
regular_date_go = sibra.dates2dic(slited_content[1])
regular_date_back = sibra.dates2dic(slited_content[2])
we_holidays_path = slited_content[3]
we_holidays_date_go = sibra.dates2dic(slited_content[4])
we_holidays_date_back = sibra.dates2dic(slited_content[5])
# On considère que le + dans la première ligne du fichier txt est une erreur 
# et doit être remplacé par un N. On transforme ensuite la chaîne de caractères
# en liste avec split
regular_path = regular_path.replace("+","N").split(" N ")
we_holidays_path = we_holidays_path.replace("+","N").split(" N ")

if depart in regular_path:
    voyage = Reseau(Arret(depart,
                          regular_date_go[depart],
                          regular_date_back[depart],
                          we_holidays_date_go[depart],
                          we_holidays_date_back[depart]))
    if arrivee in regular_path:
        if regular_path.index(depart) < regular_path.index(arrivee):
            voyage.setArretsFils(regular_path[regular_path.index(depart)+1],regular_path.index(depart)+1,"Go")
        else:
            voyage.setArretsFils(regular_path[regular_path.index(depart)-1],regular_path.index(depart)-1,"Back")
            
             
# =============================================================================
# Fin du programme principal    
# =============================================================================

data_file_name = 'data/1_Poisy-ParcDesGlaisins.txt'
#data_file_name = 'data/2_Piscine-Patinoire_Campus.txt'

#print(listeArrets[4].liaisons)

                
   
