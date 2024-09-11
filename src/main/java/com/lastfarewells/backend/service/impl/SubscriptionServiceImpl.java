package com.lastfarewells.backend.service.impl;

import com.lastfarewells.backend.dto.SubscriptionDto;
import com.lastfarewells.backend.entity.Subscription;
import com.lastfarewells.backend.exception.SubscriptionException;
import com.lastfarewells.backend.repository.SubscriptionRepository;
import com.lastfarewells.backend.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public SubscriptionDto fetchSubscriptionDetails(Long subscriptionId) {
        Subscription subscription = subscriptionRepository.findByIdWithPlan(subscriptionId)
            .orElseThrow(() -> new SubscriptionException("Subscription not found"));
        System.out.println(subscription);
        return null;
    }

}
