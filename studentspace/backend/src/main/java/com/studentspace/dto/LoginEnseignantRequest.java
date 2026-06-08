package com.studentspace.dto;
public class LoginEnseignantRequest {
    private String email;
    private String motDePasse;
    public LoginEnseignantRequest() {}
    public LoginEnseignantRequest(String email, String motDePasse) { this.email = email; this.motDePasse = motDePasse; }
    public String getEmail()      { return email; }
    public String getMotDePasse() { return motDePasse; }
    public void setEmail(String v)      { this.email = v; }
    public void setMotDePasse(String v) { this.motDePasse = v; }
}
