# -*- coding: utf-8 -*-
"""
Created on Thu Mar  5 13:56:51 2020

@author: simon
"""
from math import log
import csv

class Noeud:
    def __init__(self,nom,fils = []):
        self.nom = nom
        self.fils = fils
        
                                          
    def getNom(self):
        return self.nom
    
    def getFils(self):
        return self.fils
    
     
class Arbre:
    def __init__(self,racine):
        self.racine = racine
        
    def setFils(self,nom):
        for valeur in dicAttributs[nom]:
            #meilleurGain()
            # Récursivité
            self.racine.fils.append(Noeud(nom = valeur))
    
    def setSousEnsemble(self):
        rangSelf = self.donnees[0].index(self.racine.getNom())
        res = []
        for objet in self.donnees:
            if objet[rangSelf] == self.valSousEnsemble:
                resTemp = []
                for attribut in objet:
                    if self.donnees[0].index(attribut) != rangSelf:
                        resTemp.append(attribut)
                res.append(resTemp)
        return res


    
def lecture(nomFichier):
    res = []
    with open(nomFichier, newline='') as csvfile:
        spamreader = csv.reader(csvfile, delimiter=',')
        for row in spamreader:
            res.append(row)
        return res

def listeClasses(donnees):
    res = []
    for objet in donnees:
        if objet[-1] not in res:
            res.append(objet[-1])
    del res[0] #Suppression de la 1ere ligne (intitulé de la colonne)
    return res

def dicAttributs(donnees):
    dic = {}
    for i in range(len(donnees[0])-1):
        dic[donnees[0][i]] = [donnees[1][i]]
        for j in range(1,len(donnees)):
            if donnees[j][i] not in dic[donnees[0][i]] and donnees[j][i] != "?":
                dic[donnees[0][i]].append(donnees[j][i])
    return dic
    
#data = lecture('Data/soybean-app.csv')
data = lecture('Data/golf.csv')
listeClasses = listeClasses(data)
dicAttributs = dicAttributs(data)
  

def meilleurGain(donnees):
    meilleurVal = 0
    total = gainTotal(donnees)
    for attribut in dicAttributs.keys():
        gainTemp = total-E(repartition(donnees,attribut))
        if gainTemp>meilleurVal:
            meilleurVal = gainTemp
            meilleurAttr = attribut 
    return meilleurAttr
                        
def repartition(donnees,attribut):
    rangAttribut = donnees[0].index(attribut)
    res = []
    for valeur in dicAttributs[attribut]:
        valGain = []
        nbTotal = 0
        for classe in listeClasses:
            nbValeurs = 0
            for objet in donnees:
                if objet[rangAttribut] == valeur and objet[-1] == classe:
                    nbValeurs += 1
                    nbTotal += 1
            valGain.append(nbValeurs)   
        res.append(I(valGain))
        res.append(nbTotal)
    return res

def gainTotal(donnees):
    res = []
    for classe in listeClasses:
        nbValeurs = 0
        for objet in donnees:
            if objet[-1] == classe:
                nbValeurs += 1
        res.append(nbValeurs)
    return I(res)

        
                
def I(listeValeurs):
    somme = sum(listeValeurs)
    res = 0
    for valeur in listeValeurs:
        if valeur != 0 :
            res += -(valeur/somme)*log(valeur/somme,2)
    return res

def E(valeurs):
    res = 0
    i=0
    while i != len(valeurs):
        res += valeurs[i]*(valeurs[i+1]/(len(data)-1))
        i += 2
    return res  

meilleurGain(data)
#noeudRacine = Arbre(Noeud(nom = meilleurGain(data)))
#noeudRacine = Arbre(Noeud(nom = meilleurGain(data)))
#noeudRacine.racine.nom = calcul.meilleurGain(data)
























