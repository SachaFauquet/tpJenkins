#!/bin/bash

# Script de commandes rapides pour le projet JavaJenkins

echo "=== JavaJenkins - Commandes Rapides ==="
echo ""

# Fonction pour afficher le menu
show_menu() {
    echo "Choisissez une action:"
    echo "1) Compiler le projet"
    echo "2) Exécuter les tests"
    echo "3) Générer le JAR"
    echo "4) Exécuter l'application"
    echo "5) Build complet (clean + build + test)"
    echo "6) Nettoyer le projet"
    echo "7) Afficher les informations du projet"
    echo "8) Préparer pour Git"
    echo "9) Quitter"
    echo ""
}

# Fonction pour compiler
compile() {
    echo "=== Compilation du projet ==="
    ./gradlew compileJava
}

# Fonction pour tester
test() {
    echo "=== Exécution des tests ==="
    ./gradlew test
}

# Fonction pour générer le JAR
jar() {
    echo "=== Génération du JAR ==="
    ./gradlew jar
    echo ""
    echo "JAR généré:"
    ls -lh build/libs/*.jar
}

# Fonction pour exécuter
run() {
    echo "=== Exécution de l'application ==="
    ./gradlew run
}

# Fonction pour build complet
build_all() {
    echo "=== Build complet ==="
    ./gradlew clean build
    echo ""
    echo "✓ Build terminé avec succès"
}

# Fonction pour nettoyer
clean() {
    echo "=== Nettoyage du projet ==="
    ./gradlew clean
    echo "✓ Projet nettoyé"
}

# Fonction pour afficher les infos
info() {
    echo "=== Informations du projet ==="
    echo ""
    echo "Fichiers Java:"
    find src -name "*.java"
    echo ""
    echo "Lignes de code:"
    find src -name "*.java" | xargs wc -l
    echo ""
    echo "Tests disponibles:"
    find src/test -name "*Test.java"
}

# Fonction pour Git
git_prepare() {
    echo "=== Préparation pour Git ==="
    echo ""
    echo "Fichiers à ajouter:"
    git status
    echo ""
    read -p "Voulez-vous ajouter tous les fichiers? (y/n) " -n 1 -r
    echo
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        git add .
        read -p "Message de commit: " commit_msg
        git commit -m "$commit_msg"
        echo "✓ Commit créé"
        read -p "Voulez-vous pousser vers le remote? (y/n) " -n 1 -r
        echo
        if [[ $REPLY =~ ^[Yy]$ ]]; then
            git push
            echo "✓ Code poussé vers le remote"
        fi
    fi
}

# Boucle principale
while true; do
    show_menu
    read -p "Votre choix: " choice
    echo ""

    case $choice in
        1) compile ;;
        2) test ;;
        3) jar ;;
        4) run ;;
        5) build_all ;;
        6) clean ;;
        7) info ;;
        8) git_prepare ;;
        9) echo "Au revoir!"; exit 0 ;;
        *) echo "Choix invalide" ;;
    esac

    echo ""
    read -p "Appuyez sur Entrée pour continuer..."
    clear
done

