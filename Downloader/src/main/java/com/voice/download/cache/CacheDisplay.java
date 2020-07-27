package com.voice.download.cache;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class CacheDisplay {

	private Integer size;
	Map<Integer,Integer> entries;
	
	public CacheDisplay(){
		size =0;
		entries = new HashMap<Integer, Integer>();
	}
	public Map<Integer, Integer> getEntries() {
		return entries;
	}
	public void setEntries(Map<Integer, Integer> entries) {
		this.entries = entries;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
