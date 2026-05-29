# 🎲 DICE DICE BABY

Jeu de société multijoueur réalisé dans le cadre du titre RNCP « Concepteur Développeur d'Applications » (niveau 6).

[![Statut du Projet](https://img.shields.io/badge/Statut-En%20cours%20de%20d%C3%A9veloppement-yellow)](url_de_votre_board)

[![Technologies Clés](https://img.shields.io/badge/Stack-Java%2FReact%2FPostgreSQL-blue)](url_de_votre_board)

---
## 1. Quick Start (Environnement de Développement)

Cette section détaille les étapes pour lancer l'application complète (Backend, Frontend, Base de Données) via Docker Compose.

### Prérequis

* **Docker** (v20.10+)
* **Docker Compose** (v2.0+)

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

### Accès aux Services

Une fois l'orchestration Docker démarrée, les services sont disponibles sur votre machine :

* **Interface Utilisateur (Frontend React) :** `http://localhost:5173`
* **API REST (Backend Spring Boot) :** `http://localhost:8080/api/`
* **Base de Données (PostgreSQL) :** `localhost:5432` (Accessible via votre outil d'administration habituel avec les identifiants du `.env`)

---
## 2. Technologies et Architecture

Ce projet suit une architecture microservices conteneurisée utilisant la stack suivant :

| Service | Technologie | Description |
| :--- | :--- | :--- |
| **Frontend** | [React (TypeScript) + Vite](https://react.dev/) | PWA Responsive (Mobile-First), interface dynamique construite en composants réutilisables, stylisée avec TailwindCSS. |
| **Backend** | [Java (Spring Boot)](https://spring.io/guides) | API RESTful structurée en couches (Controller, Service, Repository). Sécurisée par Spring Security et tokens JWT. |
| **Base de Données** | [PostgreSQL](https://www.postgresql.org/) | Persistance relationnelle (Transactions ACID). Versioning du schéma géré par Flyway. |
| **Conteneurisation** | [Docker / Docker Compose](https://docs.docker.com/) | Builds optimisés en multi-stage. |


---
## 3. Documentation de l'API & Guide de Jeu

Pour faciliter la prise en main de l'application par les développeurs et les utilisateurs, deux documentations dédiées sont intégrées au projet :

* Documentation interactive des routes (Swagger/OpenAPI) : Elle liste l'ensemble des endpoints (Auth, Game, ...) et permet de tester les requêtes en direct. [Consulter la documentation Swagger UI](http://localhost:8080/api/swagger-ui/index.html)

> *Note : Assurez-vous que le conteneur Docker `dev_back` est bien actif sur le port 8080 avant de lancer le lien.*. 

* Guide officiel des règles du jeu : Pour comprendre les mécaniques de Dice Dice Baby (lancers de dés, verrouillage, combinaisons de cartes), veuillez consulter le [Guide officiel des règles du jeu](./USER_GUIDE.md).

---
## 4. Structure du Répertoire

```Plain text
dice-dice-baby/
├── backend/            # Code source Java / Spring Boot (Maven)
├── frontend/           # Code source TypeScript / React (Vite)
├── .env.example        # Modèle des variables d'environnement (Dev)
├── docker-compose.yml  # Orchestrateur des conteneurs locaux
└── README.md           # Documentation principale
```

---
## 5. Licence et Auteur

* **Auteur :** Clément HAZERA
* **Licence :** [MIT License](https://opensource.org/licenses/MIT)

> Le code source est sous licence MIT, ce qui permet la réutilisation et la modification à des fins personnelles et éducatives, **tout en conservant le droit d'auteur au profit de l'auteur original**. Veuillez consulter le fichier [LICENSE](LICENSE) pour plus de détails.