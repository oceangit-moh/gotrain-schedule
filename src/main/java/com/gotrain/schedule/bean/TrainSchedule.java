package com.gotrain.schedule.bean;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "train_schedule")
public class TrainSchedule {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "line")
	private String line;
	
	@Column(name = "departure")
	private int departure;
	
	@Column(name = "arrival")
	private int arrival;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public int getDeparture() {
		return departure;
	}

	public void setDeparture(int departure) {
		this.departure = departure;
	}

	public int getArrival() {
		return arrival;
	}

	public void setArrival(int arrival) {
		this.arrival = arrival;
	}

	@Override
	public int hashCode() {
		return Objects.hash(arrival, departure, id, line);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrainSchedule other = (TrainSchedule) obj;
		return arrival == other.arrival && departure == other.departure && id == other.id
				&& Objects.equals(line, other.line);
	}

	@Override
	public String toString() {
		return "TrainSchedule [id=" + id + ", line=" + line + ", departure=" + departure + ", arrival=" + arrival + "]";
	}
}
