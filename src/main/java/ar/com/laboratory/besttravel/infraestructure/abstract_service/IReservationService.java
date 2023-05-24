package ar.com.laboratory.besttravel.infraestructure.abstract_service;

import ar.com.laboratory.besttravel.api.models.requests.ReservationRequest;
import ar.com.laboratory.besttravel.api.models.responses.ReservationResponse;

import java.math.BigDecimal;
import java.util.UUID;

public interface IReservationService  extends CrudService<ReservationRequest, ReservationResponse, UUID> {

    BigDecimal findPrice(Long idHotel);
}
