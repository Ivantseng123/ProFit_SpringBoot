package com.ProFit.controller.users.backend;

import org.springframework.core.io.InputStreamResource;
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
public class LogExportController {

	private static final String CSV_FILE_PATH = "logs/aop-logs.csv";

	@GetMapping("/download/logs")
	public ResponseEntity<InputStreamResource> downloadCsvFile() throws FileNotFoundException {
		FileInputStream fileInputStream = new FileInputStream(CSV_FILE_PATH);

		// 設置回應頭，以提示瀏覽器下載文件
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=aop-logs.csv");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(new InputStreamResource(fileInputStream));
	}
}
