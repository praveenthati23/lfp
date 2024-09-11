package com.lastfarewells.backend.controller;

import com.lastfarewells.backend.dto.SubscriptionDto;
import com.lastfarewells.backend.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/subscription")
@RequiredArgsConstructor
@Slf4j
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/details/{subscriptionId}")
    public SubscriptionDto fetchSubscriptionDetails(@PathVariable Long subscriptionId) {
        return subscriptionService.fetchSubscriptionDetails(subscriptionId);
    }

}
