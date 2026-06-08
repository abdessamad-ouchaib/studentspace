package com.studentspace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentSpaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentSpaceApplication.class, args);
        System.out.println("""
            ╔══════════════════════════════════════════════════╗
            ║  🎓 StudentSpace Backend démarré ! v2            ║
            ║  API  : http://localhost:8080/api                ║
            ║  Auteur : Abdessamad Ouchaib                     ║
            ╚══════════════════════════════════════════════════╝
            """);
    }
}
