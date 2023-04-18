package com.gotrain.schedule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gotrain.schedule.bean.TrainSchedule;

public interface TrainScheduleRepository extends JpaRepository<TrainSchedule, Integer>{
	List<TrainSchedule> findByLine(String line);
	List<TrainSchedule> getTrainScheduleByLineAndDeparture(String line, int departure);
}
