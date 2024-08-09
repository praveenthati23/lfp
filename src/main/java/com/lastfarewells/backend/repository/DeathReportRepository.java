package com.lastfarewells.backend.repository;

import com.lastfarewells.backend.entity.DeathReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeathReportRepository extends JpaRepository<DeathReport, Long> {

}
