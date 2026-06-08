package com.studentspace.model;
import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "message")
public class Message {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, columnDefinition = "TEXT") private String contenu;
    @Column(name = "date_envoi", nullable = false) private OffsetDateTime dateEnvoi = OffsetDateTime.now();
    @Column(name = "date_creation") private OffsetDateTime dateCreation = OffsetDateTime.now();
    @Column(name = "date_mise_a_jour") private OffsetDateTime dateMiseAJour = OffsetDateTime.now();
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "expediteur_id", nullable = false) private Utilisateur expediteur;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "module_id", nullable = false) private Module module;
    @PreUpdate public void preUpdate() { this.dateMiseAJour = OffsetDateTime.now(); }
    public Message() {}
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public String getContenu(){return contenu;} public void setContenu(String v){contenu=v;}
    public OffsetDateTime getDateEnvoi(){return dateEnvoi;} public void setDateEnvoi(OffsetDateTime v){dateEnvoi=v;}
    public OffsetDateTime getDateCreation(){return dateCreation;} public void setDateCreation(OffsetDateTime v){dateCreation=v;}
    public OffsetDateTime getDateMiseAJour(){return dateMiseAJour;} public void setDateMiseAJour(OffsetDateTime v){dateMiseAJour=v;}
    public Utilisateur getExpediteur(){return expediteur;} public void setExpediteur(Utilisateur v){expediteur=v;}
    public Module getModule(){return module;} public void setModule(Module v){module=v;}
}
