package com.getgifted.demo.service;

import java.util.List;

import com.getgifted.demo.entity.Feeds;
import com.getgifted.demo.exception.ResourceAlreadyFoundException;
import com.getgifted.demo.exception.ResourceNotFoundException;

public interface FeedsService {
	
	public Feeds saveFeed(Feeds feedsData) throws ResourceAlreadyFoundException;
	
	public List<Feeds> getAllFeeds() throws ResourceNotFoundException;
	
	public Feeds updateFeed(Feeds feedsData, Long id) throws ResourceNotFoundException;

}
