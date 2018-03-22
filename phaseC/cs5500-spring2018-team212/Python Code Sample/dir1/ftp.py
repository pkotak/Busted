import ftplib

ftp = ftplib.FTP('ftp.sunet.se', 'anonymous', 'anonymous@sunet.se')

print "File List: "

files = ftp.dir()

print files

ftp.cwd("/pub/unix") #changing to /pub/unix