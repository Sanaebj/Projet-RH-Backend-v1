package com.example.projetrh.Controllers;

import com.example.projetrh.Dtos.ReunionRequestDTO;
import com.example.projetrh.Dtos.ReunionResponseDTO;
import com.example.projetrh.Entities.Reunion;
import com.example.projetrh.Repositories.ReunionRepository;
import com.example.projetrh.Services.ReunionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reunions")
public class ReunionController {

    private final ReunionService reunionService;
    private final ReunionRepository reunionRepository;



    public ReunionController(ReunionService reunionService, ReunionRepository reunionRepository) {
        this.reunionService = reunionService;
        this.reunionRepository = reunionRepository;
    }


    @PostMapping
    public ReunionResponseDTO createReunion(@Valid @RequestBody ReunionRequestDTO dto) {
        return reunionService.createReunionWithParticipantsDTO(dto);
    }

    @GetMapping
    public List<ReunionResponseDTO> getAll() {
        return reunionService.findAllDTO();
    }


    @GetMapping("/{id}")
    public Reunion getById(@PathVariable Integer id) {
        return reunionService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        reunionService.delete(id);
    }
    @GetMapping("/upcoming")
    public List<ReunionResponseDTO> getUpcomingReunions() {
        return reunionService.findUpcomingReunionsDTO();
    }

}
