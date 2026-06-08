package com.studentspace.model;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.*;

@Entity
@Table(name = "filiere")
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
    private Set<Module> modulesFiliere = new HashSet<>();
    @OneToMany(mappedBy = "filiere", fetch = FetchType.LAZY)
    private Set<Etudiant> etudiantsFiliere = new HashSet<>();
    @PreUpdate public void preUpdate() { this.dateMiseAJour = OffsetDateTime.now(); }
    public Filiere() {}
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public String getNom(){return nom;} public void setNom(String v){nom=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;}
    public Integer getAnneeUniversitaire(){return anneeUniversitaire;} public void setAnneeUniversitaire(Integer v){anneeUniversitaire=v;}
    public Integer getSemestre(){return semestre;} public void setSemestre(Integer v){semestre=v;}
    public OffsetDateTime getDateCreation(){return dateCreation;} public void setDateCreation(OffsetDateTime v){dateCreation=v;}
    public OffsetDateTime getDateMiseAJour(){return dateMiseAJour;} public void setDateMiseAJour(OffsetDateTime v){dateMiseAJour=v;}
    public Set<Module> getModulesFiliere(){return modulesFiliere;} public void setModulesFiliere(Set<Module> v){modulesFiliere=v;}
    public Set<Etudiant> getEtudiantsFiliere(){return etudiantsFiliere;} public void setEtudiantsFiliere(Set<Etudiant> v){etudiantsFiliere=v;}
}
