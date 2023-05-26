package ar.com.laboratory.besttravel.infraestructure.abstract_service;

import ar.com.laboratory.besttravel.api.models.requests.TourRequest;
import ar.com.laboratory.besttravel.api.models.responses.TourResponse;

import java.util.UUID;

public interface ITourService extends SimpleCrudService<TourRequest, TourResponse,Long> {

    void removeTicket(UUID ticketId, Long tourId);
    UUID addTicket(Long flyId, Long tourId);
    void removeHotel(UUID hoteltId, Long tourId);
    UUID addHotel(Long hotelId, Long tourId);
    void removeReservation(UUID reservationId, Long tourId);
    UUID addReservation(Long reservationId, Long tourId);

}
