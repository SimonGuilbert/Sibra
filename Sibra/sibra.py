# -*- coding: utf-8 -*-
"""
Created on Thu Jan 23 08:23:04 2020

@author: guilbers
"""

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
