package com.lastfarewells.backend.service;

import com.lastfarewells.backend.dto.DeathReportDto;
import com.lastfarewells.backend.entity.DeathReport;

public interface ReportDeathService {

    DeathReport reportDeath(DeathReportDto deathReportDto);

}
