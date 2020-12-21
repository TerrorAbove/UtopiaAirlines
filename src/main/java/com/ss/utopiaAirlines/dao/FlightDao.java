package com.ss.utopiaAirlines.dao;

import org.springframework.stereotype.Repository;

import com.ss.utopiaAirlines.entity.Flight;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * @author David Pitcher
 *
 */
@Repository
public interface FlightDao extends JpaRepository<Flight, Integer>
{
	List<Flight> findByArriveCityIdAndDepartCityIdAndDepartTimeBetween(int arriveCityId, int departCityId, Timestamp departTime, Timestamp nextDay);
	
	List<Flight> findByDepartCityIdAndDepartTimeBetween(int departCityId, Timestamp departTime, Timestamp nextDay);
	List<Flight> findByArriveCityIdAndDepartTimeBetween(int arriveCityId, Timestamp departTime, Timestamp nextDay);
	List<Flight> findByArriveCityIdAndDepartCityId(int arriveCityId, int departCityId);
	
	List<Flight> findByDepartTimeBetween(Timestamp departTime, Timestamp nextDay);
	List<Flight> findByArriveCityId(int arriveCityId);
	List<Flight> findByDepartCityId(int departCityId);
}