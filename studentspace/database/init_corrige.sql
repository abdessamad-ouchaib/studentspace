-- ╔══════════════════════════════════════════════════════════════╗
-- ║  SCRIPT PostgreSQL — StudentSpace (VERSION CORRIGÉE)         ║
-- ║  Base de données : studentspace                              ║
-- ╚══════════════════════════════════════════════════════════════╝

-- ── CONNEXION : lancez ce script depuis psql en tant que postgres
-- ── COMMANDE  : psql -U postgres -f init_corrige.sql

-- Supprimer la base si elle existe déjà (pour repartir propre)


-- Créer la base (nom identique à application.properties)


-- Se connecter à la base


-- ── TABLE UTILISATEUR ──────────────────────────────────────────
CREATE TABLE utilisateur (
    id                  BIGSERIAL PRIMARY KEY,
    email               VARCHAR(255) NOT NULL UNIQUE,
    mot_de_passe_hash   VARCHAR(255) NOT NULL,
    role                VARCHAR(20)  NOT NULL CHECK (role IN ('ETUDIANT','ENSEIGNANT','ADMIN')),
    date_creation       TIMESTAMPTZ  DEFAULT NOW(),
    date_mise_a_jour    TIMESTAMPTZ  DEFAULT NOW()
);

-- ── TABLE FILIERE ──────────────────────────────────────────────
CREATE TABLE filiere (
    id                   BIGSERIAL PRIMARY KEY,
    nom                  VARCHAR(255) NOT NULL UNIQUE,
    description          TEXT,
    annee_universitaire  INTEGER,
    semestre             INTEGER,
    date_creation        TIMESTAMPTZ  DEFAULT NOW(),
    date_mise_a_jour     TIMESTAMPTZ  DEFAULT NOW()
);

-- ── TABLE ETUDIANT ─────────────────────────────────────────────
CREATE TABLE etudiant (
    utilisateur_id  BIGINT       PRIMARY KEY REFERENCES utilisateur(id) ON DELETE CASCADE,
    numero_apogee   VARCHAR(50)  NOT NULL UNIQUE,
    nom             VARCHAR(100) NOT NULL,
    prenom          VARCHAR(100) NOT NULL,
    filiere_id      BIGINT       REFERENCES filiere(id) ON DELETE SET NULL
);

-- ── TABLE ENSEIGNANT ───────────────────────────────────────────
CREATE TABLE enseignant (
    utilisateur_id  BIGINT       PRIMARY KEY REFERENCES utilisateur(id) ON DELETE CASCADE,
    nom             VARCHAR(100) NOT NULL,
    prenom          VARCHAR(100) NOT NULL,
    specialite      VARCHAR(200),
    telephone       VARCHAR(20),
    departement     VARCHAR(200),
    bureau          VARCHAR(100)
);

-- ── TABLE MODULE ───────────────────────────────────────────────
CREATE TABLE module (
    id               BIGSERIAL    PRIMARY KEY,
    nom              VARCHAR(255) NOT NULL,
    description      TEXT,
    enseignant_id    BIGINT       REFERENCES enseignant(utilisateur_id) ON DELETE SET NULL,
    filiere_id       BIGINT       REFERENCES filiere(id) ON DELETE SET NULL,
    date_creation    TIMESTAMPTZ  DEFAULT NOW(),
    date_mise_a_jour TIMESTAMPTZ  DEFAULT NOW()
);

-- ── TABLE EXAMEN ───────────────────────────────────────────────
CREATE TABLE examen (
    id                  BIGSERIAL    PRIMARY KEY,
    titre               VARCHAR(255) NOT NULL,
    annee_examen        INTEGER,
    fichier_url         TEXT,
    date_televersement  TIMESTAMPTZ,
    date_creation       TIMESTAMPTZ  DEFAULT NOW(),
    date_mise_a_jour    TIMESTAMPTZ  DEFAULT NOW(),
    module_id           BIGINT       NOT NULL REFERENCES module(id) ON DELETE CASCADE
);

-- ── TABLE MESSAGE ──────────────────────────────────────────────
CREATE TABLE message (
    id               BIGSERIAL   PRIMARY KEY,
    contenu          TEXT        NOT NULL,
    date_envoi       TIMESTAMPTZ DEFAULT NOW(),
    date_creation    TIMESTAMPTZ DEFAULT NOW(),
    date_mise_a_jour TIMESTAMPTZ DEFAULT NOW(),
    expediteur_id    BIGINT      NOT NULL REFERENCES utilisateur(id) ON DELETE CASCADE,
    module_id        BIGINT      NOT NULL REFERENCES module(id) ON DELETE CASCADE
);

-- ── TABLE INSCRIPTIONS ─────────────────────────────────────────
CREATE TABLE student_module_enrollment (
    id               BIGSERIAL   PRIMARY KEY,
    date_inscription DATE        DEFAULT CURRENT_DATE,
    date_creation    TIMESTAMPTZ DEFAULT NOW(),
    date_mise_a_jour TIMESTAMPTZ DEFAULT NOW(),
    etudiant_id      BIGINT      NOT NULL REFERENCES etudiant(utilisateur_id) ON DELETE CASCADE,
    module_id        BIGINT      NOT NULL REFERENCES module(id) ON DELETE CASCADE,
    UNIQUE (etudiant_id, module_id)
);

-- ── INDEX ──────────────────────────────────────────────────────
CREATE INDEX idx_etudiant_apogee      ON etudiant(numero_apogee);
CREATE INDEX idx_etudiant_filiere     ON etudiant(filiere_id);
CREATE INDEX idx_module_enseignant    ON module(enseignant_id);
CREATE INDEX idx_module_filiere       ON module(filiere_id);
CREATE INDEX idx_message_module       ON message(module_id);
CREATE INDEX idx_inscription_etudiant ON student_module_enrollment(etudiant_id);

-- ── DONNÉES DE TEST ────────────────────────────────────────────
-- Tous les mots de passe = admin123

-- Admin
INSERT INTO utilisateur (email, mot_de_passe_hash, role)
VALUES ('admin@fsr.ac.ma',
        '$2a$10$N.zmdr9zkPt9LD6/VNp0EOhxNYxdXMJXNYEtBwPFO.B2Y0sF5K0wy',
        'ADMIN');

-- Filières
INSERT INTO filiere (nom, description, annee_universitaire, semestre) VALUES
    ('Intelligent Processing Systems', 'Master IPS', 2025, 4),
    ('Licence SMI', 'Licence Sciences Mathématiques et Informatique', 2025, 6);

-- Enseignants (utilisateur_id = 2, 3, 4)
INSERT INTO utilisateur (email, mot_de_passe_hash, role) VALUES
    ('soumia.ziti@um5.ac.ma', '$2a$10$N.zmdr9zkPt9LD6/VNp0EOhxNYxdXMJXNYEtBwPFO.B2Y0sF5K0wy', 'ENSEIGNANT'),
    ('ahmed.benjelloun@um5.ac.ma', '$2a$10$N.zmdr9zkPt9LD6/VNp0EOhxNYxdXMJXNYEtBwPFO.B2Y0sF5K0wy', 'ENSEIGNANT'),
    ('sara.ziani@um5.ac.ma', '$2a$10$N.zmdr9zkPt9LD6/VNp0EOhxNYxdXMJXNYEtBwPFO.B2Y0sF5K0wy', 'ENSEIGNANT');

INSERT INTO enseignant (utilisateur_id, nom, prenom, specialite, telephone, departement, bureau) VALUES
    (2, 'ZITI',       'Soumia', 'Physique Quantique',  '0666559977', 'Département Informatique', 'Bureau 101'),
    (3, 'BENJELLOUN', 'Ahmed',  'Mathématiques',       '0612312121', 'Département Maths',        'Bureau 203'),
    (4, 'ZIANI',      'Sara',   'Chimie',              '0644222979', 'Département Chimie',        'Bureau 305');

-- Étudiants (utilisateur_id = 5, 6, 7)
INSERT INTO utilisateur (email, mot_de_passe_hash, role) VALUES
    ('ouchaib@etudiant.ma', '$2a$10$N.zmdr9zkPt9LD6/VNp0EOhxNYxdXMJXNYEtBwPFO.B2Y0sF5K0wy', 'ETUDIANT'),
    ('khalid@etudiant.ma',  '$2a$10$N.zmdr9zkPt9LD6/VNp0EOhxNYxdXMJXNYEtBwPFO.B2Y0sF5K0wy', 'ETUDIANT'),
    ('emaad@etudiant.ma',   '$2a$10$N.zmdr9zkPt9LD6/VNp0EOhxNYxdXMJXNYEtBwPFO.B2Y0sF5K0wy', 'ETUDIANT');

INSERT INTO etudiant (utilisateur_id, numero_apogee, nom, prenom, filiere_id) VALUES
    (5, '2005050',  'OUCHAIB',  'Abdessamad', 1),
    (6, '21011442', 'ELISSOUI', 'Khalid',     1),
    (7, '1111',     'OU',       'Emaad',      1);

-- Modules
INSERT INTO module (nom, description, enseignant_id, filiere_id) VALUES
    ('Programmation Web',     'Développement web moderne',           2, 1),
    ('Cryptographie',         'Sécurité et chiffrement',             3, 1),
    ('Data Mining',           'Extraction de connaissances',         3, 1),
    ('Ingénierie Logicielle', 'Conception et développement logiciel',3, 1),
    ('Analyse Mathématique',  'Analyse réelle et complexe',          2, 1),
    ('Algèbre Linéaire',      'Espaces vectoriels',                  2, 1);

-- Inscriptions
INSERT INTO student_module_enrollment (etudiant_id, module_id, date_inscription) VALUES
    (5, 1, '2025-06-22'), (5, 2, '2025-06-22'),
    (5, 3, '2025-06-22'), (5, 4, '2025-06-22'),
    (6, 1, '2025-06-22'), (6, 2, '2025-06-22'),
    (7, 1, '2025-06-22');

-- Examen de test
INSERT INTO examen (titre, annee_examen, module_id) VALUES
    ('Examen PHP 2025', 2025, 1);

-- Messages de test
INSERT INTO message (contenu, expediteur_id, module_id) VALUES
    ('Bonjour tout le monde !',        5, 1),
    ('Bienvenue au module !',          2, 1),
    ('Quand est le prochain examen ?', 6, 1);

-- ── VUE RÉCAPITULATIVE ─────────────────────────────────────────
CREATE VIEW vue_etudiants_modules AS
SELECT
    e.numero_apogee,
    e.nom || ' ' || e.prenom AS etudiant,
    m.nom AS module,
    f.nom AS filiere,
    sme.date_inscription
FROM student_module_enrollment sme
JOIN etudiant e ON e.utilisateur_id = sme.etudiant_id
JOIN module m   ON m.id = sme.module_id
JOIN filiere f  ON f.id = m.filiere_id;

-- ── VÉRIFICATION FINALE ────────────────────────────────────────
SELECT 'Utilisateurs : ' || COUNT(*) FROM utilisateur;
SELECT 'Étudiants    : ' || COUNT(*) FROM etudiant;
SELECT 'Enseignants  : ' || COUNT(*) FROM enseignant;
SELECT 'Filières     : ' || COUNT(*) FROM filiere;
SELECT 'Modules      : ' || COUNT(*) FROM module;
