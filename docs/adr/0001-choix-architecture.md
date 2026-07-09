---
id: "0001"
title: "Choix d'architecture"
date: 2026-07-07
status: "accepted"
authors:
  - "Clément H."
tags:
  - "architecture"
  - "global"
---

# 0001. Choix d'architecture

## Contexte
Dice Dice Baby est composé d'un backend, d'un frontend web et d'une configuration Docker Compose à la racine du projet.

Le frontend et le backend évoluent souvent ensemble : ajout d'endpoints, adaptation des DTO, modification des règles de jeu ou ajustement de l'interface. Il fallait donc choisir une organisation simple à maintenir pour un MVP développé par une personne.

## Options envisagées
* **Repositories séparés**
  * Avantages : séparation claire des cycles de vie et des pipelines.
  * Inconvénients : coordination plus lourde, risque de désynchronisation entre frontend et backend.

* **Monorepo**
  * Avantages : changements front/back dans un même commit, configuration Docker centralisée, documentation commune.
  * Inconvénients : CI à affiner par chemin, conventions de dossier à respecter.

## Décision
Nous retenons une architecture en **monorepo**, avec deux dossiers principaux : `backend/` et `frontend/`.

Ce choix facilite le développement du MVP tout en gardant une séparation claire des responsabilités : le backend porte les règles métier et l'API, le frontend consomme cette API.

## Conséquences
* **Positives**
  * Les évolutions fonctionnelles peuvent être versionnées de manière cohérente dans un même commit ou une même pull request.
  * La configuration locale reste centralisée à la racine du projet avec Docker Compose.
  * La documentation, les ADR et la CI sont regroupés au même endroit.

* **Négatives**
  * La CI devra être ajustée pour éviter de lancer tous les tests à chaque modification.
  * Les conventions de dossier devront rester claires pour éviter de mélanger les responsabilités du frontend et du backend.
  * Ce choix pourra être réévalué si l'équipe ou le produit grandit fortement.