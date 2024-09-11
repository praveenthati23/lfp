package com.lastfarewells.backend.repository;

import com.lastfarewells.backend.entity.Feature;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FeatureRepository extends JpaRepository<Feature, Long> {

    @Query(value = "SELECT f.* FROM feature f " +
        "JOIN plan_feature pf ON f.id = pf.feature_id " +
        "WHERE pf.plan_id = :planId", nativeQuery = true)
    List<Feature> findFeaturesByPlanId(@Param("planId") Long planId);

}
