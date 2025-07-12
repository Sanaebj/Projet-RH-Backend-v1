package com.example.projetrh.Controllers;

import com.example.projetrh.Entities.Conge;
import com.example.projetrh.Services.CongeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conges")
public class CongeController {

    private final CongeService congeService;

    public CongeController(CongeService congeService) {
        this.congeService = congeService;
    }

    @PostMapping
    public Conge create(@RequestBody Conge conge) {
        return congeService.save(conge);
    }

    @GetMapping
    public List<Conge> getAll() {
        return congeService.findAll();
    }

    @GetMapping("/{id}")
    public Conge getById(@PathVariable Integer id) {
        return congeService.findById(id);
    }

    @GetMapping("/employe/{employeId}")
    public List<Conge> getByEmployeId(@PathVariable Integer employeId) {
        return congeService.findByEmployeId(employeId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        congeService.delete(id);
    }
}

