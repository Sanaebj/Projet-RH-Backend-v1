package com.example.projetrh.Services;



import com.example.projetrh.Entities.Conge;
import com.example.projetrh.Entities.Employe;
import com.example.projetrh.Enums.StatutConge;
import com.example.projetrh.Repositories.CongeRepository;
import com.example.projetrh.Repositories.EmployeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CongeService {

    private final CongeRepository congeRepository;
    private final EmployeRepository employeRepository;


    public CongeService(CongeRepository congeRepository, EmployeRepository employeRepository) {
        this.congeRepository = congeRepository;
        this.employeRepository = employeRepository;
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

    //  : Demande de congé par employé
    public Conge demanderConge(Integer employeId, Conge conge) {
        Employe employe = employeRepository.findById(employeId)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));

        conge.setEmploye(employe);
        conge.setStatut(StatutConge.EN_ATTENTE);
        conge.setDateDemande(LocalDate.now());
        return congeRepository.save(conge);
    }


    public Conge approuverConge(Integer congeId) {
        Conge conge = congeRepository.findById(congeId)
                .orElseThrow(() -> new RuntimeException("Congé non trouvé"));
        conge.setStatut(StatutConge.APPROUVE);
        return congeRepository.save(conge);
    }


    public Conge refuserConge(Integer congeId) {
        Conge conge = congeRepository.findById(congeId)
                .orElseThrow(() -> new RuntimeException("Congé non trouvé"));
        conge.setStatut(StatutConge.REFUSE);
        return congeRepository.save(conge);
    }


    public List<Conge> listerCongesEnAttente() {
        return congeRepository.findByStatut(StatutConge.EN_ATTENTE);
    }
}
