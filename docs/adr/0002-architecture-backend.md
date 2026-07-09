---
id: "0002"
title: "Architecture backend"
date: 2026-07-07
status: "accepted"
authors:
  - "Clément H."
tags:
  - "architecture"
  - "backend"
---

# 0002. Architecture backend

## Contexte
Dice Dice Baby nécessite un backend capable de gérer les règles de jeu, les parties, les joueurs, l'authentification et la persistance des données.

Le backend doit exposer une API consommée par le frontend, tout en gardant les règles principales du jeu côté serveur. L'objectif est d'éviter que le client puisse modifier facilement l'état d'une partie ou contourner les règles.

## Options envisagées
* **Option 1 : Node.js / Express**
  * *Avantages* : développement rapide, écosystème JavaScript commun avec le frontend, grande flexibilité.
  * *Inconvénients* : structure à définir entièrement, risque de disperser la logique métier si le projet grandit, choix supplémentaires nécessaires pour la persistance et la sécurité.

* **Option 2 : Python / FastAPI**
  * *Avantages* : framework moderne, API rapide à développer, documentation OpenAPI intégrée.
  * *Inconvénients* : écosystème différent du frontend, moins adapté si l'équipe vise une structure fortement orientée couches et typage strict.

* **Option 3 : Java / Spring Boot**
  * *Avantages* : framework robuste, architecture en couches naturelle, intégration avec JPA, validation, tests et sécurité.
  * *Inconvénients* : plus verbeux, temps de mise en place initial plus important, conventions plus nombreuses.

## Décision
Nous retenons l'**Option 3 : Java / Spring Boot**.

Ce choix permet de structurer le backend autour de responsabilités claires : contrôleurs pour l'API REST, services pour la logique métier, repositories pour l'accès aux données et entités pour le modèle persistant.

Spring Boot est adapté au besoin du projet : règles de jeu côté serveur, persistance relationnelle, authentification et évolution progressive vers une application plus sécurisée.

## Conséquences
* **Positives**
  * La logique métier peut être centralisée côté backend.
  * L'architecture en couches facilite la maintenance et les tests.
  * L'intégration avec la persistance, la validation et la sécurité est facilitée.
  * Le projet dispose d'une base solide pour évoluer après le MVP.

* **Négatives**
  * Le développement initial est plus lourd qu'avec un backend minimal.
  * Le code peut être plus verbeux pour des fonctionnalités simples.
  * Il faut respecter les conventions de structure pour éviter des services trop volumineux ou des responsabilités mélangées.