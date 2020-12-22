package com.ss.utopiaAirlines.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ss.utopiaAirlines.dao.FlightDao;
import com.ss.utopiaAirlines.entity.Flight;
import com.ss.utopiaAirlines.exception.CreateUpdateDeleteException;

/**
 * 
 * @author David Pitcher
 *
 */
@Component
public class FlightService
{
	@Autowired
	FlightDao flightDao;
	
	private static final long MS_IN_A_DAY = 86400000L;
	
	public Optional<Flight> getFlightById(int id)
	{
		return flightDao.findById(id);
	}
	
	public List<Flight> getFlights()
	{
		return flightDao.findAll();
	}
	
	public List<Flight> getFlights(Integer arriveCityId, Integer departCityId, Timestamp date)//, Sort sort, Float minPrice, Float maxPrice, Boolean showFullFlights)
	{
		//System.out.println(arriveCityId + "," + departCityId + "," + /*minPrice + "," + maxPrice + "," +*/ date);// + "," + sort + "," + showFullFlights);
		
		boolean hasArriveId = arriveCityId != null, hasDepartId = departCityId != null, hasDate = date != null;
		
		Timestamp ts_next_day = null;
		if(hasDate)
		{
			ts_next_day = new Timestamp(date.getTime() + MS_IN_A_DAY - 1L);//gets the next day's date minus 1 millisecond
		}
		
		if(hasArriveId)
		{
			if(hasDepartId)
			{
				if(hasDate)
				{
					return flightDao.findByArriveCityIdAndDepartCityIdAndDepartTimeBetween(arriveCityId, departCityId, date, ts_next_day);
				}
				else
				{
					return flightDao.findByArriveCityIdAndDepartCityId(arriveCityId, departCityId);
				}
			}
			else
			{
				if(hasDate)
				{
					return flightDao.findByArriveCityIdAndDepartTimeBetween(arriveCityId, date, ts_next_day);
				}
				else
				{
					return flightDao.findByArriveCityId(arriveCityId);
				}
			}
		}
		else
		{
			if(hasDepartId)
			{
				if(hasDate)
				{
					return flightDao.findByDepartCityIdAndDepartTimeBetween(departCityId, date, ts_next_day);
				}
				else
				{
					return flightDao.findByDepartCityId(departCityId);
				}
			}
			else
			{
				if(hasDate)
				{
					return flightDao.findByDepartTimeBetween(date, ts_next_day);
				}
				else
				{
					return flightDao.findAll();
				}
			}
		}
	}
	
	public Flight createFlight(Flight flight) throws CreateUpdateDeleteException
	{
		final int ORIG_ID = flight.getFlightId();
		
		int id_diff = 0;
		boolean found = false;
		
		while(id_diff < Short.MAX_VALUE)//try up to 32767 spaces after flightId for vacant spot
		{
			if(flightDao.existsById(ORIG_ID + id_diff))
				id_diff++;
			else
			{
				found = true;
				break;
			}
		}
		
		if(found)
		{
			flight.setFlightId(ORIG_ID + id_diff);
			return flightDao.save(flight);
		}
		
		throw new CreateUpdateDeleteException("Could not find vacant flightId for new flight.");
	}
	
	public Flight createOrUpdateFlight(Flight flight)// throws CreateUpdateDeleteException
	{
		return flightDao.save(flight);
	}
	
	public void deleteFlight(int id) throws CreateUpdateDeleteException
	{
		if(!flightDao.existsById(id))
			throw new CreateUpdateDeleteException("Flight does not exist, cannot delete: flightId="+id);
		
		flightDao.deleteById(id);
	}
}
