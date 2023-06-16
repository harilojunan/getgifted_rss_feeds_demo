package com.getgifted.demo.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.getgifted.demo.entity.Feeds;

/**
 * The Interface FeedsRepo.
 */
@Repository
public interface FeedsRepo extends JpaRepository<Feeds, Long> {
	
	/**
	 * Find by title containing.
	 *
	 * @param title the title
	 * @param pageable the pageable
	 * @return the page
	 */
	Page<Feeds> findByTitleContaining(String title, Pageable pageable);
	
	/**
	 * Find by title containing.
	 *
	 * @param title the title
	 * @param sort the sort
	 * @return the list
	 */
	List<Feeds> findByTitleContaining(String title, Sort sort);
	
	/**
	 * Exists by title.
	 *
	 * @param title the title
	 * @return the boolean
	 */
	Boolean existsByTitle(String title);

}
