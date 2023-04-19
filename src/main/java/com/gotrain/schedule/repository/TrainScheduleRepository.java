package com.gotrain.schedule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gotrain.schedule.bean.TrainSchedule;

/**
 * @author Sagar.Mohanty
 * go-train-schedule
 *	Apr 18, 2023
 */

@Repository
public interface TrainScheduleRepository extends JpaRepository<TrainSchedule, Integer> {
	List<TrainSchedule> findByLine(String line);

	List<TrainSchedule> findByLineAndDeparture(String line, int departure);
}
