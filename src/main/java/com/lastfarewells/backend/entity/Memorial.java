package com.lastfarewells.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "memorial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Memorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "background_image")
    private String  backgroundImage;
    @Column(name = "headshot")
    private String  headshot;
    @Column(name = "epitaph")
    private String  epitaph;
    @Column(name = "obituary")
    private String  obituary;
    @Column(name = "alias")
    private String  alias;
    @Column(name = "is_tribute_page")
    private Boolean isTributePage;

    @Column(name = "created_on")
    private Instant createdOn;
    @Column(name = "updated_on")
    private Instant updatedOn;

}
