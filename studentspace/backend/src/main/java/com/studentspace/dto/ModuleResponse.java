package com.studentspace.dto;
public class ModuleResponse {
    private Long id; private String nom; private String description;
    private String filiere; private Long filiereId;
    private String enseignant; private Long enseignantId; private int nombreEtudiants;
    public ModuleResponse() {}
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public String getNom(){return nom;} public void setNom(String v){nom=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;}
    public String getFiliere(){return filiere;} public void setFiliere(String v){filiere=v;}
    public Long getFiliereId(){return filiereId;} public void setFiliereId(Long v){filiereId=v;}
    public String getEnseignant(){return enseignant;} public void setEnseignant(String v){enseignant=v;}
    public Long getEnseignantId(){return enseignantId;} public void setEnseignantId(Long v){enseignantId=v;}
    public int getNombreEtudiants(){return nombreEtudiants;} public void setNombreEtudiants(int v){nombreEtudiants=v;}
    public static Builder builder(){return new Builder();}
    public static class Builder {
        private final ModuleResponse r=new ModuleResponse();
        public Builder id(Long v){r.id=v;return this;}
        public Builder nom(String v){r.nom=v;return this;}
        public Builder description(String v){r.description=v;return this;}
        public Builder filiere(String v){r.filiere=v;return this;}
        public Builder filiereId(Long v){r.filiereId=v;return this;}
        public Builder enseignant(String v){r.enseignant=v;return this;}
        public Builder enseignantId(Long v){r.enseignantId=v;return this;}
        public Builder nombreEtudiants(int v){r.nombreEtudiants=v;return this;}
        public ModuleResponse build(){return r;}
    }
}
