package com.lastfarewells.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lastfarewells.backend.entity.FailedEmails;

public interface FailedEmailsRepository extends JpaRepository<FailedEmails, Long> {

}
