# 📚 CourseManager

Application RESTful de gestion de cours, étudiants, enseignants et inscriptions.

---

## 🚀 Démarrage rapide

### 1. Cloner le projet

```bash
git clone https://github.com/guilliammrst/CourseManager
cd coursemanager
```

---

### 2. Démarrage de la base de données (Docker)

```bash
docker-compose up -d
```

---

### 3. Démarrage de l’application

```bash
mvn spring-boot:run
```

---

### 4. Accès

- API : http://localhost:8080  
- Swagger : http://localhost:8080/swagger-ui.html  

---

## 🏗️ Architecture

```
controller → service → repository → database
```

---

## 🧠 Design Patterns

### ⚙️ Result Pattern
Implémenté via la classe ```Result```. 

#### A quoi ça sert ?
- A éviter les exceptions pour gérer les cas métier et à standardiser les réponses
- Ne pas utiliser les exceptions pour la logique métier
- Retourner succès / erreur proprement
- Centraliser la gestion des réponses HTTP

#### Avantages
- Code plus propre
- Pas de try/catch partout
- API cohérente
- Parfait pour les tests unitaires

### ⚙️ Strategy Pattern 
Implémenté via l'interface EnrollmentPolicy et les classes qui l'implémentent. 

#### À quoi ça sert ?
- A changer dynamiquement un comportement sans faire de if/else
- Rendre le code extensible
- Séparer les règles métier

#### Avantages
- Code extensible (ajouter un type = nouvelle classe)
- Lisibilité ++
- Test unitaire facile
- Respect du Open/Closed Principle

---

## 🔌 API

### Versioning
Ajout de ```/api/v1``` devant les routes pour pouvoir facilement ajouter une nouvelle route sans décomissioner l'ancienne de suite. 

### Students
- GET /students  
- GET /students/{id}
- GET /students/{id}/enrollments
- POST /students  
- PUT /students/{id}  
- DELETE /students/{id}  

### Teachers
- GET /teachers  
- GET /teachers/{id}
- GET /teachers/{id}/courses
- POST /teachers  
- PUT /teachers/{id}  
- DELETE /teachers/{id}  

### Courses
- GET /courses  
- GET /courses/{id}
- GET /courses/{id}/students
- POST /courses  
- PUT /courses/{id}  
- DELETE /courses/{id}  

### Enrollments
- POST /enrollments  
- PUT /enrollments/{id}  
- DELETE /enrollments/{id}  

---

## 🧪 Tests unitaires

L’application est couverte par une suite complète de tests unitaires, garantissant la fiabilité des composants critiques sans dépendre d’une base de données ou du contexte Spring complet. 

### 📊 Couverture  
✅ 93% de couverture globale des lignes de code  
✅ 100% des couches critiques couvertes :
- Controllers (API REST)
- Services (logique métier)
- Strategies (règles métier sensibles)

![coverage.png](coverage.png)
Lancement des tests avec jacoco ```mvn clean verify```.

### 🎯 Objectifs
- Vérifier la logique métier isolée
- Garantir la stabilité des endpoints REST
- Tester les cas limites et erreurs
- Éviter les régressions lors des évolutions

### ⚙️ Technologies utilisées
- JUnit 5
- Mockito
- AssertJ

### 🧩 Approche
- Les controllers sont testés avec des services mockés
- Les services sont testés indépendamment des repositories
- Les strategies sont testées comme unités de logique pure
- Aucun accès à la base de données (tests rapides et déterministes)

### 📈 Pourquoi c’est important ?

Les modules les plus sensibles (controllers, services, strategies) étant couverts à 100%, cela garantit :
- 🔒 Une robustesse élevée de l’application
- ⚡ Des tests rapides (pas de Spring Boot lancé)
- 🔁 Une évolution sécurisée du code

---

## 🗄️ Base de données

- Script ```data.sql``` lancé au démarage de l'application
- PostgreSQL (Docker)
- Hibernate auto-create :

```properties
spring.jpa.hibernate.ddl-auto=create
```

---

## ⚙️ Configuration

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mydatabase
spring.datasource.username=myuser
spring.datasource.password=secret
```

---

## 📌 Auteur

Projet réalisé dans le cadre d’un cursus ESGI par Guilliam MORISSET et Elouan LE BRAS.
