package com.studentspace.dto;
public class ExamenRequest {
    private String titre; private Integer anneeExamen; private String fichierUrl; private Long moduleId;
    public ExamenRequest() {}
    public String getTitre(){return titre;} public void setTitre(String v){titre=v;}
    public Integer getAnneeExamen(){return anneeExamen;} public void setAnneeExamen(Integer v){anneeExamen=v;}
    public String getFichierUrl(){return fichierUrl;} public void setFichierUrl(String v){fichierUrl=v;}
    public Long getModuleId(){return moduleId;} public void setModuleId(Long v){moduleId=v;}
}
