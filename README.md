# 🎲 DICE DICE BABY

Jeu de société multijoueur réalisé dans le cadre du titre RNCP « Concepteur Développeur d'Applications ».

[![Statut du Projet](https://img.shields.io/badge/Statut-En%20cours%20de%20d%C3%A9veloppement-yellow)](url_de_votre_board)

[![Technologies Clés](https://img.shields.io/badge/Stack-Java%2FReact%2FPostgreSQL-blue)](url_de_votre_board)

---
## 1. Quick Start (Lancement en Développement)

Cette section détaille les étapes pour lancer l'application complète (Backend, Frontend, Base de Données) via Docker Compose.

### Prérequis

* **Docker**
* **Docker Compose**

### Étapes de Lancement

1.  **Clonage du dépôt :**
    ```bash
    git clone [URL_DE_VOTRE_REPO]
    cd [NOM_DU_REPO]
    ```

2.  **Configuration des variables d'environnement :**
    Créez le fichier de configuration de l'environnement de développement à partir du modèle :
    ```bash
    cp .env.example .env
    ```
    *(Veuillez vérifier et ajuster les valeurs nécessaires dans le nouveau fichier `.env`)*

3.  **Lancement des services :**
    L'option `--build` permet de reconstruire les images si nécessaire.
    ```bash
    docker compose up --build
    ```

### Accès

Une fois les services lancés, vous pouvez y accéder via les adresses suivantes :

* **Application (Frontend) :** `http://localhost:5173`
* **API (Backend) :** `http://localhost:8080/api/`
* **Base de Données :** Accessible uniquement via le service `db` à l'intérieur du réseau Docker.

---
## 2. Technologies et Architecture

Ce projet suit une architecture microservices conteneurisée utilisant la stack suivant :

| Service | Technologie | Description |
| :--- | :--- | :--- |
| **Backend** | [Java (Spring Boot)](https://spring.io/guides) | Gestion de l'état du jeu, logique métier, API RESTful **et gestion des échanges en temps réel (WebSockets)**. |
| **Frontend** | [React](https://react.dev/) | Interface utilisateur dynamique, affichage du plateau, gestion des interactions utilisateur. |
| **Base de Données** | [PostgreSQL](https://www.postgresql.org/) | Persistance des données (utilisateurs, parties en cours, historique des scores). |
| **Conteneurisation** | [Docker / Docker Compose](https://docs.docker.com/) | Environnements de développement et de test isolés et reproductibles. |


---
## 3. Fonctionnalités Implémentées

Voici un aperçu des fonctionnalités clés de l'application :

* **Authentification :** Enregistrement et connexion des utilisateurs.
* **Salon de Jeu :** Création et gestion de parties.
* **Logique de Jeu :** Gestion des tours, lancement des dés et application des règles.
* **Plateau Dynamique :** Mise à jour en temps réel de l'état du jeu pour tous les joueurs.
* **Historique :** Sauvegarde et consultation des parties terminées.

---
## 4. Structure du Répertoire

* `/backend` : Contient le code source de l'application Spring Boot.
* `/frontend` : Contient le code source de l'interface utilisateur React.
* `.env.example` : Fichier modèle pour la configuration des variables d'environnement.
* `docker-compose.yml` : Fichier d'orchestration Docker.

---
## 5. Licence et Auteur

* **Auteur :** Clément HAZERA
* **Licence :** [MIT License](https://opensource.org/licenses/MIT)

> Le code source est sous licence MIT, ce qui permet la réutilisation et la modification à des fins personnelles et éducatives, **tout en conservant le droit d'auteur au profit de l'auteur original**. Veuillez consulter le fichier [LICENSE](LICENSE) pour plus de détails.