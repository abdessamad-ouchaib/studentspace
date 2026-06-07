package com.studentspace.controller;

import com.studentspace.dto.ExamenRequest;
import com.studentspace.dto.ExamenResponse;
import com.studentspace.service.ExamenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/examens")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "https://studentspace01.vercel.app"})
public class ExamenController {

    private final ExamenService examenService;

    @GetMapping
    public ResponseEntity<List<ExamenResponse>> listerTous() {
        return ResponseEntity.ok(examenService.listerTous());
    }

    @GetMapping("/module/{moduleId}")
    public ResponseEntity<List<ExamenResponse>> parModule(@PathVariable Long moduleId) {
        return ResponseEntity.ok(examenService.parModule(moduleId));
    }

    @PostMapping
    public ResponseEntity<ExamenResponse> creer(@RequestBody ExamenRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(examenService.creer(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamenResponse> modifier(@PathVariable Long id,
                                                    @RequestBody ExamenRequest req) {
        return ResponseEntity.ok(examenService.modifier(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        examenService.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}
