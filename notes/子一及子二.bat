@echo off

rem project01: open tomcat
call C:\Users\market\Downloads\apache-tomcat-7.0.53\apache-tomcat-7.0.53\bin\startup.bat 

rem project02: open apache (wamp)
call C:\wamp\wampmanager.exe

rem project00: open rails, rails server setup path (C:\RailsInstaller\Ruby1.9.3\rails.bat)
cd C:\Users\market\islab
rails s

pause