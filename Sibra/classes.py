# -*- coding: utf-8 -*-
"""
Created on Thu Jan 23 10:32:41 2020

@author: guilbers
"""

from datetime import timedelta
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

class Arret:
    def __init__(self, intitule, horairesSemaineGo, horairesSemaineBack, horairesWEGo, horairesWEBack, liaisons):
        self.intitule = intitule
        self.horairesSemaineGo = self.setHoraires(horairesSemaineGo)
        self.horairesSemaineBack = self.setHoraires(horairesSemaineBack)
        self.horairesWEGo = self.setHoraires(horairesWEGo)
        self.horairesWEBack = self.setHoraires(horairesWEBack)
        self.liaisons = liaisons
        for liaison in sibra.regular_path :
            if liaison != self.intitule :
                self.liaisons.append(liaison)
#                self.liaisons.append(Trajet())
    
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
        
#
#class Trajet:
#    def __init__(self,duree,arretDepart,arretArrivee):
#        self.duree
#        
#        
#        
#
#class Reseau :
#    def __init__(self,racine):
#        self.racine = racine
#        
        
listeArrets = []
for arret in sibra.regular_path :  
    listeArrets.append(Arret(arret,
             sibra.regular_date_go[arret],
             sibra.regular_date_back[arret],
             sibra.we_holidays_date_go[arret],
             sibra.we_holidays_date_back[arret],
             []))
    

print(listeArrets[2].getHorairesSemaineBack()[4]-listeArrets[2].getHorairesSemaineBack()[2])

#print(listeArrets[4].liaisons)
                
   