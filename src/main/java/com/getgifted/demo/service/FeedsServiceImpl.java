package com.getgifted.demo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.getgifted.demo.entity.Feeds;
import com.getgifted.demo.exception.ResourceAlreadyFoundException;
import com.getgifted.demo.exception.ResourceNotFoundException;
import com.getgifted.demo.repo.FeedsRepo;

/**
 * The Class FeedsServiceImpl.
 */
@Service
public class FeedsServiceImpl implements FeedsService {

	/** The feeds repo. */
	@Autowired
	private FeedsRepo feedsRepo;

	/**
	 * Instantiates a new feeds service impl.
	 *
	 * @param feedsRepo the feeds repo
	 */
	public FeedsServiceImpl(FeedsRepo feedsRepo) {
		this.feedsRepo = feedsRepo;
	}

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(FeedsService.class);
	

	/**
	 * Gets the all feeds.
	 *
	 * @return the all feeds
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@Override
	public List<Feeds> getAllFeeds() throws ResourceNotFoundException {
		logger.info("Logging Stated!!! Inside Feeds Service @Get All Feeds Method Running");
		return feedsRepo.findAll();
	}

	/**
	 * Gets the rss feeds.
	 *
	 * @param feedsData the feeds data
	 * @return the rss feeds
	 * @throws ResourceAlreadyFoundException the resource already found exception
	 */
	@Override
	public Feeds getRssFeeds(Feeds feedsData) throws ResourceAlreadyFoundException {
		logger.info("Logging Stated!!! Inside Feeds Service @Save Feed Method Running");
		if (feedsRepo.existsById(feedsData.getId())) {
			throw new ResourceAlreadyFoundException("Feed already found");
		}
		Feeds savedFeeds = feedsRepo.save(feedsData);
		return savedFeeds;
	}

	/**
	 * Update rss feeds.
	 *
	 * @param feedsData the feeds data
	 * @return the feeds
	 * @throws ResourceAlreadyFoundException the resource already found exception
	 */
	@Override
	public Feeds updateRssFeeds(Feeds feedsData) throws ResourceAlreadyFoundException {
		logger.info("Logging Stated!!! Inside Feeds Service @Find By ID Feed Method Running");

		if (feedsRepo.existsByTitle(feedsData.getTitle()) == true) {
			throw new ResourceAlreadyFoundException("Feeds already found with title :" + feedsData.getTitle());
		} else {
			Feeds feed = new Feeds();
			logger.info("Loggig Stated!!! Inside Feeds Service @Update Feed Method Running");
			feed.setId(feedsData.getId());
			Feeds updatedFeeds = feedsRepo.save(feedsData);
			return updatedFeeds;
		}
	}

}
