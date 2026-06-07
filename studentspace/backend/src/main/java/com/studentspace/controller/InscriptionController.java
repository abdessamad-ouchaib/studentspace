package com.studentspace.controller;

import com.studentspace.dto.InscriptionRequest;
import com.studentspace.dto.InscriptionResponse;
import com.studentspace.service.InscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inscriptions")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "https://studentspace01.vercel.app"})
public class InscriptionController {

    private final InscriptionService inscriptionService;

    @GetMapping
    public ResponseEntity<List<InscriptionResponse>> listerToutes() {
        return ResponseEntity.ok(inscriptionService.listerToutes());
    }

    @GetMapping("/etudiant/{id}")
    public ResponseEntity<List<InscriptionResponse>> parEtudiant(@PathVariable Long id) {
        return ResponseEntity.ok(inscriptionService.parEtudiant(id));
    }

    @PostMapping
    public ResponseEntity<InscriptionResponse> inscrire(@RequestBody InscriptionRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inscriptionService.inscrire(req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desinscrire(@PathVariable Long id) {
        inscriptionService.desinscrire(id);
        return ResponseEntity.noContent().build();
    }
}
