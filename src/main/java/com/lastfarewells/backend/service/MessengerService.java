package com.lastfarewells.backend.service;

import com.lastfarewells.backend.dto.MessengerRequestDto;
import com.lastfarewells.backend.entity.Messenger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface MessengerService {

    Messenger createMessenger(MessengerRequestDto messengerRequestDto);

    Page<Messenger> findAllMessenger(PageRequest pageRequest, Long userId);

    Messenger updateMessenger(Long id, MessengerRequestDto messengerRequestDto);

}
