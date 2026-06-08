package com.studentspace.dto;
public class MessageResponse {
    private Long id; private String contenu; private String expediteurNom;
    private Long expediteurId; private String dateEnvoi; private Long moduleId;
    public MessageResponse() {}
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public String getContenu(){return contenu;} public void setContenu(String v){contenu=v;}
    public String getExpediteurNom(){return expediteurNom;} public void setExpediteurNom(String v){expediteurNom=v;}
    public Long getExpediteurId(){return expediteurId;} public void setExpediteurId(Long v){expediteurId=v;}
    public String getDateEnvoi(){return dateEnvoi;} public void setDateEnvoi(String v){dateEnvoi=v;}
    public Long getModuleId(){return moduleId;} public void setModuleId(Long v){moduleId=v;}
    public static Builder builder(){return new Builder();}
    public static class Builder {
        private final MessageResponse r=new MessageResponse();
        public Builder id(Long v){r.id=v;return this;}
        public Builder contenu(String v){r.contenu=v;return this;}
        public Builder expediteurNom(String v){r.expediteurNom=v;return this;}
        public Builder expediteurId(Long v){r.expediteurId=v;return this;}
        public Builder dateEnvoi(String v){r.dateEnvoi=v;return this;}
        public Builder moduleId(Long v){r.moduleId=v;return this;}
        public MessageResponse build(){return r;}
    }
}
