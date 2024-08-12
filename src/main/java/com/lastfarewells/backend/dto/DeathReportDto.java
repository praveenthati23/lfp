package com.lastfarewells.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeathReportDto {

    @NotNull(message = "userId cannot be null")
    private Long   userId;
    @NotNull(message = "deathDate cannot be null")
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date   deathDate;
    private String attachment;
    private String attachmentFilename;
    private String obituaryLink;

}
