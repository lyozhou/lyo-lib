__author__ = 'lijie'
#!/usr/bin/env python
#coding: utf8
import MySQLdb
import sys
reload(sys)
sys.setdefaultencoding("utf8")
print sys.stdout.encoding
__all__ = ['MySQL']
class MySQL(object):

    conn = ''
    cursor = ''
    def __init__(self,host='localhost',user='root',passwd='root',db='test',charset='utf8'):
        
        try:
            self.conn = MySQLdb.connect(host,user,passwd,db,use_unicode=1,charset="utf8")
        except MySQLdb.Error,e:
            errormsg = 'Cannot connect to server\nERROR (%s): %s' %(e.args[0],e.args[1])
            print errormsg
            sys.exit()

        self.cursor = self.conn.cursor()

    def query(self,sql):

        return self.cursor.execute(sql)

    def show(self):

        return self.cursor.fetchall()

    def __del__(self):

        self.conn.close()
        self.cursor.close()


if __name__ == '__main__':
    print sys.stdout.encoding

    mysql = MySQL(host='localhost',passwd='root',db='test',charset='utf8')
    #2262 3053 4992
    mysql.query('select * from doubanmovie where link in (select link from doubanpinglun ,test where doubanpinglun.userName=test.username and test.id=5040 )group by (director) order by count(director) desc')
    
    userresult = mysql.show()
    print len(userresult)
    director=3
    time=2
    mysql1= MySQL(host='localhost',passwd='root',db='test',charset='utf8')
    mysql1.query('select * from doubanmovie')
    totalmovie=mysql1.show()
    print len(totalmovie)
    
    dic={}
    testclass=userresult[0:9]
    for j in totalmovie:
        li=j[9]
        dic[li]=0
        for i in userresult[10:]:
            #print j[0],i[0]
            if i[0]!=j[0]:
                if i[2]==j[2]:
                    #print"aa %s",i[2],j[2]
                    dic[li]+=1*8*director
                s='{0:}'.format(i[3])
                if s!="":
                    actor=s.split('.')[0:2]
                    s='{0:}'.format(j[3])
                
                    for ac in actor:
                        if ac in s and ac!="":
                            #print"bb %s",ac
                            dic[li]+=1
                            break
                if i[4]==j[4] :
                    dic[li]+=1*9
                s='{0:7}'.format(i[5])
                s1='{0:7}'.format(j[5])
                #print s[0:7]
                if s[0:7] in s1 and s[0:7]!="":
                    dic[li]+=1*1*time
  
    dic= sorted(dic.iteritems(), key=lambda d:d[1], reverse = True)
    print dic[0:10]
    for i in testclass:
        print i[9]
    sum=0
    print dic[3500]
    for i in testclass:
        s='{0:6}'.format(i[9])
        #print"**** %s",s
        for j in dic[0:2000]:
            if s == j[0]:
                sum=sum+1
                print j[1]
            
    
    print sum
    
