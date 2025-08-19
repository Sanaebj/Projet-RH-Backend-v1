package com.example.projetrh.Services;

import com.example.projetrh.Dtos.ReunionRequestDTO;
import com.example.projetrh.Dtos.ReunionResponseDTO;
import com.example.projetrh.Entities.ParticipationReunion;
import com.example.projetrh.Entities.Reunion;
import com.example.projetrh.Enums.StatutParticipation;
import com.example.projetrh.Repositories.EmployeRepository;
import com.example.projetrh.Repositories.ParticipationReunionRepository;
import com.example.projetrh.Repositories.ReunionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public ReunionResponseDTO createReunionWithParticipantsDTO(ReunionRequestDTO dto) {
        // Créer la réunion
        Reunion reunion = new Reunion();
        reunion.setTitre(dto.getTitre());
        reunion.setDateHeure(dto.getDateHeure());
        reunion.setLieu(dto.getLieu());
        reunion.setDescription(dto.getDescription());

        Reunion savedReunion = reunionRepository.save(reunion);

        // Créer les participations et les sauvegarder
        List<ReunionResponseDTO.ParticipationDTO> participationDTOs = dto.getParticipantIds().stream()
                .map(empId -> employeRepository.findById(empId).map(employe -> {
                    ParticipationReunion participation = new ParticipationReunion();
                    participation.setReunion(savedReunion);
                    participation.setEmploye(employe);
                    participation.setStatut(StatutParticipation.EN_ATTENTE);
                    participationReunionRepository.save(participation);

                    // Créer directement le DTO correspondant
                    ReunionResponseDTO.ParticipationDTO pdto = new ReunionResponseDTO.ParticipationDTO();
                    pdto.setEmployeId(employe.getId());
                    pdto.setNomComplet(employe.getNom() + " " + employe.getPrenom());
                    return pdto;
                }).orElse(null))
                .filter(pdto -> pdto != null)
                .toList();

        // Construire le DTO final
        ReunionResponseDTO response = new ReunionResponseDTO();
        response.setId(savedReunion.getId());
        response.setTitre(savedReunion.getTitre());
        response.setDateHeure(savedReunion.getDateHeure());
        response.setLieu(savedReunion.getLieu());
        response.setDescription(savedReunion.getDescription());
        response.setParticipations(participationDTOs);

        return response;
    }
    public List<ReunionResponseDTO> findAllDTO() {
        return reunionRepository.findAll().stream().map(reunion -> {
            ReunionResponseDTO dto = new ReunionResponseDTO();
            dto.setId(reunion.getId());
            dto.setTitre(reunion.getTitre());
            dto.setDateHeure(reunion.getDateHeure());
            dto.setLieu(reunion.getLieu());
            dto.setDescription(reunion.getDescription());

            List<ReunionResponseDTO.ParticipationDTO> participants =
                    participationReunionRepository.findByReunion(reunion).stream()
                            .map(part -> {
                                ReunionResponseDTO.ParticipationDTO pdto = new ReunionResponseDTO.ParticipationDTO();
                                pdto.setEmployeId(part.getEmploye().getId());
                                pdto.setNomComplet(part.getEmploye().getNom() + " " + part.getEmploye().getPrenom());
                                return pdto;
                            })
                            .toList();

            dto.setParticipations(participants);
            return dto;
        }).toList();
    }
    public List<ReunionResponseDTO> findUpcomingReunionsDTO() {
        return reunionRepository.findByDateHeureAfterOrderByDateHeureAsc(LocalDateTime.now())
                .stream()
                .map(reunion -> {  // 👈 Plus besoin de "Object"
                    ReunionResponseDTO dto = new ReunionResponseDTO();
                    dto.setId(reunion.getId());
                    dto.setTitre(reunion.getTitre());
                    dto.setDateHeure(reunion.getDateHeure());
                    dto.setLieu(reunion.getLieu());
                    dto.setDescription(reunion.getDescription());

                    List<ReunionResponseDTO.ParticipationDTO> participants =
                            participationReunionRepository.findByReunion(reunion).stream()
                                    .map(part -> {
                                        ReunionResponseDTO.ParticipationDTO pdto = new ReunionResponseDTO.ParticipationDTO();
                                        pdto.setEmployeId(part.getEmploye().getId());
                                        pdto.setNomComplet(part.getEmploye().getNom() + " " + part.getEmploye().getPrenom());
                                        return pdto;
                                    })
                                    .toList();

                    dto.setParticipations(participants);
                    return dto;
                })
                .toList();
    }


}
