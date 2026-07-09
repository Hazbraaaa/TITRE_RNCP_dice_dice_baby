---
id: "0003"
title: "Architecture frontend"
date: 2026-07-07
status: "accepted"
authors:
  - "Clément H."
tags:
  - "architecture"
  - "frontend"
---

# 0003. Architecture frontend

## Contexte
Dice Dice Baby nécessite une interface web interactive permettant aux joueurs de créer ou rejoindre une partie, lancer les dés, sélectionner des cartes et suivre l'état du jeu.

Le frontend doit consommer l'API exposée par le backend, gérer plusieurs écrans et afficher rapidement les changements d'état d'une partie. Le choix devait donc porter à la fois sur le framework frontend et sur l'utilisation ou non d'un typage statique.

## Options envisagées
* **Option 1 : Vue.js**
  * *Avantages* : framework progressif, syntaxe accessible, séparation claire entre template, logique et style dans les composants.
  * *Inconvénients* : écosystème moins familier pour le projet, conventions spécifiques à Vue à adopter, réutilisation de logique parfois plus encadrée par le framework.

* **Option 2 : React**
  * *Avantages* : écosystème très riche, modèle par composants flexible, bonne adaptation aux interfaces interactives, forte disponibilité de ressources et de bibliothèques.
  * *Inconvénients* : plus de choix d'organisation à faire, risque de complexité si la gestion d'état n'est pas maîtrisée.

* **Option 3 : JavaScript**
  * *Avantages* : mise en place simple, moins de contraintes au démarrage.
  * *Inconvénients* : erreurs de structure de données détectées plus tard, contrats avec l'API moins explicites, refactorings plus risqués.

* **Option 4 : TypeScript**
  * *Avantages* : typage des données manipulées par le frontend, meilleure lisibilité des modèles échangés avec l'API, refactorings plus sûrs.
  * *Inconvénients* : configuration et syntaxe plus exigeantes, nécessité de maintenir les types à jour.

## Décision
Nous retenons **React** pour le framework frontend et **TypeScript** pour le typage.

React est choisi pour sa flexibilité, son écosystème et son adaptation aux interfaces composées de nombreux éléments interactifs. TypeScript est choisi en complément pour sécuriser les échanges avec l'API et rendre l'état du jeu plus explicite côté frontend.

Ce choix permet de construire une interface découpée en composants tout en limitant les erreurs liées aux données manipulées.

## Conséquences
* **Positives**
  * L'interface peut être organisée en composants réutilisables.
  * Le typage rend les données de jeu et les réponses API plus explicites.
  * Les refactorings sont plus sûrs lorsque l'interface évolue.
  * L'écosystème React facilite l'ajout de bibliothèques ou d'outils frontend.

* **Négatives**
  * React laisse plus de liberté d'organisation, ce qui demande des conventions claires.
  * TypeScript ajoute une complexité initiale par rapport à JavaScript.
  * Les types frontend doivent rester cohérents avec les données réellement renvoyées par le backend.