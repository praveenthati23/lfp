package com.lastfarewells.backend.service;

import com.lastfarewells.backend.dto.SubscriptionDto;

public interface SubscriptionService {

    SubscriptionDto fetchSubscriptionDetails(Long subscriptionId);
}
