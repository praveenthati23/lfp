package com.lastfarewells.backend.service.impl;

import com.lastfarewells.backend.dto.DeathReportDto;
import com.lastfarewells.backend.entity.DeathReport;
import com.lastfarewells.backend.repository.DeathReportRepository;
import com.lastfarewells.backend.service.ReportDeathService;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportDeathServiceImpl implements ReportDeathService {

    private final DeathReportRepository deathReportRepository;

    @Override
    public DeathReport reportDeath(DeathReportDto deathReportDto) {
        log.info("Reporting death for user {}", deathReportDto.getUserId());
        DeathReport deathReport = DeathReport.builder().userId(deathReportDto.getUserId())
            .deathDate(deathReportDto.getDeathDate()).attachment(deathReportDto.getAttachment())
            .attachmentFilename(deathReportDto.getAttachmentFilename()).obituaryLink(deathReportDto.getObituaryLink())
            .createdOn(Instant.now()).build();
        return deathReportRepository.save(deathReport);
    }

}
