# 🏦 Metz Numeric Bank

> Projet éducatif réalisé dans le cadre du module **Architecture Logicielle**  
> **Metz Numeric School – Promotion M2i 2024/2026**

---

## 🌐 Présentation

**Metz Numeric Bank (MNB)** est une application bancaire développée à des fins pédagogiques.  
Elle a pour objectif de mettre en pratique les principes d’**architecture logicielle** à travers une approche **microservices**.  

L’application simule les fonctionnalités essentielles d’une banque en ligne :  
- Gestion des **clients**  
- Gestion des **comptes**  
- Consultation des **soldes et opérations**  
- Centralisation des données via un service de **persistance**  

---

## 🧱 Architecture technique

Technologies et frameworks principaux :  
- **Spring Boot** → Backend REST pour chaque microservice  
- **Spring Cloud Gateway** → point d’entrée unique de l’application  
- **Spring Data JPA** + **Hibernate** → gestion de la persistance  
- **H2 Database** → base embarquée pour les tests  
- **Lombok** → réduction du boilerplate Java  
- **Feign Client** → communication inter-services  

---

## 👥 Équipe projet

- **Chef de Projet** : Benjamin Lecossois  
- **Architecte Logiciel** : Lilian Flipo  
- **Développeur** : Nicolas Frau  
- **Testeur** : Thomas Ceglarski  

---

## 🚀 Déploiement local

1. Clonez le dépôt et récupérez les services :  
   - `gateway-service`  
   - `account-service`  
   - `bank-service`  
   - `persistence-service`  

2. Lancez chaque service en parallèle (par défaut sur leurs ports respectifs).  

3. Toutes les requêtes doivent passer par la **Gateway** : http://localhost:8080
4. Les **endpoints** disponibles sont documentés dans chaque microservice.

---

## 🐳 Déploiement via Docker

Une configuration **docker-compose** est fournie.  
Depuis le répertoire du projet, exécutez :  

```bash
docker-compose up --build