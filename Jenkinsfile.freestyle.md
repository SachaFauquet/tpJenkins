# Configuration Pipeline Freestyle Jenkins

Ce document décrit la configuration manuelle d'un pipeline Jenkins Freestyle pour le projet JavaJenkins.

## 📋 Configuration étape par étape

### 1. Créer un nouveau Job

1. Dans Jenkins Dashboard, cliquer sur **"New Item"**
2. Nom du job : `JavaJenkins-Factorielle-Freestyle`
3. Sélectionner **"Freestyle project"**
4. Cliquer sur **"OK"**

---

## ⚙️ Configuration Générale

### General Section

- **Description** :
  ```
  Pipeline Freestyle pour l'application Java de calcul de factorielle.
  Compile, teste et génère un JAR avec différentes conditions selon l'environnement.
  ```

- ☑️ **This project is parameterized**
  
  **Paramètre 1 : ENVIRONMENT**
  - Type : `Choice Parameter`
  - Name : `ENVIRONMENT`
  - Choices :
    ```
    dev
    test
    prod
    ```
  - Description : `Environnement de déploiement`

  **Paramètre 2 : VERSION**
  - Type : `String Parameter`
  - Name : `VERSION`
  - Default Value : `1.0-SNAPSHOT`
  - Description : `Version de l'application`

  **Paramètre 3 : RUN_TESTS**
  - Type : `Boolean Parameter`
  - Name : `RUN_TESTS`
  - Default Value : ☑️ (coché)
  - Description : `Exécuter les tests unitaires`

  **Paramètre 4 : BUILD_JAR**
  - Type : `Boolean Parameter`
  - Name : `BUILD_JAR`
  - Default Value : ☑️ (coché)
  - Description : `Générer le fichier JAR`

---

## 📦 Source Code Management

### Git

- **Repository URL** : 
  ```
  https://github.com/votre-username/JavaJenkins.git
  ```
  OU
  ```
  https://gitlab.com/votre-username/JavaJenkins.git
  ```

- **Credentials** : 
  - Cliquer sur **"Add"** → **"Jenkins"**
  - Kind : `Username with password`
  - Username : `votre-username`
  - Password : `votre-token-accès-personnel`
  - ID : `git-credentials`
  - Description : `Git Credentials`
  - Puis sélectionner les credentials créés

- **Branches to build** :
  - Branch Specifier : `*/main` (ou `*/master`, `*/dev`, etc.)

- **Additional Behaviours** :
  - Ajouter : **"Clean before checkout"**
  - Ajouter : **"Check out to specific local branch"**
    - Branch name : `main`

---

## 🏗️ Build Environment

- ☑️ **Delete workspace before build starts**
  - Nettoie le workspace avant chaque build

- ☑️ **Add timestamps to the Console Output**
  - Ajoute des horodatages aux logs

- ☑️ **Color ANSI Console Output** (si plugin installé)
  - Rend les logs plus lisibles

---

## 🔨 Build Steps

### Étape 1 : Affichage des informations

**Type** : `Execute shell` (Linux/Mac) ou `Execute Windows batch command` (Windows)

**Linux/Mac :**
```bash
echo "=== Informations du Build ==="
echo "Environnement: $ENVIRONMENT"
echo "Version: $VERSION"
echo "Tests: $RUN_TESTS"
echo "Build JAR: $BUILD_JAR"
echo "Branche Git: $GIT_BRANCH"
echo "Commit: $GIT_COMMIT"
```

**Windows :**
```batch
echo === Informations du Build ===
echo Environnement: %ENVIRONMENT%
echo Version: %VERSION%
echo Tests: %RUN_TESTS%
echo Build JAR: %BUILD_JAR%
echo Branche Git: %GIT_BRANCH%
```

---

### Étape 2 : Vérification du JAR JUnit

**Type** : `Execute shell` (Linux/Mac) ou `Execute Windows batch command` (Windows)

**Linux/Mac :**
```bash
echo "=== Vérification du JAR JUnit ==="
if [ -f "src/main/java/org/example/junit-platform-console-standalone-1.9.3.jar" ]; then
    echo "✓ Fichier JUnit trouvé"
    ls -lh src/main/java/org/example/junit-platform-console-standalone-1.9.3.jar
else
    echo "⚠ Fichier JUnit non trouvé, le build continuera avec Gradle"
fi
```

**Windows :**
```batch
echo === Verification du JAR JUnit ===
if exist "src\main\java\org\example\junit-platform-console-standalone-1.9.3.jar" (
    echo JUnit JAR trouve
) else (
    echo JUnit JAR non trouve, le build continuera avec Gradle
)
```

---

### Étape 3 : Compilation

**Type** : `Invoke Gradle script`

- **Use Gradle Wrapper** : ☑️
- **Tasks** : `clean compileJava`
- **Switches** : `--info`

OU **Type** : `Execute shell` / `Execute Windows batch command`

**Linux/Mac :**
```bash
echo "=== Compilation du code Java ==="
./gradlew clean compileJava --info
echo "✓ Compilation réussie"
```

**Windows :**
```batch
echo === Compilation du code Java ===
gradlew.bat clean compileJava --info
echo Compilation reussie
```

---

### Étape 4 : Compilation des tests

**Type** : `Invoke Gradle script`

- **Use Gradle Wrapper** : ☑️
- **Tasks** : `compileTestJava`

OU **Execute shell/batch** :

**Linux/Mac :**
```bash
echo "=== Compilation des tests ==="
./gradlew compileTestJava
```

**Windows :**
```batch
echo === Compilation des tests ===
gradlew.bat compileTestJava
```

---

### Étape 5 : Exécution des tests unitaires

**Type** : `Conditional step (single)` (nécessite le plugin Conditional BuildStep)

**Condition** : `Boolean condition`
- Token : `${RUN_TESTS}`

**Build Step** : `Invoke Gradle script`
- **Use Gradle Wrapper** : ☑️
- **Tasks** : `test`
- **Switches** : `--info`

OU sans plugin conditionnel, **Execute shell/batch** :

**Linux/Mac :**
```bash
if [ "$RUN_TESTS" = "true" ]; then
    echo "=== Exécution des tests unitaires ==="
    ./gradlew test --info || true
    echo "✓ Tests terminés"
else
    echo "⊘ Tests désactivés"
fi
```

**Windows :**
```batch
if "%RUN_TESTS%"=="true" (
    echo === Execution des tests unitaires ===
    gradlew.bat test --info
    echo Tests termines
) else (
    echo Tests desactives
)
```

---

### Étape 6 : Tests spécifiques par environnement

**Type** : `Execute shell` / `Execute Windows batch command`

**Linux/Mac :**
```bash
echo "=== Tests spécifiques à l'environnement: $ENVIRONMENT ==="

case $ENVIRONMENT in
    dev)
        echo "📘 Environnement DEV"
        echo "Exécution des tests de développement"
        ;;
    test)
        echo "📗 Environnement TEST/QA"
        echo "Exécution des tests de validation complète"
        ;;
    prod)
        echo "📕 Environnement PRODUCTION"
        echo "Vérifications de sécurité et smoke tests"
        ;;
    *)
        echo "⚠ Environnement non reconnu: $ENVIRONMENT"
        ;;
esac
```

**Windows :**
```batch
echo === Tests specifiques a l'environnement: %ENVIRONMENT% ===

if "%ENVIRONMENT%"=="dev" (
    echo Environnement DEV
    echo Execution des tests de developpement
) else if "%ENVIRONMENT%"=="test" (
    echo Environnement TEST/QA
    echo Execution des tests de validation complete
) else if "%ENVIRONMENT%"=="prod" (
    echo Environnement PRODUCTION
    echo Verifications de securite et smoke tests
) else (
    echo Environnement non reconnu: %ENVIRONMENT%
)
```

---

### Étape 7 : Génération du JAR

**Type** : `Conditional step (single)`

**Condition** : `Boolean condition`
- Token : `${BUILD_JAR}`

**Build Step** : `Invoke Gradle script`
- **Use Gradle Wrapper** : ☑️
- **Tasks** : `jar`

OU **Execute shell/batch** :

**Linux/Mac :**
```bash
if [ "$BUILD_JAR" = "true" ]; then
    echo "=== Génération du fichier JAR ==="
    echo "Version: $VERSION"
    ./gradlew jar
    echo "✓ JAR généré"
    ls -lh build/libs/*.jar
else
    echo "⊘ Génération du JAR désactivée"
fi
```

**Windows :**
```batch
if "%BUILD_JAR%"=="true" (
    echo === Generation du fichier JAR ===
    echo Version: %VERSION%
    gradlew.bat jar
    echo JAR genere
    dir build\libs\*.jar
) else (
    echo Generation du JAR desactivee
)
```

---

### Étape 8 : Analyse du code

**Type** : `Execute shell` / `Execute Windows batch command`

**Linux/Mac :**
```bash
echo "=== Analyse du code ==="
echo "Comptage des lignes de code Java:"
find src -name "*.java" | xargs wc -l
echo ""
echo "Nombre de fichiers Java:"
find src -name "*.java" | wc -l
```

**Windows :**
```batch
echo === Analyse du code ===
echo Fichiers Java dans le projet:
dir /s /b src\*.java
```

---

### Étape 9 : Déploiement conditionnel

**Type** : `Execute shell` / `Execute Windows batch command`

**Linux/Mac :**
```bash
echo "=== Déploiement en environnement $ENVIRONMENT ==="

case $ENVIRONMENT in
    dev)
        echo "📦 Déploiement automatique en DEV"
        echo "Copie des artefacts vers le serveur de dev"
        # cp build/libs/*.jar /path/to/dev/server/
        ;;
    test)
        echo "📦 Déploiement en TEST"
        echo "Copie des artefacts vers le serveur de test"
        echo "Exécution des tests d'intégration"
        # cp build/libs/*.jar /path/to/test/server/
        ;;
    prod)
        echo "📦 Déploiement en PRODUCTION"
        echo "⚠ ATTENTION: Déploiement en production!"
        echo "Validation manuelle requise"
        # Copie vers production (nécessite validation)
        # cp build/libs/*.jar /path/to/prod/server/
        ;;
esac

echo "✓ Déploiement $ENVIRONMENT terminé"
```

**Windows :**
```batch
echo === Deploiement en environnement %ENVIRONMENT% ===

if "%ENVIRONMENT%"=="dev" (
    echo Deploiement automatique en DEV
    echo Copie des artefacts vers le serveur de dev
    rem copy build\libs\*.jar \\dev-server\path\
) else if "%ENVIRONMENT%"=="test" (
    echo Deploiement en TEST
    echo Copie des artefacts vers le serveur de test
    rem copy build\libs\*.jar \\test-server\path\
) else if "%ENVIRONMENT%"=="prod" (
    echo Deploiement en PRODUCTION
    echo ATTENTION: Deploiement en production!
    rem copy build\libs\*.jar \\prod-server\path\
)

echo Deploiement %ENVIRONMENT% termine
```

---

## 📊 Post-build Actions

### 1. Publish JUnit test result report

- **Test report XMLs** : 
  ```
  **/build/test-results/test/*.xml
  ```
- ☑️ **Retain long standard output/error**
- ☑️ **Do not fail the build on empty test results**

---

### 2. Archive the artifacts

- **Files to archive** :
  ```
  **/build/libs/*.jar
  ```
- ☑️ **Fingerprint Artifacts**
- **Advanced** :
  - ☑️ **Archive artifacts only if build is successful**

---

### 3. Email Notification (optionnel)

- **Recipients** : `votre-email@example.com`
- ☑️ **Send e-mail for every unstable build**
- ☑️ **Send separate e-mails to individuals who broke the build**

---

### 4. Build other projects (optionnel)

- **Projects to build** : `JavaJenkins-Factorielle-Deploy`
- **Trigger only if build is stable**

---

### 5. Editable Email Notification (optionnel, nécessite le plugin)

**Project Recipient List** : `votre-email@example.com`

**Triggers** :
- Success
- Failure
- Unstable

**Subject** :
```
Jenkins Build $BUILD_STATUS: $PROJECT_NAME - #$BUILD_NUMBER
```

**Content** :
```
Build: $BUILD_NUMBER
Environment: $ENVIRONMENT
Status: $BUILD_STATUS
Duration: $BUILD_DURATION

Changes:
$CHANGES

Console Output:
${BUILD_URL}console

Tests: ${TEST_COUNTS}
```

---

## 🔄 Build Triggers

### Poll SCM

- ☑️ **Poll SCM**
- **Schedule** : 
  ```
  H/5 * * * *
  ```
  (Vérifie Git toutes les 5 minutes)

### GitHub hook trigger (si GitHub)

- ☑️ **GitHub hook trigger for GITScm polling**

### Build periodically

- **Schedule** (pour tests nocturnes) :
  ```
  H 2 * * *
  ```
  (Tous les jours à 2h du matin)

---

## 🎯 Configuration selon les branches

Pour configurer différents comportements selon les branches:

### Option 1 : Jobs multiples

Créer 3 jobs différents:
- `JavaJenkins-Dev` (branche: */dev)
- `JavaJenkins-Test` (branche: */test)
- `JavaJenkins-Prod` (branche: */main)

### Option 2 : Branches multiples dans un seul job

**Source Code Management** → **Branches to build** :
```
*/dev
*/test
*/main
```

Puis dans les scripts shell, utiliser `$GIT_BRANCH` :
```bash
case $GIT_BRANCH in
    origin/dev)
        ENVIRONMENT="dev"
        ;;
    origin/test)
        ENVIRONMENT="test"
        ;;
    origin/main)
        ENVIRONMENT="prod"
        ;;
esac
```

---

## 📝 Notes importantes

### Variables d'environnement disponibles

- `$BUILD_NUMBER` : Numéro du build
- `$BUILD_ID` : ID du build
- `$JOB_NAME` : Nom du job
- `$WORKSPACE` : Chemin du workspace
- `$GIT_BRANCH` : Branche Git
- `$GIT_COMMIT` : Hash du commit
- `$ENVIRONMENT` : Paramètre environnement
- `$VERSION` : Paramètre version
- `$RUN_TESTS` : Paramètre tests
- `$BUILD_JAR` : Paramètre JAR

### Plugins recommandés

1. **Git Plugin** - Intégration Git
2. **JUnit Plugin** - Publication des résultats de tests
3. **Conditional BuildStep Plugin** - Étapes conditionnelles
4. **Parameterized Trigger Plugin** - Paramètres
5. **Email Extension Plugin** - Notifications email avancées
6. **AnsiColor Plugin** - Couleurs dans la console
7. **Timestamper Plugin** - Horodatage des logs
8. **Build Name and Description Setter** - Noms de builds personnalisés

---

## ✅ Validation

Après la configuration, tester avec différents paramètres:

1. **Build Dev** : ENVIRONMENT=dev, RUN_TESTS=true, BUILD_JAR=true
2. **Build Test** : ENVIRONMENT=test, RUN_TESTS=true, BUILD_JAR=true
3. **Build Prod** : ENVIRONMENT=prod, RUN_TESTS=true, BUILD_JAR=true
4. **Build sans tests** : RUN_TESTS=false
5. **Build sans JAR** : BUILD_JAR=false

---

## 🆘 Dépannage

### Erreur "gradlew: Permission denied"

```bash
chmod +x gradlew
git add gradlew
git commit -m "Fix gradlew permissions"
git push
```

### Erreur Git credentials

- Vérifier les credentials dans Jenkins
- Utiliser un Personal Access Token au lieu du mot de passe
- Pour GitHub : Settings → Developer settings → Personal access tokens

### Tests qui échouent

- Vérifier les logs dans Console Output
- Vérifier les rapports de tests dans Test Result
- Exécuter localement : `./gradlew test --info`

---

## 📚 Ressources

- [Jenkins Freestyle Projects](https://www.jenkins.io/doc/book/pipeline/getting-started/#defining-a-pipeline-in-scm)
- [Jenkins Parameters](https://www.jenkins.io/doc/book/pipeline/syntax/#parameters)
- [JUnit Plugin](https://plugins.jenkins.io/junit/)

