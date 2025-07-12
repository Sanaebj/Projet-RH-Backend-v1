package com.example.projetrh.Services;


import com.example.projetrh.Entities.Notification;
import com.example.projetrh.Repositories.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    public Notification findById(Integer id) {
        return notificationRepository.findById(id).orElse(null);
    }

    public List<Notification> findByUtilisateurId(Integer utilisateurId) {
        return notificationRepository.findByUtilisateurId(utilisateurId);
    }

    public void delete(Integer id) {
        notificationRepository.deleteById(id);
    }
}
