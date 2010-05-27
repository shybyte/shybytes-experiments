import urllib
import re

for i in range(1,51):
    filehandle = urllib.urlopen('http://www.waylink-english.co.uk/?page=11620&pw='+str(i))
    filecontent = filehandle.read()
    filehandle.close()
    
    for m in re.finditer(r"<f6>(\w+)</f6>",filecontent):
        print m.group(1)


