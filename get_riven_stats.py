import urllib.request, json 
import ast
import requests
import lzma
from lzma import FORMAT_AUTO, LZMAError, LZMADecompressor
import re

user_agent = 'Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.0.7) Gecko/2009021910 Firefox/3.0.7'
url = "https://10o.io/rivens/rivencalc.json"
headers={'User-Agent':user_agent,} 
request=urllib.request.Request(url,None,headers) #The assembled request
response = urllib.request.urlopen(request)
data = response.read().decode()
data = json.loads(data)

rifle = 'Rifle'
shotgun = 'Shotgun'
pistol = 'Pistol'
archgun = 'Archgun'
melee = 'Melee'

weapon_list = data["buffs"]
data = {}
data[rifle] = {}
data[pistol] = {}
data[melee] = {}
data[shotgun] = {}
data[archgun] = {}

data[rifle]['Buff'] = {}
data[pistol]['Buff'] = {}
data[melee]['Buff'] = {}
data[shotgun]['Buff'] = {}
data[archgun]['Buff'] = {}

data[rifle]['Curse'] = {}
data[pistol]['Curse'] = {}
data[melee]['Curse'] = {}
data[shotgun]['Curse'] = {}
data[archgun]['Curse'] = {}

for elem in weapon_list:
    if elem["riventype"] == 'Rifle':
        if elem['text'] == "|val|% Damage":
            data[rifle]['Buff']["Damage"] = elem['value']
            data[rifle]['Curse']["Damage"] = elem['curse']
        elif elem['text'] == "|val|% Multishot":
            data[rifle]['Buff']["Multishot"] = elem['value']
            data[rifle]['Curse']["Multishot"] = elem['curse']
        elif elem['text'] == "|val|% Physical Damage":
            data[rifle]['Buff']["Physical Damage"] = elem['value']
            data[rifle]['Curse']["Physical Damage"] = elem['curse']
        elif elem['text'] == "|val|% Critical Chance":
            data[rifle]['Buff']["Critical Chance"] = elem['value']
            data[rifle]['Curse']["Critical Chance"] = elem['curse']
        elif elem['text'] == "|val|% Critical Damage":
            data[rifle]['Buff']["Critical Damage"] = elem['value']
            data[rifle]['Curse']["Critical Damage"] = elem['curse']
        elif elem['text'] == "|val|%"+ " Elemental Damage":
            data[rifle]['Buff']["Elemental Damage"] = elem['value']
            data[rifle]['Curse']["Elemental Damage"] = elem['curse']
        elif elem['text'] == "|val|% Status Chance":
            data[rifle]['Buff']["Status Chance"] = elem['value']
            data[rifle]['Curse']["Status Chance"] = elem['curse']
        elif elem['text'] == "|val|% Status Duration":
            data[rifle]['Buff']["Status Duration"] = elem['value']
            data[rifle]['Curse']["Status Duration"] = elem['curse']
        elif elem['text'] == "|val|% Damage to Faction":
            data[rifle]['Buff']["Damage to Faction"] = elem['value']
            data[rifle]['Curse']["Damage to Faction"] = elem['curse']
        elif elem['text'] == "|val|%"+" Fire Rate":
            data[rifle]['Buff']["Fire Rate"] = elem['value']
            data[rifle]['Curse']["Fire Rate"] = elem['curse']
        elif elem['text'] == "|val|% Magazine Capacity":
            data[rifle]['Buff']["Magazine Capacity"] = elem['value']
            data[rifle]['Curse']["Magazine Capacity"] = elem['curse']
        elif elem['text'] == "|val|% Ammo Maximum":
            data[rifle]['Buff']["Ammo Maximum"] = elem['value']
            data[rifle]['Curse']["Ammo Maximum"] = elem['curse']
        elif elem['text'] == "|val|% Reload Speed":
            data[rifle]['Buff']["Reload Speed"] = elem['value']
            data[rifle]['Curse']["Reload Speed"] = elem['curse']
    elif elem["riventype"] == 'Pistol':
        if elem['text'] == "|val|% Damage":
            data[pistol]['Buff']["Damage"] = elem['value']
            data[pistol]['Curse']["Damage"] = elem['curse']
        elif elem['text'] == "|val|% Multishot":
            data[pistol]['Buff']["Multishot"] = elem['value']
            data[pistol]['Curse']["Multishot"] = elem['curse']
        elif elem['text'] == "|val|% Physical Damage":
            data[pistol]['Buff']["Physical Damage"] = elem['value']
            data[pistol]['Curse']["Physical Damage"] = elem['curse']
        elif elem['text'] == "|val|% Critical Chance":
            data[pistol]['Buff']["Critical Chance"] = elem['value']
            data[pistol]['Curse']["Critical Chance"] = elem['curse']
        elif elem['text'] == "|val|% Critical Damage":
            data[pistol]['Buff']["Critical Damage"] = elem['value']
            data[pistol]['Curse']["Critical Damage"] = elem['curse']
        elif elem['text'] == "|val|%"+ " Elemental Damage":
            data[pistol]['Buff']["Elemental Damage"] = elem['value']
            data[pistol]['Curse']["Elemental Damage"] = elem['curse']
        elif elem['text'] == "|val|% Status Chance":
            data[pistol]['Buff']["Status Chance"] = elem['value']
            data[pistol]['Curse']["Status Chance"] = elem['curse']
        elif elem['text'] == "|val|% Status Duration":
            data[pistol]['Buff']["Status Duration"] = elem['value']
            data[pistol]['Curse']["Status Duration"] = elem['curse']
        elif elem['text'] == "|val|% Damage to Faction":
            data[pistol]['Buff']["Damage to Faction"] = elem['value']
            data[pistol]['Curse']["Damage to Faction"] = elem['curse']
        elif elem['text'] == "|val|%"+" Fire Rate":
            data[pistol]['Buff']["Fire Rate"] = elem['value']
            data[pistol]['Curse']["Fire Rate"] = elem['curse']
        elif elem['text'] == "|val|% Magazine Capacity":
            data[pistol]['Buff']["Magazine Capacity"] = elem['value']
            data[pistol]['Curse']["Magazine Capacity"] = elem['curse']
        elif elem['text'] == "|val|% Ammo Maximum":
            data[pistol]['Buff']["Ammo Maximum"] = elem['value']
            data[pistol]['Curse']["Ammo Maximum"] = elem['curse']
        elif elem['text'] == "|val|% Reload Speed":
            data[pistol]['Buff']["Reload Speed"] = elem['value']    
            data[pistol]['Curse']["Reload Speed"] = elem['curse']    
    elif elem["riventype"] == 'Shotgun':
        if elem['text'] == "|val|% Damage":
            data[shotgun]['Buff']["Damage"] = elem['value']
            data[shotgun]['Curse']["Damage"] = elem['curse']
        elif elem['text'] == "|val|% Multishot":
            data[shotgun]['Buff']["Multishot"] = elem['value']
            data[shotgun]['Curse']["Multishot"] = elem['curse']
        elif elem['text'] == "|val|% Physical Damage":
            data[shotgun]['Buff']["Physical Damage"] = elem['value']
            data[shotgun]['Curse']["Physical Damage"] = elem['curse']
        elif elem['text'] == "|val|% Critical Chance":
            data[shotgun]['Buff']["Critical Chance"] = elem['value']
            data[shotgun]['Curse']["Critical Chance"] = elem['curse']
        elif elem['text'] == "|val|% Critical Damage":
            data[shotgun]['Buff']["Critical Damage"] = elem['value']
            data[shotgun]['Curse']["Critical Damage"] = elem['curse']
        elif elem['text'] == "|val|%"+ " Elemental Damage":
            data[shotgun]['Buff']["Elemental Damage"] = elem['value']
            data[shotgun]['Curse']["Elemental Damage"] = elem['curse']
        elif elem['text'] == "|val|% Status Chance":
            data[shotgun]['Buff']["Status Chance"] = elem['value']
            data[shotgun]['Curse']["Status Chance"] = elem['curse']
        elif elem['text'] == "|val|% Status Duration":
            data[shotgun]['Buff']["Status Duration"] = elem['value']
            data[shotgun]['Curse']["Status Duration"] = elem['curse']
        elif elem['text'] == "|val|% Damage to Faction":
            data[shotgun]['Buff']["Damage to Faction"] = elem['value']
            data[shotgun]['Curse']["Damage to Faction"] = elem['curse']
        elif elem['text'] == "|val|%"+" Fire Rate":
            data[shotgun]['Buff']["Fire Rate"] = elem['value']
            data[shotgun]['Curse']["Fire Rate"] = elem['curse']
        elif elem['text'] == "|val|% Magazine Capacity":
            data[shotgun]['Buff']["Magazine Capacity"] = elem['value']
            data[shotgun]['Curse']["Magazine Capacity"] = elem['curse']
        elif elem['text'] == "|val|% Ammo Maximum":
            data[shotgun]['Buff']["Ammo Maximum"] = elem['value']
            data[shotgun]['Curse']["Ammo Maximum"] = elem['curse']
        elif elem['text'] == "|val|% Reload Speed":
            data[shotgun]['Buff']["Reload Speed"] = elem['value']    
            data[shotgun]['Curse']["Reload Speed"] = elem['curse']    
    elif elem["riventype"] == 'Melee':
        if elem['text'] == "|val|% Melee Damage":
            data[melee]['Buff']["Damage"] = elem['value']
            data[melee]['Curse']["Damage"] = elem['curse']
        elif elem['text'] == "|val|% Multishot":
            data[melee]['Buff']["Multishot"] = elem['value']
            data[melee]['Curse']["Multishot"] = elem['curse']
        elif elem['text'] == "|val|% Physical Damage":
            data[melee]['Buff']["Physical Damage"] = elem['value']
            data[melee]['Curse']["Physical Damage"] = elem['curse']
        elif elem['text'] == "|val|% Critical Chance":
            data[melee]['Buff']["Critical Chance"] = elem['value']
            data[melee]['Curse']["Critical Chance"] = elem['curse']
        elif elem['text'] == "|val|% Critical Damage":
            data[melee]['Buff']["Critical Damage"] = elem['value']
            data[melee]['Curse']["Critical Damage"] = elem['curse']
        elif elem['text'] == "|val|%"+ " Elemental Damage":
            data[melee]['Buff']["Elemental Damage"] = elem['value']
            data[melee]['Curse']["Elemental Damage"] = elem['curse']
        elif elem['text'] == "|val|% Status Chance":
            data[melee]['Buff']["Status Chance"] = elem['value']
            data[melee]['Curse']["Status Chance"] = elem['curse']
        elif elem['text'] == "|val|% Status Duration":
            data[melee]['Buff']["Status Duration"] = elem['value']
            data[melee]['Curse']["Status Duration"] = elem['curse']
        elif elem['text'] == "|val|% Damage to Faction":
            data[melee]['Buff']["Damage to Faction"] = elem['value']
            data[melee]['Curse']["Damage to Faction"] = elem['curse']
        elif elem['text'] == "|val|%"+" Attack Speed":
            data[melee]['Buff']["Fire Rate"] = elem['value']
            data[melee]['Curse']["Fire Rate"] = elem['curse']
        elif elem['text'] == "|val|% Magazine Capacity":
            data[melee]['Buff']["Magazine Capacity"] = elem['value']
            data[melee]['Curse']["Magazine Capacity"] = elem['curse']
        elif elem['text'] == "|val|% Ammo Maximum":
            data[melee]['Buff']["Ammo Maximum"] = elem['value']
            data[melee]['Curse']["Ammo Maximum"] = elem['curse']
        elif elem['text'] == "|val|% Reload Speed":
            data[melee]['Buff']["Reload Speed"] = elem['value']    
            data[melee]['Curse']["Reload Speed"] = elem['curse']    
    elif elem["riventype"] == 'Archgun':
        if elem['text'] == "|val|% Damage":
            data[archgun]['Buff']["Damage"] = elem['value']
            data[archgun]['Curse']["Damage"] = elem['curse']
        elif elem['text'] == "|val|% Multishot":
            data[archgun]['Buff']["Multishot"] = elem['value']
            data[archgun]['Curse']["Multishot"] = elem['curse']
        elif elem['text'] == "|val|% Physical Damage":
            data[archgun]['Buff']["Physical Damage"] = elem['value']
            data[archgun]['Curse']["Physical Damage"] = elem['curse']
        elif elem['text'] == "|val|% Critical Chance":
            data[archgun]['Buff']["Critical Chance"] = elem['value']
            data[archgun]['Curse']["Critical Chance"] = elem['curse']
        elif elem['text'] == "|val|% Critical Damage":
            data[archgun]['Buff']["Critical Damage"] = elem['value']
            data[archgun]['Curse']["Critical Damage"] = elem['curse']
        elif elem['text'] == "|val|%"+ " Elemental Damage":
            data[archgun]['Buff']["Elemental Damage"] = elem['value']
            data[archgun]['Curse']["Elemental Damage"] = elem['curse']
        elif elem['text'] == "|val|% Status Chance":
            data[archgun]['Buff']["Status Chance"] = elem['value']
            data[archgun]['Curse']["Status Chance"] = elem['curse']
        elif elem['text'] == "|val|% Status Duration":
            data[archgun]['Buff']["Status Duration"] = elem['value']
            data[archgun]['Curse']["Status Duration"] = elem['curse']
        elif elem['text'] == "|val|% Damage to Faction":
            data[archgun]['Buff']["Damage to Faction"] = elem['value']
            data[archgun]['Curse']["Damage to Faction"] = elem['curse']
        elif elem['text'] == "|val|%"+" Fire Rate":
            data[archgun]['Buff']["Fire Rate"] = elem['value']
            data[archgun]['Curse']["Fire Rate"] = elem['curse']
        elif elem['text'] == "|val|% Magazine Capacity":
            data[archgun]['Buff']["Magazine Capacity"] = elem['value']
            data[archgun]['Curse']["Magazine Capacity"] = elem['curse']
        elif elem['text'] == "|val|% Ammo Maximum":
            data[archgun]['Buff']["Ammo Maximum"] = elem['value']
            data[archgun]['Curse']["Ammo Maximum"] = elem['curse']
        elif elem['text'] == "|val|% Reload Speed":
            data[archgun]['Buff']["Reload Speed"] = elem['value']    
            data[archgun]['Curse']["Reload Speed"] = elem['curse']    

json_stuff = json.dumps(data)
f = open('riven_stats.json',"w")
f.write(json_stuff)
f.close()        
