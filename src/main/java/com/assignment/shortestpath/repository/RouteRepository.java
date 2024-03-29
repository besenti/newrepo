package com.assignment.shortestpath.repository;

import com.assignment.shortestpath.entity.Route;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends CrudRepository<Route, Long> {
}
