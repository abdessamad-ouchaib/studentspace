package com.studentspace.dto;
public class EnseignantResponse {
    private Long id; private String nom; private String prenom; private String email;
    private String telephone; private String specialite; private String departement;
    private String bureau; private int nombreModules;
    public EnseignantResponse() {}
    public Long getId() { return id; } public void setId(Long v) { id=v; }
    public String getNom() { return nom; } public void setNom(String v) { nom=v; }
    public String getPrenom() { return prenom; } public void setPrenom(String v) { prenom=v; }
    public String getEmail() { return email; } public void setEmail(String v) { email=v; }
    public String getTelephone() { return telephone; } public void setTelephone(String v) { telephone=v; }
    public String getSpecialite() { return specialite; } public void setSpecialite(String v) { specialite=v; }
    public String getDepartement() { return departement; } public void setDepartement(String v) { departement=v; }
    public String getBureau() { return bureau; } public void setBureau(String v) { bureau=v; }
    public int getNombreModules() { return nombreModules; } public void setNombreModules(int v) { nombreModules=v; }
    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private final EnseignantResponse r = new EnseignantResponse();
        public Builder id(Long v)            { r.id=v; return this; }
        public Builder nom(String v)         { r.nom=v; return this; }
        public Builder prenom(String v)      { r.prenom=v; return this; }
        public Builder email(String v)       { r.email=v; return this; }
        public Builder telephone(String v)   { r.telephone=v; return this; }
        public Builder specialite(String v)  { r.specialite=v; return this; }
        public Builder departement(String v) { r.departement=v; return this; }
        public Builder bureau(String v)      { r.bureau=v; return this; }
        public Builder nombreModules(int v)  { r.nombreModules=v; return this; }
        public EnseignantResponse build()    { return r; }
    }
}
