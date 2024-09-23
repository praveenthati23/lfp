package com.lastfarewells.backend.controller;

import com.lastfarewells.backend.dto.MemorialDto;
import com.lastfarewells.backend.dto.MessagesDto;
import com.lastfarewells.backend.entity.Memorial;
import com.lastfarewells.backend.entity.Messages;
import com.lastfarewells.backend.service.MemorialService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/user/{userId}")
    public Memorial getUserMemorial(@PathVariable Long userId) {
        return memorialService.getUserMemorial(userId);
    }

    @PutMapping("/{id}")
    public Memorial updateMemorial(@PathVariable Long id, @RequestBody @Valid MemorialDto memorialDto) {
        return memorialService.updateMemorial(id, memorialDto);
    }

    @DeleteMapping("/{id}")
    public void deleteMemorial(@PathVariable("id") Long id) {
        memorialService.deleteMemorial(id);
    }

    @GetMapping("/checkSlug")
    public ResponseEntity<Map<String, Boolean>> getUserMemorial(@RequestParam("alias") String alias) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("isAliasAvailable", memorialService.getUserMemorial(alias));
        return ResponseEntity.ok(response);
    }
}
