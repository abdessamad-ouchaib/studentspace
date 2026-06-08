package com.studentspace.dto;
public class InscriptionRequest {
    private Long etudiantId; private Long moduleId;
    public InscriptionRequest() {}
    public Long getEtudiantId(){return etudiantId;} public void setEtudiantId(Long v){etudiantId=v;}
    public Long getModuleId(){return moduleId;} public void setModuleId(Long v){moduleId=v;}
}
