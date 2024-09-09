package com.example.pfunctional_calling.config;


import com.example.pfunctional_calling.service.MockBookingStatusService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.Map;
import java.util.function.Function;

@Configuration
public class AppConfig {

    @Bean
    @Description("Get the status of a hotel booking") // function description
    public Function<MockBookingStatusService.BookingRequest, MockBookingStatusService.BookingResponse> bookingStatusFunction() {
        return new MockBookingStatusService();
    }

}
