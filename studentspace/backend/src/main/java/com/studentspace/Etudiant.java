package com.studentspace.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "etudiant")
@PrimaryKeyJoinColumn(name = "utilisateur_id")
public class Etudiant extends Utilisateur {

    @Column(name = "numero_apogee", nullable = false, unique = true)
    private String numeroApogee;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filiere_id")
    private Filiere filiere;

    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<InscriptionEtudiantModule> inscriptions = new HashSet<>();

    public Etudiant() {}

    // Getters & Setters
    public String getNumeroApogee() { return numeroApogee; }
    public void setNumeroApogee(String v) { numeroApogee = v; }
    public String getNom() { return nom; }
    public void setNom(String v) { nom = v; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String v) { prenom = v; }
    public Filiere getFiliere() { return filiere; }
    public void setFiliere(Filiere v) { filiere = v; }
    public Set<InscriptionEtudiantModule> getInscriptions() { return inscriptions; }
    public void setInscriptions(Set<InscriptionEtudiantModule> v) { inscriptions = v; }
}
