package com.lastfarewells.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemorialDto {

    private Long    userId;
    private String  backgroundImage;
    private String  headshot;
    private String  epitaph;
    private String  obituary;
    private String  alias;
    private Boolean isTributePage;

}
