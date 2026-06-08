package com.studentspace.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.*;

@Entity
@Table(name = "utilisateur")
@Inheritance(strategy = InheritanceType.JOINED)
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "mot_de_passe_hash", nullable = false)
    private String motDePasseHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "date_creation")
    private OffsetDateTime dateCreation = OffsetDateTime.now();

    @Column(name = "date_mise_a_jour")
    private OffsetDateTime dateMiseAJour = OffsetDateTime.now();

    @OneToMany(mappedBy = "expediteur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Message> messagesEnvoyes = new HashSet<>();

    @PreUpdate
    public void preUpdate() { this.dateMiseAJour = OffsetDateTime.now(); }

    public enum Role { ETUDIANT, ENSEIGNANT, ADMIN }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long v) { id = v; }
    public String getEmail() { return email; }
    public void setEmail(String v) { email = v; }
    public String getMotDePasseHash() { return motDePasseHash; }
    public void setMotDePasseHash(String v) { motDePasseHash = v; }
    public Role getRole() { return role; }
    public void setRole(Role v) { role = v; }
    public OffsetDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(OffsetDateTime v) { dateCreation = v; }
    public OffsetDateTime getDateMiseAJour() { return dateMiseAJour; }
    public void setDateMiseAJour(OffsetDateTime v) { dateMiseAJour = v; }
    public Set<Message> getMessagesEnvoyes() { return messagesEnvoyes; }
    public void setMessagesEnvoyes(Set<Message> v) { messagesEnvoyes = v; }
}
