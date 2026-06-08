package com.studentspace.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "enseignant")
@PrimaryKeyJoinColumn(name = "utilisateur_id")
public class Enseignant extends Utilisateur {

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    private String specialite;
    private String telephone;
    private String departement;
    private String bureau;

    @OneToMany(mappedBy = "enseignant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Module> modulesEnseignant = new HashSet<>();

    public Enseignant() {}

    // Getters & Setters
    public String getNom() { return nom; }
    public void setNom(String v) { nom = v; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String v) { prenom = v; }
    public String getSpecialite() { return specialite; }
    public void setSpecialite(String v) { specialite = v; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String v) { telephone = v; }
    public String getDepartement() { return departement; }
    public void setDepartement(String v) { departement = v; }
    public String getBureau() { return bureau; }
    public void setBureau(String v) { bureau = v; }
    public Set<Module> getModulesEnseignant() { return modulesEnseignant; }
    public void setModulesEnseignant(Set<Module> v) { modulesEnseignant = v; }
}
