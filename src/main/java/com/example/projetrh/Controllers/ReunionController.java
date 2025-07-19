package com.example.projetrh.Controllers;

import com.example.projetrh.Dtos.ReunionRequestDTO;
import com.example.projetrh.Entities.Reunion;
import com.example.projetrh.Services.ReunionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reunions")
public class ReunionController {

    private final ReunionService reunionService;

    public ReunionController(ReunionService reunionService) {
        this.reunionService = reunionService;
    }

    @PostMapping
    public Reunion createReunion(@Valid @RequestBody ReunionRequestDTO dto) {
        return reunionService.createReunionWithParticipants(dto);
    }

    @GetMapping
    public List<Reunion> getAll() {
        return reunionService.findAll();
    }

    @GetMapping("/{id}")
    public Reunion getById(@PathVariable Integer id) {
        return reunionService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        reunionService.delete(id);
    }
}
