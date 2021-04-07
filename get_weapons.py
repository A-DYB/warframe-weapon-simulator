import urllib.request, json 
import ast
import requests
import lzma
from lzma import FORMAT_AUTO, LZMAError, LZMADecompressor
import re

def main():
    melee_data = get_wfcd_melee()
    secondary_data = get_wfcd_secondary()
    primary_data = get_wfcd_primary()

    response = requests.get('http://content.warframe.com/PublicExport/index_en.txt.lzma')
    endpoint = fix()

    conc = ''
    with urllib.request.urlopen("http://content.warframe.com/PublicExport/Manifest/"+endpoint) as url:
        d = url.read().decode().replace('\r', ',')
        for line in d:
            line = line.rstrip()
            conc += line
        data = json.loads(conc)

    weapon_list = data["ExportWeapons"]
    data = {}
    for wep in weapon_list:
        data[wep['name']] = wep
        data[wep['name']]['OtherFireModes'] = {}
        data[wep['name']]['SecondaryEffects'] = {}
        try:
            if "HELD" in wep["trigger"]:
                data[wep['name']]["ammoCost"] = 0.5
        except:
            pass
        '''
        try:
            if "CHARGE" in wep["trigger"]:
                print(wep["name"])
        except:
            pass
        '''

    for melee in melee_data:
        name = melee['name']
        name = name.replace(" ", "").upper()
        try:
            data[name]['type'] = melee['type']
        except:
            print("Melee item key error: ~", name,"~")
    for primary in primary_data:
        name = primary['name']
        name = name.replace(" ", "").upper()
        name = name.strip()
        try:
            data[name]['ammo'] = primary['ammo']
        except:
            #print("Primary item key error: ~", name,"~")
            pass

    for secondary in secondary_data:
        name = secondary['name']
        name = name.replace(" ", "").upper()
        name = name.strip()
        try:
            data[name]['ammo'] = secondary['ammo']
        except:
            #print("Secondary item key error: ~", name,"~")
            pass
    

    fill_in_the_blanks(data)

    json_stuff = json.dumps(data)
    f = open('weapons.json',"w")
    f.write(json_stuff)
    f.close()

def fill_in_the_blanks(data):
    '''
    0 impact 
    1 puncture
    2 Slash
    3 Heat
    4 Cold
    5 Electric
    6 Toxin
    7 Blast
    8 Radiation
    9 Gas
    10 Magnetic
    11 Viral
    12 Corrosive
    13 
    14 
    15 
    16
    17
    18
    19

    '''
    ###BEAM WEAPONS

    #AMPREX
    data["AMPREX"]["damageRamp"] = {'min':0.3}
    #ATOMOS
    data["ATOMOS"]["damageRamp"] = {'min':0.35}
    #ARTAX
    data["ATOMOS"]["damageRamp"] = {'min':0.2}
    #BASMU
    data["BASMU"]["damageRamp"] = {'min':0.2}
    #CATABOLYST
    data["CATABOLYST"]["damageRamp"] = {'min':0.2}
    #CONVECTRIX
    data["CONVECTRIX"]["damageRamp"] = {'min':0.6}
    new_damage = [0] * 20
    new_damage[0] = 1.8
    new_damage[1] = 1.8
    new_damage[2] = 14.4
    data["CONVECTRIX"]['OtherFireModes']['AltFire'] = {'damagePerShot': new_damage, 'fireRate': 4, 'ammoCost': 0.5}
    data["CONVECTRIX"]['OtherFireModes']['AltFire']["damageRamp"] = {'min':0.8}
    data["CONVECTRIX"]['OtherFireModes']['AltFire']['SecondaryEffects'] = {}
    #CORTEGE
    data["CORTEGE"]["damageRamp"] = {'min':0.2}
    #CRYOTRA
    data["CRYOTRA"]["damageRamp"] = {'min':0.2}
    #CYCRON
    data["CYCRON"]["damageRamp"] = {'min':0.3}
    #EMBOLYST
    data["EMBOLIST"]["damageRamp"] = {'min':0.45}
    #FLUXRIFLE
    data["FLUXRIFLE"]["damageRamp"] = {'min':0.25}
    #GAMMACOR
    data["GAMMACOR"]["damageRamp"] = {'min':0.25}
    #GLAXION
    data["GLAXION"]["damageRamp"] = {'min':0.2}
    #GLAXIONVANDAL
    data["GLAXIONVANDAL"]["damageRamp"] = {'min':0.3}
    #IGNIS
    data["IGNIS"]["damageRamp"] = {'min':0.35}
    #IGNISWRAITH
    data["IGNISWRAITH"]["damageRamp"] = {'min':0.35}
    #KUVANUKOR
    data["KUVANUKOR"]["damageRamp"] = {'min':0.3}
    #LARKSPUR
    data["LARKSPUR"]["damageRamp"] = {'min':0.2}
    #NUKOR
    data["NUKOR"]["damageRamp"] = {'min':0.3}
    #OCUCOR
    data["OCUCOR"]["damageRamp"] = {'min':0.2}
    #PHAGE
    data["PHAGE"]["damageRamp"] = {'min':0.7}
    #PHANTASMA
    data["PHANTASMA"]["damageRamp"] = {'min':0.15}
    #QUANTA
    data["QUANTA"]["damageRamp"] = {'min':0.3}
    #QUANTAVANDAL
    data["QUANTAVANDAL"]["damageRamp"] = {'min':0.3}
    #SPECTRA
    data["SPECTRA"]["damageRamp"] = {'min':0.25}
    #SPECTRAVANDAL
    data["SPECTRAVANDAL"]["damageRamp"] = {'min':0.25}
    #SYNAPSE
    data["SYNAPSE"]["damageRamp"] = {'min':0.2}
    #SYNOIDGAMMACOR
    data["SYNOIDGAMMACOR"]["damageRamp"] = {'min':0.3}
    #VERGLAS
    data["VERGLAS"]["damageRamp"] = {'min':0.2}

    ### CHARGE WEAPONS
    data["BALLISTICAPRIME"]["chargeTime"] = 0.8
    data["BALLISTICA"]["chargeTime"] = 1
    data["DREAD"]["chargeTime"] = 0.5
    data["PARISPRIME"]["chargeTime"] = 0.5
    data["PARIS"]["chargeTime"] = 0.5     
    data["PROBOSCISCERNOS"]["chargeTime"] = 0.7
    data["CERNOSPRIME"]["chargeTime"] = 0.5
    data["DAIKYU"]["chargeTime"] = 1
    data["CERNOS"]["chargeTime"] = 0.5
    data["VELOCITUS"]["chargeTime"] = 1
    data["CORVAS"]["chargeTime"] = 0.5
    data["RAKTABALLISTICA"]["chargeTime"] = 1
    data["RAKTACERNOS"]["chargeTime"] = 0.25
    data["MK1-PARIS"]["chargeTime"] = 0.5
    data["MUTALISTCERNOS"]["chargeTime"] = 0.5
    data["JAVLOK"]["chargeTime"] = 0.3
    data["MITER"]["chargeTime"] = 0.75
    data["DRAKGOON"]["chargeTime"] = 0.5
    data["KUVADRAKGOON"]["chargeTime"] = 0.3
    data["KUVABRAMMA"]["chargeTime"] = 0.4
    data["PRISMAANGSTRUM"]["chargeTime"] = 0.2
    data["ANGSTRUM"]["chargeTime"] = 0.5
    data["STATICOR"]["chargeTime"] = 1
    data["FERROX"]["chargeTime"] = 0.5
    data["OPTICOR"]["chargeTime"] = 2
    data["OPTICORVANDAL"]["chargeTime"] = 0.6
    data["LENZ"]["chargeTime"] = 1.2
    data["LANKA"]["chargeTime"] = 1
    data["OGRIS"]["chargeTime"] = 0.3
    data["VULCAX"]["chargeTime"] = 1
    data["ArtemisBow"]["chargeTime"] = 1
    data["ARTEMISBOWPRIME"]["chargeTime"] = 1
    data["BALEFIRECHARGER"]["chargeTime"] = 2
    
    #QUANTA
    new_damage = [0] * 20
    new_damage[5] = 100
    data["QUANTA"]['OtherFireModes']['AltFire'] = {'damagePerShot': new_damage, 'criticalChance': 0.05, 'criticalMultiplier': 1.5, 'procChance': 0.26, 'fireRate': 4, 'ammoCost': 10, 'trigger': 'SEMI'}
    data["QUANTA"]['OtherFireModes']['AltFire']['SecondaryEffects'] = {}
    new_damage = [0] * 20
    new_damage[7] = 150
    data["QUANTA"]['OtherFireModes']['AltFire']['SecondaryEffects']['CubeExplosion'] = {'damagePerShot': new_damage, 'radius': 0.5}
    new_damage = [0] * 20
    new_damage[7] = 600
    data["QUANTA"]['OtherFireModes']['AltFire']['SecondaryEffects']['CubeShot'] = {'damagePerShot': new_damage, 'radius': 6}

    #QUANTAVANDAL
    new_damage = [0] * 20
    new_damage[5] = 100
    data["QUANTAVANDAL"]['OtherFireModes']['AltFire'] = {'damagePerShot': new_damage, 'criticalChance': 0.05, 'criticalMultiplier': 1.5, 'procChance': 0.26, 'fireRate': 4, 'ammoCost': 10, 'trigger': 'SEMI'}
    data["QUANTAVANDAL"]['OtherFireModes']['AltFire']['SecondaryEffects'] = {}
    new_damage = [0] * 20
    new_damage[7] = 150
    data["QUANTAVANDAL"]['OtherFireModes']['AltFire']['SecondaryEffects']['CubeExplosion'] = {'damagePerShot': new_damage, 'radius': 0.5}
    new_damage = [0] * 20
    new_damage[7] = 600
    data["QUANTAVANDAL"]['OtherFireModes']['AltFire']['SecondaryEffects']['CubeShot'] = {'damagePerShot': new_damage, 'radius': 6}

    #KUVABRAMMA
    new_damage = [0] * 20
    new_damage[index('impact')] = 49
    
    data["KUVABRAMMA"]['SecondaryEffects']['BombletImpact'] = {'damagePerShot': new_damage, 'multishot': 3}
    new_damage = [0] * 20
    new_damage[index('blast')] = 57
    data["KUVABRAMMA"]['SecondaryEffects']['BombletExplosion'] = {'damagePerShot': new_damage, 'multishot': 3, 'radius': 3.5, 'falloff': 0.5}

    #ARGONAK
    new_damage = [0] * 20
    new_damage[0] = 24.5
    new_damage[1] = 6.3
    new_damage[2] = 26.2
    data["ARGONAK"]['OtherFireModes']['FullAuto'] = {'damagePerShot': new_damage, 'criticalChance': 0.09, 'criticalMultiplier': 1.5, 'procChance': 0.27, 'fireRate': 6, 'trigger': 'AUTO'}

    #BATTACOR
    new_damage = [0] * 20
    new_damage[8] = 208
    data["BATTACOR"]['OtherFireModes']['AltFire'] = {'damagePerShot': new_damage, 'criticalChance': 0.34, 'criticalMultiplier': 3, 'procChance': 0.08, 'fireRate': 5, 'chargeRate': 0.4, 'trigger': 'CHARGE'}
    data["BATTACOR"]['OtherFireModes']['AltFire']['SecondaryEffects'] = {}
    data["BATTACOR"]['OtherFireModes']['AltFire']['SecondaryEffects']['AOE'] = {'damagePerShot': new_damage, 'falloff': 0.4}

    #BASMU
    new_damage = [0] * 20
    new_damage[5] = 12
    data["BASMU"]['OtherFireModes']['AltFire'] = {'damagePerShot': new_damage, 'criticalChance': 0.02, 'criticalMultiplier': 4.8, 'procChance': 0.3, 'fireRate': 12, 'multishot': 2, 'trigger': 'HELD'}
    data["BASMU"]['OtherFireModes']['AltFire']["damageRamp"] = {'min':0.2}
    
    #BUBONICO
    new_damage = [0] * 20
    new_damage[0] = 9
    data["BUBONICO"]['OtherFireModes']['AltFire'] = {'damagePerShot': new_damage, 'criticalChance': 0.03, 'criticalMultiplier': 3.5, 'procChance': 0.57, 'fireRate': 3.37, 'multishot': 1, 'trigger': 'BURST'}
    data["BUBONICO"]['OtherFireModes']['AltFire']['SecondaryEffects'] = {}
    new_damage = [0] * 20
    new_damage[11] = 143
    data["BUBONICO"]['OtherFireModes']['AltFire']['SecondaryEffects']['AOE'] = {'damagePerShot': new_damage, 'falloff': 0.5, 'radius': 7}

    #CORINTH
    new_damage = [0] * 20
    new_damage[0] = 100
    data["CORINTH"]['OtherFireModes']['AltFire'] = {'damagePerShot': new_damage, 'criticalChance': 0.04, 'criticalMultiplier': 1.6, 'procChance': 0.28, 'fireRate': 1.17, 'multishot': 1, 'trigger': 'SEMI'}
    data["CORINTH"]['OtherFireModes']['AltFire']['SecondaryEffects'] = {}
    new_damage = [0] * 20
    new_damage[index('blast')] = 404
    data["CORINTH"]['OtherFireModes']['AltFire']['SecondaryEffects']['AOE'] = {'damagePerShot': new_damage, 'falloff': 0.9, 'radius': 9.4}

    #CORINTHPRIME
    new_damage = [0] * 20
    new_damage[0] = 100
    data["CORINTHPRIME"]['OtherFireModes']['AltFire'] = {'damagePerShot': new_damage, 'criticalChance': 0.04, 'criticalMultiplier': 1.6, 'procChance': 0.5, 'fireRate': 0.667, 'multishot': 1, 'ammoCost': 4, 'trigger': 'SEMI'}
    data["CORINTHPRIME"]['OtherFireModes']['AltFire']['SecondaryEffects'] = {}
    new_damage = [0] * 20
    new_damage[index('blast')] = 2200
    data["CORINTHPRIME"]['OtherFireModes']['AltFire']['SecondaryEffects']['AOE'] = {'damagePerShot': new_damage, 'falloff': 0.9, 'radius': 9.8}

    #FULMIN
    data["FULMIN"]['ammoCost'] = 10
    new_damage = [0] * 20
    new_damage[index('puncture')] = 8
    new_damage[index('electric')] = 25
    data["FULMIN"]['OtherFireModes']['FullAuto'] = {'damagePerShot': new_damage, 'criticalChance': 0.28, 'criticalMultiplier': 2.4, 'procChance': 0.1, 'fireRate': 9.33, 'multishot': 1, 'ammoCost': 1, 'trigger': 'AUTO'}
    
    #HIND
    new_damage = [0] * 20
    new_damage[index('impact')] = 12
    new_damage[index('puncture')] = 12
    new_damage[index('slash')] = 36
    data["HIND"]['OtherFireModes']['SemiAuto'] = {'damagePerShot': new_damage, 'criticalChance': 0.15, 'criticalMultiplier': 2, 'procChance': 0.1, 'fireRate': 2.5, 'multishot': 1, 'trigger': 'SEMI'}
    
    #KUVAHIND
    new_damage = [0] * 20
    new_damage[index('impact')] = 18
    new_damage[index('puncture')] = 18
    new_damage[index('slash')] = 54
    data["KUVAHIND"]['OtherFireModes']['SemiAuto'] = {'damagePerShot': new_damage, 'criticalChance': 0.37, 'criticalMultiplier': 2.9, 'procChance': 0.21, 'fireRate': 2.5, 'multishot': 1, 'trigger': 'SEMI', 'ammoCost': 3}
    
    new_damage = [0] * 20
    new_damage[index('impact')] = 6
    new_damage[index('puncture')] = 6
    new_damage[index('slash')] = 18
    data["KUVAHIND"]['OtherFireModes']['Auto'] = {'damagePerShot': new_damage, 'criticalChance': 0.21, 'criticalMultiplier': 1.9, 'procChance': 0.33, 'fireRate': 10, 'multishot': 1, 'trigger': 'AUTO', 'ammoCost': 3}
    
    #KOMOREX
    new_damage = [0] * 20
    new_damage[index('impact')] = 17.4
    new_damage[index('puncture')] = 73
    new_damage[index('slash')] = 83.6
    data["KOMOREX"]['OtherFireModes']['Zoom'] = {'damagePerShot': new_damage, 'criticalChance': 0.16, 'criticalMultiplier': 2.1, 'procChance': 0.35, 'fireRate': 1.5, 'multishot': 1, 'trigger': 'SEMI', 'ammoCost': 1}
    data["KOMOREX"]['OtherFireModes']['Zoom']['SecondaryEffects'] = {}
    new_damage = [0] * 20
    new_damage[index('viral')] = 66
    data["KOMOREX"]['OtherFireModes']['Zoom']['SecondaryEffects']['AOE'] = {'damagePerShot': new_damage, 'criticalChance': 0.16, 'criticalMultiplier': 2.1, 'procChance': 0.35, 'fireRate': 1.5, 'multishot': 1, 'trigger': 'SEMI', 'ammoCost': 1, 'radius': 3.5, 'falloff': 0.4}
    
    #NAGANTAKA
    data["NAGANTAKA"]['OtherFireModes']['AltFire'] = {'fireRate': 5.81}
    
    #PANTHERA
    new_damage = [0] * 20
    new_damage[index('impact')] = 10
    new_damage[index('puncture')] = 10
    new_damage[index('slash')] = 80
    data["PANTHERA"]['OtherFireModes']['AltFire'] = {'damagePerShot': new_damage, 'criticalChance': 0.25, 'criticalMultiplier': 2, 'procChance': 0.35, 'fireRate': 2, 'multishot': 1, 'trigger': 'AUTO', 'ammoCost': 1}
    data["PANTHERA"]['OtherFireModes']['AltFire']["damageRamp"] = {'min':0.2}

    #PANTHERAPRIME
    new_damage = [0] * 20
    new_damage[index('slash')] = 100
    data["PANTHERAPRIME"]['OtherFireModes']['AltFire'] = {'damagePerShot': new_damage, 'criticalChance': 0.26, 'criticalMultiplier': 2, 'procChance': 0.38, 'fireRate': 2, 'multishot': 1, 'trigger': 'AUTO', 'ammoCost': 1}
    data["PANTHERAPRIME"]['OtherFireModes']['AltFire']['SecondaryEffects'] = {}
    new_damage = [0] * 20
    new_damage[index('slash')] = 20
    data["PANTHERAPRIME"]['OtherFireModes']['AltFire']['SecondaryEffects']['AOE'] = {'damagePerShot': new_damage, 'criticalChance': 0.18, 'criticalMultiplier': 2, 'procChance': 0.30, 'fireRate': 3.67, 'multishot': 1, 'trigger': 'AUTO', 'ammoCost': 0, 'radius': 1.6, 'falloff': 0.2}
    data["PANTHERAPRIME"]['OtherFireModes']['AltFire']["damageRamp"] = {'min':0.2}

    #PHANTASMA
    new_damage = [0] * 20
    new_damage[index('impact')] = 15
    data["PHANTASMA"]['OtherFireModes']['AltFire'] = {'damagePerShot': new_damage, 'chargeTime': 1, 'criticalChance': 0.03, 'criticalMultiplier': 1.5, 'procChance': 0.37, 'fireRate': 2, 'multishot': 1, 'trigger': 'CHARGE', 'ammoCost': 1}
    data["PHANTASMA"]['OtherFireModes']['AltFire']['SecondaryEffects'] = {}
    new_damage = [0] * 20
    new_damage[index('radiation')] = 73
    data["PHANTASMA"]['OtherFireModes']['AltFire']['SecondaryEffects']['AOE'] = {'damagePerShot': new_damage, 'procChance': 0.222, 'multishot': 1, 'ammoCost': 1, 'radius': 4.8, 'falloff': 0.5}
    new_damage = [0] * 20
    new_damage[index('impact')] = 75
    data["PHANTASMA"]['OtherFireModes']['AltFire']['SecondaryEffects']['Cluster'] = {'damagePerShot': new_damage, 'procChance': 0.37, 'multishot': 5, 'ammoCost': 1, 'radius': 4.8, 'falloff': 0.5}
    
    #QUELLOR
    new_damage = [0] * 20
    new_damage[index('impact')] = 600
    new_damage[index('cold')] = 800
    data["QUELLOR"]['OtherFireModes']['AltFire'] = {'damagePerShot': new_damage, 'chargeTime': 1.2, 'criticalChance': 0.4, 'criticalMultiplier': 2.2, 'procChance': 0.1, 'fireRate': 1, 'multishot': 1, 'trigger': 'CHARGE', 'ammoCost': 50}
    
    #STAHLTA
    new_damage = [0] * 20
    new_damage[index('radiation')] = 1200
    data["STAHLTA"]['OtherFireModes']['AltFire'] = {'damagePerShot': new_damage, 'chargeTime': 1, 'criticalChance': 0.4, 'criticalMultiplier': 3, 'procChance': 0.32, 'fireRate': 1, 'multishot': 1, 'trigger': 'CHARGE', 'ammoCost': 1, 'radius': 7.2, 'falloff': 0.7}
    
    #STRADAVAR
    new_damage = [0] * 20
    new_damage[index('impact')] = 7.5
    new_damage[index('puncture')] = 30
    new_damage[index('slash')] = 12.5
    data["STRADAVAR"]['OtherFireModes']['SemiAuto'] = {'damagePerShot': new_damage, 'criticalChance': 0.28, 'criticalMultiplier': 2.0, 'procChance': 0.16, 'fireRate': 5, 'multishot': 1, 'trigger': 'SEMI', 'ammoCost': 1}
    
    #STRADAVARPRIME
    new_damage = [0] * 20
    new_damage[index('impact')] = 8
    new_damage[index('puncture')] = 24
    new_damage[index('slash')] = 48
    data["STRADAVARPRIME"]['OtherFireModes']['SemiAuto'] = {'damagePerShot': new_damage, 'criticalChance': 0.3, 'criticalMultiplier': 2.8, 'procChance': 0.22, 'fireRate': 3.333333, 'multishot': 1, 'trigger': 'SEMI', 'ammoCost': 1}
    
    #TENORA
    new_damage = [0] * 20
    new_damage[index('impact')] = 48
    new_damage[index('puncture')] = 144
    new_damage[index('slash')] = 48
    data["TENORA"]['OtherFireModes']['AltFire'] = {'damagePerShot': new_damage, 'criticalChance': 0.34, 'criticalMultiplier': 3, 'procChance': 0.11,'chargeTime': 0.8, 'fireRate': 2, 'multishot': 1, 'trigger': 'CHARGE', 'ammoCost': 10}
    
    #TENORAPRIME
    new_damage = [0] * 20
    new_damage[index('impact')] = 56
    new_damage[index('puncture')] = 168
    new_damage[index('slash')] = 56
    data["TENORAPRIME"]['OtherFireModes']['AltFire'] = {'damagePerShot': new_damage, 'criticalChance': 0.4, 'criticalMultiplier': 3, 'procChance': 0.2,'chargeTime': 0.8, 'fireRate': 2, 'multishot': 1, 'trigger': 'CHARGE', 'ammoCost': 10}
    
    #TIBERONPRIME
    new_damage = [0] * 20
    new_damage[index('impact')] = 13.8
    new_damage[index('puncture')] = 18.4
    new_damage[index('slash')] = 13.8
    data["TIBERONPRIME"]['OtherFireModes']['SemiAuto'] = {'damagePerShot': new_damage, 'criticalChance': 0.3, 'criticalMultiplier': 3.4, 'procChance': 0.18, 'fireRate': 6, 'multishot': 1, 'trigger': 'SEMI', 'ammoCost': 1}
    new_damage = [0] * 20
    new_damage[index('impact')] = 13.8
    new_damage[index('puncture')] = 18.4
    new_damage[index('slash')] = 13.8
    data["TIBERONPRIME"]['OtherFireModes']['Auto'] = {'damagePerShot': new_damage, 'criticalChance': 0.16, 'criticalMultiplier': 2.8, 'procChance': 0.32, 'fireRate': 8.333333, 'multishot': 1, 'trigger': 'AUTO', 'ammoCost': 1}
    
    #TRUMNA
    new_damage = [0] * 20
    new_damage[index('heat')] = 50
    data["TRUMNA"]['SecondaryEffects']['AOE'] = {'damagePerShot': new_damage, 'criticalChance': 0.24, 'criticalMultiplier': 2.2, 'procChance': 0.30, 'radius': 1.6, 'falloff': 0.15}
    new_damage = [0] * 20
    new_damage[index('impact')] = 100
    new_damage[index('heat')] = 1000
    data["TRUMNA"]['OtherFireModes']['AltFire'] = {'damagePerShot': new_damage, 'criticalChance': 0.38, 'criticalMultiplier': 2.4, 'procChance': 0.5, 'fireRate': 1.333333, 'multishot': 7, 'radius': 6, 'falloff': 0.4, 'trigger': 'SEMI', 'ammoCost': 1}
    
    #ZARR
    new_damage = [0] * 20
    new_damage[index('blast')] = 50
    data["ZARR"]['SecondaryEffects']['AOE'] = {'damagePerShot': new_damage, 'procChance': 0.048, 'multishot': 6}
    new_damage = [0] * 20
    new_damage[index('impact')] = 24
    new_damage[index('puncture')] = 40
    new_damage[index('slash')] = 16
    data["ZARR"]['OtherFireModes']['Barrage'] = {'damagePerShot': new_damage, 'criticalChance': 0.17, 'criticalMultiplier': 2.5, 'procChance': 0.87, 'fireRate': 3, 'multishot': 10, 'trigger': 'SEMI', 'ammoCost': 1}
    
    #ZENITH
    new_damage = [0] * 20
    new_damage[index('impact')] = 15
    new_damage[index('puncture')] = 120
    new_damage[index('slash')] = 15
    data["ZENITH"]['OtherFireModes']['Semi'] = {'damagePerShot': new_damage, 'criticalChance': 0.35, 'criticalMultiplier': 2.5, 'procChance': 0.08, 'fireRate': 3, 'multishot': 1, 'trigger': 'SEMI', 'ammoCost': 3}
    
    #print(data["SPECTRA"])

def fix():
    response = requests.get('http://content.warframe.com/PublicExport/index_en.txt.lzma')
    data = response.content
    byt = bytes(data)
    length = len(data)
    while True:
        try:
            decompress_lzma(byt[0:length])
        except LZMAError:
            length -= 1
            continue
        break
    spl = decompress_lzma(byt[0:length]).decode().split("\r\n")
    result = ''
    for line in spl:
        if "ExportWeapons" in line:
            result = line
            break
    return result

def decompress_lzma(data):
    results = []
    while True:
        decomp = LZMADecompressor(FORMAT_AUTO, None, None)
        try:
            res = decomp.decompress(data)
        except LZMAError:
            if results:
                break  # Leftover data is not a valid LZMA/XZ stream; ignore it.
            else:
                raise  # Error on the first iteration; bail out.
        results.append(res)
        data = decomp.unused_data
        if not data:
            break
        if not decomp.eof:
            raise LZMAError("Compressed data ended before the end-of-stream marker was reached")
    return b"".join(results)

def index(name):
    if name == 'impact':
        return 0
    elif name == 'puncture':
        return 1
    elif name == 'slash':
        return 2
    elif name == 'heat':
        return 3
    elif name == 'cold':
        return 4
    elif name == 'electric':
        return 5
    elif name == 'toxin':
        return 6
    elif name == 'blast':
        return 7
    elif name == 'radiation':
        return 8
    elif name == 'gas':
        return 9
    elif name == 'magnetic':
        return 10
    elif name == 'viral':
        return 11
    elif name == 'corrosive':
        return 12
    print("Invalid damage type name," + name +" using default impact")
    return 0

def get_wfcd_melee():
    url = urllib.request.urlopen("https://raw.githubusercontent.com/WFCD/warframe-items/master/data/json/Melee.json") 
    data = url.read().decode()
    data = json.loads(data)
    return data
def get_wfcd_secondary():
    url = urllib.request.urlopen("https://raw.githubusercontent.com/WFCD/warframe-items/master/data/json/Secondary.json") 
    data = url.read().decode()
    data = json.loads(data)
    return data
def get_wfcd_primary():
    url = urllib.request.urlopen("https://raw.githubusercontent.com/WFCD/warframe-items/master/data/json/Primary.json") 
    data = url.read().decode()
    data = json.loads(data)
    return data

if __name__ == '__main__':
    main()