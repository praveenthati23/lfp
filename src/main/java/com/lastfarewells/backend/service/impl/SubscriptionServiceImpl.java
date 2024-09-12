package com.lastfarewells.backend.service.impl;

import com.lastfarewells.backend.dto.SubscriptionDto;
import com.lastfarewells.backend.dto.SubscriptionDto.FeatureDto;
import com.lastfarewells.backend.dto.SubscriptionDto.PlanDto;
import com.lastfarewells.backend.entity.Feature;
import com.lastfarewells.backend.entity.Subscription;
import com.lastfarewells.backend.exception.SubscriptionException;
import com.lastfarewells.backend.repository.FeatureRepository;
import com.lastfarewells.backend.repository.SubscriptionRepository;
import com.lastfarewells.backend.service.SubscriptionService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final FeatureRepository      featureRepository;

    @Override
    public SubscriptionDto fetchSubscriptionDetails(Long subscriptionId) {
        Subscription subscription = subscriptionRepository.findByIdWithPlan(subscriptionId)
            .orElseThrow(() -> new SubscriptionException("Subscription not found"));
        SubscriptionDto subscriptionDto = SubscriptionDto.builder().subscriptionId(subscription.getId()).name(subscription.getName())
            .createdOn(subscription.getCreatedOn()).updatedOn(subscription.getUpdatedOn()).build();
       // PlanDto planDto = PlanDto.builder().id(subscription.getPlan().getId()).build();
        List<Feature> features = featureRepository.findFeaturesByPlanId(subscription.getPlan().getId());
        subscriptionDto.setFeatures(features.stream().map(f -> FeatureDto.builder().id(f.getId()).name(f.getName()).build()).collect(Collectors.toList()));
        System.out.println(subscription);
        return subscriptionDto;
    }

}
