package com.lastfarewells.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemorialPhotosDto {

    @NotNull(message = "userId cannot be null")
    private Long   userId;
    private String filename;
    private String altText;
    @NotNull(message = "caption cannot be null")
    private String caption;
    private int    sortOrder;

}
