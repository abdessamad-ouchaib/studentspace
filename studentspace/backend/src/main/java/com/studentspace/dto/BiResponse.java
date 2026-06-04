package com.studentspace.dto;

import lombok.*;
import java.util.List;
import java.util.Map;

/**
 * ╔══════════════════════════════════════════════════════════════╗
 * ║  BI RESPONSE DTOs — Business Intelligence & Data Science     ║
 * ║  Auteur : Abdessamad Ouchaib                                 ║
 * ║  Endpoints consommés par Power BI et le dashboard Angular    ║
 * ╚══════════════════════════════════════════════════════════════╝
 */

// ── KPIs Globaux ────────────────────────────────────────────────
@Data @Builder @NoArgsConstructor @AllArgsConstructor
class KpiGlobalResponse {
    private long totalEtudiants;
    private long totalEnseignants;
    private long totalModules;
    private long totalFilieres;
    private long totalInscriptions;
    private long totalExamens;
    private double tauxInscriptionMoyen;   // inscriptions / étudiants
}

// ── Étudiants par Filière ────────────────────────────────────────
@Data @Builder @NoArgsConstructor @AllArgsConstructor
class EtudiantsParFiliereResponse {
    private Long filiereId;
    private String filiereNom;
    private Integer anneeUniversitaire;
    private Integer semestre;
    private long nombreEtudiants;
    private double pourcentage;
}

// ── Inscriptions par Module ──────────────────────────────────────
@Data @Builder @NoArgsConstructor @AllArgsConstructor
class InscriptionsParModuleResponse {
    private Long moduleId;
    private String moduleNom;
    private String filiereNom;
    private String enseignantNom;
    private long nombreInscrits;
    private long nombreExamens;
}

// ── Activité Mensuelle ───────────────────────────────────────────
@Data @Builder @NoArgsConstructor @AllArgsConstructor
class ActiviteMensuelleResponse {
    private int annee;
    private int mois;
    private String moisLabel;
    private long nouvellesInscriptions;
    private long nouveauxEtudiants;
    private long nouveauxExamens;
}

// ── Performance par Enseignant ───────────────────────────────────
@Data @Builder @NoArgsConstructor @AllArgsConstructor
class PerformanceEnseignantResponse {
    private Long enseignantId;
    private String enseignantNom;
    private String enseignantEmail;
    private long nombreModules;
    private long totalEtudiantsSuivis;
    private long totalExamensCrees;
    private double moyenneEtudiantsParModule;
}

// ── Distribution par Année Universitaire ────────────────────────
@Data @Builder @NoArgsConstructor @AllArgsConstructor
class DistributionAnneeResponse {
    private Integer anneeUniversitaire;
    private long nombreFilieres;
    private long nombreModules;
    private long nombreEtudiants;
}

// ── Données Export Power BI ──────────────────────────────────────
@Data @Builder @NoArgsConstructor @AllArgsConstructor
class PowerBiEtudiantRow {
    private Long etudiantId;
    private String numeroApogee;
    private String nom;
    private String prenom;
    private String email;
    private String filiereNom;
    private Integer anneeUniversitaire;
    private Integer semestre;
    private long nombreModulesSuivis;
    private String dateInscription;
}

@Data @Builder @NoArgsConstructor @AllArgsConstructor
class PowerBiModuleRow {
    private Long moduleId;
    private String moduleNom;
    private String description;
    private String filiereNom;
    private String enseignantNom;
    private long nombreEtudiants;
    private long nombreExamens;
    private String dateCreation;
}

@Data @Builder @NoArgsConstructor @AllArgsConstructor
class PowerBiInscriptionRow {
    private Long inscriptionId;
    private String etudiantNom;
    private String etudiantPrenom;
    private String numeroApogee;
    private String moduleNom;
    private String filiereNom;
    private String enseignantNom;
    private String dateInscription;
}
