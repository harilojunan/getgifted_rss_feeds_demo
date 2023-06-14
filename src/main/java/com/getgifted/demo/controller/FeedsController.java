package com.getgifted.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	private Sort.Direction getSortDirection(String direction) {
		if (direction.equals("asc")) {
			return Sort.Direction.ASC;
		} else if (direction.equals("desc")) {
			return Sort.Direction.DESC;
		}

		return Sort.Direction.ASC;
	}

	@PostMapping(AppConstants.FEED_URL)
	public ResponseEntity<?> saveFeed(@RequestBody Feeds feeds) throws ResourceAlreadyFoundException {
		logger.info("Logging Stated!!! Inside Feeds Controller @Save Feeds Method Initiated");
		Feeds savedFeed = feedsService.saveFeed(feeds);

		if (null != savedFeed) {
			return new ResponseEntity<Feeds>(savedFeed, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(AppConstants.FEEDS_URL)
	public ResponseEntity<Map<String, Object>> getAllFeeds(
			@RequestParam(required = false) String title,
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "3") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort) throws ResourceNotFoundException {
		logger.info("Logging Stated!!! Inside Feeds Controller @Get All Feeds Method Initiated");

		try {
			List<Order> orders = new ArrayList<Order>();

			if (sort[0].contains(",")) {
				// will sort more than 2 fields
				// sortOrder="field, direction"
				for (String sortOrder : sort) {
					String[] _sort = sortOrder.split(",");
					orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
				}
			} else {
				// sort=[field, direction]
				orders.add(new Order(getSortDirection(sort[1]), sort[0]));
			}

			List<Feeds> feedList = new ArrayList<Feeds>();
			Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

			Page<Feeds> pageFeeds;
			if (title == null)
				pageFeeds = feedsRepo.findAll(pagingSort);
			else
				pageFeeds = feedsRepo.findByTitleContaining(title, pagingSort);

			feedList = pageFeeds.getContent();

			if (feedList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			Map<String, Object> response = new HashMap<>();
			response.put("feeds", feedList);
			response.put("currentPage", pageFeeds.getNumber());
			response.put("totalItems", pageFeeds.getTotalElements());
			response.put("totalPages", pageFeeds.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

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
