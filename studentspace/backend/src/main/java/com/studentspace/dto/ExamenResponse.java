package com.studentspace.dto;
public class ExamenResponse {
    private Long id; private String titre; private Integer anneeExamen;
    private String fichierUrl; private String module; private Long moduleId; private String dateTeleversement;
    public ExamenResponse() {}
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public String getTitre(){return titre;} public void setTitre(String v){titre=v;}
    public Integer getAnneeExamen(){return anneeExamen;} public void setAnneeExamen(Integer v){anneeExamen=v;}
    public String getFichierUrl(){return fichierUrl;} public void setFichierUrl(String v){fichierUrl=v;}
    public String getModule(){return module;} public void setModule(String v){module=v;}
    public Long getModuleId(){return moduleId;} public void setModuleId(Long v){moduleId=v;}
    public String getDateTeleversement(){return dateTeleversement;} public void setDateTeleversement(String v){dateTeleversement=v;}
    public static Builder builder(){return new Builder();}
    public static class Builder {
        private final ExamenResponse r=new ExamenResponse();
        public Builder id(Long v){r.id=v;return this;}
        public Builder titre(String v){r.titre=v;return this;}
        public Builder anneeExamen(Integer v){r.anneeExamen=v;return this;}
        public Builder fichierUrl(String v){r.fichierUrl=v;return this;}
        public Builder module(String v){r.module=v;return this;}
        public Builder moduleId(Long v){r.moduleId=v;return this;}
        public Builder dateTeleversement(String v){r.dateTeleversement=v;return this;}
        public ExamenResponse build(){return r;}
    }
}
