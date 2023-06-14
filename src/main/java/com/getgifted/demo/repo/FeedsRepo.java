package com.getgifted.demo.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.getgifted.demo.entity.Feeds;

@Repository
public interface FeedsRepo extends JpaRepository<Feeds, Long> {
	Page<Feeds> findByTitleContaining(String title, Pageable pageable);
	List<Feeds> findByTitleContaining(String title, Sort sort);

}
