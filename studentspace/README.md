# 🎓 StudentSpace — Plateforme Complète (v3 : Fusionnée + BI)

**Auteurs :** Abdessamad Ouchaib · Khalid El Issaoui  
**Établissement :** Faculté des Sciences — Université Mohammed V, Rabat  
**Projet de Fin d'Études 2024-2025**

---

## 📦 Contenu de ce dossier fusionné

```
studentspace/
├── backend/                        ← Spring Boot (Java 21)
│   ├── pom.xml                     ← Dépendances Maven
│   └── src/main/java/com/studentspace/
│       ├── model/                  ← Entités JPA
│       ├── dto/                    ← Objets de transfert
│       ├── repository/             ← Accès base de données
│       ├── service/
│       │   └── BiService.java      ★ MODULE BI — Logique analytique
│       ├── controller/
│       │   └── BiController.java   ★ MODULE BI — 8 endpoints REST
│       ├── config/
│       │   ├── SecurityConfig.java ← JWT + CORS (patché pour /api/bi/**)
│       │   └── JwtAuthFilter.java
│       └── exception/
│
├── frontend/                       ← Angular 17 + TailwindCSS
│   └── src/app/
│       ├── admin/
│       │   └── admin.component.ts  ★ PATCHÉ — Menu BI intégré
│       ├── bi/
│       │   └── bi-dashboard.component.ts  ★ MODULE BI — Dashboard complet
│       ├── auth/                   ← Page de connexion
│       ├── student/                ← Espace étudiant
│       ├── teacher/                ← Espace enseignant
│       └── shared/                 ← Services, modèles
│
└── database/
    ├── init.sql                    ← Script SQL initial
    └── init_corrige.sql            ← Script SQL corrigé (recommandé)
```

---

## 🚀 Démarrage rapide (Windows)

### Prérequis
| Outil | Version | Lien |
|-------|---------|------|
| Java JDK | 21 | https://adoptium.net |
| Maven | 3.9+ | https://maven.apache.org |
| Node.js | 18+ | https://nodejs.org |
| PostgreSQL | 15+ | https://postgresql.org |
| Angular CLI | 17+ | `npm install -g @angular/cli` |

---

### Étape 1 — Base de données

```sql
-- Dans pgAdmin ou psql :
CREATE DATABASE studentspace;
```

```bash
# Importer les tables et données de test
psql -U postgres -d studentspace -f database/init_corrige.sql
```

---

### Étape 2 — Backend Spring Boot

```bash
cd backend

# Modifier si nécessaire src/main/resources/application.properties
# (mot de passe PostgreSQL, port, etc.)

mvn spring-boot:run
```

Le backend démarre sur **http://localhost:8080**

✅ Vérification rapide :
```
http://localhost:8080/api/bi/kpis
http://localhost:8080/swagger-ui/index.html
```

---

### Étape 3 — Frontend Angular

```bash
cd frontend
npm install
ng serve
```

L'application est accessible sur **http://localhost:4200**

---

## 🌐 Déploiement en ligne (lien fonctionnel PC + Mobile)

Pour rendre la plateforme accessible depuis n'importe quel appareil, voici la méthode recommandée :

### Option A — Render.com (gratuit, recommandé)

**Backend (Spring Boot) :**
1. Créer un compte sur https://render.com
2. New → Web Service → connecter votre GitHub
3. Build Command : `cd backend && mvn package -DskipTests`
4. Start Command : `java -jar backend/target/studentspace-backend-1.0.0.jar`
5. Variables d'environnement :
   - `SPRING_DATASOURCE_URL` = votre URL PostgreSQL
   - `SPRING_DATASOURCE_USERNAME` = votre user
   - `SPRING_DATASOURCE_PASSWORD` = votre mot de passe
   - `JWT_SECRET` = votre clé secrète

**Frontend (Angular) → Vercel :**
1. Aller sur https://vercel.com → New Project → importer GitHub
2. Framework : Angular
3. Build Command : `cd frontend && npm install && ng build --configuration production`
4. Output Directory : `frontend/dist/browser`
5. Variable d'environnement : mettre l'URL du backend dans `services.ts`

**Base de données → Supabase (gratuit) :**
1. https://supabase.com → New Project
2. Copier la connection string PostgreSQL
3. Importer `init_corrige.sql` via l'éditeur SQL de Supabase

### Option B — VPS (Ubuntu)

```bash
# 1. Backend
sudo apt install default-jdk maven postgresql nginx -y
# Configurer PostgreSQL, cloner le projet, mvn package
# Créer un service systemd pour le backend

# 2. Frontend
npm install && ng build --configuration production
# Copier dist/ dans /var/www/html
# Configurer Nginx pour servir Angular + proxy vers :8080
```

---

## 📊 Module Business Intelligence

### Endpoints disponibles (sans authentification)

| URL | Description |
|-----|-------------|
| `GET /api/bi/kpis` | KPIs globaux (total étudiants, modules, etc.) |
| `GET /api/bi/etudiants-filiere` | Répartition étudiants par filière |
| `GET /api/bi/inscriptions-module` | Inscriptions par module (trié) |
| `GET /api/bi/performance-enseignants` | Stats des enseignants |
| `GET /api/bi/distribution-annee` | Distribution par année universitaire |
| `GET /api/bi/export/etudiants` | Export table étudiants (Power BI) |
| `GET /api/bi/export/modules` | Export table modules (Power BI) |
| `GET /api/bi/export/inscriptions` | Export table inscriptions (Power BI) |

### Connexion Power BI Desktop

1. Télécharger : https://powerbi.microsoft.com/fr-fr/downloads/
2. Obtenir des données → Web → `http://localhost:8080/api/bi/kpis`
3. Répéter pour chaque endpoint export

---

## 🔐 Comptes de test

| Rôle | Identifiant | Mot de passe |
|------|-------------|--------------|
| Étudiant | `21011442` (APOGEE) | *(défini à l'inscription)* |
| Enseignant | `test@mail.com` | `password` |
| Admin | Compte créé par l'admin | — |

---

## 🛠 Modifications apportées lors de la fusion

1. **`SecurityConfig.java`** — Ajout de `.requestMatchers("/api/bi/**").permitAll()`
2. **`admin.component.ts`** — Intégration du menu BI + composant `<app-bi-dashboard>`
3. **`BiService.java`** — Copié dans `service/`
4. **`BiController.java`** — Copié dans `controller/`
5. **`bi-dashboard.component.ts`** — Copié dans `frontend/src/app/bi/`
6. **`pom.xml`** — Créé avec toutes les dépendances nécessaires
7. **`application.properties`** — Créé avec la configuration complète

---

## 📞 Support

En cas de problème :
- Vérifier que PostgreSQL tourne sur le port 5432
- Vérifier le mot de passe dans `application.properties`
- Vérifier que `ng serve` et `mvn spring-boot:run` sont tous les deux actifs
