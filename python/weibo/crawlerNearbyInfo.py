from weibo import APIClient
from urllib2 import URLError, HTTPError
import re
import MySQLdb
import webbrowser
import time


APP_KEY = '3711333581' # app key
APP_SECRET = '98539126069b0723285524d79e4c7a3e' # app secret
CALLBACK_URL = 'http://open.weibo.com/apps/3711333581/privilege/oauth' #callback url

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
row = cur.execute('select userId,locId,checkTime from weibo_loctimeline ')
results  = cur.fetchmany(row)
for r in results:
    saved_userLoc.add(r)

saved_location = []
row = cur.execute('select id from weibo_location order by checkin_num desc limit 2,300')
results = cur.fetchmany(row)
for r in results:
    saved_location.append(r[0])
    
saved_checkUser = set()
regex = re.compile(ur"[^\u4e00-\u9fa5a-zA-Z0-9,.\:;()'<>£¬¡£¡¢£»£º£¨£©¡°¡±¡¶¡·!£¡@#£¤%¡­¡­&*$|~¡ª]")
def checkUserQuery(poid,page):
    global data
    try:
        data = client.place.poi_timeline.get(poiid=poid,page=page,count=20)
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
        users = data['statuses']
        for i in range(len(users)):
        #for i in [4]:
            for k,v in users[i].items():
                skipFlag = False
                try:
                    if k=='text':
                        insertSql1[7]=regex.sub('',v)
                    elif k=='created_at':
                        insertSql1[8]=v
                    elif k=='user':
                        insertSql1[0]=v['id']
                        insertSql1[2]=v['location']
                        insertSql1[3]=v['province']
                        insertSql1[4]=v['city']
                        insertSql1[5]=regex.sub('',v['description'])
                        insertSql1[6]=v['gender']
                    elif k=='annotations':
                        insertSql1[1] = v[0]['place']['poiid']
                    insertSql1[9] = int(users[i]['reposts_count'])+int(users[i]['comments_count'])+int(users[i]['attitudes_count'])
                except KeyError:
                    insertSql1[1] = poid
                    #print 'keyError'
                except UnicodeDecodeError,e:
                    skipFlag = True
                    print e
                except Exception,e:
                    skipFlag = True
                    print e
            if not (insertSql1[0],insertSql1[1],insertSql1[8]) in saved_userLoc and not skipFlag:
                checkUserTup = tuple(insertSql1)
                insertValue.append(checkUserTup)
                saved_userLoc.add((insertSql1[0],insertSql1[1],insertSql1[8]))
        try:
            cur.executemany('''insert into weibo_loctimeline values
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
