package com.studentspace.dto;
public class FiliereRequest {
    private String nom; private String description; private Integer anneeUniversitaire; private Integer semestre;
    public FiliereRequest() {}
    public String getNom(){return nom;} public void setNom(String v){nom=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;}
    public Integer getAnneeUniversitaire(){return anneeUniversitaire;} public void setAnneeUniversitaire(Integer v){anneeUniversitaire=v;}
    public Integer getSemestre(){return semestre;} public void setSemestre(Integer v){semestre=v;}
}
