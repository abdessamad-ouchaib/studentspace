package com.studentspace.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "etudiant")
@PrimaryKeyJoinColumn(name = "utilisateur_id")
@Getter @Setter
@NoArgsConstructor
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

    public Etudiant(String email, String motDePasseHash, String numeroApogee, String nom, String prenom, Filiere filiere) {
        setEmail(email);
        setMotDePasseHash(motDePasseHash);
        setRole(Role.ETUDIANT);
        this.numeroApogee = numeroApogee;
        this.nom = nom;
        this.prenom = prenom;
        this.filiere = filiere;
    }
}
