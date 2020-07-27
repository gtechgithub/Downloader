package com.voice.download.service;

import org.ehcache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voice.download.cache.DownloadCache;

@Component
public class DownloaderService {

	
	private int max = 5000;
	private int min = 1000;

	@Autowired
	DownloadCache cache;
	
	private static Logger logger =  LoggerFactory.getLogger(DownloaderService.class);
	
	public int generateOtp() {

	      int randomInt = (int)(Math.random() * (max - min + 1) + min);
	      logger.info("otp:" + randomInt);

	      //add into the cache
	      cache.putOtpIntoCache(randomInt);
	      
	      return randomInt;
	}
	
	public boolean isOtpValid(int key) {
		
		//get the otps from the cache
		return cache.getDownloadOptFromCache().containsKey(Integer.valueOf(key));
	}
	

	public Cache<Integer,Integer> getDownloadOptFromCache() {
		
		return cache.getDownloadOptFromCache();
	
	}
	

}
