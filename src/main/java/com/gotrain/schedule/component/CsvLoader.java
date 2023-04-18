package com.gotrain.schedule.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CsvLoader implements ApplicationRunner {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Load CSV data
        String sql = "INSERT INTO TRAIN_SCHEDULE (id, line, departure, arrival) SELECT * FROM CSVREAD('classpath:train.csv')";
        jdbcTemplate.execute(sql);
    }
}

