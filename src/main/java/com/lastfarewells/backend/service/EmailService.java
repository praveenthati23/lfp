package com.lastfarewells.backend.service;

import com.lastfarewells.backend.dto.EmailMessage;

public interface EmailService {
	
	void sendEmail(EmailMessage mail);

}
