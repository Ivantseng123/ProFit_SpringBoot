package com.ProFit.controller.users;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ProFit.model.bean.usersBean.LogEntry;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogExportController {

	private static final String LOG_FILE_PATH = "logs/aop-logs.log";

	@GetMapping("/export/xml")
	public ResponseEntity<byte[]> exportLogToXml() {
		List<LogEntry> logEntries = new ArrayList<>();

		try (BufferedReader reader = Files.newBufferedReader(Paths.get(LOG_FILE_PATH))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(" ", 3); // 假設 Log 格式為 [時間][等級] 訊息
				if (parts.length == 3) {
					logEntries.add(new LogEntry(parts[0], parts[1], parts[2]));
				}
			}

			// 將 Log 條目轉為 XML
			JAXBContext jaxbContext = JAXBContext.newInstance(LogEntry.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			marshaller.marshal(logEntries, baos);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_XML);
			headers.setContentDispositionFormData("attachment", "logs.xml");

			return ResponseEntity.ok().headers(headers).body(baos.toByteArray());

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).build();
		}
	}
}
