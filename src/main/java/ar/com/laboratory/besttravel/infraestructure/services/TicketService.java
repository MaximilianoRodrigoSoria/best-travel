package ar.com.laboratory.besttravel.infraestructure.services;

import ar.com.laboratory.besttravel.api.models.requests.TicketRequest;
import ar.com.laboratory.besttravel.api.models.responses.FlyResponse;
import ar.com.laboratory.besttravel.api.models.responses.TicketResponse;
import ar.com.laboratory.besttravel.domain.entities.jpa.TicketEntity;
import ar.com.laboratory.besttravel.domain.repositories.jpa.CustomerRepository;
import ar.com.laboratory.besttravel.domain.repositories.jpa.FlyRepository;
import ar.com.laboratory.besttravel.domain.repositories.jpa.TicketRepository;
import ar.com.laboratory.besttravel.infraestructure.abstract_service.ITicketService;
import ar.com.laboratory.besttravel.infraestructure.abstract_service.helpers.BlacklistHelper;
import ar.com.laboratory.besttravel.util.BestTravelUtil;
import ar.com.laboratory.besttravel.util.enums.Tables;
import ar.com.laboratory.besttravel.util.exceptions.IdNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class TicketService implements ITicketService {


    private final FlyRepository flyRepository;
    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;
    private final BlacklistHelper blacklistHelper;


    @Override
    public TicketResponse created(TicketRequest request) {
        blacklistHelper.isInBlacklist(request.getIdClient());
        var fly = flyRepository.findById(request.getIdFly()).orElseThrow(()-> new IdNotFoundException(Tables.Fly.name()));
        var customer = customerRepository.findById(request.getIdClient()).orElseThrow(()->new IdNotFoundException(Tables.Customer.name()));
        var ticketToPersist = TicketEntity.builder()
                .id(UUID.randomUUID())
                .fly(fly)
                .customer(customer)
                .price(fly.getPrice().add(fly.getPrice().multiply(CHARGER_PRICE_PERCENTAGE)))
                .purchaseDate(LocalDate.now())
                .arrivalDate(BestTravelUtil.getRandomSoon())
                .departureDate(BestTravelUtil.getRandomLatter())
                .build();

        var ticketPersisted = this.ticketRepository.save(ticketToPersist);
        log.info("Ticket saved id: {}", ticketPersisted.getId());
        return this.entityToResponse(ticketToPersist);
    }

    @Override
    public TicketResponse read(UUID uuid) {
        var ticketFROMDB = this.ticketRepository.findById(uuid).orElseThrow(()-> new IdNotFoundException(Tables.Ticket.name()));
        return this.entityToResponse(ticketFROMDB);
    }

    @Override
    public TicketResponse update(TicketRequest request, UUID uuid) {
        var ticketToUpdate = ticketRepository.findById(uuid).orElseThrow(()-> new IdNotFoundException(Tables.Ticket.name()));
        var fly = flyRepository.findById(request.getIdFly()).orElseThrow(()-> new IdNotFoundException(Tables.Fly.name()));
        ticketToUpdate.setFly(fly);
        ticketToUpdate.setPrice(fly.getPrice().add(fly.getPrice().multiply(CHARGER_PRICE_PERCENTAGE)));
        ticketToUpdate.setArrivalDate(BestTravelUtil.getRandomSoon());
        ticketToUpdate.setDepartureDate(BestTravelUtil.getRandomLatter());
        var ticketUpdated = this.ticketRepository.save(ticketToUpdate);
        log.info("Ticket updated with id {}", ticketUpdated.getId());
        return this.entityToResponse(ticketUpdated);
    }

    @Override
    public void delete(UUID uuid) {
        var ticketToDelete = ticketRepository.findById(uuid).orElseThrow(()-> new IdNotFoundException(Tables.Ticket.name()));
        log.info("Ticket {}", ticketToDelete.getId());
        this.ticketRepository.delete(ticketToDelete);
    }

    private TicketResponse entityToResponse(TicketEntity entity){
        var response = new TicketResponse();
        BeanUtils.copyProperties(entity,response);
        var flyResponse = new FlyResponse();
        BeanUtils.copyProperties(entity.getFly(),flyResponse);
        response.setFly(flyResponse);
        return  response;

    }

    @Override
    public BigDecimal findPrice(Long idFly) {
        var fly = this.flyRepository.findById(idFly).orElseThrow(()-> new IdNotFoundException(Tables.Fly.name()));
        return fly.getPrice().add(fly.getPrice().multiply(CHARGER_PRICE_PERCENTAGE));
    }

    private  static  final  BigDecimal CHARGER_PRICE_PERCENTAGE = BigDecimal.valueOf(0.25);
}
