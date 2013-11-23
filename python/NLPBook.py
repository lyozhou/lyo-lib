from __future__ import division
from nltk.corpus import state_union
import nltk


'''
open outputIng.txt and print out words end with ing
'''
def outputIng():
    print "start"
    for line in open("D:\\alyo-lib\\lyo-lib\\python\\outputIng.txt"):
        for word in line.split():
            if(word.endswith("ing")):
                print word

'''
text word's diversity : how many different word in a text
'''
def lexical_diversity(text):
        return len(text) / len(set(text))

'''
count / total * 100%
'''
def percentage(count,total):
    return 100 * count / total

'''
delete stop word from a NLTK.Text
'''
def content_fraction(text):
    stopwords = nltk.corpus.stopwords.words('english')
    content = [w for w in text if w.lower() not in stopwords]
    return len(content) / len(text)

'''
nlp chapter 2.4
'''
def state_union_men_stat():
    cfd = nltk.ConditionalFreqDist((target,year[:4])
        for year in state_union.fileids()
        for w in state_union.words(year)
        for target in ['men','women','people']
        if w.lower().startswith(target)
    )
    cfd.plot()

'''
nlp chapter 2.5
'''

def wordNet():
    from nltk.corpus import wordnet as wn
    words = ['human.n.01','car.n.01','tree.n.01']

    for w in words:
    	print "\n"*3
    	print w
    	print "member_meronyms"
    	print wn.synset(w).member_meronyms()
    	print "part_meronyms"
    	print wn.synset(w).part_meronyms()
    	print "substance_meronyms"
    	print wn.synset(w).substance_meronyms()

    	print "\n" + "member_holonyms"
    	print wn.synset(w).member_holonyms()
    	print "part_holonyms"
    	print wn.synset(w).part_holonyms()
    	print "substance_holonyms"
    	print wn.synset(w).substance_holonyms()

'''
nlp chapter 2.8
'''
def nameDistribution():
    cfd = nltk.ConditionalFreqDist((target,firstName[0])
        for target in nltk.corpus.names.fileids()
        for firstName in nltk.corpus.names.words(target)
    )
    cfd.plot()

'''
nlp chapter 2.12
'''
def pron():
    allWord = [w for w in nltk.corpus.cmudict.words()]
    distinctWord = set(allWord)
    print 'number of distinct word = ' + str(len(distinctWord))
    fd = nltk.FreqDist(allWord)
    multiWord = [w for w in allWord if fd[w]>1]
    print 'multi words percentage is ' + str(len(multiWord)/len(allWord))

'''
nlp chapter 2.18
'''
def freq_bigrams_wo_stopwords():
    stopWord = set(s.lower() for s in nltk.corpus.stopwords.words('english'))
    text = nltk.corpus.genesis.words('english-web.txt')
    biggramWithouStopWord = [b for b in nltk.bigrams(text)
                             if b[0].isalpha() and b[1].isalpha()
                             and (len( set(k.lower() for k in b ) & stopWord)==0)
                            ]
    top50 = nltk.FreqDist(biggramWithouStopWord).keys()[:50]
    print [" ".join(t) for t in top50]

'''
stem word
'''
def stem1(word):
    for suffix in ['ing','ly','ed','ious','ies','ive','es','s','ment']:
        if word.endswith(suffix):
            return word[:-len(suffix)]
    return word
def stem2(word):
    import re
    regexp = r'^(.*?)(ing|ly|ed|ious|ies|ive|es|s|ment)?$'
    stem,suffix = re.findall(regexp,word)[0]
    return [stem,suffix]

'''
tokenizor with regular expression
(?x) tell python ignore comments and space charater
'''
def regexp_tokenize():
    text = 'That U.S.A. poster-print costs $12.40 ...'
    pattern =r'''(?x) #set flag to allow verboseregexps
    ([A-Z]\.)+ #abbreviations, e.g. U.S.A.
    | \w+(-\w+)* #wordswithoptional internal hyphens
    | \$?\d+(\.\d+)?%? #currency and percentages,e.g. $12.40,82% 116
    | \.\.\. #ellipsis
    | [][.,;"'?():-_`] #these are separate tokens
    '''
    print nltk.regexp_tokenize(text,pattern)
