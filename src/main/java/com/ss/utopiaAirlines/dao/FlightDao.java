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
	List<Flight> findByArriveCityIdAndDepartCityIdAndDepartTimeGreaterThanAndDepartTimeLessThan(int arriveCityId, int departCityId, Timestamp departTime, Timestamp nextDay);
	
	List<Flight> findByDepartCityIdAndDepartTimeGreaterThanAndDepartTimeLessThan(int departCityId, Timestamp departTime, Timestamp nextDay);
	List<Flight> findByArriveCityIdAndDepartTimeGreaterThanAndDepartTimeLessThan(int arriveCityId, Timestamp departTime, Timestamp nextDay);
	List<Flight> findByArriveCityIdAndDepartCityId(int arriveCityId, int departCityId);
	
	List<Flight> findByDepartTimeGreaterThanAndDepartTimeLessThan(Timestamp departTime, Timestamp nextDay);
	List<Flight> findByArriveCityId(int arriveCityId);
	List<Flight> findByDepartCityId(int departCityId);
}