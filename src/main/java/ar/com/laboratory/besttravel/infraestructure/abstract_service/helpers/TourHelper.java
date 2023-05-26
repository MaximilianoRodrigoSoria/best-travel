package ar.com.laboratory.besttravel.infraestructure.abstract_service.helpers;

import ar.com.laboratory.besttravel.domain.entities.jpa.*;
import ar.com.laboratory.besttravel.domain.repositories.jpa.ReservationRepository;
import ar.com.laboratory.besttravel.domain.repositories.jpa.TicketRepository;
import ar.com.laboratory.besttravel.util.BestTravelUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
@Transactional
@AllArgsConstructor
public class TourHelper {

    private final TicketRepository ticketRepository;
    private final ReservationRepository reservationRepository;

    public Set<TicketEntity>createTickets(Set<FlyEntity>flights, CustomerEntity customer){
        var response = new HashSet<TicketEntity>(flights.size());
        flights.forEach(fly ->{
            var ticketToPersist = TicketEntity.builder()
                    .id(UUID.randomUUID())
                    .fly(fly)
                    .customer(customer)
                    .price(fly.getPrice().add(fly.getPrice().multiply(CHARGER_PRICE_PERCENTAGE)))
                    .purchaseDate(LocalDate.now())
                    .arrivalDate(BestTravelUtil.getRandomSoon())
                    .departureDate(BestTravelUtil.getRandomLatter())
                    .build();
            response.add(this.ticketRepository.save(ticketToPersist));

        });
        return  response;
    }


    public Set<ReservationEntity>createReservations(HashMap<HotelEntity, Integer> hotels, CustomerEntity customer){
        var response = new HashSet<ReservationEntity>(hotels.size());
        hotels.forEach((hotel, totalDays) ->{
        var reservationToPersist = ReservationEntity.builder()
                .id(UUID.randomUUID())
                .price(hotel.getPrice())
                .hotel(hotel)
                .customer(customer)
                .dateTimeReservation(LocalDateTime.now())
                .totalDays(totalDays)
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now().plusDays(totalDays))
                .price(hotel.getPrice().add(hotel.getPrice().multiply(CHARGER_PRICE_PERCENTAGE)))
                .build();
            response.add(reservationRepository.save(reservationToPersist));

        });
        return  response;
    }

    public TicketEntity createTicket(FlyEntity fly, CustomerEntity customer){
        var ticketToPersist = TicketEntity.builder()
                .id(UUID.randomUUID())
                .fly(fly)
                .customer(customer)
                .price(fly.getPrice().add(fly.getPrice().multiply(CHARGER_PRICE_PERCENTAGE)))
                .purchaseDate(LocalDate.now())
                .arrivalDate(BestTravelUtil.getRandomSoon())
                .departureDate(BestTravelUtil.getRandomLatter())
                .build();

        return  this.ticketRepository.save(ticketToPersist);
    }
    private  static  final BigDecimal CHARGER_PRICE_PERCENTAGE = BigDecimal.valueOf(0.25);

}
