package com.lastfarewells.backend.controller;

import com.lastfarewells.backend.dto.MemorialDto;
import com.lastfarewells.backend.entity.Memorial;
import com.lastfarewells.backend.service.MemorialService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/memorial")
@AllArgsConstructor
public class MemorialController {

    private final MemorialService memorialService;

    @PostMapping("")
    public Memorial createMemorial(@RequestBody @Valid MemorialDto memorialDto) {
        return memorialService.createMemorial(memorialDto);
    }

    @GetMapping("/{userId}")
    public Memorial getUserMemorial(@PathVariable Long userId) {
        return memorialService.getUserMemorial(userId);
    }

}
