package com.studentspace.dto;

import java.util.List;
import java.util.Map;

// ── KPIs Globaux ────────────────────────────────────────────────
class KpiGlobalResponse {
    private long totalEtudiants; private long totalEnseignants; private long totalModules;
    private long totalFilieres; private long totalInscriptions; private long totalExamens;
    private double tauxInscriptionMoyen;
    public KpiGlobalResponse() {}
    public long getTotalEtudiants(){return totalEtudiants;} public void setTotalEtudiants(long v){totalEtudiants=v;}
    public long getTotalEnseignants(){return totalEnseignants;} public void setTotalEnseignants(long v){totalEnseignants=v;}
    public long getTotalModules(){return totalModules;} public void setTotalModules(long v){totalModules=v;}
    public long getTotalFilieres(){return totalFilieres;} public void setTotalFilieres(long v){totalFilieres=v;}
    public long getTotalInscriptions(){return totalInscriptions;} public void setTotalInscriptions(long v){totalInscriptions=v;}
    public long getTotalExamens(){return totalExamens;} public void setTotalExamens(long v){totalExamens=v;}
    public double getTauxInscriptionMoyen(){return tauxInscriptionMoyen;} public void setTauxInscriptionMoyen(double v){tauxInscriptionMoyen=v;}
    public static Builder builder(){return new Builder();}
    public static class Builder {
        private final KpiGlobalResponse r=new KpiGlobalResponse();
        public Builder totalEtudiants(long v){r.totalEtudiants=v;return this;}
        public Builder totalEnseignants(long v){r.totalEnseignants=v;return this;}
        public Builder totalModules(long v){r.totalModules=v;return this;}
        public Builder totalFilieres(long v){r.totalFilieres=v;return this;}
        public Builder totalInscriptions(long v){r.totalInscriptions=v;return this;}
        public Builder totalExamens(long v){r.totalExamens=v;return this;}
        public Builder tauxInscriptionMoyen(double v){r.tauxInscriptionMoyen=v;return this;}
        public KpiGlobalResponse build(){return r;}
    }
}

// ── Étudiants par Filière ────────────────────────────────────────
class EtudiantsParFiliereResponse {
    private Long filiereId; private String filiereNom; private Integer anneeUniversitaire;
    private Integer semestre; private long nombreEtudiants; private double pourcentage;
    public EtudiantsParFiliereResponse() {}
    public Long getFiliereId(){return filiereId;} public void setFiliereId(Long v){filiereId=v;}
    public String getFiliereNom(){return filiereNom;} public void setFiliereNom(String v){filiereNom=v;}
    public Integer getAnneeUniversitaire(){return anneeUniversitaire;} public void setAnneeUniversitaire(Integer v){anneeUniversitaire=v;}
    public Integer getSemestre(){return semestre;} public void setSemestre(Integer v){semestre=v;}
    public long getNombreEtudiants(){return nombreEtudiants;} public void setNombreEtudiants(long v){nombreEtudiants=v;}
    public double getPourcentage(){return pourcentage;} public void setPourcentage(double v){pourcentage=v;}
    public static Builder builder(){return new Builder();}
    public static class Builder {
        private final EtudiantsParFiliereResponse r=new EtudiantsParFiliereResponse();
        public Builder filiereId(Long v){r.filiereId=v;return this;}
        public Builder filiereNom(String v){r.filiereNom=v;return this;}
        public Builder anneeUniversitaire(Integer v){r.anneeUniversitaire=v;return this;}
        public Builder semestre(Integer v){r.semestre=v;return this;}
        public Builder nombreEtudiants(long v){r.nombreEtudiants=v;return this;}
        public Builder pourcentage(double v){r.pourcentage=v;return this;}
        public EtudiantsParFiliereResponse build(){return r;}
    }
}

// ── Inscriptions par Module ──────────────────────────────────────
class InscriptionsParModuleResponse {
    private Long moduleId; private String moduleNom; private String filiereNom;
    private String enseignantNom; private long nombreInscrits; private long nombreExamens;
    public InscriptionsParModuleResponse() {}
    public Long getModuleId(){return moduleId;} public void setModuleId(Long v){moduleId=v;}
    public String getModuleNom(){return moduleNom;} public void setModuleNom(String v){moduleNom=v;}
    public String getFiliereNom(){return filiereNom;} public void setFiliereNom(String v){filiereNom=v;}
    public String getEnseignantNom(){return enseignantNom;} public void setEnseignantNom(String v){enseignantNom=v;}
    public long getNombreInscrits(){return nombreInscrits;} public void setNombreInscrits(long v){nombreInscrits=v;}
    public long getNombreExamens(){return nombreExamens;} public void setNombreExamens(long v){nombreExamens=v;}
    public static Builder builder(){return new Builder();}
    public static class Builder {
        private final InscriptionsParModuleResponse r=new InscriptionsParModuleResponse();
        public Builder moduleId(Long v){r.moduleId=v;return this;}
        public Builder moduleNom(String v){r.moduleNom=v;return this;}
        public Builder filiereNom(String v){r.filiereNom=v;return this;}
        public Builder enseignantNom(String v){r.enseignantNom=v;return this;}
        public Builder nombreInscrits(long v){r.nombreInscrits=v;return this;}
        public Builder nombreExamens(long v){r.nombreExamens=v;return this;}
        public InscriptionsParModuleResponse build(){return r;}
    }
}

// ── Performance par Enseignant ───────────────────────────────────
class PerformanceEnseignantResponse {
    private Long enseignantId; private String enseignantNom; private String enseignantEmail;
    private long nombreModules; private long totalEtudiantsSuivis; private long totalExamensCrees;
    private double moyenneEtudiantsParModule;
    public PerformanceEnseignantResponse() {}
    public Long getEnseignantId(){return enseignantId;} public void setEnseignantId(Long v){enseignantId=v;}
    public String getEnseignantNom(){return enseignantNom;} public void setEnseignantNom(String v){enseignantNom=v;}
    public String getEnseignantEmail(){return enseignantEmail;} public void setEnseignantEmail(String v){enseignantEmail=v;}
    public long getNombreModules(){return nombreModules;} public void setNombreModules(long v){nombreModules=v;}
    public long getTotalEtudiantsSuivis(){return totalEtudiantsSuivis;} public void setTotalEtudiantsSuivis(long v){totalEtudiantsSuivis=v;}
    public long getTotalExamensCrees(){return totalExamensCrees;} public void setTotalExamensCrees(long v){totalExamensCrees=v;}
    public double getMoyenneEtudiantsParModule(){return moyenneEtudiantsParModule;} public void setMoyenneEtudiantsParModule(double v){moyenneEtudiantsParModule=v;}
    public static Builder builder(){return new Builder();}
    public static class Builder {
        private final PerformanceEnseignantResponse r=new PerformanceEnseignantResponse();
        public Builder enseignantId(Long v){r.enseignantId=v;return this;}
        public Builder enseignantNom(String v){r.enseignantNom=v;return this;}
        public Builder enseignantEmail(String v){r.enseignantEmail=v;return this;}
        public Builder nombreModules(long v){r.nombreModules=v;return this;}
        public Builder totalEtudiantsSuivis(long v){r.totalEtudiantsSuivis=v;return this;}
        public Builder totalExamensCrees(long v){r.totalExamensCrees=v;return this;}
        public Builder moyenneEtudiantsParModule(double v){r.moyenneEtudiantsParModule=v;return this;}
        public PerformanceEnseignantResponse build(){return r;}
    }
}

// ── Distribution par Année Universitaire ────────────────────────
class DistributionAnneeResponse {
    private Integer anneeUniversitaire; private long nombreFilieres;
    private long nombreModules; private long nombreEtudiants;
    public DistributionAnneeResponse() {}
    public Integer getAnneeUniversitaire(){return anneeUniversitaire;} public void setAnneeUniversitaire(Integer v){anneeUniversitaire=v;}
    public long getNombreFilieres(){return nombreFilieres;} public void setNombreFilieres(long v){nombreFilieres=v;}
    public long getNombreModules(){return nombreModules;} public void setNombreModules(long v){nombreModules=v;}
    public long getNombreEtudiants(){return nombreEtudiants;} public void setNombreEtudiants(long v){nombreEtudiants=v;}
    public static Builder builder(){return new Builder();}
    public static class Builder {
        private final DistributionAnneeResponse r=new DistributionAnneeResponse();
        public Builder anneeUniversitaire(Integer v){r.anneeUniversitaire=v;return this;}
        public Builder nombreFilieres(long v){r.nombreFilieres=v;return this;}
        public Builder nombreModules(long v){r.nombreModules=v;return this;}
        public Builder nombreEtudiants(long v){r.nombreEtudiants=v;return this;}
        public DistributionAnneeResponse build(){return r;}
    }
}

// ── Export Power BI ──────────────────────────────────────────────
class PowerBiEtudiantRow {
    private Long etudiantId; private String numeroApogee; private String nom; private String prenom;
    private String email; private String filiereNom; private Integer anneeUniversitaire;
    private Integer semestre; private long nombreModulesSuivis; private String dateInscription;
    public PowerBiEtudiantRow() {}
    public Long getEtudiantId(){return etudiantId;} public void setEtudiantId(Long v){etudiantId=v;}
    public String getNumeroApogee(){return numeroApogee;} public void setNumeroApogee(String v){numeroApogee=v;}
    public String getNom(){return nom;} public void setNom(String v){nom=v;}
    public String getPrenom(){return prenom;} public void setPrenom(String v){prenom=v;}
    public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getFiliereNom(){return filiereNom;} public void setFiliereNom(String v){filiereNom=v;}
    public Integer getAnneeUniversitaire(){return anneeUniversitaire;} public void setAnneeUniversitaire(Integer v){anneeUniversitaire=v;}
    public Integer getSemestre(){return semestre;} public void setSemestre(Integer v){semestre=v;}
    public long getNombreModulesSuivis(){return nombreModulesSuivis;} public void setNombreModulesSuivis(long v){nombreModulesSuivis=v;}
    public String getDateInscription(){return dateInscription;} public void setDateInscription(String v){dateInscription=v;}
    public static Builder builder(){return new Builder();}
    public static class Builder {
        private final PowerBiEtudiantRow r=new PowerBiEtudiantRow();
        public Builder etudiantId(Long v){r.etudiantId=v;return this;}
        public Builder numeroApogee(String v){r.numeroApogee=v;return this;}
        public Builder nom(String v){r.nom=v;return this;}
        public Builder prenom(String v){r.prenom=v;return this;}
        public Builder email(String v){r.email=v;return this;}
        public Builder filiereNom(String v){r.filiereNom=v;return this;}
        public Builder anneeUniversitaire(Integer v){r.anneeUniversitaire=v;return this;}
        public Builder semestre(Integer v){r.semestre=v;return this;}
        public Builder nombreModulesSuivis(long v){r.nombreModulesSuivis=v;return this;}
        public Builder dateInscription(String v){r.dateInscription=v;return this;}
        public PowerBiEtudiantRow build(){return r;}
    }
}

class PowerBiModuleRow {
    private Long moduleId; private String moduleNom; private String description;
    private String filiereNom; private String enseignantNom; private long nombreEtudiants;
    private long nombreExamens; private String dateCreation;
    public PowerBiModuleRow() {}
    public Long getModuleId(){return moduleId;} public void setModuleId(Long v){moduleId=v;}
    public String getModuleNom(){return moduleNom;} public void setModuleNom(String v){moduleNom=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;}
    public String getFiliereNom(){return filiereNom;} public void setFiliereNom(String v){filiereNom=v;}
    public String getEnseignantNom(){return enseignantNom;} public void setEnseignantNom(String v){enseignantNom=v;}
    public long getNombreEtudiants(){return nombreEtudiants;} public void setNombreEtudiants(long v){nombreEtudiants=v;}
    public long getNombreExamens(){return nombreExamens;} public void setNombreExamens(long v){nombreExamens=v;}
    public String getDateCreation(){return dateCreation;} public void setDateCreation(String v){dateCreation=v;}
    public static Builder builder(){return new Builder();}
    public static class Builder {
        private final PowerBiModuleRow r=new PowerBiModuleRow();
        public Builder moduleId(Long v){r.moduleId=v;return this;}
        public Builder moduleNom(String v){r.moduleNom=v;return this;}
        public Builder description(String v){r.description=v;return this;}
        public Builder filiereNom(String v){r.filiereNom=v;return this;}
        public Builder enseignantNom(String v){r.enseignantNom=v;return this;}
        public Builder nombreEtudiants(long v){r.nombreEtudiants=v;return this;}
        public Builder nombreExamens(long v){r.nombreExamens=v;return this;}
        public Builder dateCreation(String v){r.dateCreation=v;return this;}
        public PowerBiModuleRow build(){return r;}
    }
}

class PowerBiInscriptionRow {
    private Long inscriptionId; private String etudiantNom; private String etudiantPrenom;
    private String numeroApogee; private String moduleNom; private String filiereNom;
    private String enseignantNom; private String dateInscription;
    public PowerBiInscriptionRow() {}
    public Long getInscriptionId(){return inscriptionId;} public void setInscriptionId(Long v){inscriptionId=v;}
    public String getEtudiantNom(){return etudiantNom;} public void setEtudiantNom(String v){etudiantNom=v;}
    public String getEtudiantPrenom(){return etudiantPrenom;} public void setEtudiantPrenom(String v){etudiantPrenom=v;}
    public String getNumeroApogee(){return numeroApogee;} public void setNumeroApogee(String v){numeroApogee=v;}
    public String getModuleNom(){return moduleNom;} public void setModuleNom(String v){moduleNom=v;}
    public String getFiliereNom(){return filiereNom;} public void setFiliereNom(String v){filiereNom=v;}
    public String getEnseignantNom(){return enseignantNom;} public void setEnseignantNom(String v){enseignantNom=v;}
    public String getDateInscription(){return dateInscription;} public void setDateInscription(String v){dateInscription=v;}
    public static Builder builder(){return new Builder();}
    public static class Builder {
        private final PowerBiInscriptionRow r=new PowerBiInscriptionRow();
        public Builder inscriptionId(Long v){r.inscriptionId=v;return this;}
        public Builder etudiantNom(String v){r.etudiantNom=v;return this;}
        public Builder etudiantPrenom(String v){r.etudiantPrenom=v;return this;}
        public Builder numeroApogee(String v){r.numeroApogee=v;return this;}
        public Builder moduleNom(String v){r.moduleNom=v;return this;}
        public Builder filiereNom(String v){r.filiereNom=v;return this;}
        public Builder enseignantNom(String v){r.enseignantNom=v;return this;}
        public Builder dateInscription(String v){r.dateInscription=v;return this;}
        public PowerBiInscriptionRow build(){return r;}
    }
}
