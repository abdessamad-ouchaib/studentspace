package com.studentspace.dto;
public class EtudiantRequest {
    private String numeroApogee; private String nom; private String prenom;
    private String email; private String motDePasse; private Long filiereId;
    public EtudiantRequest() {}
    public String getNumeroApogee(){return numeroApogee;} public void setNumeroApogee(String v){numeroApogee=v;}
    public String getNom(){return nom;} public void setNom(String v){nom=v;}
    public String getPrenom(){return prenom;} public void setPrenom(String v){prenom=v;}
    public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getMotDePasse(){return motDePasse;} public void setMotDePasse(String v){motDePasse=v;}
    public Long getFiliereId(){return filiereId;} public void setFiliereId(Long v){filiereId=v;}
}
