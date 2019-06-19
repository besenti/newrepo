package com.assignment.shortestpath.repository;

import com.assignment.shortestpath.entity.Traffic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrafficRepository extends CrudRepository<Traffic, Long> {
}
