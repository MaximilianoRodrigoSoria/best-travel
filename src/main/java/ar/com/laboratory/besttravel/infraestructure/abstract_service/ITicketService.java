package ar.com.laboratory.besttravel.infraestructure.abstract_service;

import ar.com.laboratory.besttravel.api.models.requests.TicketRequest;
import ar.com.laboratory.besttravel.api.models.responses.TicketResponse;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.UUID;

public interface ITicketService extends CrudService<TicketRequest, TicketResponse, UUID> {


    BigDecimal findPrice(Long idFly);
}
