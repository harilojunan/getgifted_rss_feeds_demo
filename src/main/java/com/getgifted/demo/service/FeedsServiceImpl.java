package com.getgifted.demo.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.getgifted.demo.entity.Feeds;
import com.getgifted.demo.exception.ResourceAlreadyFoundException;
import com.getgifted.demo.exception.ResourceNotFoundException;
import com.getgifted.demo.repo.FeedsRepo;

@Service
public class FeedsServiceImpl implements FeedsService {
	
	@Autowired
	private FeedsRepo feedsRepo;
	
	public FeedsServiceImpl(FeedsRepo feedsRepo) {
		this.feedsRepo = feedsRepo;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(FeedsService.class);

	@Override
	public Feeds saveFeed(Feeds feedsData) throws ResourceAlreadyFoundException {
		logger.info("Logging Stated!!! Inside Feeds Service @Save Feed Method Running");
		if (feedsRepo.existsById(feedsData.getId())) {
			throw new ResourceAlreadyFoundException("Feed already found");
		}
		Feeds savedFeeds = feedsRepo.save(feedsData);
		return savedFeeds;
	}

	@Override
	public List<Feeds> getAllFeeds() throws ResourceNotFoundException {
		logger.info("Logging Stated!!! Inside Feeds Service @Get All Feeds Method Running");
		return feedsRepo.findAll();
	}

	@Override
	public Feeds updateFeed(Feeds feedsData, Long id) throws ResourceNotFoundException {
		logger.info("Logging Stated!!! Inside Feeds Service @Find By ID Feed Method Running");

		if (feedsRepo.existsById(feedsData.getId()) == false) {
			throw new ResourceNotFoundException("Feeds not found with id :" + id);
		} else {
			Optional<Feeds> feed = feedsRepo.findById(id);
			logger.info("Loggig Stated!!! Inside Feeds Service @Update Feed Method Running");
			feed.get().setId(feedsData.getId());
			Feeds updatedFeeds = feedsRepo.save(feedsData);
			return updatedFeeds;
		}
	}

}
