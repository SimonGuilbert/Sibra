# -*- coding: utf-8 -*-
"""
Created on Thu Jan 23 08:23:04 2020

@author: guilbers
"""
import csv
from datetime import datetime

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
# Jours fériés
# =============================================================================
joursFeriesVacances = []
with open('data/jours-feries-seuls.csv', newline='') as csvfile:
    csvfile.readline()
    spamreader = csv.reader(csvfile, delimiter=',', quotechar='|')
    for row in spamreader:
        joursFeriesVacances.append(datetime(
                year = int(left(row[0],4)),
                month = int(mid(row[0],5,2)),
                day = int(right(row[0],2)))) 
        
# =============================================================================
# Vacances scolaire 2020
# =============================================================================
with open('data/vacancesScolaires2020.csv', newline='') as csvfile:
    csvfile.readline()
    spamreader = csv.reader(csvfile, delimiter=';', quotechar='|')
    for row in spamreader:
        joursFeriesVacances.append(datetime(
                year = int(left(row[1],4)),
                month = int(mid(row[1],5,2)),
                day = int(right(row[1],2))))
        
def getJoursFeriesVacances():
    return joursFeriesVacances
        
# =============================================================================
# Lecture des fichiers relatifs aux lignes de bus
# =============================================================================
def lecture(data_file_name):
    try:
        with open(data_file_name, 'r') as f:
            content = f.read().replace("Ã‰","E")
            content = content.replace("Ãˆ","E")
            content = content.replace("Ã©","E")
            content = content.replace("Ã¢","A")
    except OSError:
        # 'File not found' error message.
        print("File not found")
        content = 'Vide'
    return content.lower().split("\n\n")

def dates2dic(dates):
    dic = {}
    splitted_dates = dates.split("\n")
#    print(splitted_dates)
    for stop_dates in splitted_dates:
        tmp = stop_dates.split(" ")
        dic[tmp[0]] = tmp[1:]
    return dic
