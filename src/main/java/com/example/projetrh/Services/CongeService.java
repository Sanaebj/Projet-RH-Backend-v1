package com.example.projetrh.Services;



import com.example.projetrh.Entities.Conge;
import com.example.projetrh.Repositories.CongeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CongeService {

    private final CongeRepository congeRepository;

    public CongeService(CongeRepository congeRepository) {
        this.congeRepository = congeRepository;
    }

    public Conge save(Conge conge) {
        return congeRepository.save(conge);
    }

    public List<Conge> findAll() {
        return congeRepository.findAll();
    }

    public Conge findById(Integer id) {
        return congeRepository.findById(id).orElse(null);
    }

    public List<Conge> findByEmployeId(Integer employeId) {
        return congeRepository.findByEmployeId(employeId);
    }

    public void delete(Integer id) {
        congeRepository.deleteById(id);
    }
}
