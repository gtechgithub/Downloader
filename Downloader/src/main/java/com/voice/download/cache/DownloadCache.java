package com.voice.download.cache;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.core.spi.service.StatisticsService;
import org.ehcache.core.statistics.CacheStatistics;
import org.ehcache.impl.internal.statistics.DefaultStatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voice.download.service.DownloaderService;

@Component
public class DownloadCache {

	private static String CACHE_DOWNLOAD_OTP = "downloader"; 
	private static int CACHE_DOWNLOAD_SIZE = 10;
	private static int CACHE_DOWNLOAD_EXPIRY_SECONDS = 120;
	private CacheManager cacheManager;
	private Cache<Integer, Integer> downloadOtpCache;
	private CacheConfiguration<Integer, Integer> cacheConfiguration;
	private StatisticsService statisticsService;
	private static Logger logger =  LoggerFactory.getLogger(DownloaderService.class);

	public DownloadCache(){
		
		statisticsService = new DefaultStatisticsService();
		cacheManager =  CacheManagerBuilder.newCacheManagerBuilder().using(statisticsService).build();
		
		cacheManager.init();

		

		cacheConfiguration = CacheConfigurationBuilder.newCacheConfigurationBuilder(Integer.class,
				Integer.class, ResourcePoolsBuilder.heap(CACHE_DOWNLOAD_SIZE))
				.withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(CACHE_DOWNLOAD_EXPIRY_SECONDS))).build();
		
		downloadOtpCache =  cacheManager.createCache(CACHE_DOWNLOAD_OTP, cacheConfiguration);
	}
	
	public Cache<Integer,Integer> getDownloadOptFromCache(){
		return cacheManager.getCache(CACHE_DOWNLOAD_OTP, Integer.class, Integer.class);
	}
	
	public void putOtpIntoCache(int otpNumber) {
		
		downloadOtpCache.put(Integer.valueOf(otpNumber), Integer.valueOf(otpNumber));
	}
	
	
	
	

}
