package com.gotrain.schedule;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.gotrain.schedule.bean.TrainSchedule;
import com.gotrain.schedule.repository.TrainScheduleRepository;

/**
 * @author Sagar.Mohanty
 * go-train-schedule
 *	Apr 18, 2023
 */

@SpringBootTest
@AutoConfigureMockMvc
class GoTrainScheduleApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TrainScheduleRepository trainScheduleRepository;

	TrainSchedule trainSchedule1 = new TrainSchedule(1, "Lakeshore", 800, 900);
	TrainSchedule trainSchedule2 = new TrainSchedule(2, "Lakeshore", 1000, 1100);
	TrainSchedule trainSchedule3 = new TrainSchedule(3, "Lakeshore", 1200, 1300);
	TrainSchedule trainSchedule4 = new TrainSchedule(4, "Lakeshore", 1400, 1500);
	List<TrainSchedule> list = new ArrayList<>();
	List<TrainSchedule> emptyList = new ArrayList<>();

	@Test
	public void testGetTrainScheduleAll200() throws Exception {
		list.add(trainSchedule1);
		list.add(trainSchedule2);
		list.add(trainSchedule3);
		list.add(trainSchedule4);
		when(trainScheduleRepository.findAll()).thenReturn(list);
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/schedule").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		System.out.println(result.getResponse());
	}

	@Test
	public void testGetTrainScheduleByLine200() throws Exception {
		list.add(trainSchedule1);
		when(trainScheduleRepository.findByLine("Lakeshore")).thenReturn(list);
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/schedule/Lakeshore").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		System.out.println(result.getResponse());
	}

	@Test
	public void testGetTrainScheduleByLine404() throws Exception {
		when(trainScheduleRepository.findByLine("Lakeshore1")).thenReturn(Collections.emptyList());
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/schedule/Lakeshore1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
		System.out.println(result.getResponse());
	}

	@Test
	public void testGetTrainScheduleByLineAndDeparture12Hour() throws Exception {
		list.add(trainSchedule1);
		when(trainScheduleRepository.findByLineAndDeparture("Lakeshore", 800)).thenReturn(list);
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/schedule/v1/Lakeshore?departure=08:00am")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		System.out.println(result.getResponse());
	}

	@Test
	public void testGetTrainScheduleByLineAndDeparture24Hour() throws Exception {
		list.add(trainSchedule4);
		when(trainScheduleRepository.findByLineAndDeparture("Lakeshore", 1400)).thenReturn(list);
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/schedule/v1/Lakeshore?departure=1400")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		System.out.println(result.getResponse());
	}

	@Test
	public void testGetTrainScheduleByLineAndDeparture400() throws Exception {
		list.add(trainSchedule1);
		when(trainScheduleRepository.findByLineAndDeparture("Lakeshore", 8100)).thenReturn(list);
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/schedule/v1/Lakeshore?departure=8100")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
		System.out.println(result.getResponse());
	}
	
}