package com.example.projetrh.Controllers;



import com.example.projetrh.Entities.Notification;


import com.example.projetrh.Services.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public Notification create(@RequestBody Notification notification) {
        return notificationService.save(notification);
    }

    @GetMapping
    public List<Notification> getAll() {
        return notificationService.findAll();
    }

    @GetMapping("/{id}")
    public Notification getById(@PathVariable Integer id) {
        return notificationService.findById(id);
    }

    @GetMapping("/utilisateur/{utilisateurId}")
    public List<Notification> getByUtilisateur(@PathVariable Integer utilisateurId) {
        return notificationService.findByUtilisateurId(utilisateurId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        notificationService.delete(id);
    }
}

