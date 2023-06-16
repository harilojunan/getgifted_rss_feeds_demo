package com.getgifted.demo.service;

import java.util.List;

import com.getgifted.demo.entity.Feeds;
import com.getgifted.demo.exception.ResourceAlreadyFoundException;
import com.getgifted.demo.exception.ResourceNotFoundException;

/**
 * The Interface FeedsService.
 */
public interface FeedsService {
	
	/**
	 * Gets the rss feeds.
	 *
	 * @param feedsData the feeds data
	 * @return the rss feeds
	 * @throws ResourceAlreadyFoundException the resource already found exception
	 */
	public Feeds getRssFeeds(Feeds feedsData) throws ResourceAlreadyFoundException;
	
	/**
	 * Gets the all feeds.
	 *
	 * @return the all feeds
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	public List<Feeds> getAllFeeds() throws ResourceNotFoundException;
	
	/**
	 * Update rss feeds.
	 *
	 * @param feedsData the feeds data
	 * @return the feeds
	 * @throws ResourceAlreadyFoundException the resource already found exception
	 */
	public Feeds updateRssFeeds(Feeds feedsData) throws ResourceAlreadyFoundException;

}
