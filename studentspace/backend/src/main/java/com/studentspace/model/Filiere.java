package com.studentspace.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.*;

/**
 * Entité Filière — Unité d'organisation académique.
 * Ex : Licence SMI, Master IPS...
 * Correspond à la table 'filiere' dans PostgreSQL.
 */
@Entity
@Table(name = "filiere")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class Filiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "annee_universitaire")
    private Integer anneeUniversitaire;

    private Integer semestre;

    @Column(name = "date_creation")
    private OffsetDateTime dateCreation = OffsetDateTime.now();

    @Column(name = "date_mise_a_jour")
    private OffsetDateTime dateMiseAJour = OffsetDateTime.now();

    // Une filière contient plusieurs modules
    @OneToMany(mappedBy = "filiere", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Module> modulesFiliere = new HashSet<>();

    // Une filière a plusieurs étudiants
    @OneToMany(mappedBy = "filiere", fetch = FetchType.LAZY)
    private Set<Etudiant> etudiantsFiliere = new HashSet<>();

    @PreUpdate
    public void preUpdate() { this.dateMiseAJour = OffsetDateTime.now(); }
}
