package com.getgifted.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.getgifted.demo.entity.Feeds;
import com.getgifted.demo.exception.ResourceAlreadyFoundException;
import com.getgifted.demo.exception.ResourceNotFoundException;
import com.getgifted.demo.repo.FeedsRepo;
import com.getgifted.demo.service.FeedsService;
import com.getgifted.demo.util.AppConstants;

@RestController
@RequestMapping(AppConstants.BASE_URL)
@CrossOrigin(origins = AppConstants.CROSS_ORIGIN_URL)
public class FeedsController {
	
	@Autowired
	private FeedsService feedsService;
	
	@Autowired
	private FeedsRepo feedsRepo;

	/** The logger. */
	private static final Logger logger = LoggerFactory.getLogger(FeedsController.class);
	
	@PostMapping(AppConstants.FEED_URL)
	public ResponseEntity<?> saveFeed(@RequestBody Feeds feeds) throws ResourceAlreadyFoundException {
		logger.info("Logging Stated!!! Inside Feeds Controller @Save Feeds Method Initiated");
		Feeds savedFeed= feedsService.saveFeed(feeds);

		if (null != savedFeed) {
			return new ResponseEntity<Feeds>(savedFeed, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
	
	@GetMapping(AppConstants.FEEDS_URL)
	public ResponseEntity<List<Feeds>> getAllFeedss() throws ResourceNotFoundException {
		logger.info("Logging Stated!!! Inside Feeds Controller @Get All Feeds Method Initiated");
		return new ResponseEntity<>(feedsService.getAllFeeds().stream().collect(Collectors.toList()),
				HttpStatus.OK);
	}
	
	@PutMapping(AppConstants.FEED_BY_ID_URL)
	public ResponseEntity<?> updateProduct(@RequestBody Feeds feedsRequest, @PathVariable Long id) throws Exception {
		logger.info("Logging Stated!!! Inside Feeds Controller @Get Feed By ID Method Initiated");
		Optional<Feeds> feedsData = feedsRepo.findById(id);
		if (feedsData != null) {
			logger.info("Loggig Stated!!! Inside Feeds Controller @Update Feed Method Initiated");
			return new ResponseEntity<>(feedsService.updateFeed(feedsRequest, id), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

}
