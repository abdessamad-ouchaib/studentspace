package com.studentspace.dto;
public class FiliereResponse {
    private Long id; private String nom; private String description;
    private Integer anneeUniversitaire; private Integer semestre;
    private int nombreEtudiants; private int nombreModules;
    public FiliereResponse() {}
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public String getNom(){return nom;} public void setNom(String v){nom=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;}
    public Integer getAnneeUniversitaire(){return anneeUniversitaire;} public void setAnneeUniversitaire(Integer v){anneeUniversitaire=v;}
    public Integer getSemestre(){return semestre;} public void setSemestre(Integer v){semestre=v;}
    public int getNombreEtudiants(){return nombreEtudiants;} public void setNombreEtudiants(int v){nombreEtudiants=v;}
    public int getNombreModules(){return nombreModules;} public void setNombreModules(int v){nombreModules=v;}
    public static Builder builder(){return new Builder();}
    public static class Builder {
        private final FiliereResponse r=new FiliereResponse();
        public Builder id(Long v){r.id=v;return this;}
        public Builder nom(String v){r.nom=v;return this;}
        public Builder description(String v){r.description=v;return this;}
        public Builder anneeUniversitaire(Integer v){r.anneeUniversitaire=v;return this;}
        public Builder semestre(Integer v){r.semestre=v;return this;}
        public Builder nombreEtudiants(int v){r.nombreEtudiants=v;return this;}
        public Builder nombreModules(int v){r.nombreModules=v;return this;}
        public FiliereResponse build(){return r;}
    }
}
