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

- Repository Pattern  
- Service Layer  
- DTO Pattern  
- Result Pattern  
- Strategy Pattern (optionnel)  
- Dependency Injection  

---

## 🔌 API

### Students
- GET /students  
- GET /students/{id}  
- POST /students  
- PUT /students/{id}  
- DELETE /students/{id}  

### Teachers
- GET /teachers  
- GET /teachers/{id}  
- POST /teachers  
- PUT /teachers/{id}  
- DELETE /teachers/{id}  

### Courses
- GET /courses  
- GET /courses/{id}  
- POST /courses  
- PUT /courses/{id}  
- DELETE /courses/{id}  

### Enrollments
- POST /enrollments  
- PUT /enrollments/{id}  
- DELETE /enrollments/{id}  

---

## 🗄️ Base de données

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
