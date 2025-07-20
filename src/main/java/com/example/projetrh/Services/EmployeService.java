package com.example.projetrh.Services;


import com.example.projetrh.Entities.Employe;
import com.example.projetrh.Repositories.EmployeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeService {

    private final EmployeRepository employeRepository;

    public EmployeService(EmployeRepository employeRepository) {
        this.employeRepository = employeRepository;
    }

    public Employe save(Employe employe) {
        if (employe.getMatricule() == null || employe.getMatricule().isEmpty()) {
            long count = employeRepository.count();

            String matricule = "EMP" + String.format("%03d", count + 1);
            employe.setMatricule(matricule);
        }

        return employeRepository.save(employe);
    }


    public List<Employe> findAll() {
        return employeRepository.findAll();
    }

    public Employe findById(Integer id) {
        return employeRepository.findById(id).orElse(null);
    }

    public void delete(Integer id) {
        employeRepository.deleteById(id);
    }
}

