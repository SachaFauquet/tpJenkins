@echo off
REM Script pour telecharger le JAR JUnit necessaire pour le TP Jenkins

echo === Telechargement du JAR JUnit ===
echo.

set JUNIT_URL=https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.9.3/junit-platform-console-standalone-1.9.3.jar
set JUNIT_DIR=src\main\java\org\example
set JUNIT_FILE=junit-platform-console-standalone-1.9.3.jar

echo URL: %JUNIT_URL%
echo Destination: %JUNIT_DIR%\%JUNIT_FILE%
echo.

REM Creer le repertoire si necessaire
if not exist "%JUNIT_DIR%" (
    echo Creation du repertoire %JUNIT_DIR%...
    mkdir "%JUNIT_DIR%"
)

REM Telecharger avec PowerShell
echo Telechargement en cours...
powershell -Command "Invoke-WebRequest -Uri '%JUNIT_URL%' -OutFile '%JUNIT_DIR%\%JUNIT_FILE%'"

if exist "%JUNIT_DIR%\%JUNIT_FILE%" (
    echo.
    echo ✓ Telechargement reussi!
    echo.
    echo Fichier: %JUNIT_DIR%\%JUNIT_FILE%
    dir "%JUNIT_DIR%\%JUNIT_FILE%"
    echo.
    echo Le fichier JUnit est pret pour Jenkins.
    echo N'oubliez pas de l'ajouter a Git:
    echo   git add %JUNIT_DIR%\%JUNIT_FILE%
    echo   git commit -m "Ajout du JAR JUnit pour les tests"
) else (
    echo.
    echo ✗ Erreur lors du telechargement
    echo Verifiez votre connexion Internet et reessayez.
)

echo.
pause

