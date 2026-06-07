package com.studentspace.controller;

import com.studentspace.dto.EtudiantRequest;
import com.studentspace.dto.EtudiantResponse;
import com.studentspace.service.EtudiantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/etudiants")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "https://studentspace01.vercel.app"})
public class EtudiantController {

    private final EtudiantService etudiantService;

    @GetMapping
    public ResponseEntity<List<EtudiantResponse>> listerTous() {
        return ResponseEntity.ok(etudiantService.listerTous());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EtudiantResponse> trouverParId(@PathVariable Long id) {
        return ResponseEntity.ok(etudiantService.trouverParId(id));
    }

    @GetMapping("/apogee/{apogee}")
    public ResponseEntity<EtudiantResponse> trouverParApogee(@PathVariable String apogee) {
        return ResponseEntity.ok(etudiantService.trouverParApogee(apogee));
    }

    @PostMapping
    public ResponseEntity<EtudiantResponse> creer(@RequestBody EtudiantRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(etudiantService.creer(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EtudiantResponse> modifier(@PathVariable Long id,
                                                      @RequestBody EtudiantRequest req) {
        return ResponseEntity.ok(etudiantService.modifier(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        etudiantService.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}
