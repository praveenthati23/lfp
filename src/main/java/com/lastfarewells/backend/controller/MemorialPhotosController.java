package com.lastfarewells.backend.controller;

import com.lastfarewells.backend.dto.MemorialPhotosDto;
import com.lastfarewells.backend.dto.MessagesDto;
import com.lastfarewells.backend.entity.MemorialPhotos;
import com.lastfarewells.backend.entity.Messages;
import com.lastfarewells.backend.service.MemorialPhotosService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/photo-album")
@AllArgsConstructor
public class MemorialPhotosController {

    private final MemorialPhotosService memorialPhotosService;

    @PostMapping("")
    public MemorialPhotos createMemorialPhotos(@RequestBody @Valid MemorialPhotosDto memorialPhotosDto) {
        return memorialPhotosService.createMemorialPhotos(memorialPhotosDto);
    }

    @GetMapping("/user/{userId}")
    public List<MemorialPhotos> findAllMemorialPhotos(@PathVariable Long userId) {
        return memorialPhotosService.findAllMemorialPhotos(userId);
    }

    @PutMapping("/{id}")
    public MemorialPhotos updateMemorialPhotos(@PathVariable Long id, @RequestBody @Valid MemorialPhotosDto memorialPhotosDto) {
        return memorialPhotosService.updateMemorialPhotos(id, memorialPhotosDto);
    }

    @DeleteMapping("/{id}")
    public void deleteMemorialPhotos(@PathVariable("id") Long id) {
        memorialPhotosService.deleteMemorialPhotos(id);
    }
}
