set projectLocation=C:\Users\njy\workspace\API-Automation
cd %projectLocation%
set classpath=%projectLocation%\bin;%projectLocation%\lib\*
java org.testng.TestNG %projectLocation%\TestNG.xml
pause