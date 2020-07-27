package com.voice.download.propertyconfig;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "${externalpropertyfile}")
@ConfigurationProperties
public class ExternalProperties {

	private String downloadDirectory;
	private int maxfilesizedownloadMB;
	public String getDownloadDirectory() {
		return downloadDirectory;
	}
	public void setDownloadDirectory(String downloadDirectory) {
		this.downloadDirectory = downloadDirectory;
	}
	public int getMaxfilesizedownloadMB() {
		return maxfilesizedownloadMB;
	}
	public void setMaxfilesizedownloadMB(int maxfilesizedownloadMB) {
		this.maxfilesizedownloadMB = maxfilesizedownloadMB;
	}
	
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
}
