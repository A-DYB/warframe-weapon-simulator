import urllib.request, json 
import ast
import requests
import lzma
from lzma import FORMAT_AUTO, LZMAError, LZMADecompressor
import re
import string

def main():
    melee_data = get_wfcd_melee()
    secondary_data = get_wfcd_secondary()
    primary_data = get_wfcd_primary()

    response = requests.get('http://content.warframe.com/PublicExport/index_en.txt.lzma')
    endpoint = fix()

    conc = ''
    with urllib.request.urlopen("http://content.warframe.com/PublicExport/Manifest/"+endpoint) as url:
        text = url.read().decode()
        regex = re.compile(r'[\n\r\t]')
        text = regex.sub(" ", text)
        data = json.loads(text)

    weapon_list = data["ExportWeapons"]
    data = {}
    for wep in weapon_list:
        if wep['totalDamage'] == 0:
            #weapon does 0 damage, skip
            continue
        wepname = string.capwords(wep['name'])
        data[wepname] = wep
        data[wepname]['OtherFireModes'] = {}
        data[wepname]['SecondaryEffects'] = {}
        try:
            if "HELD" in wep["trigger"]:
                data[wepname]["ammoCost"] = 0.5
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
        name = name.strip()
        #name = name.upper()
        try:
            data[name]['type'] = melee['type']
        except:
            print("Melee item key error: ~", name,"~")
    for primary in primary_data:
        name = primary['name']
        #name = name.upper()
        name = name.strip()
        try:
            data[name]['ammo'] = primary['ammo']
        except:
            #print("Primary item key error: ~", name,"~")
            pass

    for secondary in secondary_data:
        name = secondary['name']
        #name = name.upper()
        name = name.strip()
        try:
            data[name]['ammo'] = secondary['ammo']
        except:
            #print("Secondary item key error: ~", name,"~")
            pass
    
    user_agent = 'Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.0.7) Gecko/2009021910 Firefox/3.0.7'
    rivurl = "https://10o.io/rivens/rivencalc.json"
    rivheaders={'User-Agent':user_agent,} 
    rivrequest=urllib.request.Request(rivurl,None,rivheaders) #The assembled request
    rivresponse = urllib.request.urlopen(rivrequest)
    rivdata = rivresponse.read().decode()
    rivdata = json.loads(rivdata)

    for rivelem in rivdata["weapons"]:
        try:
            data[string.capwords(rivelem['name'])]["rivenType"] = rivelem['riventype']
        except:
            print("No key for ", string.capwords(rivelem['name']))

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
    data[string.capwords("AMPREX")]["damageRamp"] = {'min':0.3}
    #ATOMOS
    data[string.capwords("ATOMOS")]["damageRamp"] = {'min':0.35}
    #ARTAX
    data[string.capwords("ATOMOS")]["damageRamp"] = {'min':0.2}
    #BASMU
    data[string.capwords("BASMU")]["damageRamp"] = {'min':0.2}
    #CATABOLYST
    data[string.capwords("CATABOLYST")]["damageRamp"] = {'min':0.2}
    #CONVECTRIX
    data[string.capwords("CONVECTRIX")]["damageRamp"] = {'min':0.6}
    new_damage = [0] * 20
    new_damage[0] = 1.8
    new_damage[1] = 1.8
    new_damage[2] = 14.4
    data[string.capwords("CONVECTRIX")]['OtherFireModes']['AltFire'] = {'damagePerShot': new_damage, 'fireRate': 4, 'ammoCost': 0.5}
    data[string.capwords("CONVECTRIX")]['OtherFireModes']['AltFire']["damageRamp"] = {'min':0.8}
    data[string.capwords("CONVECTRIX")]['OtherFireModes']['AltFire']['SecondaryEffects'] = {}
    #ARTAX
    data[string.capwords("ARTAX")]["damageRamp"] = {'min':0.2}
    #CORTEGE
    data[string.capwords("CORTEGE")]["damageRamp"] = {'min':0.2}
    #CRYOTRA
    data[string.capwords("CRYOTRA")]["damageRamp"] = {'min':0.2}
    #CYCRON
    data[string.capwords("CYCRON")]["damageRamp"] = {'min':0.3}
    #EMBOLYST
    data[string.capwords("EMBOLIST")]["damageRamp"] = {'min':0.45}
    #FLUX RIFLE
    data[string.capwords("FLUX RIFLE")]["damageRamp"] = {'min':0.25}
    #GAMMACOR
    data[string.capwords("GAMMACOR")]["damageRamp"] = {'min':0.25}
    #GLAXION
    data[string.capwords("GLAXION")]["damageRamp"] = {'min':0.2}
    #GLAXIONVANDAL
    data[string.capwords("GLAXION VANDAL")]["damageRamp"] = {'min':0.3}
    #IGNIS
    data[string.capwords("IGNIS")]["damageRamp"] = {'min':0.35}
    #IGNIS WRAITH
    data[string.capwords("IGNIS WRAITH")]["damageRamp"] = {'min':0.35}
    #KUVANUKOR
    data[string.capwords("KUVA NUKOR")]["damageRamp"] = {'min':0.3}
    #LARKSPUR
    data[string.capwords("LARKSPUR")]["damageRamp"] = {'min':0.2}
    #NUKOR
    data[string.capwords("NUKOR")]["damageRamp"] = {'min':0.3}
    #OCUCOR
    data[string.capwords("OCUCOR")]["damageRamp"] = {'min':0.2}
    #PHAGE
    data[string.capwords("PHAGE")]["damageRamp"] = {'min':0.7}
    #PHANTASMA
    data[string.capwords("PHANTASMA")]["damageRamp"] = {'min':0.15}
    #QUANTA
    data[string.capwords("QUANTA")]["damageRamp"] = {'min':0.3}
    #QUANTAVANDAL
    data[string.capwords("QUANTA VANDAL")]["damageRamp"] = {'min':0.3}
    #SPECTRA
    data[string.capwords("SPECTRA")]["damageRamp"] = {'min':0.25}
    #SPECTRAVANDAL
    data[string.capwords("SPECTRA VANDAL")]["damageRamp"] = {'min':0.25}
    #SYNAPSE
    data[string.capwords("SYNAPSE")]["damageRamp"] = {'min':0.2}
    #SYNOIDGAMMACOR
    data[string.capwords("SYNOID GAMMACOR")]["damageRamp"] = {'min':0.3}
    #VERGLAS
    data[string.capwords("VERGLAS")]["damageRamp"] = {'min':0.2}

    ### CHARGE WEAPONS
    data[string.capwords("BALLISTICA PRIME")]["chargeTime"] = 0.8
    data[string.capwords("BALLISTICA")]["chargeTime"] = 1
    data[string.capwords("DREAD")]["chargeTime"] = 0.5
    data[string.capwords("PARIS PRIME")]["chargeTime"] = 0.5
    data[string.capwords("PARIS")]["chargeTime"] = 0.5     
    data[string.capwords("PROBOSCIS CERNOS")]["chargeTime"] = 0.7
    data[string.capwords("CERNOS PRIME")]["chargeTime"] = 0.5
    data[string.capwords("DAIKYU")]["chargeTime"] = 1
    data[string.capwords("CERNOS")]["chargeTime"] = 0.5
    data[string.capwords("VELOCITUS")]["chargeTime"] = 1
    data[string.capwords("CORVAS")]["chargeTime"] = 0.5
    data[string.capwords("RAKTA BALLISTICA")]["chargeTime"] = 1
    data[string.capwords("RAKTA CERNOS")]["chargeTime"] = 0.25
    data[string.capwords("MK1-PARIS")]["chargeTime"] = 0.5
    data[string.capwords("MUTALIST CERNOS")]["chargeTime"] = 0.5
    data[string.capwords("JAVLOK")]["chargeTime"] = 0.3
    data[string.capwords("MITER")]["chargeTime"] = 0.75
    data[string.capwords("DRAKGOON")]["chargeTime"] = 0.5
    data[string.capwords("KUVA DRAKGOON")]["chargeTime"] = 0.3
    data[string.capwords("KUVA BRAMMA")]["chargeTime"] = 0.4
    data[string.capwords("PRISMA ANGSTRUM")]["chargeTime"] = 0.2
    data[string.capwords("ANGSTRUM")]["chargeTime"] = 0.5
    data[string.capwords("STATICOR")]["chargeTime"] = 1
    data[string.capwords("FERROX")]["chargeTime"] = 0.5
    data[string.capwords("OPTICOR")]["chargeTime"] = 2
    data[string.capwords("OPTICOR VANDAL")]["chargeTime"] = 0.6
    data[string.capwords("LENZ")]["chargeTime"] = 1.2
    data[string.capwords("LANKA")]["chargeTime"] = 1
    data[string.capwords("OGRIS")]["chargeTime"] = 0.3
    data[string.capwords("VULCAX")]["chargeTime"] = 1
    #data[string.capwords("ArtemisBow")]["chargeTime"] = 1
    #data[string.capwords("ARTEMISBOWPRIME")]["chargeTime"] = 1
    #data[string.capwords("BALEFIRECHARGER")]["chargeTime"] = 2
    data[string.capwords("EPITAPH")]["chargeTime"] = 0.36
    data[string.capwords("PROBOSCIS CERNOS")]["chargeTime"] = 0.7
    
    #PROBOSCISCERNOS
    new_damage = [0] * 20
    new_damage[index('viral')] = 1003
    data[string.capwords("PROBOSCIS CERNOS")]["SecondaryEffects"]["AOE"] = {'damagePerShot': new_damage, 'radius': 7, 'falloff': 0.5, 'embedDelay': 1.7}
    new_damage = [0] * 20
    new_damage[index('slash')] = 50.625
    new_damage[index('viral')] = 39.375
    data[string.capwords("PROBOSCIS CERNOS")]["SecondaryEffects"]["Appendages"] = {'damagePerShot': new_damage, 'radius': 9, 'falloff': 0, 'multishot': 2, 'embedDelay': 0.85}

    #EPITAPH
    new_damage = [0] * 20
    new_damage[index('impact')] = 40
    new_damage[index('puncture')] = 30
    new_damage[index('slash')] = 30
    data[string.capwords("EPITAPH")]['OtherFireModes']['QuickShot'] = {'damagePerShot': new_damage, 'fireRate': 1.5, 'ammoCost': 1, 'trigger': 'SEMI'}
    data[string.capwords("EPITAPH")]['OtherFireModes']['QuickShot']['SecondaryEffects'] = {}
    new_damage = [0] * 20
    new_damage[index('blast')] = 20
    data[string.capwords("EPITAPH")]['OtherFireModes']['QuickShot']['SecondaryEffects']['AOE'] = {'damagePerShot': new_damage, 'radius': 8, 'falloff': 0.8}

    #QUANTA
    new_damage = [0] * 20
    new_damage[5] = 100
    data[string.capwords("QUANTA")]['OtherFireModes']['AltFire'] = {'damagePerShot': new_damage, 'criticalChance': 0.05, 'criticalMultiplier': 1.5, 'procChance': 0.26, 'fireRate': 4, 'ammoCost': 10, 'trigger': 'SEMI'}
    data[string.capwords("QUANTA")]['OtherFireModes']['AltFire']['SecondaryEffects'] = {}
    new_damage = [0] * 20
    new_damage[7] = 150
    data[string.capwords("QUANTA")]['OtherFireModes']['AltFire']['SecondaryEffects']['CubeExplosion'] = {'damagePerShot': new_damage, 'radius': 0.5}
    new_damage = [0] * 20
    new_damage[7] = 600
    data[string.capwords("QUANTA")]['OtherFireModes']['AltFire']['SecondaryEffects']['CubeShot'] = {'damagePerShot': new_damage, 'radius': 6}

    #QUANTAVANDAL
    new_damage = [0] * 20
    new_damage[5] = 100
    data[string.capwords("QUANTA VANDAL")]['OtherFireModes']['AltFire'] = {'damagePerShot': new_damage, 'criticalChance': 0.05, 'criticalMultiplier': 1.5, 'procChance': 0.26, 'fireRate': 4, 'ammoCost': 10, 'trigger': 'SEMI'}
    data[string.capwords("QUANTA VANDAL")]['OtherFireModes']['AltFire']['SecondaryEffects'] = {}
    new_damage = [0] * 20
    new_damage[7] = 150
    data[string.capwords("QUANTA VANDAL")]['OtherFireModes']['AltFire']['SecondaryEffects']['CubeExplosion'] = {'damagePerShot': new_damage, 'radius': 0.5}
    new_damage = [0] * 20
    new_damage[7] = 600
    data[string.capwords("QUANTA VANDAL")]['OtherFireModes']['AltFire']['SecondaryEffects']['CubeShot'] = {'damagePerShot': new_damage, 'radius': 6}

    #KUVABRAMMA
    new_damage = [0] * 20
    new_damage[index('impact')] = 49
    
    data[string.capwords("KUVA BRAMMA")]['SecondaryEffects']['BombletImpact'] = {'damagePerShot': new_damage, 'multishot': 3}
    new_damage = [0] * 20
    new_damage[index('blast')] = 57
    data[string.capwords("KUVA BRAMMA")]['SecondaryEffects']['BombletExplosion'] = {'damagePerShot': new_damage, 'multishot': 3, 'radius': 3.5, 'falloff': 0.5}

    #ARGONAK
    new_damage = [0] * 20
    new_damage[0] = 24.5
    new_damage[1] = 6.3
    new_damage[2] = 26.2
    data[string.capwords("ARGONAK")]['OtherFireModes']['FullAuto'] = {'damagePerShot': new_damage, 'criticalChance': 0.09, 'criticalMultiplier': 1.5, 'procChance': 0.27, 'fireRate': 6, 'trigger': 'AUTO'}

    #BATTACOR
    new_damage = [0] * 20
    new_damage[8] = 208
    data[string.capwords("BATTACOR")]['OtherFireModes']['AltFire'] = {'damagePerShot': new_damage, 'criticalChance': 0.34, 'criticalMultiplier': 3, 'procChance': 0.08, 'fireRate': 5, 'chargeTime': 0.4, 'trigger': 'CHARGE'}
    data[string.capwords("BATTACOR")]['OtherFireModes']['AltFire']['SecondaryEffects'] = {}
    data[string.capwords("BATTACOR")]['OtherFireModes']['AltFire']['SecondaryEffects']['AOE'] = {'damagePerShot': new_damage, 'falloff': 0.4}

    #BASMU
    new_damage = [0] * 20
    new_damage[5] = 12
    data[string.capwords("BASMU")]['OtherFireModes']['AltFire'] = {'damagePerShot': new_damage, 'criticalChance': 0.02, 'criticalMultiplier': 4.8, 'procChance': 0.3, 'fireRate': 12, 'multishot': 2, 'trigger': 'HELD'}
    data[string.capwords("BASMU")]['OtherFireModes']['AltFire']["damageRamp"] = {'min':0.2}
    
    #BUBONICO
    new_damage = [0] * 20
    new_damage[0] = 9
    data[string.capwords("BUBONICO")]['OtherFireModes']['AltFire'] = {'damagePerShot': new_damage, 'criticalChance': 0.03, 'criticalMultiplier': 3.5, 'procChance': 0.57, 'fireRate': 3.37, 'multishot': 1, 'trigger': 'BURST'}
    data[string.capwords("BUBONICO")]['OtherFireModes']['AltFire']['SecondaryEffects'] = {}
    new_damage = [0] * 20
    new_damage[11] = 143
    data[string.capwords("BUBONICO")]['OtherFireModes']['AltFire']['SecondaryEffects']['AOE'] = {'damagePerShot': new_damage, 'falloff': 0.5, 'radius': 7}

    #CORINTH
    new_damage = [0] * 20
    new_damage[0] = 100
    data[string.capwords("CORINTH")]['OtherFireModes']['AltFire'] = {'damagePerShot': new_damage, 'criticalChance': 0.04, 'criticalMultiplier': 1.6, 'procChance': 0.28, 'fireRate': 1.17, 'multishot': 1, 'trigger': 'SEMI'}
    data[string.capwords("CORINTH")]['OtherFireModes']['AltFire']['SecondaryEffects'] = {}
    new_damage = [0] * 20
    new_damage[index('blast')] = 404
    data[string.capwords("CORINTH")]['OtherFireModes']['AltFire']['SecondaryEffects']['AOE'] = {'damagePerShot': new_damage, 'falloff': 0.9, 'radius': 9.4}

    #CORINTHPRIME
    new_damage = [0] * 20
    new_damage[0] = 100
    data[string.capwords("CORINTH PRIME")]['OtherFireModes']['AltFire'] = {'damagePerShot': new_damage, 'criticalChance': 0.04, 'criticalMultiplier': 1.6, 'procChance': 0.5, 'fireRate': 0.667, 'multishot': 1, 'ammoCost': 4, 'trigger': 'SEMI'}
    data[string.capwords("CORINTH PRIME")]['OtherFireModes']['AltFire']['SecondaryEffects'] = {}
    new_damage = [0] * 20
    new_damage[index('blast')] = 2200
    data[string.capwords("CORINTH PRIME")]['OtherFireModes']['AltFire']['SecondaryEffects']['AOE'] = {'damagePerShot': new_damage, 'falloff': 0.9, 'radius': 9.8}

    #FULMIN
    data[string.capwords("FULMIN")]['ammoCost'] = 10
    new_damage = [0] * 20
    new_damage[index('puncture')] = 8
    new_damage[index('electric')] = 25
    data[string.capwords("FULMIN")]['OtherFireModes']['FullAuto'] = {'damagePerShot': new_damage, 'criticalChance': 0.28, 'criticalMultiplier': 2.4, 'procChance': 0.1, 'fireRate': 9.33, 'multishot': 1, 'ammoCost': 1, 'trigger': 'AUTO'}
    
    #HIND
    new_damage = [0] * 20
    new_damage[index('impact')] = 12
    new_damage[index('puncture')] = 12
    new_damage[index('slash')] = 36
    data[string.capwords("HIND")]['OtherFireModes']['SemiAuto'] = {'damagePerShot': new_damage, 'criticalChance': 0.15, 'criticalMultiplier': 2, 'procChance': 0.1, 'fireRate': 2.5, 'multishot': 1, 'trigger': 'SEMI'}
    
    #KUVAHIND
    new_damage = [0] * 20
    new_damage[index('impact')] = 18
    new_damage[index('puncture')] = 18
    new_damage[index('slash')] = 54
    data[string.capwords("KUVA HIND")]['OtherFireModes']['SemiAuto'] = {'damagePerShot': new_damage, 'criticalChance': 0.37, 'criticalMultiplier': 2.9, 'procChance': 0.21, 'fireRate': 2.5, 'multishot': 1, 'trigger': 'SEMI', 'ammoCost': 3}
    
    new_damage = [0] * 20
    new_damage[index('impact')] = 6
    new_damage[index('puncture')] = 6
    new_damage[index('slash')] = 18
    data[string.capwords("KUVA HIND")]['OtherFireModes']['Auto'] = {'damagePerShot': new_damage, 'criticalChance': 0.21, 'criticalMultiplier': 1.9, 'procChance': 0.33, 'fireRate': 10, 'multishot': 1, 'trigger': 'AUTO', 'ammoCost': 3}
    
    #KOMOREX
    new_damage = [0] * 20
    new_damage[index('impact')] = 17.4
    new_damage[index('puncture')] = 73
    new_damage[index('slash')] = 83.6
    data[string.capwords("KOMOREX")]['OtherFireModes']['Zoom'] = {'damagePerShot': new_damage, 'criticalChance': 0.16, 'criticalMultiplier': 2.1, 'procChance': 0.35, 'fireRate': 1.5, 'multishot': 1, 'trigger': 'SEMI', 'ammoCost': 1}
    data[string.capwords("KOMOREX")]['OtherFireModes']['Zoom']['SecondaryEffects'] = {}
    new_damage = [0] * 20
    new_damage[index('viral')] = 66
    data[string.capwords("KOMOREX")]['OtherFireModes']['Zoom']['SecondaryEffects']['AOE'] = {'damagePerShot': new_damage, 'criticalChance': 0.16, 'criticalMultiplier': 2.1, 'procChance': 0.35, 'fireRate': 1.5, 'multishot': 1, 'trigger': 'SEMI', 'ammoCost': 1, 'radius': 3.5, 'falloff': 0.4}
    
    #NAGANTAKA
    data[string.capwords("NAGANTAKA")]['OtherFireModes']['AltFire'] = {'fireRate': 5.81}
    
    #PANTHERA
    new_damage = [0] * 20
    new_damage[index('impact')] = 10
    new_damage[index('puncture')] = 10
    new_damage[index('slash')] = 80
    data[string.capwords("PANTHERA")]['OtherFireModes']['AltFire'] = {'damagePerShot': new_damage, 'criticalChance': 0.25, 'criticalMultiplier': 2, 'procChance': 0.35, 'fireRate': 2, 'multishot': 1, 'trigger': 'AUTO', 'ammoCost': 1}
    data[string.capwords("PANTHERA")]['OtherFireModes']['AltFire']["damageRamp"] = {'min':0.2}

    #PANTHERAPRIME
    new_damage = [0] * 20
    new_damage[index('slash')] = 100
    data[string.capwords("PANTHERA PRIME")]['OtherFireModes']['AltFire'] = {'damagePerShot': new_damage, 'criticalChance': 0.26, 'criticalMultiplier': 2, 'procChance': 0.38, 'fireRate': 2, 'multishot': 1, 'trigger': 'AUTO', 'ammoCost': 1}
    data[string.capwords("PANTHERA PRIME")]['OtherFireModes']['AltFire']['SecondaryEffects'] = {}
    new_damage = [0] * 20
    new_damage[index('slash')] = 20
    data[string.capwords("PANTHERA PRIME")]['OtherFireModes']['AltFire']['SecondaryEffects']['AOE'] = {'damagePerShot': new_damage, 'criticalChance': 0.18, 'criticalMultiplier': 2, 'procChance': 0.30, 'fireRate': 3.67, 'multishot': 1, 'trigger': 'AUTO', 'ammoCost': 0, 'radius': 1.6, 'falloff': 0.2}
    data[string.capwords("PANTHERA PRIME")]['OtherFireModes']['AltFire']["damageRamp"] = {'min':0.2}

    #PHANTASMA
    new_damage = [0] * 20
    new_damage[index('impact')] = 15
    data[string.capwords("PHANTASMA")]['OtherFireModes']['AltFire'] = {'damagePerShot': new_damage, 'chargeTime': 1, 'criticalChance': 0.03, 'criticalMultiplier': 1.5, 'procChance': 0.37, 'fireRate': 2, 'multishot': 1, 'trigger': 'CHARGE', 'ammoCost': 1}
    data[string.capwords("PHANTASMA")]['OtherFireModes']['AltFire']['SecondaryEffects'] = {}
    new_damage = [0] * 20
    new_damage[index('radiation')] = 73
    data[string.capwords("PHANTASMA")]['OtherFireModes']['AltFire']['SecondaryEffects']['AOE'] = {'damagePerShot': new_damage, 'procChance': 0.222, 'multishot': 1, 'ammoCost': 1, 'radius': 4.8, 'falloff': 0.5}
    new_damage = [0] * 20
    new_damage[index('impact')] = 75
    data[string.capwords("PHANTASMA")]['OtherFireModes']['AltFire']['SecondaryEffects']['Cluster'] = {'damagePerShot': new_damage, 'procChance': 0.37, 'multishot': 5, 'ammoCost': 1, 'radius': 4.8, 'falloff': 0.5}
    
    #QUELLOR
    new_damage = [0] * 20
    new_damage[index('impact')] = 600
    new_damage[index('cold')] = 800
    data[string.capwords("QUELLOR")]['OtherFireModes']['AltFire'] = {'damagePerShot': new_damage, 'chargeTime': 1.2, 'criticalChance': 0.4, 'criticalMultiplier': 2.2, 'procChance': 0.1, 'fireRate': 1, 'multishot': 1, 'trigger': 'CHARGE', 'ammoCost': 50}
    
    #STAHLTA
    new_damage = [0] * 20
    new_damage[index('impact')] = 120
    new_damage[index('puncture')] = 180
    new_damage[index('slash')] = 300
    data[string.capwords("STAHLTA")]['OtherFireModes']['AltFire'] = {'damagePerShot': new_damage, 'chargeTime': 1.6, 'criticalChance': 0.4, 'criticalMultiplier': 3, 'procChance': 0.32, 'fireRate': 0.666666, 'multishot': 1, 'trigger': 'CHARGE','ammoCost': 20}
    
    new_damage = [0] * 20
    new_damage[index('radiation')] = 1200
    data[string.capwords("STAHLTA")]['OtherFireModes']['AltFire']['SecondaryEffects'] = {}
    data[string.capwords("STAHLTA")]['OtherFireModes']['AltFire']['SecondaryEffects']['AOE'] = {'damagePerShot': new_damage, 'radius': 7.2, 'falloff': 0.7, 'embedDelay': 0.4}

    #STRADAVAR
    new_damage = [0] * 20
    new_damage[index('impact')] = 7.5
    new_damage[index('puncture')] = 30
    new_damage[index('slash')] = 12.5
    data[string.capwords("STRADAVAR")]['OtherFireModes']['SemiAuto'] = {'damagePerShot': new_damage, 'criticalChance': 0.28, 'criticalMultiplier': 2.0, 'procChance': 0.16, 'fireRate': 5, 'multishot': 1, 'trigger': 'SEMI', 'ammoCost': 1}
    
    #STRADAVARPRIME
    new_damage = [0] * 20
    new_damage[index('impact')] = 8
    new_damage[index('puncture')] = 24
    new_damage[index('slash')] = 48
    data[string.capwords("STRADAVAR PRIME")]['OtherFireModes']['SemiAuto'] = {'damagePerShot': new_damage, 'criticalChance': 0.3, 'criticalMultiplier': 2.8, 'procChance': 0.22, 'fireRate': 3.333333, 'multishot': 1, 'trigger': 'SEMI', 'ammoCost': 1}
    
    #TENORA
    new_damage = [0] * 20
    new_damage[index('impact')] = 48
    new_damage[index('puncture')] = 144
    new_damage[index('slash')] = 48
    data[string.capwords("TENORA")]['OtherFireModes']['AltFire'] = {'damagePerShot': new_damage, 'criticalChance': 0.34, 'criticalMultiplier': 3, 'procChance': 0.11,'chargeTime': 0.8, 'fireRate': 2, 'multishot': 1, 'trigger': 'CHARGE', 'ammoCost': 10}
    
    #TENORAPRIME
    new_damage = [0] * 20
    new_damage[index('impact')] = 56
    new_damage[index('puncture')] = 168
    new_damage[index('slash')] = 56
    data[string.capwords("TENORA PRIME")]['OtherFireModes']['AltFire'] = {'damagePerShot': new_damage, 'criticalChance': 0.4, 'criticalMultiplier': 3, 'procChance': 0.2,'chargeTime': 0.8, 'fireRate': 2, 'multishot': 1, 'trigger': 'CHARGE', 'ammoCost': 10}
    
    #TIBERONPRIME
    new_damage = [0] * 20
    new_damage[index('impact')] = 13.8
    new_damage[index('puncture')] = 18.4
    new_damage[index('slash')] = 13.8
    data[string.capwords("TIBERON PRIME")]['OtherFireModes']['SemiAuto'] = {'damagePerShot': new_damage, 'criticalChance': 0.3, 'criticalMultiplier': 3.4, 'procChance': 0.18, 'fireRate': 6, 'multishot': 1, 'trigger': 'SEMI', 'ammoCost': 1}
    new_damage = [0] * 20
    new_damage[index('impact')] = 13.8
    new_damage[index('puncture')] = 18.4
    new_damage[index('slash')] = 13.8
    data[string.capwords("TIBERON PRIME")]['OtherFireModes']['Auto'] = {'damagePerShot': new_damage, 'criticalChance': 0.16, 'criticalMultiplier': 2.8, 'procChance': 0.32, 'fireRate': 8.333333, 'multishot': 1, 'trigger': 'AUTO', 'ammoCost': 1}
    
    #TRUMNA
    new_damage = [0] * 20
    new_damage[index('heat')] = 50
    data[string.capwords("TRUMNA")]['SecondaryEffects']['AOE'] = {'damagePerShot': new_damage, 'criticalChance': 0.24, 'criticalMultiplier': 2.2, 'procChance': 0.30, 'radius': 1.6, 'falloff': 0.15}
    new_damage = [0] * 20
    new_damage[index('impact')] = 100
    new_damage[index('heat')] = 1000
    data[string.capwords("TRUMNA")]['OtherFireModes']['AltFire'] = {'damagePerShot': new_damage, 'criticalChance': 0.38, 'criticalMultiplier': 2.4, 'procChance': 0.5, 'fireRate': 1.333333, 'multishot': 7, 'radius': 6, 'falloff': 0.4, 'trigger': 'SEMI', 'ammoCost': 1}
    
    #ZARR
    new_damage = [0] * 20
    new_damage[index('blast')] = 50
    data[string.capwords("ZARR")]['SecondaryEffects']['AOE'] = {'damagePerShot': new_damage, 'procChance': 0.048, 'multishot': 6}
    new_damage = [0] * 20
    new_damage[index('impact')] = 24
    new_damage[index('puncture')] = 40
    new_damage[index('slash')] = 16
    data[string.capwords("ZARR")]['OtherFireModes']['Barrage'] = {'damagePerShot': new_damage, 'criticalChance': 0.17, 'criticalMultiplier': 2.5, 'procChance': 0.87, 'fireRate': 3, 'multishot': 10, 'trigger': 'SEMI', 'ammoCost': 1}
    
    #ZENITH
    new_damage = [0] * 20
    new_damage[index('impact')] = 15
    new_damage[index('puncture')] = 120
    new_damage[index('slash')] = 15
    data[string.capwords("ZENITH")]['OtherFireModes']['Semi'] = {'damagePerShot': new_damage, 'criticalChance': 0.35, 'criticalMultiplier': 2.5, 'procChance': 0.08, 'fireRate': 3, 'multishot': 1, 'trigger': 'SEMI', 'ammoCost': 3}
    
    data["Volnus Prime"]["rivenType"] = "Melee"
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