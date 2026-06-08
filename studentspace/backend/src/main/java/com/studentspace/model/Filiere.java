package com.studentspace.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.*;

@Entity
@Table(name = "filiere")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Filiere {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) private String nom;
    @Column(columnDefinition = "TEXT") private String description;
    @Column(name = "annee_universitaire") private Integer anneeUniversitaire;
    private Integer semestre;
    @Column(name = "date_creation") private OffsetDateTime dateCreation = OffsetDateTime.now();
    @Column(name = "date_mise_a_jour") private OffsetDateTime dateMiseAJour = OffsetDateTime.now();
    @OneToMany(mappedBy = "filiere", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Module> modulesFiliere = new HashSet<>();
    @OneToMany(mappedBy = "filiere", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Etudiant> etudiantsFiliere = new HashSet<>();
    @PreUpdate public void preUpdate() { this.dateMiseAJour = OffsetDateTime.now(); }
}
