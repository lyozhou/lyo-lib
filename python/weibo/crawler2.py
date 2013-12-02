from weibo import APIClient
from urllib2 import URLError, HTTPError
import MySQLdb
import webbrowser
import time


APP_KEY = '3194450337' # app key
APP_SECRET = '4fd22945a41209a23a2f46de079d7005' # app secret
CALLBACK_URL = 'http://open.weibo.com/apps/3194450337/privilege/oauth' #callback url

client = APIClient(app_key=APP_KEY,
                   app_secret=APP_SECRET,
                   redirect_uri=CALLBACK_URL)
url = client.get_authorize_url()

content = webbrowser.open_new(url)

# raw input code from previous url
code = raw_input()

r = client.request_access_token(code)
access_token = r.access_token
expires_in = r.expires_in

client.set_access_token(access_token, expires_in)

conn=MySQLdb.connect(host='localhost',user='root',passwd='root',db='test',port=3306,charset='utf8')
cur=conn.cursor()

saved_location = set()
saved_latLon = set()
saved_latLon.add(('31.2372364494','121.501606738'))
row = cur.execute('select id from weibo_location')
results = cur.fetchmany(row)
for r in results:
    saved_location.add(r[0])


row = cur.execute('select lat,lon from weibo_location where checkin_num>500 LIMIT 1332,666')
results  = cur.fetchmany(row)
for r in results:
    saved_latLon.add(r)


#user = client.place.pois.users.get(poiid='B2094654D16CABFE419E')
def loactionQuery(lat,lon,page):
    global data
    try:
        data = client.place.nearby.pois.get(lat=lat,long=lon,page=page,count=20,sort=3)
    except URLError,e:
        print 'URLError'
        return 'fail'
    except HTTPError, e:
        print 'HTTPError'
        return 'fail'
    except Exception,e:
        print 'other fail'
        return 'fail'
    insertValue = []
    insertSql1 = ['' for i in range(20)]
    if not data == []:
        locations = data['pois']
        for i in range(len(locations)):
            skipFlag = False
            for k,v in locations[i].items():
                if k=='categorys':
                    insertSql1[13]=v
                elif k== 'checkin_num':
                    insertSql1[15]=int(v)
                    if int(v)<500:
                        skip_latLon = True
                    else:
                        skip_latLon = False
                elif k=='postcode':
                    insertSql1[11]=v
                elif k == 'category':
                    insertSql1[5]=v
                elif k == 'city':
                    insertSql1[6]=v
                elif k == 'title':
                    insertSql1[1]=v
                elif k == 'lon':
                    insertSql1[3]=v
                elif k == 'category_name':
                    insertSql1[14]=v
                elif k == 'province':
                    insertSql1[7]=v
                elif k == 'herenow_user_num':
                    insertSql1[19]=int(v)
                elif k == 'phone':
                    insertSql1[10]=v
                elif k == 'address':
                    insertSql1[2]=v
                elif k == 'lat':
                    insertSql1[4]=v
                elif k == 'tip_num':
                    insertSql1[17]=v
                elif k == 'poiid':
                    if(v in saved_location):
                        skipFlag = True
                        break
                    else :
                        insertSql1[0]=v
                        saved_location.add(v)
                elif k == 'distance':
                    insertSql1[18]=int(v)
                elif k == 'checkin_user_num':
                    insertSql1[16]=int(v)
                elif k == 'url':
                    insertSql1[9]=v
                elif k == 'country':
                    insertSql1[8]=v
            if (not skipFlag):
                locationTup = tuple(insertSql1)
                insertValue.append(locationTup)
                print insertSql1[0],insertSql1[1]
                if (not skip_latLon):
                    saved_latLon.add((insertSql1[4],insertSql1[3]))
        try:
            print 'save!'
            cur.executemany('''insert into weibo_location values
                            (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)''',insertValue)
            conn.commit()
            return 'Ok'
        except MySQLdb.Error,e:
            print e
            return 'fail'
    else:
        return 'Empty'


while len(saved_latLon) <> 0 :
    lat,lon = saved_latLon.pop()
    searchFlag = True
    pageNo = 1
    while(searchFlag):
        time.sleep(24)
        result = loactionQuery(lat,lon,pageNo)
        if result ==  'Ok':
            pageNo += 1
        else:
            searchFlag = False
            

cur.close()
conn.close()
