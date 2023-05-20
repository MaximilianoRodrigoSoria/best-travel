package ar.com.laboratory.besttravel;

import ar.com.laboratory.besttravel.domain.entities.ReservationEntity;
import ar.com.laboratory.besttravel.domain.entities.TicketEntity;
import ar.com.laboratory.besttravel.domain.entities.TourEntity;
import ar.com.laboratory.besttravel.domain.repositories.jpa.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@SpringBootApplication
public class BestTravelApplication {

    public static void main(String[] args) {
        SpringApplication.run(BestTravelApplication.class, args);
    }

}
