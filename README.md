# Projet Etudiant Openclassrooms n°6 – Complétez votre backend pour rendre votre application plus sécurisée


<img src="/preview.png" alt="Logo de l'application">


<h1 align="center">Poseidon Capital Solutions</h1>


## Description

Le projet est une application Spring Boot conçue à des fins éducatives dans le cadre d'un projet étudiant OpenClassrooms. Il se concentre sur l'amélioration de la sécurité du backend avec Spring Security et inclut des fonctionnalités telles que l'inscription et l'authentification des utilisateurs, ainsi que des opérations CRUD complètes pour divers types de données.


## Prérequis
- Java 17
- Maven 3.6.0+
- MySQL


## Installation

1. Clonez le dépôt :
    ```sh
    git clone https://github.com/Xenophee/Poseidon-Capital-Solutions.git
    ```

2. Configurez la base de données :
    - Créez une base de données MySQL nommée `poseidon`.
    - Mettez à jour le fichier `application.properties` avec votre configuration de base de données.


3. Construisez le projet :
    ```sh
    mvn clean install
    ```

4. Exécutez l'application :
    ```sh
    mvn spring-boot:run
    ```

## Utilisation

- Accédez à l'application à l'adresse `http://localhost:8080`.
- Identifiants de connexion par défaut :
    - Admin : `admin` / `Abc123@!`
    - Utilisateur : `user` / `Abc123@!`


## Exécution des tests

Pour exécuter les tests, utilisez la commande suivante :

```sh
mvn test
```

Pour consulter le rapport de couverture de code, le fichier `index.html` est généré dans le dossier `target/site/jacoco`.
