package com.studentspace.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;
import java.util.*;

@Entity
@Table(name = "module")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enseignant_id")
    private Enseignant enseignant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filiere_id")
    private Filiere filiere;

    @Column(name = "date_creation")
    private OffsetDateTime dateCreation = OffsetDateTime.now();

    @Column(name = "date_mise_a_jour")
    private OffsetDateTime dateMiseAJour = OffsetDateTime.now();

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Message> messagesModule = new HashSet<>();

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Examen> examensModule = new HashSet<>();

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<InscriptionEtudiantModule> inscriptions = new HashSet<>();

    @PreUpdate
    public void preUpdate() { this.dateMiseAJour = OffsetDateTime.now(); }
}
