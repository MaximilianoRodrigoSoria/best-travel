package ar.com.laboratory.besttravel.infraestructure.services;

import ar.com.laboratory.besttravel.api.models.requests.ReservationRequest;
import ar.com.laboratory.besttravel.api.models.responses.HotelResponse;
import ar.com.laboratory.besttravel.api.models.responses.ReservationResponse;
import ar.com.laboratory.besttravel.domain.entities.jpa.ReservationEntity;
import ar.com.laboratory.besttravel.domain.repositories.jpa.CustomerRepository;
import ar.com.laboratory.besttravel.domain.repositories.jpa.HotelRepository;
import ar.com.laboratory.besttravel.domain.repositories.jpa.ReservationRepository;
import ar.com.laboratory.besttravel.infraestructure.abstract_service.IReservationService;
import ar.com.laboratory.besttravel.util.exceptions.IdNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class ReservationService implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final HotelRepository hotelRepository;
    private final CustomerRepository customerRepository;
    @Override
    public ReservationResponse created(ReservationRequest request) {
        var hotel = hotelRepository.findById(request.getIdHotel()).orElseThrow(()-> new IdNotFoundException("Hotel"));
        var customer = customerRepository.findById(request.getIdClient()).orElseThrow(()-> new IdNotFoundException("Customer"));
        var reservation = ReservationEntity.builder()
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
        var reservationSaved = reservationRepository.save(reservation);
        return this.entityToResponse(reservationSaved) ;
    }

    @Override
    public ReservationResponse read(UUID uuid) {
        var reservertionBD = reservationRepository.findById(uuid).orElseThrow(()-> new IdNotFoundException("Reservation"));
        return this.entityToResponse(reservertionBD);
    }

    @Override
    public ReservationResponse update(ReservationRequest request, UUID uuid) {
        var hotel = hotelRepository.findById(request.getIdHotel()).orElseThrow(()-> new IdNotFoundException("Hotel"));
        var reservationToUpdate = reservationRepository.findById(uuid).orElseThrow(()-> new IdNotFoundException("Reservation"));


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
        var reservation= reservationRepository.findById(uuid).orElseThrow(()-> new IdNotFoundException("Reservation"));
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
    public BigDecimal findPrice(Long idHotel) {
        var hotel = hotelRepository.findById(idHotel).orElseThrow(()-> new IdNotFoundException("Hotel"));
        return hotel.getPrice();
    }
}
