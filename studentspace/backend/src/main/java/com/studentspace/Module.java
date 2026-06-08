package com.studentspace.model;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.*;

@Entity
@Table(name = "module")
public class Module {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private String nom;
    @Column(columnDefinition = "TEXT") private String description;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "enseignant_id") private Enseignant enseignant;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "filiere_id") private Filiere filiere;
    @Column(name = "date_creation") private OffsetDateTime dateCreation = OffsetDateTime.now();
    @Column(name = "date_mise_a_jour") private OffsetDateTime dateMiseAJour = OffsetDateTime.now();
    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, fetch = FetchType.LAZY) private Set<Message> messagesModule = new HashSet<>();
    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, fetch = FetchType.LAZY) private Set<Examen> examensModule = new HashSet<>();
    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, fetch = FetchType.LAZY) private Set<InscriptionEtudiantModule> inscriptions = new HashSet<>();
    @PreUpdate public void preUpdate() { this.dateMiseAJour = OffsetDateTime.now(); }
    public Module() {}
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public String getNom(){return nom;} public void setNom(String v){nom=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;}
    public Enseignant getEnseignant(){return enseignant;} public void setEnseignant(Enseignant v){enseignant=v;}
    public Filiere getFiliere(){return filiere;} public void setFiliere(Filiere v){filiere=v;}
    public OffsetDateTime getDateCreation(){return dateCreation;} public void setDateCreation(OffsetDateTime v){dateCreation=v;}
    public OffsetDateTime getDateMiseAJour(){return dateMiseAJour;} public void setDateMiseAJour(OffsetDateTime v){dateMiseAJour=v;}
    public Set<Message> getMessagesModule(){return messagesModule;} public void setMessagesModule(Set<Message> v){messagesModule=v;}
    public Set<Examen> getExamensModule(){return examensModule;} public void setExamensModule(Set<Examen> v){examensModule=v;}
    public Set<InscriptionEtudiantModule> getInscriptions(){return inscriptions;} public void setInscriptions(Set<InscriptionEtudiantModule> v){inscriptions=v;}
}
