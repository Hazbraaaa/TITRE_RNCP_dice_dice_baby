# Guide de Jeu - Dice Dice Baby

Bienvenue dans le guide officiel de Dice Dice Baby, une adaptation numérique du jeu Piña Coladice. Ce guide détaille les règles de jeu et le parcours utilisateur au sein de l'application.

## 1. Modes de Connexion et Profils
L'application propose deux façons d'accéder à l'interface de jeu :

- Mode Authentifié : Permet aux utilisateurs (Comptes Parents/Enfants) de se connecter via un flux sécurisé (JWT). Ce mode est indispensable pour sauvegarder l'historique des scores et alimenter le module de statistiques personnelles.

- Mode Invité (Guest) : Permet de lancer une partie instantanément sans créer de compte. Les scores et statistiques de ce profil ne seront pas persistés en base de données à la fin de la partie.

## 2. Configuration de la Partie (Le Lobby)
Avant de lancer les dés, les joueurs se retrouvent sur plusieurs  écrans de configuration :

- Choix du mode de jeu: sur un écran partagé ou sur plusieurs écrans (actuellement en devéloppement)

- Choix du nombre de joueurs.

- Attribution des profils (Sélection d'un compte existant ou d'un profil invité).

- Initialisation du plateau de jeu et de la main de dés par le serveur.

## 3. Mécaniques du Tour de Jeu (Gameplay)
Le jeu mêle la stratégie du Yam's (combinaisons de dés) et le placement du Morpion (alignement sur un plateau).

- Le lancer de dés : À son tour, un joueur dispose de 3 lancers maximum.

- Le verrouillage : Entre chaque lancer, le joueur peut choisir de "verrouiller" (conserver) un ou plusieurs dés de sa main et ne relancer que les autres.

- Validation sur le plateau : Une fois ses lancers terminés (ou s'il est satisfait avant le 3ème lancer), le joueur doit associer sa combinaison finale de dés à une carte disponible sur le plateau interactif.

- Changement de tour : Une fois le score ou le jeton validé, la main passe automatiquement au joueur suivant.

## 4. Différences avec la Version Physique (Notes de Version 1.0)
Dans cette première version numérique (MVP), l'accent a été mis sur la robustesse du moteur de jeu et l'expérience en local :

- Boucle de jeu : L'application gère l'enchaînement infini des tours et des manches.

- Fin de partie : La détection automatique de fin de partie (alignement d'une ligne/colonne de jetons ou atteinte du score plafond) est en cours de développement pour la version 2.0. Les joueurs peuvent actuellement réinitialiser ou quitter la session à leur convenance depuis l'interface.