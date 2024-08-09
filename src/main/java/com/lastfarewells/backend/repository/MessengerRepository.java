package com.lastfarewells.backend.repository;

import com.lastfarewells.backend.dto.MessengerForDto;
import com.lastfarewells.backend.entity.Messenger;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessengerRepository extends JpaRepository<Messenger, Long> {

    Optional<Messenger> findByMessengerForAndEmail(Long messengerFor, String email);

    Page<Messenger> findAllByMessengerFor(Long messengerFor, Pageable pageable);

    Optional<Messenger> findByInvitationToken(String invitationToken);

/*    @Query(value = "select m.messenger_for as id, u.first_name as firstName, u.last_name as lastName, "
        + "u.email "
        + "from messengers m "
        + "JOIN users u "
        + "ON m.messenger_for = u.id "
        + "where m.messenger_user_id = :userId", nativeQuery = true)*/

    @Query(value = "select m.messenger_for as id, u.first_name as firstName, u.last_name as lastName, u.email, "
        + " CASE "
        + "    WHEN d.status = 'CONFIRMED' THEN true "
        + "    ELSE false "
        + "  END  "
        + "  AS isDeceased, "
        + "d.death_date as deathDate, d.attachment, d.attachment_filename as attachmentFilename, "
        + "d.obituary_link as obituaryLink, d.is_verified as isVerified, d.verified_by as verifiedBy, "
        + "d.verified_at as verifiedAt, d.status, d.status_note as statusNote, d.custom_note as customNote, "
        + "d.created_on as deathReportCreatedOn, d.updated_on as deathReportUpdatedOn "
        + "from messengers m "
        + "JOIN users u "
        + "ON m.messenger_for = u.id "
        + "LEFT JOIN death_report d "
        + "ON m.id = d.messenger_id "
        + "where m.messenger_user_id = :userId", nativeQuery = true)
    Page<MessengerForDto> findAllMessengerForUsers(@Param("userId") Long userId, Pageable pageable);
}
