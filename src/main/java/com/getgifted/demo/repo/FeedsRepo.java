package com.getgifted.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.getgifted.demo.entity.Feeds;

@Repository
public interface FeedsRepo extends JpaRepository<Feeds, Long> {

}
