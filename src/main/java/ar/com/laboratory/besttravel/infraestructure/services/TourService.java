package ar.com.laboratory.besttravel.infraestructure.services;

import ar.com.laboratory.besttravel.api.models.requests.TourRequest;
import ar.com.laboratory.besttravel.api.models.responses.TourResponse;
import ar.com.laboratory.besttravel.domain.entities.jpa.*;
import ar.com.laboratory.besttravel.domain.repositories.jpa.CustomerRepository;
import ar.com.laboratory.besttravel.domain.repositories.jpa.FlyRepository;
import ar.com.laboratory.besttravel.domain.repositories.jpa.HotelRepository;
import ar.com.laboratory.besttravel.domain.repositories.jpa.TourRepository;
import ar.com.laboratory.besttravel.infraestructure.abstract_service.ITourService;
import ar.com.laboratory.besttravel.infraestructure.abstract_service.helpers.TourHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
@AllArgsConstructor
public class TourService implements ITourService {

    private final TourRepository tourRepository;
    private final CustomerRepository customerRepository;
    private final FlyRepository flyRepository;
    private final HotelRepository hotelRepository;
    private final TourHelper tourHelper;

    @Override
    public void removeTicket(Long tourId,UUID ticketId) {
       var tourUpdate = this.tourRepository.findById(tourId).orElseThrow();
       tourUpdate.removeTicket(ticketId);
       this.tourRepository.save(tourUpdate);
    }

    @Override
    public UUID addTicket(Long tourId,Long flyId) {
        var tourUpdate = this.tourRepository.findById(tourId).orElseThrow();
        var fly = this.flyRepository.findById(flyId).orElseThrow();
        var ticket = tourHelper.createTicket(fly,tourUpdate.getCustomer());
        tourUpdate.addTicket(ticket);
        this.tourRepository.save(tourUpdate);
        return ticket.getId();
    }

    @Override
    public void removeReservation(Long tourId,UUID reservationId) {
        var tourUpdate = this.tourRepository.findById(tourId).orElseThrow();
        tourUpdate.removeReservation(reservationId);
        this.tourRepository.save(tourUpdate);
    }

    @Override
    public UUID addReservation(Long tourId,Long hotelId, Integer totalDays) {
        var tourUpdate = this.tourRepository.findById(tourId).orElseThrow();
        var hotel = this.hotelRepository.findById(hotelId).orElseThrow();
        var reservation = this.tourHelper.createReservation(hotel, tourUpdate.getCustomer(), totalDays);
        tourUpdate.addReservation(reservation);
        this.tourRepository.save(tourUpdate);
        return reservation.getId();
    }

    @Override
    public TourResponse create(TourRequest request) {
        var customer = customerRepository.findById(request.getCustomerId()).orElseThrow();
        var flights = new HashSet<FlyEntity>();
        var hotels = new HashMap<HotelEntity,Integer>();
        request.getFlights().forEach(fly ->flights.add(flyRepository.findById(fly.getId()).orElseThrow()));
        request.getHotels().forEach(hotel -> hotels.put(hotelRepository.findById(hotel.getId()).orElseThrow(), hotel.getTotalDays()));
        var tourToSave = TourEntity.builder()
                .customer(customer)
                .tickets(tourHelper.createTickets(flights, customer))
                .reservations(tourHelper.createReservations(hotels,customer))
                .customer(customer)
                .build();
        var tourSaved  =  this.tourRepository.save(tourToSave);
        return TourResponse.builder()
                .id(tourSaved.getId())
                .ticketIds(tourSaved.getTickets().stream().map(TicketEntity::getId).collect(Collectors.toSet()))
                .reservationIds(tourSaved.getReservations().stream().map(ReservationEntity::getId).collect(Collectors.toSet())).build();
    }

    @Override
    public TourResponse read(Long id) {
        var tourFromDb = this.tourRepository.findById(id).orElseThrow();
        return TourResponse.builder()
                .reservationIds(tourFromDb.getTickets().stream().map(TicketEntity::getId).collect(Collectors.toSet()))
                .ticketIds(tourFromDb.getTickets().stream().map(TicketEntity::getId).collect(Collectors.toSet()))
                .id(tourFromDb.getId())
                .build();
    }

    @Override
    public void delete(Long id) {
        var tourToDelete = this.tourRepository.findById(id).orElseThrow();
        this.tourRepository.delete(tourToDelete);

    }
}
