package com.studentspace.controller;

import com.studentspace.dto.ModuleRequest;
import com.studentspace.dto.ModuleResponse;
import com.studentspace.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/modules")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "https://studentspace01.vercel.app"})
public class ModuleController {

    private final ModuleService moduleService;

    @GetMapping
    public ResponseEntity<List<ModuleResponse>> listerTous() {
        return ResponseEntity.ok(moduleService.listerTous());
    }

    @GetMapping("/enseignant/{id}")
    public ResponseEntity<List<ModuleResponse>> parEnseignant(@PathVariable Long id) {
        return ResponseEntity.ok(moduleService.parEnseignant(id));
    }

    @GetMapping("/filiere/{id}")
    public ResponseEntity<List<ModuleResponse>> parFiliere(@PathVariable Long id) {
        return ResponseEntity.ok(moduleService.parFiliere(id));
    }

    @PostMapping
    public ResponseEntity<ModuleResponse> creer(@RequestBody ModuleRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(moduleService.creer(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModuleResponse> modifier(@PathVariable Long id,
                                                    @RequestBody ModuleRequest req) {
        return ResponseEntity.ok(moduleService.modifier(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        moduleService.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}
