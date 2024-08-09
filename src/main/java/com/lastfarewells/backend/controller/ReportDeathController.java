package com.lastfarewells.backend.controller;

import com.lastfarewells.backend.dto.DeathReportDto;
import com.lastfarewells.backend.entity.DeathReport;
import com.lastfarewells.backend.service.ReportDeathService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/report-death")
@AllArgsConstructor
public class ReportDeathController {

    private ReportDeathService reportDeathService;

    @PostMapping()
    public DeathReport reportDeath(@RequestBody @Valid DeathReportDto deathReportDto) {
        return reportDeathService.reportDeath(deathReportDto);
    }

}
