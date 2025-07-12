package com.example.projetrh.Controllers;


import com.example.projetrh.Entities.Employe;
import com.example.projetrh.Services.EmployeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employes")
public class EmployeController {

    private final EmployeService employeService;

    public EmployeController(EmployeService employeService) {
        this.employeService = employeService;
    }

    @PostMapping
    public Employe create(@RequestBody Employe employe) {
        return employeService.save(employe);
    }

    @GetMapping
    public List<Employe> getAll() {
        return employeService.findAll();
    }

    @GetMapping("/{id}")
    public Employe getById(@PathVariable Integer id) {
        return employeService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        employeService.delete(id);
    }
}
