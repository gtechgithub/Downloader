package com.voice.download.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletContext;

import org.ehcache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.voice.download.cache.CacheDisplay;
import com.voice.download.cache.DownloadCache;
import com.voice.download.propertyconfig.ExternalProperties;
import com.voice.download.service.DownloaderService;
import com.voice.download.utils.MediaTypeUtils;

@RestController
@RequestMapping("/app")
public class DownloadController {

	// private static int otp=0;

	private static Logger logger = LoggerFactory.getLogger(DownloadController.class);

	@Autowired
	private ServletContext servletContext;

	@Autowired
	ExternalProperties config;
	
	@Autowired
	DownloaderService service;

	@GetMapping("/generate")
	public ResponseEntity<?> generateOTP() {
		int otp = service.generateOtp();
		return new ResponseEntity<String>(String.valueOf(otp), HttpStatus.OK);

	}

	@GetMapping("/download")
	public ResponseEntity<?> downloader(@RequestParam(required = true) String filename,
			@RequestParam(required = true) int otp) throws Exception {

		HttpHeaders httpHeaders = new HttpHeaders();
		InputStreamResource resource;
		MediaType mediaType;
		Path path;
		
		try {
		// check the otp is present before proceeding for download
		if (!service.isOtpValid(otp)) {
			return new ResponseEntity<String>("OTP Expired or Invalid", HttpStatus.OK);
		}

		
		logger.info("filename:{}", filename);
		mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, filename);
		logger.info("Media type:{}", mediaType);
		
		logger.info("property config:{}",config);

		
		path = Paths.get(config.getDownloadDirectory()  + filename);
		httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + path.getFileName().toString());
		httpHeaders.add("Cache-Control", "no-cache, no-store, must-revalidate");
		httpHeaders.add("Pragma", "no-cache");
		httpHeaders.add("Expires", "0");

		//ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
		
		resource = new InputStreamResource(new FileInputStream(path.toFile()));
		}catch(Exception e) {
			e.printStackTrace();
			throw new Exception (e.getLocalizedMessage());
		}
		
		return ResponseEntity.ok().headers(httpHeaders).contentLength(path.toFile().length())
				.contentType(mediaType).body(resource);

	}

	@GetMapping("/cacheStatistics")
	public ResponseEntity<?> getDownloadCacheStats() {
		Cache<Integer, Integer> cache = service.getDownloadOptFromCache();

		CacheDisplay display = new CacheDisplay();
		display.getEntries().clear();

		cache.forEach(item -> display.getEntries().put(item.getKey(), item.getValue()));
		display.setSize(display.getEntries().size());

		logger.info("size:{}", display.getSize());

		display.getEntries().forEach((itemKey, itemValue) -> logger.debug("key:{}, value:{}", itemKey, itemValue));

		return new ResponseEntity<CacheDisplay>(display, HttpStatus.OK);

	}
}
