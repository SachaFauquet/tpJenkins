@echo off
REM Script de commandes rapides pour le projet JavaJenkins (Windows)

:menu
cls
echo === JavaJenkins - Commandes Rapides ===
echo.
echo Choisissez une action:
echo 1) Compiler le projet
echo 2) Executer les tests
echo 3) Generer le JAR
echo 4) Executer l'application
echo 5) Build complet (clean + build + test)
echo 6) Nettoyer le projet
echo 7) Afficher les informations du projet
echo 8) Quitter
echo.

set /p choice="Votre choix: "

if "%choice%"=="1" goto compile
if "%choice%"=="2" goto test
if "%choice%"=="3" goto jar
if "%choice%"=="4" goto run
if "%choice%"=="5" goto build_all
if "%choice%"=="6" goto clean
if "%choice%"=="7" goto info
if "%choice%"=="8" goto quit
goto invalid

:compile
echo === Compilation du projet ===
call gradlew.bat compileJava
pause
goto menu

:test
echo === Execution des tests ===
call gradlew.bat test
pause
goto menu

:jar
echo === Generation du JAR ===
call gradlew.bat jar
echo.
echo JAR genere:
dir build\libs\*.jar
pause
goto menu

:run
echo === Execution de l'application ===
call gradlew.bat run
pause
goto menu

:build_all
echo === Build complet ===
call gradlew.bat clean build
echo.
echo Build termine avec succes
pause
goto menu

:clean
echo === Nettoyage du projet ===
call gradlew.bat clean
echo Projet nettoye
pause
goto menu

:info
echo === Informations du projet ===
echo.
echo Fichiers Java:
dir /s /b src\*.java
echo.
echo Tests disponibles:
dir /s /b src\test\*Test.java
echo.
pause
goto menu

:invalid
echo Choix invalide
pause
goto menu

:quit
echo Au revoir!
exit /b 0

