package com.studentspace.dto;
public class EtudiantResponse {
    private Long id; private String numeroApogee; private String nom;
    private String prenom; private String email; private String filiere;
    private Long filiereId; private String dateCreation;
    public EtudiantResponse() {}
    public Long getId() { return id; } public void setId(Long v) { id=v; }
    public String getNumeroApogee() { return numeroApogee; } public void setNumeroApogee(String v) { numeroApogee=v; }
    public String getNom() { return nom; } public void setNom(String v) { nom=v; }
    public String getPrenom() { return prenom; } public void setPrenom(String v) { prenom=v; }
    public String getEmail() { return email; } public void setEmail(String v) { email=v; }
    public String getFiliere() { return filiere; } public void setFiliere(String v) { filiere=v; }
    public Long getFiliereId() { return filiereId; } public void setFiliereId(Long v) { filiereId=v; }
    public String getDateCreation() { return dateCreation; } public void setDateCreation(String v) { dateCreation=v; }
    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private final EtudiantResponse r = new EtudiantResponse();
        public Builder id(Long v)            { r.id=v; return this; }
        public Builder numeroApogee(String v){ r.numeroApogee=v; return this; }
        public Builder nom(String v)         { r.nom=v; return this; }
        public Builder prenom(String v)      { r.prenom=v; return this; }
        public Builder email(String v)       { r.email=v; return this; }
        public Builder filiere(String v)     { r.filiere=v; return this; }
        public Builder filiereId(Long v)     { r.filiereId=v; return this; }
        public Builder dateCreation(String v){ r.dateCreation=v; return this; }
        public EtudiantResponse build()      { return r; }
    }
}
