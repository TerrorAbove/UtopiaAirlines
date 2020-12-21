package com.ss.utopiaAirlines.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ss.utopiaAirlines.entity.Flight;
import com.ss.utopiaAirlines.service.FlightService;

/**
 * 
 * @author David Pitcher
 *
 */
@RestController
@RequestMapping("/flights")
public class FlightController
{

	@Autowired
	FlightService flightService;

	@GetMapping("/{flightId}")
	public Flight getFlightById(@PathVariable int flightId, HttpServletResponse response)
	{
		Optional<Flight> flight = flightService.getFlightById(flightId);
		
		if(flight == null)
		{
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return null;
		}
		return flight.get();
	}

	@GetMapping
	public List<Flight> getFlights(@RequestParam(required = false) Integer arriveCityId,
			@RequestParam(required = false) Integer departCityId,
			@RequestParam(required = false) String date,
			//@RequestParam(required = false) String sort,
			//@RequestParam(defaultValue = "0F") Float minPrice,
			//@RequestParam(defaultValue = ""+Float.MAX_VALUE) Float maxPrice,
			//@RequestParam(defaultValue = "false") Boolean showFullFlights,
			HttpServletResponse response)
	{
		Timestamp ts = null;
		
		//TODO switch to airport names instead of ids in mapping?
		
		if(date != null)
		{
			try
			{
				String[] spl = date.split("-");
				
				if(spl.length == 3)
				{
					int month = Integer.parseInt(spl[0]);
					int day = Integer.parseInt(spl[1]);
					int year = Integer.parseInt(spl[2]);
					
					ts = Timestamp.valueOf(year + "-" + month + "-" + day + " 00:00:00");
				}
			}
			catch(Exception exc)
			{
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				return null;
			}
		}
		
		return flightService.getFlights(arriveCityId, departCityId, ts);//, Sort.by(Direction.ASC, sort), minPrice, maxPrice, showFullFlights);
	}
	
	/*
	@PostMapping
	public Flight createFlight(@RequestBody Flight flight, HttpServletResponse response)
	{
		try
		{
			return flightService.createFlight(flight);
		}
		catch (CreateUpdateDeleteException exc)
		{
			System.err.println(exc.getMessage());
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			return null;
		}
	}
	
	@PutMapping
	public Flight updateFlight(@RequestBody Flight flight, HttpServletResponse response)
	{
		try
		{
			return flightService.updateFlight(flight);
		}
		catch (CreateUpdateDeleteException exc)
		{
			System.err.println(exc.getMessage());
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			return null;
		}
	}
	
	@DeleteMapping("/{flightId}")
	public void deleteFlight(@PathVariable int flightId, HttpServletResponse response)
	{
		try
		{
			flightService.deleteFlight(flightId);
		}
		catch (CreateUpdateDeleteException exc)
		{
			System.err.println(exc.getMessage());
			response.setStatus(HttpStatus.BAD_REQUEST.value());
		}
	}
	*/
}