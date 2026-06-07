package com.studentspace.controller;

import com.studentspace.dto.EnseignantRequest;
import com.studentspace.dto.EnseignantResponse;
import com.studentspace.service.EnseignantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/enseignants")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "https://studentspace01.vercel.app"})
public class EnseignantController {

    private final EnseignantService enseignantService;

    @GetMapping
    public ResponseEntity<List<EnseignantResponse>> listerTous() {
        return ResponseEntity.ok(enseignantService.listerTous());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnseignantResponse> trouverParId(@PathVariable Long id) {
        return ResponseEntity.ok(enseignantService.trouverParId(id));
    }

    @PostMapping
    public ResponseEntity<EnseignantResponse> creer(@RequestBody EnseignantRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enseignantService.creer(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnseignantResponse> modifier(@PathVariable Long id,
                                                        @RequestBody EnseignantRequest req) {
        return ResponseEntity.ok(enseignantService.modifier(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        enseignantService.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}
