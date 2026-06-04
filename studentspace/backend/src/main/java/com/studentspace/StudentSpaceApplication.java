package com.studentspace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ╔══════════════════════════════════════════════════════════════╗
 * ║  POINT D'ENTRÉE — StudentSpace Backend                       ║
 * ║  Auteur : Abdessamad Ouchaib                                 ║
 * ║  Khalid El Issaoui                                           ║
 * ║  Université Mohammed V — Faculté des Sciences                ║
 * ║  Projet PFE 2024-2025                                        ║
 * ╚══════════════════════════════════════════════════════════════╝
 *
 * Technologies :
 *   - Spring Boot 3.2 (backend)
 *   - PostgreSQL (base de données)
 *   - JWT (authentification)
 *   - Spring Security (sécurité)
 *   - Spring Data JPA + Hibernate (ORM)
 *
 * Lancer : mvn spring-boot:run
 * API    : http://localhost:8080/api
 */
@SpringBootApplication
public class StudentSpaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentSpaceApplication.class, args);
        System.out.println("""
            ╔══════════════════════════════════════════════════╗
            ║  🎓 StudentSpace Backend démarré !               ║
            ║  API  : http://localhost:8080/api                ║
            ║  Auteur : Abdessamad Ouchaib                     ║
            ╚══════════════════════════════════════════════════╝
            """);
    }
}
