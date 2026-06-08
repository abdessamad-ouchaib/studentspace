package com.studentspace.dto;
public class InscriptionResponse {
    private Long id; private String etudiantNom; private Long etudiantId;
    private String moduleNom; private Long moduleId; private String dateInscription;
    public InscriptionResponse() {}
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public String getEtudiantNom(){return etudiantNom;} public void setEtudiantNom(String v){etudiantNom=v;}
    public Long getEtudiantId(){return etudiantId;} public void setEtudiantId(Long v){etudiantId=v;}
    public String getModuleNom(){return moduleNom;} public void setModuleNom(String v){moduleNom=v;}
    public Long getModuleId(){return moduleId;} public void setModuleId(Long v){moduleId=v;}
    public String getDateInscription(){return dateInscription;} public void setDateInscription(String v){dateInscription=v;}
    public static Builder builder(){return new Builder();}
    public static class Builder {
        private final InscriptionResponse r=new InscriptionResponse();
        public Builder id(Long v){r.id=v;return this;}
        public Builder etudiantNom(String v){r.etudiantNom=v;return this;}
        public Builder etudiantId(Long v){r.etudiantId=v;return this;}
        public Builder moduleNom(String v){r.moduleNom=v;return this;}
        public Builder moduleId(Long v){r.moduleId=v;return this;}
        public Builder dateInscription(String v){r.dateInscription=v;return this;}
        public InscriptionResponse build(){return r;}
    }
}
