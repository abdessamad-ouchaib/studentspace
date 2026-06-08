package com.studentspace.dto;
public class EnseignantRequest {
    private String nom; private String prenom; private String email;
    private String motDePasse; private String telephone; private String specialite;
    private String departement; private String bureau;
    public EnseignantRequest() {}
    public String getNom(){return nom;} public void setNom(String v){nom=v;}
    public String getPrenom(){return prenom;} public void setPrenom(String v){prenom=v;}
    public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getMotDePasse(){return motDePasse;} public void setMotDePasse(String v){motDePasse=v;}
    public String getTelephone(){return telephone;} public void setTelephone(String v){telephone=v;}
    public String getSpecialite(){return specialite;} public void setSpecialite(String v){specialite=v;}
    public String getDepartement(){return departement;} public void setDepartement(String v){departement=v;}
    public String getBureau(){return bureau;} public void setBureau(String v){bureau=v;}
}
