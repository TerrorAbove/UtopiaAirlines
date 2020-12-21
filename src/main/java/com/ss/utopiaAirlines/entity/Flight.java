package com.ss.utopiaAirlines.entity;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author David Pitcher
 *
 */
@Entity
@Table(name = "tbl_flight")
public class Flight
{
	@Id
	private Integer flightId;
	
	private Integer arriveCityId, departCityId, seatsAvailable;
	
	private Float price;
	
	private Timestamp departTime;

	public Integer getFlightId() {
		return flightId;
	}

	public void setFlightId(Integer flightId) {
		this.flightId = flightId;
	}

	public Integer getArriveCityId() {
		return arriveCityId;
	}

	public void setArriveCityId(Integer arriveCityId) {
		this.arriveCityId = arriveCityId;
	}

	public Integer getDepartCityId() {
		return departCityId;
	}

	public void setDepartCityId(Integer departCityId) {
		this.departCityId = departCityId;
	}

	public Integer getSeatsAvailable() {
		return seatsAvailable;
	}

	public void setSeatsAvailable(Integer seatsAvailable) {
		this.seatsAvailable = seatsAvailable;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Timestamp getDepartTime() {
		return departTime;
	}

	public void setDepartTime(Timestamp departTime) {
		this.departTime = departTime;
	}
}