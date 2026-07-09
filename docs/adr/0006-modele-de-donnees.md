---
id: "0006"
title: "Modèle de données"
date: 2026-07-07
status: "accepted"
authors:
  - "Clément H."
tags:
  - "architecture"
  - "database"
  - "backend"
---

# 0006. Modèle de données

## Contexte
Dice Dice Baby doit conserver les informations nécessaires au fonctionnement d'une partie : utilisateurs, joueurs, parties, état du plateau, cartes, lancers de dés et progression du jeu.

Ces données sont liées entre elles : un utilisateur peut participer à plusieurs parties, une partie contient plusieurs joueurs, un plateau contient plusieurs cartes, et l'état du jeu évolue au fil des tours. Le choix du modèle de données doit donc permettre de représenter ces relations de manière claire et maintenable.

## Options envisagées
* **Option 1 : Base relationnelle SQL**
  * *Avantages* : relations explicites entre les entités, contraintes d'intégrité, requêtes adaptées aux utilisateurs, parties, joueurs et historiques.
  * *Inconvénients* : schéma à concevoir précisément, évolutions de structure à gérer avec attention.

* **Option 2 : Base NoSQL orientée documents**
  * *Avantages* : stockage flexible de l'état d'une partie, structure facile à faire évoluer, peu de contraintes sur le format des données.
  * *Inconvénients* : relations entre utilisateurs, parties et joueurs moins explicites, cohérence davantage gérée dans le code applicatif.

* **Option 3 : Base orientée graphe**
  * *Avantages* : très adaptée aux données fortement connectées et aux requêtes de parcours entre relations.
  * *Inconvénients* : complexité supplémentaire pour un MVP, moins adaptée aux besoins principaux du projet, intégration moins directe avec l'écosystème Spring/JPA classique.

## Décision
Nous retenons l'**Option 1 : base relationnelle SQL**.

Le modèle de Dice Dice Baby repose sur des entités clairement identifiables et liées entre elles : utilisateurs, parties, joueurs, cartes, plateau et actions de jeu. Une base relationnelle permet de représenter ces liens simplement, tout en s'appuyant sur des contraintes d'intégrité pour limiter les incohérences.

## Conséquences
* **Positives**
  * Les relations entre les entités du jeu sont explicites.
  * La cohérence des données peut être renforcée par les contraintes de la base.
  * Le modèle pourra évoluer vers un historique de parties ou des statistiques.

* **Négatives**
  * Le schéma doit être maintenu avec rigueur lorsque les règles de jeu évoluent.
  * Les changements de structure peuvent nécessiter des migrations.
  * Certaines données temporaires de jeu peuvent être plus lourdes à représenter qu'avec un modèle document.