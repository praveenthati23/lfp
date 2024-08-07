
package com.lastfarewells.backend.dto;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmailMessage {

	
	private String from;
	private String to;
	private String subject;
	private String templateName;
	private Map<String, Object> props;

}
