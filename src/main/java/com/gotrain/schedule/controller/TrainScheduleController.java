package com.gotrain.schedule.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.gotrain.schedule.bean.TrainSchedule;
import com.gotrain.schedule.repository.TrainScheduleRepository;

/**
 * @author Sagar.Mohanty
 * go-train-schedule
 *	Apr 18, 2023
 */

@RestController
@RequestMapping("/schedule")
public class TrainScheduleController {

	@Autowired
	private TrainScheduleRepository trainScheduleRepository;

	@GetMapping
	@Cacheable("myCache")
	public ResponseEntity<List<TrainSchedule>> getTrainScheduleAll() {
		try {
			List<TrainSchedule> allTrainSchedule = trainScheduleRepository.findAll();
			if (allTrainSchedule.size() == 0) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			} else
				return ResponseEntity.ok().body(allTrainSchedule);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/{line}")
	@Cacheable("myCache")
	public ResponseEntity<List<TrainSchedule>> getTrainScheduleByLine(@PathVariable String line) {
		try {
			List<TrainSchedule> trainSchedule = trainScheduleRepository.findByLine(line);
			if (trainSchedule.size() == 0) {
				System.out.println("is trainSchedule.size(): " + trainSchedule.size());
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			} else
				return ResponseEntity.ok().body(trainSchedule);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/v1/{line}")
	@Cacheable("myCache")
	public ResponseEntity<List<TrainSchedule>> getTrainScheduleByLineAndDeparture(@PathVariable String line,
			@RequestParam("departure") String departure) {
		try {
			List<TrainSchedule> trainSchedule = trainScheduleRepository.findByLineAndDeparture(line,
					convertMilitaryTimeToInteger(String.valueOf((departure))));
			return ResponseEntity.ok().body(trainSchedule);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	public static int convertMilitaryTimeToInteger(String departure) throws ParseException {
		String timeValue = departure;
		int time = 0;
		int hour =0;
		int minute =0;
		// Check if input value is in 24-hour military or 12-hour military format
		boolean is24HourFormat = timeValue.matches("^(0?[0-9]|1[0-9]|2[0-3])[0-5][0-9]$");
		boolean is12HourFormat = timeValue.matches("^([0]?[1-9]|1[0-2]):([0-5]\\d)([ap]m)$");
		
		if (is24HourFormat) {
			// Convert 24-hour military format to an integer
			time = Integer.parseInt(timeValue);
			System.out.println("Input value is in 24-hour military format. Converted value: " + time);
			return time;
		} else if (is12HourFormat) {

			SimpleDateFormat format1 = new SimpleDateFormat("h:mmaa");
			Date date1 = format1.parse(timeValue);
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(date1);

			hour = cal1.get(Calendar.HOUR_OF_DAY);
			minute = cal1.get(Calendar.MINUTE);
			String amPm = cal1.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";

			System.out.println("The hour value is: " + hour + " : " + minute + " : " + amPm);
			// Convert 12-hour military format to an integer
			if (amPm.equalsIgnoreCase("pm") && hour != 12) {
				hour += 12;
			} else if (amPm.equalsIgnoreCase("am") && hour == 12) {
				hour = 0;
			}
			time = hour * 100 + minute;
			System.out.println("Input value is in 12-hour military format. Converted value: " + time);
			return time;
		} else {
			System.out.println("Input value in Integer format: " + Integer.parseInt(timeValue));
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid parameter");
		}
	}

}
