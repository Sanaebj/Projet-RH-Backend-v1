package com.example.projetrh.Repositories;


import com.example.projetrh.Entities.ParticipationReunion;
import com.example.projetrh.Entities.Reunion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ParticipationReunionRepository extends JpaRepository<ParticipationReunion, Integer> {
    List<ParticipationReunion> findByEmployeId(Integer employeId);
    List<ParticipationReunion> findByReunionId(Integer reunionId);
    List<ParticipationReunion> findByReunion(Reunion reunion);

}