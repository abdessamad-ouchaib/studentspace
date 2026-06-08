package com.studentspace.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "enseignant")
@PrimaryKeyJoinColumn(name = "utilisateur_id")
@Getter @Setter
@NoArgsConstructor
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

    public Enseignant(String email, String motDePasseHash, String nom, String prenom, String specialite, String telephone) {
        setEmail(email);
        setMotDePasseHash(motDePasseHash);
        setRole(Role.ENSEIGNANT);
        this.nom = nom;
        this.prenom = prenom;
        this.specialite = specialite;
        this.telephone = telephone;
    }
}
