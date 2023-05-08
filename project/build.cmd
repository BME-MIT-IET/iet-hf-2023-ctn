@echo off
rmdir /s /q buildout
mkdir buildout
copy /y *.cfg buildout
dir /b /s | findstr \.java > buildout/files.txt 
javac @buildout/files.txt -d buildout -encoding utf8
exit