package com.example.projetrh.Services;

import com.example.projetrh.Dtos.ReunionRequestDTO;
import com.example.projetrh.Entities.Employe;
import com.example.projetrh.Entities.ParticipationReunion;
import com.example.projetrh.Entities.Reunion;
import com.example.projetrh.Enums.StatutParticipation;
import com.example.projetrh.Repositories.EmployeRepository;
import com.example.projetrh.Repositories.ParticipationReunionRepository;
import com.example.projetrh.Repositories.ReunionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReunionService {

    private final ReunionRepository reunionRepository;
    private final EmployeRepository employeRepository;
    private final ParticipationReunionRepository participationReunionRepository;

    public ReunionService(ReunionRepository reunionRepository,
                          EmployeRepository employeRepository,
                          ParticipationReunionRepository participationReunionRepository) {
        this.reunionRepository = reunionRepository;
        this.employeRepository = employeRepository;
        this.participationReunionRepository = participationReunionRepository;
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

    public Reunion createReunionWithParticipants(ReunionRequestDTO dto) {
        // Créer la réunion
        Reunion reunion = new Reunion();
        reunion.setTitre(dto.getTitre());
        reunion.setDateHeure(dto.getDateHeure());
        reunion.setLieu(dto.getLieu());
        reunion.setDescription(dto.getDescription());

        Reunion saved = reunionRepository.save(reunion);

        // Associer les participants via leurs IDs
        for (Integer empId : dto.getParticipantIds()) {
            employeRepository.findById(empId).ifPresent(employe -> {
                ParticipationReunion participation = new ParticipationReunion();
                participation.setReunion(saved);
                participation.setEmploye(employe);
                participation.setStatut(StatutParticipation.EN_ATTENTE);
                participationReunionRepository.save(participation);
            });
        }

        return saved;
    }

}
