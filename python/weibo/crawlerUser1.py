from weibo import APIClient
from urllib2 import URLError, HTTPError
import re
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


saved_userLoc = set()
row = cur.execute('select userId,locId,checkTime from weibo_checkin_user ')
results  = cur.fetchmany(row)
for r in results:
    saved_userLoc.add(r)

saved_location = []
row = cur.execute('select id from weibo_location order by checkin_num desc limit 301,2000')
results = cur.fetchmany(row)
for r in results:
    saved_location.append(r[0])
    
saved_checkUser = set()
regex = re.compile(ur"[^\u4e00-\u9fa5a-zA-Z0-9,.\:;()'<>£¬¡£¡¢£»£º£¨£©¡°¡±¡¶¡·!£¡@#£¤%¡­¡­&*$|~¡ª]")
def checkUserQuery(poid,page):
    global data
    try:
        data = client.place.pois.users.get(poiid=poid,page=page,count=20)
    except URLError,e:
        print 'URLError'
        return 'fail'
    except HTTPError, e:
        print 'HTTPError'
        return 'fail'
    except Exception,e:
        print e
        return 'fail'
    insertValue = []
    insertSql1 = ['' for i in range(11)]
    if not data == []:
        global users
        users = data['users']
        states = data['states']
        for i in range(len(users)):
        #for i in [4]:
            for k,v in users[i].items():
                skipFlag = False
                try:
                    if k=='id':
                        insertSql1[0]=v
                    elif k=='location':
                        insertSql1[2]=v
                    elif k=='province':
                        insertSql1[3]=v
                    elif k=='city':
                        insertSql1[4]=v
                    elif k=='description':
                        insertSql1[5]=regex.sub(' ',v)
                    elif k=='gender':
                        insertSql1[6]=v
                    elif k=='checkin_at':
                        insertSql1[8] = v
                    elif k=='status':
                        #print v['text']
                        insertSql1[7] = regex.sub('',v['text'])
                        #insertSql1[7] = v['text']
                        insertSql1[9] = int(v['reposts_count'])+int(v['comments_count'])+int(v['attitudes_count'])
                        annotations =  v['annotations']
                        place = annotations[0]['place']
                        insertSql1[1] = place['poiid']    
                except KeyError:
                    skipFlag = True
                    #print 'keyError'
                except UnicodeDecodeError,e:
                    skipFlag = True
                    print e
                except Exception,e:
                    skipFlag = True
                    print e
            insertSql1[10] = states[i].values()[0]
            if not (insertSql1[0],insertSql1[1],insertSql1[8]) in saved_userLoc and not skipFlag:
                checkUserTup = tuple(insertSql1)
                insertValue.append(checkUserTup)
                saved_userLoc.add((insertSql1[0],insertSql1[1],insertSql1[8]))
        try:
            cur.executemany('''insert into weibo_checkin_user values
                            (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)''',insertValue)
            conn.commit()
            return 'Ok'
        except MySQLdb.Error,e:
            print e
            return 'fail'
    else:
        return 'Empty'
                                    
checkUserQuery('B2094655D36DA6FE449F',1)


while len(saved_location) <> 0 :
    i = 0
    loiid = saved_location[i]
    print loiid
    searchFlag = True
    pageNo = 1
    while searchFlag:
        time.sleep(24)
        result = checkUserQuery(loiid,pageNo)
        print result
        if result ==  'Empty':
            searchFlag = False
        pageNo += 1
    i += 1        

cur.close()
conn.close()
