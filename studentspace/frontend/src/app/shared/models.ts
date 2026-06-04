/**
 * ╔══════════════════════════════════════════════════════════════╗
 * ║  MODÈLES TypeScript — StudentSpace Frontend                  ║
 * ║  Auteur : Abdessamad Ouchaib                                 ║
 * ║  Correspondent exactement aux DTOs du backend Spring Boot    ║
 * ╚══════════════════════════════════════════════════════════════╝
 */

// ── Auth ──────────────────────────────────────────────────────────────────────
export interface LoginEtudiantRequest {
  numeroApogee: string;
}

export interface LoginEnseignantRequest {
  email: string;
  motDePasse: string;
}

export interface AuthResponse {
  token:        string;
  role:         'ETUDIANT' | 'ENSEIGNANT' | 'ADMIN';
  userId:       number;
  nom?:         string;
  prenom?:      string;
  email?:       string;
  numeroApogee?: string;
}

// ── Etudiant ──────────────────────────────────────────────────────────────────
export interface EtudiantResponse {
  id:           number;
  numeroApogee: string;
  nom:          string;
  prenom:       string;
  email:        string;
  filiere?:     string;
  filiereId?:   number;
  dateCreation?: string;
}

export interface EtudiantRequest {
  numeroApogee: string;
  nom:          string;
  prenom:       string;
  email:        string;
  motDePasse:   string;
  filiereId?:   number;
}

// ── Enseignant ────────────────────────────────────────────────────────────────
export interface EnseignantResponse {
  id:             number;
  nom:            string;
  prenom:         string;
  email:          string;
  telephone?:     string;
  specialite?:    string;
  departement?:   string;
  bureau?:        string;
  nombreModules?: number;
}

export interface EnseignantRequest {
  nom:          string;
  prenom:       string;
  email:        string;
  motDePasse:   string;
  telephone?:   string;
  specialite?:  string;
  departement?: string;
  bureau?:      string;
}

// ── Filière ───────────────────────────────────────────────────────────────────
export interface FiliereResponse {
  id:                  number;
  nom:                 string;
  description?:        string;
  anneeUniversitaire?: number;
  semestre?:           number;
  nombreEtudiants?:    number;
  nombreModules?:      number;
}

export interface FiliereRequest {
  nom:                 string;
  description?:        string;
  anneeUniversitaire?: number;
  semestre?:           number;
}

// ── Module ────────────────────────────────────────────────────────────────────
export interface ModuleResponse {
  id:              number;
  nom:             string;
  description?:    string;
  filiere?:        string;
  filiereId?:      number;
  enseignant?:     string;
  enseignantId?:   number;
  nombreEtudiants?: number;
}

export interface ModuleRequest {
  nom:           string;
  description?:  string;
  filiereId?:    number;
  enseignantId?: number;
}

// ── Examen ────────────────────────────────────────────────────────────────────
export interface ExamenResponse {
  id:                 number;
  titre:              string;
  anneeExamen?:       number;
  fichierUrl?:        string;
  module?:            string;
  moduleId?:          number;
  dateTeleversement?: string;
}

export interface ExamenRequest {
  titre:        string;
  anneeExamen?: number;
  fichierUrl?:  string;
  moduleId:     number;
}

// ── Message ───────────────────────────────────────────────────────────────────
export interface MessageResponse {
  id:            number;
  contenu:       string;
  expediteurNom: string;
  expediteurId:  number;
  dateEnvoi:     string;
  moduleId:      number;
}

export interface MessageRequest {
  contenu:  string;
  moduleId: number;
}

// ── Inscription ───────────────────────────────────────────────────────────────
export interface InscriptionResponse {
  id:              number;
  etudiantNom:     string;
  etudiantId:      number;
  moduleNom:       string;
  moduleId:        number;
  dateInscription: string;
}

export interface InscriptionRequest {
  etudiantId: number;
  moduleId:   number;
}

// ── Stats Admin ───────────────────────────────────────────────────────────────
export interface StatsResponse {
  nombreEtudiants:    number;
  nombreEnseignants:  number;
  nombreModules:      number;
  nombreFilieres:     number;
  nombreExamens:      number;
  nombreInscriptions: number;
}
