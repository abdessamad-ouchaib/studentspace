package com.studentspace.dto;

public class AuthResponse {
    private String token;
    private String role;
    private Long   userId;
    private String nom;
    private String prenom;
    private String email;
    private String numeroApogee;

    public AuthResponse() {}

    // ── Getters ────────────────────────────────────────────────
    public String getToken()       { return token; }
    public String getRole()        { return role; }
    public Long   getUserId()      { return userId; }
    public String getNom()         { return nom; }
    public String getPrenom()      { return prenom; }
    public String getEmail()       { return email; }
    public String getNumeroApogee(){ return numeroApogee; }

    // ── Setters ────────────────────────────────────────────────
    public void setToken(String token)             { this.token = token; }
    public void setRole(String role)               { this.role = role; }
    public void setUserId(Long userId)             { this.userId = userId; }
    public void setNom(String nom)                 { this.nom = nom; }
    public void setPrenom(String prenom)           { this.prenom = prenom; }
    public void setEmail(String email)             { this.email = email; }
    public void setNumeroApogee(String n)          { this.numeroApogee = n; }

    // ── Builder statique (remplace @Builder de Lombok) ─────────
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final AuthResponse r = new AuthResponse();
        public Builder token(String v)        { r.token = v;        return this; }
        public Builder role(String v)         { r.role = v;         return this; }
        public Builder userId(Long v)         { r.userId = v;       return this; }
        public Builder nom(String v)          { r.nom = v;          return this; }
        public Builder prenom(String v)       { r.prenom = v;       return this; }
        public Builder email(String v)        { r.email = v;        return this; }
        public Builder numeroApogee(String v) { r.numeroApogee = v; return this; }
        public AuthResponse build()           { return r; }
    }
}
