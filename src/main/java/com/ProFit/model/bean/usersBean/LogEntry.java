package com.ProFit.model.bean.usersBean;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "logEntry")
public class LogEntry {
	private String timestamp;
	private String level;
	private String message;

	public LogEntry() {
	}

	public LogEntry(String timestamp, String level, String message) {
		this.timestamp = timestamp;
		this.level = level;
		this.message = message;
	}

	@XmlElement
	public String getTimestamp() {
		return timestamp;
	}

	@XmlElement
	public String getLevel() {
		return level;
	}

	@XmlElement
	public String getMessage() {
		return message;
	}
}
