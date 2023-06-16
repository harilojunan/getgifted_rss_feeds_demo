package com.getgifted.demo.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.getgifted.demo.entity.Feeds;
import com.getgifted.demo.exception.ResourceAlreadyFoundException;
import com.getgifted.demo.exception.ResourceNotFoundException;
import com.getgifted.demo.repo.FeedsRepo;
import com.getgifted.demo.service.FeedsService;
import com.getgifted.demo.util.AppConstants;

import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * The Class FeedsController.
 */
@RestController
@RequestMapping(AppConstants.BASE_URL)
@CrossOrigin(origins = AppConstants.CROSS_ORIGIN_URL)
public class FeedsController {

	/** The feeds service. */
	@Autowired
	private FeedsService feedsService;

	/** The feeds repo. */
	@Autowired
	private FeedsRepo feedsRepo;

	/** The logger. */
	private static final Logger logger = LoggerFactory.getLogger(FeedsController.class);

	/**
	 * Gets the sort direction.
	 *
	 * @param direction the direction
	 * @return the sort direction
	 */
	private Sort.Direction getSortDirection(String direction) {
		if (direction.equals("asc")) {
			return Sort.Direction.ASC;
		} else if (direction.equals("desc")) {
			return Sort.Direction.DESC;
		}

		return Sort.Direction.ASC;
	}

	/**
	 * Gets the element value.
	 *
	 * @param parentElement the parent element
	 * @param tagName the tag name
	 * @return the element value
	 */
	private static String getElementValue(Element parentElement, String tagName) {
		NodeList nodeList = parentElement.getElementsByTagName(tagName);
		Element element = (Element) nodeList.item(0);
		if (element != null) {
			NodeList childNodes = element.getChildNodes();
			if (childNodes.getLength() > 0) {
				return childNodes.item(0).getNodeValue();
			}
		}
		return "";
	}

	/**
	 * Gets the rss feeds.
	 *
	 * @return the rss feeds
	 * @throws ResourceAlreadyFoundException the resource already found exception
	 */
	@Scheduled(fixedRate = 300000)
	public Feeds getRssFeeds() throws ResourceAlreadyFoundException {

		Feeds feedsData = new Feeds();
		String feedUrl = "https://podcastfeeds.nbcnews.com/HL4TzgYC";
		try {
			URL url = new URL(feedUrl);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(url.openStream()));
			doc.getDocumentElement().normalize();

			NodeList itemNodeList = doc.getElementsByTagName("item");
			List<Feeds> rssItems = new ArrayList<>();

			for (int i = 0; i < itemNodeList.getLength(); i++) {
				Node itemNode = itemNodeList.item(i);

				if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
					Element itemElement = (Element) itemNode;

					String title = getElementValue(itemElement, "title");
					String publicationDate = getElementValue(itemElement, "pubDate");
					String description = getElementValue(itemElement, "description");

					Feeds rssItem = new Feeds(title, description, publicationDate);
					rssItems.add(rssItem);

				}
			}

//			 Process the RSS items
			int limit = 1;
			for (Feeds rssItem : rssItems) {
				if (limit <= 10) {
					feedsData.setId(0L);
					feedsData.setTitle(rssItem.getTitle());
					feedsData.setDescription(rssItem.getDescription());
					feedsData.setPublicationDate(rssItem.getPublicationDate());
					feedsService.getRssFeeds(feedsData);
				} else {
					break;
				}
				limit++;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return feedsData;

	}

	/**
	 * Gets the all feeds.
	 *
	 * @param title the title
	 * @param page the page
	 * @param size the size
	 * @param sort the sort
	 * @return the all feeds
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@GetMapping(AppConstants.FEEDS_URL)
	public ResponseEntity<Map<String, Object>> getAllFeeds(@RequestParam(required = false) String title,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size,
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

	/**
	 * Update RSS feeds.
	 *
	 * @return the feeds
	 * @throws Exception the exception
	 */
	@Scheduled(fixedRate = 1200000)
	public Feeds updateRSSFeeds() throws Exception {
		Feeds feedsData1 = new Feeds();
		String feedUrl = "https://podcastfeeds.nbcnews.com/HL4TzgYC";
		try {
			URL url = new URL(feedUrl);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(url.openStream()));
			doc.getDocumentElement().normalize();

			NodeList itemNodeList = doc.getElementsByTagName("item");
			List<Feeds> rssItems = new ArrayList<>();

			for (int i = 0; i < itemNodeList.getLength(); i++) {
				Node itemNode = itemNodeList.item(i);

				if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
					Element itemElement = (Element) itemNode;

					String title = getElementValue(itemElement, "title");
					String publicationDate = getElementValue(itemElement, "pubDate");
					String description = getElementValue(itemElement, "description");

					Feeds rssItem = new Feeds(title, description, publicationDate);
					rssItems.add(rssItem);

				}
			}

//			 Process the RSS items
			int limit = 1;
			for (Feeds rssItem : rssItems) {
				if (limit <= 10) {
					logger.info("Logging Stated!!! Inside Feeds Controller @Get Feed By ID Method Initiated");
					if (feedsRepo.existsByTitle(rssItem.getTitle()) == true) {
						throw new ResourceAlreadyFoundException("Feeds already found with title :" + rssItem.getTitle());
					} else {
						feedsData1.setId(0L);
						feedsData1.setTitle(rssItem.getTitle());
						feedsData1.setDescription(rssItem.getDescription());
						feedsData1.setPublicationDate(rssItem.getPublicationDate());
						feedsService.getRssFeeds(feedsData1);
					}

				} else {
					break;
				}
				limit++;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return feedsData1;

	}

}
