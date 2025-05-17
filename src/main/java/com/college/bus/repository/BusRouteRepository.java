package com.college.bus.repository;

import com.college.bus.model.BusRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusRouteRepository extends JpaRepository<BusRoute, Long> {
    List<BusRoute> findByIsActiveTrue();
    List<BusRoute> findByRouteName(String routeName);
    List<BusRoute> findByStartLocationAndEndLocation(String startLocation, String endLocation);
} 