package com.ss.utopiaAirlines.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ss.utopiaAirlines.entity.Flight;
import com.ss.utopiaAirlines.exception.CreateUpdateDeleteException;
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
		
		if(!flight.isPresent())
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
					
					Instant instant = LocalDateTime.of(year, month, day, 0, 0).atZone(ZoneOffset.UTC.normalized()).toInstant();
					
					ts = new Timestamp(instant.toEpochMilli());
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
	
	@PostMapping
	public ResponseEntity<Flight> postFlight(@RequestBody Flight flight, HttpServletResponse response)
	{
		try
		{
			Flight f = flightService.createFlight(flight);
			return ResponseEntity.created(new URI("/flights/"+f.getFlightId())).body(f);
		}
		catch (CreateUpdateDeleteException exc)
		{
			System.err.println(exc.getMessage());
			response.setStatus(HttpStatus.CONFLICT.value());
		}
		catch (DataIntegrityViolationException dive)
		{
			response.setStatus(HttpStatus.BAD_REQUEST.value());
		}
		catch (URISyntaxException e)
		{
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return null;
	}
	
	@PutMapping
	public Flight putFlight(@RequestBody Flight flight, HttpServletResponse response)
	{
		try
		{
			return flightService.createOrUpdateFlight(flight);
		}
		catch (DataIntegrityViolationException dive)
		{
			response.setStatus(HttpStatus.BAD_REQUEST.value());
		}
		return null;
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
			response.setStatus(HttpStatus.NOT_FOUND.value());
		}
	}
}