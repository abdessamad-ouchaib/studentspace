package com.studentspace.controller;

import com.studentspace.dto.FiliereRequest;
import com.studentspace.dto.FiliereResponse;
import com.studentspace.service.FiliereService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/filieres")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "https://studentspace01.vercel.app"})
public class FiliereController {

    private final FiliereService filiereService;

    @GetMapping
    public ResponseEntity<List<FiliereResponse>> listerToutes() {
        return ResponseEntity.ok(filiereService.listerToutes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FiliereResponse> trouverParId(@PathVariable Long id) {
        return ResponseEntity.ok(filiereService.trouverParId(id));
    }

    @PostMapping
    public ResponseEntity<FiliereResponse> creer(@RequestBody FiliereRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(filiereService.creer(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FiliereResponse> modifier(@PathVariable Long id,
                                                     @RequestBody FiliereRequest req) {
        return ResponseEntity.ok(filiereService.modifier(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        filiereService.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}
