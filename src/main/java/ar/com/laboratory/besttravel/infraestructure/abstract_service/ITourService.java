package ar.com.laboratory.besttravel.infraestructure.abstract_service;

import ar.com.laboratory.besttravel.api.models.requests.TourRequest;
import ar.com.laboratory.besttravel.api.models.responses.TourResponse;

import java.util.UUID;

public interface ITourService extends SimpleCrudService<TourRequest, TourResponse,Long> {

    void removeTicket(Long tourId,UUID ticketId);
    UUID addTicket(Long tourId, Long flyId);
    void removeReservation(Long tourId,UUID reservationId);
    UUID addReservation(Long tourId, Long hotelId, Integer totalDays);

}
