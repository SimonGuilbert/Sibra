# -*- coding: utf-8 -*-
"""
Created on Thu Jan 23 08:23:04 2020

@author: guilbers
"""


data_file_name = 'data/1_Poisy-ParcDesGlaisins.txt'
#data_file_name = 'data/2_Piscine-Patinoire_Campus.txt'

try:
    
    with open(data_file_name, 'r') as f:
        content = f.read().replace("Ã‰","E")
        content = content.replace("Ãˆ","E")
except OSError:
    # 'File not found' error message.
    print("File not found")

def dates2dic(dates):
    dic = {}
    splitted_dates = dates.split("\n")
#    print(splitted_dates)
    for stop_dates in splitted_dates:
        tmp = stop_dates.split(" ")
        dic[tmp[0]] = tmp[1:]
    return dic

slited_content = content.split("\n\n")
regular_path = slited_content[0]
regular_date_go = dates2dic(slited_content[1])
regular_date_back = dates2dic(slited_content[2])
we_holidays_path = slited_content[3]
we_holidays_date_go = dates2dic(slited_content[4])
we_holidays_date_back = dates2dic(slited_content[5])

# On considère que le + dans la première ligne du fichier txt est une erreur 
# et doit être remplacé par un N. On transforme ensuite la chaîne de caractères
# en liste avec split
regular_path = regular_path.replace("+","N").split(" N ")
we_holidays_path = we_holidays_path.replace("+","N").split(" N ")




    
