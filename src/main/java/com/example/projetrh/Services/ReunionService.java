package com.example.projetrh.Services;


import com.example.projetrh.Entities.Reunion;
import com.example.projetrh.Repositories.ReunionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReunionService {

    private final ReunionRepository reunionRepository;

    public ReunionService(ReunionRepository reunionRepository) {
        this.reunionRepository = reunionRepository;
    }

    public Reunion save(Reunion reunion) {
        return reunionRepository.save(reunion);
    }

    public List<Reunion> findAll() {
        return reunionRepository.findAll();
    }

    public Reunion findById(Integer id) {
        return reunionRepository.findById(id).orElse(null);
    }

    public void delete(Integer id) {
        reunionRepository.deleteById(id);
    }
}

