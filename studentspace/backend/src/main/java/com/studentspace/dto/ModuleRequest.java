package com.studentspace.dto;
public class ModuleRequest {
    private String nom; private String description; private Long filiereId; private Long enseignantId;
    public ModuleRequest() {}
    public String getNom(){return nom;} public void setNom(String v){nom=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;}
    public Long getFiliereId(){return filiereId;} public void setFiliereId(Long v){filiereId=v;}
    public Long getEnseignantId(){return enseignantId;} public void setEnseignantId(Long v){enseignantId=v;}
}
