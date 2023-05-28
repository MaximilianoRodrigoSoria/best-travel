package ar.com.laboratory.besttravel.infraestructure.services;

import ar.com.laboratory.besttravel.api.models.requests.ReservationRequest;
import ar.com.laboratory.besttravel.api.models.responses.HotelResponse;
import ar.com.laboratory.besttravel.api.models.responses.ReservationResponse;
import ar.com.laboratory.besttravel.domain.entities.jpa.ReservationEntity;
import ar.com.laboratory.besttravel.domain.repositories.jpa.CustomerRepository;
import ar.com.laboratory.besttravel.domain.repositories.jpa.HotelRepository;
import ar.com.laboratory.besttravel.domain.repositories.jpa.ReservationRepository;
import ar.com.laboratory.besttravel.infraestructure.abstract_service.IReservationService;
import ar.com.laboratory.besttravel.infraestructure.abstract_service.helpers.ApiCurrencyConnectorHelper;
import ar.com.laboratory.besttravel.infraestructure.abstract_service.helpers.BlacklistHelper;
import ar.com.laboratory.besttravel.infraestructure.abstract_service.helpers.CustomerHelper;
import ar.com.laboratory.besttravel.infraestructure.abstract_service.helpers.EmailHelper;
import ar.com.laboratory.besttravel.util.enums.Tables;
import ar.com.laboratory.besttravel.util.exceptions.IdNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Objects;
import java.util.UUID;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class ReservationService implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final HotelRepository hotelRepository;
    private final CustomerRepository customerRepository;
    private final BlacklistHelper blacklistHelper;
    private final CustomerHelper customerHelper;
    private final ApiCurrencyConnectorHelper currencyConnectorHelper;
    private final EmailHelper emailHelper;
    @Override
    public ReservationResponse created(ReservationRequest request) {
        blacklistHelper.isInBlacklist(request.getIdClient());
        var hotel = hotelRepository.findById(request.getIdHotel()).orElseThrow(()-> new IdNotFoundException(Tables.Hotel.name()));
        var customer = customerRepository.findById(request.getIdClient()).orElseThrow(()-> new IdNotFoundException(Tables.Customer.name()));
        var reservationToPersist = ReservationEntity.builder()
                .id(UUID.randomUUID())
                .price(hotel.getPrice())
                .hotel(hotel)
                .customer(customer)
                .dateTimeReservation(LocalDateTime.now())
                .totalDays(request.getTotalDays())
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now().plusDays(request.getTotalDays()))
                .price(hotel.getPrice().add(hotel.getPrice().multiply(CHARGER_PRICE_PERCENTAGE)))
                .build();
        var reservationPersisted = reservationRepository.save(reservationToPersist);
        this.customerHelper.incrase(customer.getDni(), ReservationService.class);

        if(Objects.nonNull(request.getEmail())) this.emailHelper.sendMail(request.getEmail(), customer.getFullName(), Tables.Reservation.name(),"Boda de Yesi y Maxi Soria");
        return this.entityToResponse(reservationPersisted);
    }

    @Override
    public ReservationResponse read(UUID uuid) {
        var reservertionBD = reservationRepository.findById(uuid).orElseThrow(()-> new IdNotFoundException("Reservation"));
        return this.entityToResponse(reservertionBD);
    }

    @Override
    public ReservationResponse update(ReservationRequest request, UUID uuid) {
        var hotel = hotelRepository.findById(request.getIdHotel()).orElseThrow(()-> new IdNotFoundException(Tables.Hotel.name()));
        var reservationToUpdate = reservationRepository.findById(uuid).orElseThrow(()-> new IdNotFoundException(Tables.Reservation.name()));


        reservationToUpdate.setHotel(hotel);
        reservationToUpdate.setTotalDays(request.getTotalDays());
        reservationToUpdate.setDateTimeReservation(LocalDateTime.now());
        reservationToUpdate.setDateStart(LocalDate.now());
        reservationToUpdate.setDateEnd(LocalDate.now().plusDays(request.getTotalDays()));
        reservationToUpdate.setPrice(hotel.getPrice().add(hotel.getPrice().multiply(CHARGER_PRICE_PERCENTAGE)));

        var reservationUpdated = this.reservationRepository.save(reservationToUpdate);
        log.info("Reservation updated with id {}", reservationUpdated.getId());

        return this.entityToResponse(reservationUpdated);
    }

    @Override
    public void delete(UUID uuid) {
        var reservation= reservationRepository.findById(uuid).orElseThrow(()-> new IdNotFoundException(Tables.Reservation.name()));
        reservationRepository.delete(reservation);

    }

    private ReservationResponse entityToResponse(ReservationEntity entity) {
        var response = new ReservationResponse();
        BeanUtils.copyProperties(entity, response);
        var hotelResponse = new HotelResponse();
        BeanUtils.copyProperties(entity.getHotel(), hotelResponse);
        response.setHotel(hotelResponse);
        return response;
    }

    private  static  final BigDecimal CHARGER_PRICE_PERCENTAGE = BigDecimal.valueOf(0.25);

    @Override
    public BigDecimal findPrice(Long hotelId, Currency currency) {
        var hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new IdNotFoundException(Tables.Hotel.name()));

        var priceInDollars =  hotel.getPrice().add(hotel.getPrice().multiply(CHARGER_PRICE_PERCENTAGE));
        if (currency.equals(Currency.getInstance("USD"))) return priceInDollars;
        var currencyDTO = this.currencyConnectorHelper.getCurrency(currency);
        log.info("API currency in {}, response: {}", currencyDTO.getExchangeDate().toString(), currencyDTO.getRates());
        return priceInDollars.multiply(currencyDTO.getRates().get(currency));
    }
}
