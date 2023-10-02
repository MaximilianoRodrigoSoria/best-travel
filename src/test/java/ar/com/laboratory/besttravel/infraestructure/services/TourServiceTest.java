package ar.com.laboratory.besttravel.infraestructure.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ar.com.laboratory.besttravel.api.models.requests.TourFlyRequest;
import ar.com.laboratory.besttravel.api.models.requests.TourRequest;
import ar.com.laboratory.besttravel.api.models.responses.TourResponse;
import ar.com.laboratory.besttravel.domain.entities.jpa.CustomerEntity;
import ar.com.laboratory.besttravel.domain.entities.jpa.FlyEntity;
import ar.com.laboratory.besttravel.domain.entities.jpa.HotelEntity;
import ar.com.laboratory.besttravel.domain.entities.jpa.ReservationEntity;
import ar.com.laboratory.besttravel.domain.entities.jpa.TicketEntity;
import ar.com.laboratory.besttravel.domain.entities.jpa.TourEntity;
import ar.com.laboratory.besttravel.domain.repositories.jpa.CustomerRepository;
import ar.com.laboratory.besttravel.domain.repositories.jpa.FlyRepository;
import ar.com.laboratory.besttravel.domain.repositories.jpa.HotelRepository;
import ar.com.laboratory.besttravel.domain.repositories.jpa.TourRepository;
import ar.com.laboratory.besttravel.infraestructure.abstract_service.helpers.BlacklistHelper;
import ar.com.laboratory.besttravel.infraestructure.abstract_service.helpers.TourHelper;
import ar.com.laboratory.besttravel.util.enums.AeroLine;
import ar.com.laboratory.besttravel.util.exceptions.IdNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TourService.class})
@ExtendWith(SpringExtension.class)
class TourServiceTest {
    @MockBean
    private BlacklistHelper blacklistHelper;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private FlyRepository flyRepository;

    @MockBean
    private HotelRepository hotelRepository;

    @MockBean
    private TourHelper tourHelper;

    @MockBean
    private TourRepository tourRepository;

    @Autowired
    private TourService tourService;

    /**
     * Method under test: {@link TourService#removeTicket(Long, UUID)}
     */
    @Test
    void testRemoveTicket() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCreditCard("Credit Card");
        customer.setDni("Dni");
        customer.setFullName("Dr Jane Doe");
        customer.setPhoneNumber("6625550144");
        customer.setReservations(new HashSet<>());
        customer.setTickets(new HashSet<>());
        customer.setTotalFlights(1);
        customer.setTotalLodgings(1);
        customer.setTotalTours(1);
        customer.setTours(new HashSet<>());

        TourEntity tourEntity = new TourEntity();
        tourEntity.setCustomer(customer);
        tourEntity.setId(1L);
        tourEntity.setReservations(new HashSet<>());
        tourEntity.setTickets(new HashSet<>());
        Optional<TourEntity> ofResult = Optional.of(tourEntity);

        CustomerEntity customer2 = new CustomerEntity();
        customer2.setCreditCard("Credit Card");
        customer2.setDni("Dni");
        customer2.setFullName("Dr Jane Doe");
        customer2.setPhoneNumber("6625550144");
        customer2.setReservations(new HashSet<>());
        customer2.setTickets(new HashSet<>());
        customer2.setTotalFlights(1);
        customer2.setTotalLodgings(1);
        customer2.setTotalTours(1);
        customer2.setTours(new HashSet<>());

        TourEntity tourEntity2 = new TourEntity();
        tourEntity2.setCustomer(customer2);
        tourEntity2.setId(1L);
        tourEntity2.setReservations(new HashSet<>());
        tourEntity2.setTickets(new HashSet<>());
        when(tourRepository.save(Mockito.<TourEntity>any())).thenReturn(tourEntity2);
        when(tourRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        tourService.removeTicket(1L, UUID.randomUUID());
        verify(tourRepository).save(Mockito.<TourEntity>any());
        verify(tourRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link TourService#removeTicket(Long, UUID)}
     */
    @Test
    void testRemoveTicket2() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCreditCard("Credit Card");
        customer.setDni("Dni");
        customer.setFullName("Dr Jane Doe");
        customer.setPhoneNumber("6625550144");
        customer.setReservations(new HashSet<>());
        customer.setTickets(new HashSet<>());
        customer.setTotalFlights(1);
        customer.setTotalLodgings(1);
        customer.setTotalTours(1);
        customer.setTours(new HashSet<>());

        TourEntity tourEntity = new TourEntity();
        tourEntity.setCustomer(customer);
        tourEntity.setId(1L);
        tourEntity.setReservations(new HashSet<>());
        tourEntity.setTickets(new HashSet<>());
        Optional<TourEntity> ofResult = Optional.of(tourEntity);
        when(tourRepository.save(Mockito.<TourEntity>any())).thenThrow(new IdNotFoundException("Table Name"));
        when(tourRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(IdNotFoundException.class, () -> tourService.removeTicket(1L, UUID.randomUUID()));
        verify(tourRepository).save(Mockito.<TourEntity>any());
        verify(tourRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link TourService#removeTicket(Long, UUID)}
     */
    @Test
    void testRemoveTicket3() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCreditCard("Credit Card");
        customer.setDni("Dni");
        customer.setFullName("Dr Jane Doe");
        customer.setPhoneNumber("6625550144");
        customer.setReservations(new HashSet<>());
        customer.setTickets(new HashSet<>());
        customer.setTotalFlights(1);
        customer.setTotalLodgings(1);
        customer.setTotalTours(1);
        customer.setTours(new HashSet<>());
        TourEntity tourEntity = mock(TourEntity.class);
        doNothing().when(tourEntity).removeTicket(Mockito.<UUID>any());
        doNothing().when(tourEntity).setCustomer(Mockito.<CustomerEntity>any());
        doNothing().when(tourEntity).setId(Mockito.<Long>any());
        doNothing().when(tourEntity).setReservations(Mockito.<Set<ReservationEntity>>any());
        doNothing().when(tourEntity).setTickets(Mockito.<Set<TicketEntity>>any());
        tourEntity.setCustomer(customer);
        tourEntity.setId(1L);
        tourEntity.setReservations(new HashSet<>());
        tourEntity.setTickets(new HashSet<>());
        Optional<TourEntity> ofResult = Optional.of(tourEntity);

        CustomerEntity customer2 = new CustomerEntity();
        customer2.setCreditCard("Credit Card");
        customer2.setDni("Dni");
        customer2.setFullName("Dr Jane Doe");
        customer2.setPhoneNumber("6625550144");
        customer2.setReservations(new HashSet<>());
        customer2.setTickets(new HashSet<>());
        customer2.setTotalFlights(1);
        customer2.setTotalLodgings(1);
        customer2.setTotalTours(1);
        customer2.setTours(new HashSet<>());

        TourEntity tourEntity2 = new TourEntity();
        tourEntity2.setCustomer(customer2);
        tourEntity2.setId(1L);
        tourEntity2.setReservations(new HashSet<>());
        tourEntity2.setTickets(new HashSet<>());
        when(tourRepository.save(Mockito.<TourEntity>any())).thenReturn(tourEntity2);
        when(tourRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        tourService.removeTicket(1L, UUID.randomUUID());
        verify(tourRepository).save(Mockito.<TourEntity>any());
        verify(tourRepository).findById(Mockito.<Long>any());
        verify(tourEntity).removeTicket(Mockito.<UUID>any());
        verify(tourEntity).setCustomer(Mockito.<CustomerEntity>any());
        verify(tourEntity).setId(Mockito.<Long>any());
        verify(tourEntity).setReservations(Mockito.<Set<ReservationEntity>>any());
        verify(tourEntity).setTickets(Mockito.<Set<TicketEntity>>any());
    }

    /**
     * Method under test: {@link TourService#addTicket(Long, Long)}
     */
    @Test
    void testAddTicket() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCreditCard("Credit Card");
        customer.setDni("Dni");
        customer.setFullName("Dr Jane Doe");
        customer.setPhoneNumber("6625550144");
        customer.setReservations(new HashSet<>());
        customer.setTickets(new HashSet<>());
        customer.setTotalFlights(1);
        customer.setTotalLodgings(1);
        customer.setTotalTours(1);
        customer.setTours(new HashSet<>());

        TourEntity tourEntity = new TourEntity();
        tourEntity.setCustomer(customer);
        tourEntity.setId(1L);
        tourEntity.setReservations(new HashSet<>());
        tourEntity.setTickets(new HashSet<>());
        Optional<TourEntity> ofResult = Optional.of(tourEntity);

        CustomerEntity customer2 = new CustomerEntity();
        customer2.setCreditCard("Credit Card");
        customer2.setDni("Dni");
        customer2.setFullName("Dr Jane Doe");
        customer2.setPhoneNumber("6625550144");
        customer2.setReservations(new HashSet<>());
        customer2.setTickets(new HashSet<>());
        customer2.setTotalFlights(1);
        customer2.setTotalLodgings(1);
        customer2.setTotalTours(1);
        customer2.setTours(new HashSet<>());

        TourEntity tourEntity2 = new TourEntity();
        tourEntity2.setCustomer(customer2);
        tourEntity2.setId(1L);
        tourEntity2.setReservations(new HashSet<>());
        tourEntity2.setTickets(new HashSet<>());
        when(tourRepository.save(Mockito.<TourEntity>any())).thenReturn(tourEntity2);
        when(tourRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        FlyEntity flyEntity = new FlyEntity();
        flyEntity.setAeroLine(AeroLine.aero_gold);
        flyEntity.setDestinyLat(10.0d);
        flyEntity.setDestinyLng(10.0d);
        flyEntity.setDestinyName("Destiny Name");
        flyEntity.setId(1L);
        flyEntity.setOriginLat(10.0d);
        flyEntity.setOriginLng(10.0d);
        flyEntity.setOriginName("Origin Name");
        flyEntity.setPrice(BigDecimal.valueOf(1L));
        flyEntity.setTickets(new HashSet<>());
        Optional<FlyEntity> ofResult2 = Optional.of(flyEntity);
        when(flyRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        CustomerEntity customer3 = new CustomerEntity();
        customer3.setCreditCard("Credit Card");
        customer3.setDni("Dni");
        customer3.setFullName("Dr Jane Doe");
        customer3.setPhoneNumber("6625550144");
        customer3.setReservations(new HashSet<>());
        customer3.setTickets(new HashSet<>());
        customer3.setTotalFlights(1);
        customer3.setTotalLodgings(1);
        customer3.setTotalTours(1);
        customer3.setTours(new HashSet<>());

        FlyEntity fly = new FlyEntity();
        fly.setAeroLine(AeroLine.aero_gold);
        fly.setDestinyLat(10.0d);
        fly.setDestinyLng(10.0d);
        fly.setDestinyName("Destiny Name");
        fly.setId(1L);
        fly.setOriginLat(10.0d);
        fly.setOriginLng(10.0d);
        fly.setOriginName("Origin Name");
        fly.setPrice(BigDecimal.valueOf(1L));
        fly.setTickets(new HashSet<>());

        CustomerEntity customer4 = new CustomerEntity();
        customer4.setCreditCard("Credit Card");
        customer4.setDni("Dni");
        customer4.setFullName("Dr Jane Doe");
        customer4.setPhoneNumber("6625550144");
        customer4.setReservations(new HashSet<>());
        customer4.setTickets(new HashSet<>());
        customer4.setTotalFlights(1);
        customer4.setTotalLodgings(1);
        customer4.setTotalTours(1);
        customer4.setTours(new HashSet<>());

        TourEntity tour = new TourEntity();
        tour.setCustomer(customer4);
        tour.setId(1L);
        tour.setReservations(new HashSet<>());
        tour.setTickets(new HashSet<>());

        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setArrivalDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity.setCustomer(customer3);
        ticketEntity.setDepartureDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity.setFly(fly);
        UUID id = UUID.randomUUID();
        ticketEntity.setId(id);
        ticketEntity.setPrice(BigDecimal.valueOf(1L));
        ticketEntity.setPurchaseDate(LocalDate.of(1970, 1, 1));
        ticketEntity.setTour(tour);
        when(tourHelper.createTicket(Mockito.<FlyEntity>any(), Mockito.<CustomerEntity>any())).thenReturn(ticketEntity);
        assertSame(id, tourService.addTicket(1L, 1L));
        verify(tourRepository).save(Mockito.<TourEntity>any());
        verify(tourRepository).findById(Mockito.<Long>any());
        verify(flyRepository).findById(Mockito.<Long>any());
        verify(tourHelper).createTicket(Mockito.<FlyEntity>any(), Mockito.<CustomerEntity>any());
    }

    /**
     * Method under test: {@link TourService#addTicket(Long, Long)}
     */
    @Test
    void testAddTicket2() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCreditCard("Credit Card");
        customer.setDni("Dni");
        customer.setFullName("Dr Jane Doe");
        customer.setPhoneNumber("6625550144");
        customer.setReservations(new HashSet<>());
        customer.setTickets(new HashSet<>());
        customer.setTotalFlights(1);
        customer.setTotalLodgings(1);
        customer.setTotalTours(1);
        customer.setTours(new HashSet<>());

        TourEntity tourEntity = new TourEntity();
        tourEntity.setCustomer(customer);
        tourEntity.setId(1L);
        tourEntity.setReservations(new HashSet<>());
        tourEntity.setTickets(new HashSet<>());
        Optional<TourEntity> ofResult = Optional.of(tourEntity);
        when(tourRepository.save(Mockito.<TourEntity>any())).thenThrow(new IdNotFoundException("Table Name"));
        when(tourRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        FlyEntity flyEntity = new FlyEntity();
        flyEntity.setAeroLine(AeroLine.aero_gold);
        flyEntity.setDestinyLat(10.0d);
        flyEntity.setDestinyLng(10.0d);
        flyEntity.setDestinyName("Destiny Name");
        flyEntity.setId(1L);
        flyEntity.setOriginLat(10.0d);
        flyEntity.setOriginLng(10.0d);
        flyEntity.setOriginName("Origin Name");
        flyEntity.setPrice(BigDecimal.valueOf(1L));
        flyEntity.setTickets(new HashSet<>());
        Optional<FlyEntity> ofResult2 = Optional.of(flyEntity);
        when(flyRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        CustomerEntity customer2 = new CustomerEntity();
        customer2.setCreditCard("Credit Card");
        customer2.setDni("Dni");
        customer2.setFullName("Dr Jane Doe");
        customer2.setPhoneNumber("6625550144");
        customer2.setReservations(new HashSet<>());
        customer2.setTickets(new HashSet<>());
        customer2.setTotalFlights(1);
        customer2.setTotalLodgings(1);
        customer2.setTotalTours(1);
        customer2.setTours(new HashSet<>());

        FlyEntity fly = new FlyEntity();
        fly.setAeroLine(AeroLine.aero_gold);
        fly.setDestinyLat(10.0d);
        fly.setDestinyLng(10.0d);
        fly.setDestinyName("Destiny Name");
        fly.setId(1L);
        fly.setOriginLat(10.0d);
        fly.setOriginLng(10.0d);
        fly.setOriginName("Origin Name");
        fly.setPrice(BigDecimal.valueOf(1L));
        fly.setTickets(new HashSet<>());

        CustomerEntity customer3 = new CustomerEntity();
        customer3.setCreditCard("Credit Card");
        customer3.setDni("Dni");
        customer3.setFullName("Dr Jane Doe");
        customer3.setPhoneNumber("6625550144");
        customer3.setReservations(new HashSet<>());
        customer3.setTickets(new HashSet<>());
        customer3.setTotalFlights(1);
        customer3.setTotalLodgings(1);
        customer3.setTotalTours(1);
        customer3.setTours(new HashSet<>());

        TourEntity tour = new TourEntity();
        tour.setCustomer(customer3);
        tour.setId(1L);
        tour.setReservations(new HashSet<>());
        tour.setTickets(new HashSet<>());

        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setArrivalDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity.setCustomer(customer2);
        ticketEntity.setDepartureDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity.setFly(fly);
        ticketEntity.setId(UUID.randomUUID());
        ticketEntity.setPrice(BigDecimal.valueOf(1L));
        ticketEntity.setPurchaseDate(LocalDate.of(1970, 1, 1));
        ticketEntity.setTour(tour);
        when(tourHelper.createTicket(Mockito.<FlyEntity>any(), Mockito.<CustomerEntity>any())).thenReturn(ticketEntity);
        assertThrows(IdNotFoundException.class, () -> tourService.addTicket(1L, 1L));
        verify(tourRepository).save(Mockito.<TourEntity>any());
        verify(tourRepository).findById(Mockito.<Long>any());
        verify(flyRepository).findById(Mockito.<Long>any());
        verify(tourHelper).createTicket(Mockito.<FlyEntity>any(), Mockito.<CustomerEntity>any());
    }

    /**
     * Method under test: {@link TourService#addTicket(Long, Long)}
     */
    @Test
    void testAddTicket3() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCreditCard("Credit Card");
        customer.setDni("Dni");
        customer.setFullName("Dr Jane Doe");
        customer.setPhoneNumber("6625550144");
        customer.setReservations(new HashSet<>());
        customer.setTickets(new HashSet<>());
        customer.setTotalFlights(1);
        customer.setTotalLodgings(1);
        customer.setTotalTours(1);
        customer.setTours(new HashSet<>());

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCreditCard("Credit Card");
        customerEntity.setDni("Dni");
        customerEntity.setFullName("Dr Jane Doe");
        customerEntity.setPhoneNumber("6625550144");
        customerEntity.setReservations(new HashSet<>());
        customerEntity.setTickets(new HashSet<>());
        customerEntity.setTotalFlights(1);
        customerEntity.setTotalLodgings(1);
        customerEntity.setTotalTours(1);
        customerEntity.setTours(new HashSet<>());
        TourEntity tourEntity = mock(TourEntity.class);
        when(tourEntity.getCustomer()).thenReturn(customerEntity);
        doNothing().when(tourEntity).addTicket(Mockito.<TicketEntity>any());
        doNothing().when(tourEntity).setCustomer(Mockito.<CustomerEntity>any());
        doNothing().when(tourEntity).setId(Mockito.<Long>any());
        doNothing().when(tourEntity).setReservations(Mockito.<Set<ReservationEntity>>any());
        doNothing().when(tourEntity).setTickets(Mockito.<Set<TicketEntity>>any());
        tourEntity.setCustomer(customer);
        tourEntity.setId(1L);
        tourEntity.setReservations(new HashSet<>());
        tourEntity.setTickets(new HashSet<>());
        Optional<TourEntity> ofResult = Optional.of(tourEntity);

        CustomerEntity customer2 = new CustomerEntity();
        customer2.setCreditCard("Credit Card");
        customer2.setDni("Dni");
        customer2.setFullName("Dr Jane Doe");
        customer2.setPhoneNumber("6625550144");
        customer2.setReservations(new HashSet<>());
        customer2.setTickets(new HashSet<>());
        customer2.setTotalFlights(1);
        customer2.setTotalLodgings(1);
        customer2.setTotalTours(1);
        customer2.setTours(new HashSet<>());

        TourEntity tourEntity2 = new TourEntity();
        tourEntity2.setCustomer(customer2);
        tourEntity2.setId(1L);
        tourEntity2.setReservations(new HashSet<>());
        tourEntity2.setTickets(new HashSet<>());
        when(tourRepository.save(Mockito.<TourEntity>any())).thenReturn(tourEntity2);
        when(tourRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        FlyEntity flyEntity = new FlyEntity();
        flyEntity.setAeroLine(AeroLine.aero_gold);
        flyEntity.setDestinyLat(10.0d);
        flyEntity.setDestinyLng(10.0d);
        flyEntity.setDestinyName("Destiny Name");
        flyEntity.setId(1L);
        flyEntity.setOriginLat(10.0d);
        flyEntity.setOriginLng(10.0d);
        flyEntity.setOriginName("Origin Name");
        flyEntity.setPrice(BigDecimal.valueOf(1L));
        flyEntity.setTickets(new HashSet<>());
        Optional<FlyEntity> ofResult2 = Optional.of(flyEntity);
        when(flyRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        CustomerEntity customer3 = new CustomerEntity();
        customer3.setCreditCard("Credit Card");
        customer3.setDni("Dni");
        customer3.setFullName("Dr Jane Doe");
        customer3.setPhoneNumber("6625550144");
        customer3.setReservations(new HashSet<>());
        customer3.setTickets(new HashSet<>());
        customer3.setTotalFlights(1);
        customer3.setTotalLodgings(1);
        customer3.setTotalTours(1);
        customer3.setTours(new HashSet<>());

        FlyEntity fly = new FlyEntity();
        fly.setAeroLine(AeroLine.aero_gold);
        fly.setDestinyLat(10.0d);
        fly.setDestinyLng(10.0d);
        fly.setDestinyName("Destiny Name");
        fly.setId(1L);
        fly.setOriginLat(10.0d);
        fly.setOriginLng(10.0d);
        fly.setOriginName("Origin Name");
        fly.setPrice(BigDecimal.valueOf(1L));
        fly.setTickets(new HashSet<>());

        CustomerEntity customer4 = new CustomerEntity();
        customer4.setCreditCard("Credit Card");
        customer4.setDni("Dni");
        customer4.setFullName("Dr Jane Doe");
        customer4.setPhoneNumber("6625550144");
        customer4.setReservations(new HashSet<>());
        customer4.setTickets(new HashSet<>());
        customer4.setTotalFlights(1);
        customer4.setTotalLodgings(1);
        customer4.setTotalTours(1);
        customer4.setTours(new HashSet<>());

        TourEntity tour = new TourEntity();
        tour.setCustomer(customer4);
        tour.setId(1L);
        tour.setReservations(new HashSet<>());
        tour.setTickets(new HashSet<>());

        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setArrivalDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity.setCustomer(customer3);
        ticketEntity.setDepartureDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity.setFly(fly);
        UUID id = UUID.randomUUID();
        ticketEntity.setId(id);
        ticketEntity.setPrice(BigDecimal.valueOf(1L));
        ticketEntity.setPurchaseDate(LocalDate.of(1970, 1, 1));
        ticketEntity.setTour(tour);
        when(tourHelper.createTicket(Mockito.<FlyEntity>any(), Mockito.<CustomerEntity>any())).thenReturn(ticketEntity);
        assertSame(id, tourService.addTicket(1L, 1L));
        verify(tourRepository).save(Mockito.<TourEntity>any());
        verify(tourRepository).findById(Mockito.<Long>any());
        verify(tourEntity).getCustomer();
        verify(tourEntity).addTicket(Mockito.<TicketEntity>any());
        verify(tourEntity).setCustomer(Mockito.<CustomerEntity>any());
        verify(tourEntity).setId(Mockito.<Long>any());
        verify(tourEntity).setReservations(Mockito.<Set<ReservationEntity>>any());
        verify(tourEntity).setTickets(Mockito.<Set<TicketEntity>>any());
        verify(flyRepository).findById(Mockito.<Long>any());
        verify(tourHelper).createTicket(Mockito.<FlyEntity>any(), Mockito.<CustomerEntity>any());
    }

    /**
     * Method under test: {@link TourService#removeReservation(Long, UUID)}
     */
    @Test
    void testRemoveReservation() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCreditCard("Credit Card");
        customer.setDni("Dni");
        customer.setFullName("Dr Jane Doe");
        customer.setPhoneNumber("6625550144");
        customer.setReservations(new HashSet<>());
        customer.setTickets(new HashSet<>());
        customer.setTotalFlights(1);
        customer.setTotalLodgings(1);
        customer.setTotalTours(1);
        customer.setTours(new HashSet<>());

        TourEntity tourEntity = new TourEntity();
        tourEntity.setCustomer(customer);
        tourEntity.setId(1L);
        tourEntity.setReservations(new HashSet<>());
        tourEntity.setTickets(new HashSet<>());
        Optional<TourEntity> ofResult = Optional.of(tourEntity);

        CustomerEntity customer2 = new CustomerEntity();
        customer2.setCreditCard("Credit Card");
        customer2.setDni("Dni");
        customer2.setFullName("Dr Jane Doe");
        customer2.setPhoneNumber("6625550144");
        customer2.setReservations(new HashSet<>());
        customer2.setTickets(new HashSet<>());
        customer2.setTotalFlights(1);
        customer2.setTotalLodgings(1);
        customer2.setTotalTours(1);
        customer2.setTours(new HashSet<>());

        TourEntity tourEntity2 = new TourEntity();
        tourEntity2.setCustomer(customer2);
        tourEntity2.setId(1L);
        tourEntity2.setReservations(new HashSet<>());
        tourEntity2.setTickets(new HashSet<>());
        when(tourRepository.save(Mockito.<TourEntity>any())).thenReturn(tourEntity2);
        when(tourRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        tourService.removeReservation(1L, UUID.randomUUID());
        verify(tourRepository).save(Mockito.<TourEntity>any());
        verify(tourRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link TourService#removeReservation(Long, UUID)}
     */
    @Test
    void testRemoveReservation2() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCreditCard("Credit Card");
        customer.setDni("Dni");
        customer.setFullName("Dr Jane Doe");
        customer.setPhoneNumber("6625550144");
        customer.setReservations(new HashSet<>());
        customer.setTickets(new HashSet<>());
        customer.setTotalFlights(1);
        customer.setTotalLodgings(1);
        customer.setTotalTours(1);
        customer.setTours(new HashSet<>());

        TourEntity tourEntity = new TourEntity();
        tourEntity.setCustomer(customer);
        tourEntity.setId(1L);
        tourEntity.setReservations(new HashSet<>());
        tourEntity.setTickets(new HashSet<>());
        Optional<TourEntity> ofResult = Optional.of(tourEntity);
        when(tourRepository.save(Mockito.<TourEntity>any())).thenThrow(new IdNotFoundException("Table Name"));
        when(tourRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(IdNotFoundException.class, () -> tourService.removeReservation(1L, UUID.randomUUID()));
        verify(tourRepository).save(Mockito.<TourEntity>any());
        verify(tourRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link TourService#removeReservation(Long, UUID)}
     */
    @Test
    void testRemoveReservation3() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCreditCard("Credit Card");
        customer.setDni("Dni");
        customer.setFullName("Dr Jane Doe");
        customer.setPhoneNumber("6625550144");
        customer.setReservations(new HashSet<>());
        customer.setTickets(new HashSet<>());
        customer.setTotalFlights(1);
        customer.setTotalLodgings(1);
        customer.setTotalTours(1);
        customer.setTours(new HashSet<>());
        TourEntity tourEntity = mock(TourEntity.class);
        doNothing().when(tourEntity).removeReservation(Mockito.<UUID>any());
        doNothing().when(tourEntity).setCustomer(Mockito.<CustomerEntity>any());
        doNothing().when(tourEntity).setId(Mockito.<Long>any());
        doNothing().when(tourEntity).setReservations(Mockito.<Set<ReservationEntity>>any());
        doNothing().when(tourEntity).setTickets(Mockito.<Set<TicketEntity>>any());
        tourEntity.setCustomer(customer);
        tourEntity.setId(1L);
        tourEntity.setReservations(new HashSet<>());
        tourEntity.setTickets(new HashSet<>());
        Optional<TourEntity> ofResult = Optional.of(tourEntity);

        CustomerEntity customer2 = new CustomerEntity();
        customer2.setCreditCard("Credit Card");
        customer2.setDni("Dni");
        customer2.setFullName("Dr Jane Doe");
        customer2.setPhoneNumber("6625550144");
        customer2.setReservations(new HashSet<>());
        customer2.setTickets(new HashSet<>());
        customer2.setTotalFlights(1);
        customer2.setTotalLodgings(1);
        customer2.setTotalTours(1);
        customer2.setTours(new HashSet<>());

        TourEntity tourEntity2 = new TourEntity();
        tourEntity2.setCustomer(customer2);
        tourEntity2.setId(1L);
        tourEntity2.setReservations(new HashSet<>());
        tourEntity2.setTickets(new HashSet<>());
        when(tourRepository.save(Mockito.<TourEntity>any())).thenReturn(tourEntity2);
        when(tourRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        tourService.removeReservation(1L, UUID.randomUUID());
        verify(tourRepository).save(Mockito.<TourEntity>any());
        verify(tourRepository).findById(Mockito.<Long>any());
        verify(tourEntity).removeReservation(Mockito.<UUID>any());
        verify(tourEntity).setCustomer(Mockito.<CustomerEntity>any());
        verify(tourEntity).setId(Mockito.<Long>any());
        verify(tourEntity).setReservations(Mockito.<Set<ReservationEntity>>any());
        verify(tourEntity).setTickets(Mockito.<Set<TicketEntity>>any());
    }

    /**
     * Method under test: {@link TourService#addReservation(Long, Long, Integer)}
     */
    @Test
    void testAddReservation() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCreditCard("Credit Card");
        customer.setDni("Dni");
        customer.setFullName("Dr Jane Doe");
        customer.setPhoneNumber("6625550144");
        customer.setReservations(new HashSet<>());
        customer.setTickets(new HashSet<>());
        customer.setTotalFlights(1);
        customer.setTotalLodgings(1);
        customer.setTotalTours(1);
        customer.setTours(new HashSet<>());

        TourEntity tourEntity = new TourEntity();
        tourEntity.setCustomer(customer);
        tourEntity.setId(1L);
        tourEntity.setReservations(new HashSet<>());
        tourEntity.setTickets(new HashSet<>());
        Optional<TourEntity> ofResult = Optional.of(tourEntity);

        CustomerEntity customer2 = new CustomerEntity();
        customer2.setCreditCard("Credit Card");
        customer2.setDni("Dni");
        customer2.setFullName("Dr Jane Doe");
        customer2.setPhoneNumber("6625550144");
        customer2.setReservations(new HashSet<>());
        customer2.setTickets(new HashSet<>());
        customer2.setTotalFlights(1);
        customer2.setTotalLodgings(1);
        customer2.setTotalTours(1);
        customer2.setTours(new HashSet<>());

        TourEntity tourEntity2 = new TourEntity();
        tourEntity2.setCustomer(customer2);
        tourEntity2.setId(1L);
        tourEntity2.setReservations(new HashSet<>());
        tourEntity2.setTickets(new HashSet<>());
        when(tourRepository.save(Mockito.<TourEntity>any())).thenReturn(tourEntity2);
        when(tourRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        HotelEntity hotelEntity = new HotelEntity();
        hotelEntity.setAddress("42 Main St");
        hotelEntity.setId(1L);
        hotelEntity.setName("Name");
        hotelEntity.setPrice(BigDecimal.valueOf(1L));
        hotelEntity.setRating(1);
        hotelEntity.setReservation(new HashSet<>());
        Optional<HotelEntity> ofResult2 = Optional.of(hotelEntity);
        when(hotelRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        CustomerEntity customer3 = new CustomerEntity();
        customer3.setCreditCard("Credit Card");
        customer3.setDni("Dni");
        customer3.setFullName("Dr Jane Doe");
        customer3.setPhoneNumber("6625550144");
        customer3.setReservations(new HashSet<>());
        customer3.setTickets(new HashSet<>());
        customer3.setTotalFlights(1);
        customer3.setTotalLodgings(1);
        customer3.setTotalTours(1);
        customer3.setTours(new HashSet<>());

        HotelEntity hotel = new HotelEntity();
        hotel.setAddress("42 Main St");
        hotel.setId(1L);
        hotel.setName("Name");
        hotel.setPrice(BigDecimal.valueOf(1L));
        hotel.setRating(1);
        hotel.setReservation(new HashSet<>());

        CustomerEntity customer4 = new CustomerEntity();
        customer4.setCreditCard("Credit Card");
        customer4.setDni("Dni");
        customer4.setFullName("Dr Jane Doe");
        customer4.setPhoneNumber("6625550144");
        customer4.setReservations(new HashSet<>());
        customer4.setTickets(new HashSet<>());
        customer4.setTotalFlights(1);
        customer4.setTotalLodgings(1);
        customer4.setTotalTours(1);
        customer4.setTours(new HashSet<>());

        TourEntity tour = new TourEntity();
        tour.setCustomer(customer4);
        tour.setId(1L);
        tour.setReservations(new HashSet<>());
        tour.setTickets(new HashSet<>());

        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setCustomer(customer3);
        reservationEntity.setDateEnd(LocalDate.of(1970, 1, 1));
        reservationEntity.setDateStart(LocalDate.of(1970, 1, 1));
        reservationEntity.setDateTimeReservation(LocalDate.of(1970, 1, 1).atStartOfDay());
        reservationEntity.setHotel(hotel);
        UUID id = UUID.randomUUID();
        reservationEntity.setId(id);
        reservationEntity.setPrice(BigDecimal.valueOf(1L));
        reservationEntity.setTotalDays(1);
        reservationEntity.setTour(tour);
        when(
                tourHelper.createReservation(Mockito.<HotelEntity>any(), Mockito.<CustomerEntity>any(), Mockito.<Integer>any()))
                .thenReturn(reservationEntity);
        assertSame(id, tourService.addReservation(1L, 1L, 2));
        verify(tourRepository).save(Mockito.<TourEntity>any());
        verify(tourRepository).findById(Mockito.<Long>any());
        verify(hotelRepository).findById(Mockito.<Long>any());
        verify(tourHelper).createReservation(Mockito.<HotelEntity>any(), Mockito.<CustomerEntity>any(),
                Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link TourService#addReservation(Long, Long, Integer)}
     */
    @Test
    void testAddReservation2() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCreditCard("Credit Card");
        customer.setDni("Dni");
        customer.setFullName("Dr Jane Doe");
        customer.setPhoneNumber("6625550144");
        customer.setReservations(new HashSet<>());
        customer.setTickets(new HashSet<>());
        customer.setTotalFlights(1);
        customer.setTotalLodgings(1);
        customer.setTotalTours(1);
        customer.setTours(new HashSet<>());

        TourEntity tourEntity = new TourEntity();
        tourEntity.setCustomer(customer);
        tourEntity.setId(1L);
        tourEntity.setReservations(new HashSet<>());
        tourEntity.setTickets(new HashSet<>());
        Optional<TourEntity> ofResult = Optional.of(tourEntity);
        when(tourRepository.save(Mockito.<TourEntity>any())).thenThrow(new IdNotFoundException("Table Name"));
        when(tourRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        HotelEntity hotelEntity = new HotelEntity();
        hotelEntity.setAddress("42 Main St");
        hotelEntity.setId(1L);
        hotelEntity.setName("Name");
        hotelEntity.setPrice(BigDecimal.valueOf(1L));
        hotelEntity.setRating(1);
        hotelEntity.setReservation(new HashSet<>());
        Optional<HotelEntity> ofResult2 = Optional.of(hotelEntity);
        when(hotelRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        CustomerEntity customer2 = new CustomerEntity();
        customer2.setCreditCard("Credit Card");
        customer2.setDni("Dni");
        customer2.setFullName("Dr Jane Doe");
        customer2.setPhoneNumber("6625550144");
        customer2.setReservations(new HashSet<>());
        customer2.setTickets(new HashSet<>());
        customer2.setTotalFlights(1);
        customer2.setTotalLodgings(1);
        customer2.setTotalTours(1);
        customer2.setTours(new HashSet<>());

        HotelEntity hotel = new HotelEntity();
        hotel.setAddress("42 Main St");
        hotel.setId(1L);
        hotel.setName("Name");
        hotel.setPrice(BigDecimal.valueOf(1L));
        hotel.setRating(1);
        hotel.setReservation(new HashSet<>());

        CustomerEntity customer3 = new CustomerEntity();
        customer3.setCreditCard("Credit Card");
        customer3.setDni("Dni");
        customer3.setFullName("Dr Jane Doe");
        customer3.setPhoneNumber("6625550144");
        customer3.setReservations(new HashSet<>());
        customer3.setTickets(new HashSet<>());
        customer3.setTotalFlights(1);
        customer3.setTotalLodgings(1);
        customer3.setTotalTours(1);
        customer3.setTours(new HashSet<>());

        TourEntity tour = new TourEntity();
        tour.setCustomer(customer3);
        tour.setId(1L);
        tour.setReservations(new HashSet<>());
        tour.setTickets(new HashSet<>());

        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setCustomer(customer2);
        reservationEntity.setDateEnd(LocalDate.of(1970, 1, 1));
        reservationEntity.setDateStart(LocalDate.of(1970, 1, 1));
        reservationEntity.setDateTimeReservation(LocalDate.of(1970, 1, 1).atStartOfDay());
        reservationEntity.setHotel(hotel);
        reservationEntity.setId(UUID.randomUUID());
        reservationEntity.setPrice(BigDecimal.valueOf(1L));
        reservationEntity.setTotalDays(1);
        reservationEntity.setTour(tour);
        when(tourHelper.createReservation(Mockito.<HotelEntity>any(), Mockito.<CustomerEntity>any(),
                Mockito.<Integer>any())).thenReturn(reservationEntity);
        assertThrows(IdNotFoundException.class, () -> tourService.addReservation(1L, 1L, 2));
        verify(tourRepository).save(Mockito.<TourEntity>any());
        verify(tourRepository).findById(Mockito.<Long>any());
        verify(hotelRepository).findById(Mockito.<Long>any());
        verify(tourHelper).createReservation(Mockito.<HotelEntity>any(), Mockito.<CustomerEntity>any(),
                Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link TourService#addReservation(Long, Long, Integer)}
     */
    @Test
    void testAddReservation3() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCreditCard("Credit Card");
        customer.setDni("Dni");
        customer.setFullName("Dr Jane Doe");
        customer.setPhoneNumber("6625550144");
        customer.setReservations(new HashSet<>());
        customer.setTickets(new HashSet<>());
        customer.setTotalFlights(1);
        customer.setTotalLodgings(1);
        customer.setTotalTours(1);
        customer.setTours(new HashSet<>());

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCreditCard("Credit Card");
        customerEntity.setDni("Dni");
        customerEntity.setFullName("Dr Jane Doe");
        customerEntity.setPhoneNumber("6625550144");
        customerEntity.setReservations(new HashSet<>());
        customerEntity.setTickets(new HashSet<>());
        customerEntity.setTotalFlights(1);
        customerEntity.setTotalLodgings(1);
        customerEntity.setTotalTours(1);
        customerEntity.setTours(new HashSet<>());
        TourEntity tourEntity = mock(TourEntity.class);
        when(tourEntity.getCustomer()).thenReturn(customerEntity);
        doNothing().when(tourEntity).addReservation(Mockito.<ReservationEntity>any());
        doNothing().when(tourEntity).setCustomer(Mockito.<CustomerEntity>any());
        doNothing().when(tourEntity).setId(Mockito.<Long>any());
        doNothing().when(tourEntity).setReservations(Mockito.<Set<ReservationEntity>>any());
        doNothing().when(tourEntity).setTickets(Mockito.<Set<TicketEntity>>any());
        tourEntity.setCustomer(customer);
        tourEntity.setId(1L);
        tourEntity.setReservations(new HashSet<>());
        tourEntity.setTickets(new HashSet<>());
        Optional<TourEntity> ofResult = Optional.of(tourEntity);

        CustomerEntity customer2 = new CustomerEntity();
        customer2.setCreditCard("Credit Card");
        customer2.setDni("Dni");
        customer2.setFullName("Dr Jane Doe");
        customer2.setPhoneNumber("6625550144");
        customer2.setReservations(new HashSet<>());
        customer2.setTickets(new HashSet<>());
        customer2.setTotalFlights(1);
        customer2.setTotalLodgings(1);
        customer2.setTotalTours(1);
        customer2.setTours(new HashSet<>());

        TourEntity tourEntity2 = new TourEntity();
        tourEntity2.setCustomer(customer2);
        tourEntity2.setId(1L);
        tourEntity2.setReservations(new HashSet<>());
        tourEntity2.setTickets(new HashSet<>());
        when(tourRepository.save(Mockito.<TourEntity>any())).thenReturn(tourEntity2);
        when(tourRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        HotelEntity hotelEntity = new HotelEntity();
        hotelEntity.setAddress("42 Main St");
        hotelEntity.setId(1L);
        hotelEntity.setName("Name");
        hotelEntity.setPrice(BigDecimal.valueOf(1L));
        hotelEntity.setRating(1);
        hotelEntity.setReservation(new HashSet<>());
        Optional<HotelEntity> ofResult2 = Optional.of(hotelEntity);
        when(hotelRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        CustomerEntity customer3 = new CustomerEntity();
        customer3.setCreditCard("Credit Card");
        customer3.setDni("Dni");
        customer3.setFullName("Dr Jane Doe");
        customer3.setPhoneNumber("6625550144");
        customer3.setReservations(new HashSet<>());
        customer3.setTickets(new HashSet<>());
        customer3.setTotalFlights(1);
        customer3.setTotalLodgings(1);
        customer3.setTotalTours(1);
        customer3.setTours(new HashSet<>());

        HotelEntity hotel = new HotelEntity();
        hotel.setAddress("42 Main St");
        hotel.setId(1L);
        hotel.setName("Name");
        hotel.setPrice(BigDecimal.valueOf(1L));
        hotel.setRating(1);
        hotel.setReservation(new HashSet<>());

        CustomerEntity customer4 = new CustomerEntity();
        customer4.setCreditCard("Credit Card");
        customer4.setDni("Dni");
        customer4.setFullName("Dr Jane Doe");
        customer4.setPhoneNumber("6625550144");
        customer4.setReservations(new HashSet<>());
        customer4.setTickets(new HashSet<>());
        customer4.setTotalFlights(1);
        customer4.setTotalLodgings(1);
        customer4.setTotalTours(1);
        customer4.setTours(new HashSet<>());

        TourEntity tour = new TourEntity();
        tour.setCustomer(customer4);
        tour.setId(1L);
        tour.setReservations(new HashSet<>());
        tour.setTickets(new HashSet<>());

        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setCustomer(customer3);
        reservationEntity.setDateEnd(LocalDate.of(1970, 1, 1));
        reservationEntity.setDateStart(LocalDate.of(1970, 1, 1));
        reservationEntity.setDateTimeReservation(LocalDate.of(1970, 1, 1).atStartOfDay());
        reservationEntity.setHotel(hotel);
        UUID id = UUID.randomUUID();
        reservationEntity.setId(id);
        reservationEntity.setPrice(BigDecimal.valueOf(1L));
        reservationEntity.setTotalDays(1);
        reservationEntity.setTour(tour);
        when(tourHelper.createReservation(Mockito.<HotelEntity>any(), Mockito.<CustomerEntity>any(),
                Mockito.<Integer>any())).thenReturn(reservationEntity);
        assertSame(id, tourService.addReservation(1L, 1L, 2));
        verify(tourRepository).save(Mockito.<TourEntity>any());
        verify(tourRepository).findById(Mockito.<Long>any());
        verify(tourEntity).getCustomer();
        verify(tourEntity).addReservation(Mockito.<ReservationEntity>any());
        verify(tourEntity).setCustomer(Mockito.<CustomerEntity>any());
        verify(tourEntity).setId(Mockito.<Long>any());
        verify(tourEntity).setReservations(Mockito.<Set<ReservationEntity>>any());
        verify(tourEntity).setTickets(Mockito.<Set<TicketEntity>>any());
        verify(hotelRepository).findById(Mockito.<Long>any());
        verify(tourHelper).createReservation(Mockito.<HotelEntity>any(), Mockito.<CustomerEntity>any(),
                Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link TourService#create(TourRequest)}
     */
    @Test
    void testCreate() {
        doThrow(new IdNotFoundException("Table Name")).when(blacklistHelper).isInBlacklist(Mockito.<String>any());
        assertThrows(IdNotFoundException.class, () -> tourService.create(new TourRequest()));
        verify(blacklistHelper).isInBlacklist(Mockito.<String>any());
    }

    /**
     * Method under test: {@link TourService#create(TourRequest)}
     */
    @Test
    void testCreate2() {
        Optional<CustomerEntity> emptyResult = Optional.empty();
        when(customerRepository.findById(Mockito.<String>any())).thenReturn(emptyResult);
        doNothing().when(blacklistHelper).isInBlacklist(Mockito.<String>any());
        assertThrows(IdNotFoundException.class, () -> tourService.create(new TourRequest()));
        verify(customerRepository).findById(Mockito.<String>any());
        verify(blacklistHelper).isInBlacklist(Mockito.<String>any());
    }

    /**
     * Method under test: {@link TourService#create(TourRequest)}
     */
    @Test
    void testCreate3() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCreditCard("Credit Card");
        customer.setDni("Dni");
        customer.setFullName("Dr Jane Doe");
        customer.setPhoneNumber("6625550144");
        customer.setReservations(new HashSet<>());
        customer.setTickets(new HashSet<>());
        customer.setTotalFlights(1);
        customer.setTotalLodgings(1);
        customer.setTotalTours(1);
        customer.setTours(new HashSet<>());

        TourEntity tourEntity = new TourEntity();
        tourEntity.setCustomer(customer);
        tourEntity.setId(1L);
        tourEntity.setReservations(new HashSet<>());
        tourEntity.setTickets(new HashSet<>());
        when(tourRepository.save(Mockito.<TourEntity>any())).thenReturn(tourEntity);

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCreditCard("Credit Card");
        customerEntity.setDni("Dni");
        customerEntity.setFullName("Dr Jane Doe");
        customerEntity.setPhoneNumber("6625550144");
        customerEntity.setReservations(new HashSet<>());
        customerEntity.setTickets(new HashSet<>());
        customerEntity.setTotalFlights(1);
        customerEntity.setTotalLodgings(1);
        customerEntity.setTotalTours(1);
        customerEntity.setTours(new HashSet<>());
        Optional<CustomerEntity> ofResult = Optional.of(customerEntity);
        when(customerRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        when(tourHelper.createReservations(Mockito.<HashMap<HotelEntity, Integer>>any(), Mockito.<CustomerEntity>any()))
                .thenReturn(new HashSet<>());
        when(tourHelper.createTickets(Mockito.<Set<FlyEntity>>any(), Mockito.<CustomerEntity>any()))
                .thenReturn(new HashSet<>());
        doNothing().when(blacklistHelper).isInBlacklist(Mockito.<String>any());
        TourRequest.TourRequestBuilder emailResult = TourRequest.builder().customerId("42").email("jane.doe@example.org");
        TourRequest.TourRequestBuilder flightsResult = emailResult.flights(new HashSet<>());
        TourResponse actualCreateResult = tourService.create(flightsResult.hotels(new HashSet<>()).build());
        assertEquals(1L, actualCreateResult.getId().longValue());
        assertTrue(actualCreateResult.getTicketIds().isEmpty());
        assertTrue(actualCreateResult.getReservationIds().isEmpty());
        verify(tourRepository).save(Mockito.<TourEntity>any());
        verify(customerRepository).findById(Mockito.<String>any());
        verify(tourHelper).createReservations(Mockito.<HashMap<HotelEntity, Integer>>any(),
                Mockito.<CustomerEntity>any());
        verify(tourHelper).createTickets(Mockito.<Set<FlyEntity>>any(), Mockito.<CustomerEntity>any());
        verify(blacklistHelper).isInBlacklist(Mockito.<String>any());
    }

    /**
     * Method under test: {@link TourService#create(TourRequest)}
     */
    @Test
    void testCreate4() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCreditCard("Credit Card");
        customerEntity.setDni("Dni");
        customerEntity.setFullName("Dr Jane Doe");
        customerEntity.setPhoneNumber("6625550144");
        customerEntity.setReservations(new HashSet<>());
        customerEntity.setTickets(new HashSet<>());
        customerEntity.setTotalFlights(1);
        customerEntity.setTotalLodgings(1);
        customerEntity.setTotalTours(1);
        customerEntity.setTours(new HashSet<>());
        Optional<CustomerEntity> ofResult = Optional.of(customerEntity);
        when(customerRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        when(tourHelper.createTickets(Mockito.<Set<FlyEntity>>any(), Mockito.<CustomerEntity>any()))
                .thenThrow(new IdNotFoundException("Table Name"));
        doNothing().when(blacklistHelper).isInBlacklist(Mockito.<String>any());
        TourRequest.TourRequestBuilder emailResult = TourRequest.builder().customerId("42").email("jane.doe@example.org");
        TourRequest.TourRequestBuilder flightsResult = emailResult.flights(new HashSet<>());
        assertThrows(IdNotFoundException.class, () -> tourService.create(flightsResult.hotels(new HashSet<>()).build()));
        verify(customerRepository).findById(Mockito.<String>any());
        verify(tourHelper).createTickets(Mockito.<Set<FlyEntity>>any(), Mockito.<CustomerEntity>any());
        verify(blacklistHelper).isInBlacklist(Mockito.<String>any());
    }

    /**
     * Method under test: {@link TourService#create(TourRequest)}
     */
    @Test
    void testCreate5() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCreditCard("Credit Card");
        customer.setDni("Dni");
        customer.setFullName("Dr Jane Doe");
        customer.setPhoneNumber("6625550144");
        customer.setReservations(new HashSet<>());
        customer.setTickets(new HashSet<>());
        customer.setTotalFlights(1);
        customer.setTotalLodgings(1);
        customer.setTotalTours(1);
        customer.setTours(new HashSet<>());
        TourEntity tourEntity = mock(TourEntity.class);
        when(tourEntity.getId()).thenReturn(1L);
        when(tourEntity.getReservations()).thenReturn(new HashSet<>());
        when(tourEntity.getTickets()).thenReturn(new HashSet<>());
        doNothing().when(tourEntity).setCustomer(Mockito.<CustomerEntity>any());
        doNothing().when(tourEntity).setId(Mockito.<Long>any());
        doNothing().when(tourEntity).setReservations(Mockito.<Set<ReservationEntity>>any());
        doNothing().when(tourEntity).setTickets(Mockito.<Set<TicketEntity>>any());
        tourEntity.setCustomer(customer);
        tourEntity.setId(1L);
        tourEntity.setReservations(new HashSet<>());
        tourEntity.setTickets(new HashSet<>());
        when(tourRepository.save(Mockito.<TourEntity>any())).thenReturn(tourEntity);

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCreditCard("Credit Card");
        customerEntity.setDni("Dni");
        customerEntity.setFullName("Dr Jane Doe");
        customerEntity.setPhoneNumber("6625550144");
        customerEntity.setReservations(new HashSet<>());
        customerEntity.setTickets(new HashSet<>());
        customerEntity.setTotalFlights(1);
        customerEntity.setTotalLodgings(1);
        customerEntity.setTotalTours(1);
        customerEntity.setTours(new HashSet<>());
        Optional<CustomerEntity> ofResult = Optional.of(customerEntity);
        when(customerRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        when(tourHelper.createReservations(Mockito.<HashMap<HotelEntity, Integer>>any(), Mockito.<CustomerEntity>any()))
                .thenReturn(new HashSet<>());
        when(tourHelper.createTickets(Mockito.<Set<FlyEntity>>any(), Mockito.<CustomerEntity>any()))
                .thenReturn(new HashSet<>());
        doNothing().when(blacklistHelper).isInBlacklist(Mockito.<String>any());
        TourRequest.TourRequestBuilder emailResult = TourRequest.builder().customerId("42").email("jane.doe@example.org");
        TourRequest.TourRequestBuilder flightsResult = emailResult.flights(new HashSet<>());
        TourResponse actualCreateResult = tourService.create(flightsResult.hotels(new HashSet<>()).build());
        assertEquals(1L, actualCreateResult.getId().longValue());
        assertTrue(actualCreateResult.getTicketIds().isEmpty());
        assertTrue(actualCreateResult.getReservationIds().isEmpty());
        verify(tourRepository).save(Mockito.<TourEntity>any());
        verify(tourEntity).getId();
        verify(tourEntity).getReservations();
        verify(tourEntity).getTickets();
        verify(tourEntity).setCustomer(Mockito.<CustomerEntity>any());
        verify(tourEntity).setId(Mockito.<Long>any());
        verify(tourEntity).setReservations(Mockito.<Set<ReservationEntity>>any());
        verify(tourEntity).setTickets(Mockito.<Set<TicketEntity>>any());
        verify(customerRepository).findById(Mockito.<String>any());
        verify(tourHelper).createReservations(Mockito.<HashMap<HotelEntity, Integer>>any(),
                Mockito.<CustomerEntity>any());
        verify(tourHelper).createTickets(Mockito.<Set<FlyEntity>>any(), Mockito.<CustomerEntity>any());
        verify(blacklistHelper).isInBlacklist(Mockito.<String>any());
    }

    /**
     * Method under test: {@link TourService#create(TourRequest)}
     */
    @Test
    void testCreate6() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCreditCard("Credit Card");
        customer.setDni("Dni");
        customer.setFullName("Dr Jane Doe");
        customer.setPhoneNumber("6625550144");
        customer.setReservations(new HashSet<>());
        customer.setTickets(new HashSet<>());
        customer.setTotalFlights(1);
        customer.setTotalLodgings(1);
        customer.setTotalTours(1);
        customer.setTours(new HashSet<>());

        CustomerEntity customer2 = new CustomerEntity();
        customer2.setCreditCard("Credit Card");
        customer2.setDni("Dni");
        customer2.setFullName("Dr Jane Doe");
        customer2.setPhoneNumber("6625550144");
        customer2.setReservations(new HashSet<>());
        customer2.setTickets(new HashSet<>());
        customer2.setTotalFlights(1);
        customer2.setTotalLodgings(1);
        customer2.setTotalTours(1);
        customer2.setTours(new HashSet<>());

        HotelEntity hotel = new HotelEntity();
        hotel.setAddress("42 Main St");
        hotel.setId(1L);
        hotel.setName("Name");
        hotel.setPrice(BigDecimal.valueOf(1L));
        hotel.setRating(1);
        hotel.setReservation(new HashSet<>());

        CustomerEntity customer3 = new CustomerEntity();
        customer3.setCreditCard("Credit Card");
        customer3.setDni("Dni");
        customer3.setFullName("Dr Jane Doe");
        customer3.setPhoneNumber("6625550144");
        customer3.setReservations(new HashSet<>());
        customer3.setTickets(new HashSet<>());
        customer3.setTotalFlights(1);
        customer3.setTotalLodgings(1);
        customer3.setTotalTours(1);
        customer3.setTours(new HashSet<>());

        TourEntity tour = new TourEntity();
        tour.setCustomer(customer3);
        tour.setId(1L);
        tour.setReservations(new HashSet<>());
        tour.setTickets(new HashSet<>());

        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setCustomer(customer2);
        reservationEntity.setDateEnd(LocalDate.of(1970, 1, 1));
        reservationEntity.setDateStart(LocalDate.of(1970, 1, 1));
        reservationEntity.setDateTimeReservation(LocalDate.of(1970, 1, 1).atStartOfDay());
        reservationEntity.setHotel(hotel);
        reservationEntity.setId(UUID.randomUUID());
        reservationEntity.setPrice(BigDecimal.valueOf(1L));
        reservationEntity.setTotalDays(1);
        reservationEntity.setTour(tour);

        HashSet<ReservationEntity> reservationEntitySet = new HashSet<>();
        reservationEntitySet.add(reservationEntity);
        TourEntity tourEntity = mock(TourEntity.class);
        when(tourEntity.getId()).thenReturn(1L);
        when(tourEntity.getReservations()).thenReturn(reservationEntitySet);
        when(tourEntity.getTickets()).thenReturn(new HashSet<>());
        doNothing().when(tourEntity).setCustomer(Mockito.<CustomerEntity>any());
        doNothing().when(tourEntity).setId(Mockito.<Long>any());
        doNothing().when(tourEntity).setReservations(Mockito.<Set<ReservationEntity>>any());
        doNothing().when(tourEntity).setTickets(Mockito.<Set<TicketEntity>>any());
        tourEntity.setCustomer(customer);
        tourEntity.setId(1L);
        tourEntity.setReservations(new HashSet<>());
        tourEntity.setTickets(new HashSet<>());
        when(tourRepository.save(Mockito.<TourEntity>any())).thenReturn(tourEntity);

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCreditCard("Credit Card");
        customerEntity.setDni("Dni");
        customerEntity.setFullName("Dr Jane Doe");
        customerEntity.setPhoneNumber("6625550144");
        customerEntity.setReservations(new HashSet<>());
        customerEntity.setTickets(new HashSet<>());
        customerEntity.setTotalFlights(1);
        customerEntity.setTotalLodgings(1);
        customerEntity.setTotalTours(1);
        customerEntity.setTours(new HashSet<>());
        Optional<CustomerEntity> ofResult = Optional.of(customerEntity);
        when(customerRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        when(tourHelper.createReservations(Mockito.<HashMap<HotelEntity, Integer>>any(), Mockito.<CustomerEntity>any()))
                .thenReturn(new HashSet<>());
        when(tourHelper.createTickets(Mockito.<Set<FlyEntity>>any(), Mockito.<CustomerEntity>any()))
                .thenReturn(new HashSet<>());
        doNothing().when(blacklistHelper).isInBlacklist(Mockito.<String>any());
        TourRequest.TourRequestBuilder emailResult = TourRequest.builder().customerId("42").email("jane.doe@example.org");
        TourRequest.TourRequestBuilder flightsResult = emailResult.flights(new HashSet<>());
        TourResponse actualCreateResult = tourService.create(flightsResult.hotels(new HashSet<>()).build());
        assertEquals(1L, actualCreateResult.getId().longValue());
        assertTrue(actualCreateResult.getTicketIds().isEmpty());
        assertEquals(1, actualCreateResult.getReservationIds().size());
        verify(tourRepository).save(Mockito.<TourEntity>any());
        verify(tourEntity).getId();
        verify(tourEntity).getReservations();
        verify(tourEntity).getTickets();
        verify(tourEntity).setCustomer(Mockito.<CustomerEntity>any());
        verify(tourEntity).setId(Mockito.<Long>any());
        verify(tourEntity).setReservations(Mockito.<Set<ReservationEntity>>any());
        verify(tourEntity).setTickets(Mockito.<Set<TicketEntity>>any());
        verify(customerRepository).findById(Mockito.<String>any());
        verify(tourHelper).createReservations(Mockito.<HashMap<HotelEntity, Integer>>any(),
                Mockito.<CustomerEntity>any());
        verify(tourHelper).createTickets(Mockito.<Set<FlyEntity>>any(), Mockito.<CustomerEntity>any());
        verify(blacklistHelper).isInBlacklist(Mockito.<String>any());
    }

    /**
     * Method under test: {@link TourService#create(TourRequest)}
     */
    @Test
    void testCreate7() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCreditCard("Credit Card");
        customer.setDni("Dni");
        customer.setFullName("Dr Jane Doe");
        customer.setPhoneNumber("6625550144");
        customer.setReservations(new HashSet<>());
        customer.setTickets(new HashSet<>());
        customer.setTotalFlights(1);
        customer.setTotalLodgings(1);
        customer.setTotalTours(1);
        customer.setTours(new HashSet<>());

        CustomerEntity customer2 = new CustomerEntity();
        customer2.setCreditCard("Credit Card");
        customer2.setDni("Dni");
        customer2.setFullName("Dr Jane Doe");
        customer2.setPhoneNumber("6625550144");
        customer2.setReservations(new HashSet<>());
        customer2.setTickets(new HashSet<>());
        customer2.setTotalFlights(1);
        customer2.setTotalLodgings(1);
        customer2.setTotalTours(1);
        customer2.setTours(new HashSet<>());

        HotelEntity hotel = new HotelEntity();
        hotel.setAddress("42 Main St");
        hotel.setId(1L);
        hotel.setName("Name");
        hotel.setPrice(BigDecimal.valueOf(1L));
        hotel.setRating(1);
        hotel.setReservation(new HashSet<>());

        CustomerEntity customer3 = new CustomerEntity();
        customer3.setCreditCard("Credit Card");
        customer3.setDni("Dni");
        customer3.setFullName("Dr Jane Doe");
        customer3.setPhoneNumber("6625550144");
        customer3.setReservations(new HashSet<>());
        customer3.setTickets(new HashSet<>());
        customer3.setTotalFlights(1);
        customer3.setTotalLodgings(1);
        customer3.setTotalTours(1);
        customer3.setTours(new HashSet<>());

        TourEntity tour = new TourEntity();
        tour.setCustomer(customer3);
        tour.setId(1L);
        tour.setReservations(new HashSet<>());
        tour.setTickets(new HashSet<>());

        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setCustomer(customer2);
        reservationEntity.setDateEnd(LocalDate.of(1970, 1, 1));
        reservationEntity.setDateStart(LocalDate.of(1970, 1, 1));
        reservationEntity.setDateTimeReservation(LocalDate.of(1970, 1, 1).atStartOfDay());
        reservationEntity.setHotel(hotel);
        reservationEntity.setId(UUID.randomUUID());
        reservationEntity.setPrice(BigDecimal.valueOf(1L));
        reservationEntity.setTotalDays(1);
        reservationEntity.setTour(tour);

        CustomerEntity customer4 = new CustomerEntity();
        customer4.setCreditCard("ar.com.laboratory.besttravel.domain.entities.jpa.CustomerEntity");
        customer4.setDni("ar.com.laboratory.besttravel.domain.entities.jpa.CustomerEntity");
        customer4.setFullName("Mr John Smith");
        customer4.setPhoneNumber("8605550118");
        customer4.setReservations(new HashSet<>());
        customer4.setTickets(new HashSet<>());
        customer4.setTotalFlights(2);
        customer4.setTotalLodgings(2);
        customer4.setTotalTours(2);
        customer4.setTours(new HashSet<>());

        HotelEntity hotel2 = new HotelEntity();
        hotel2.setAddress("17 High St");
        hotel2.setId(2L);
        hotel2.setName("ar.com.laboratory.besttravel.domain.entities.jpa.HotelEntity");
        hotel2.setPrice(BigDecimal.valueOf(1L));
        hotel2.setRating(2);
        hotel2.setReservation(new HashSet<>());

        CustomerEntity customer5 = new CustomerEntity();
        customer5.setCreditCard("ar.com.laboratory.besttravel.domain.entities.jpa.CustomerEntity");
        customer5.setDni("ar.com.laboratory.besttravel.domain.entities.jpa.CustomerEntity");
        customer5.setFullName("Mr John Smith");
        customer5.setPhoneNumber("8605550118");
        customer5.setReservations(new HashSet<>());
        customer5.setTickets(new HashSet<>());
        customer5.setTotalFlights(2);
        customer5.setTotalLodgings(2);
        customer5.setTotalTours(2);
        customer5.setTours(new HashSet<>());

        TourEntity tour2 = new TourEntity();
        tour2.setCustomer(customer5);
        tour2.setId(2L);
        tour2.setReservations(new HashSet<>());
        tour2.setTickets(new HashSet<>());

        ReservationEntity reservationEntity2 = new ReservationEntity();
        reservationEntity2.setCustomer(customer4);
        reservationEntity2.setDateEnd(LocalDate.of(1970, 1, 1));
        reservationEntity2.setDateStart(LocalDate.of(1970, 1, 1));
        reservationEntity2.setDateTimeReservation(LocalDate.of(1970, 1, 1).atStartOfDay());
        reservationEntity2.setHotel(hotel2);
        reservationEntity2.setId(UUID.randomUUID());
        reservationEntity2.setPrice(BigDecimal.valueOf(1L));
        reservationEntity2.setTotalDays(2);
        reservationEntity2.setTour(tour2);

        HashSet<ReservationEntity> reservationEntitySet = new HashSet<>();
        reservationEntitySet.add(reservationEntity2);
        reservationEntitySet.add(reservationEntity);
        TourEntity tourEntity = mock(TourEntity.class);
        when(tourEntity.getId()).thenReturn(1L);
        when(tourEntity.getReservations()).thenReturn(reservationEntitySet);
        when(tourEntity.getTickets()).thenReturn(new HashSet<>());
        doNothing().when(tourEntity).setCustomer(Mockito.<CustomerEntity>any());
        doNothing().when(tourEntity).setId(Mockito.<Long>any());
        doNothing().when(tourEntity).setReservations(Mockito.<Set<ReservationEntity>>any());
        doNothing().when(tourEntity).setTickets(Mockito.<Set<TicketEntity>>any());
        tourEntity.setCustomer(customer);
        tourEntity.setId(1L);
        tourEntity.setReservations(new HashSet<>());
        tourEntity.setTickets(new HashSet<>());
        when(tourRepository.save(Mockito.<TourEntity>any())).thenReturn(tourEntity);

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCreditCard("Credit Card");
        customerEntity.setDni("Dni");
        customerEntity.setFullName("Dr Jane Doe");
        customerEntity.setPhoneNumber("6625550144");
        customerEntity.setReservations(new HashSet<>());
        customerEntity.setTickets(new HashSet<>());
        customerEntity.setTotalFlights(1);
        customerEntity.setTotalLodgings(1);
        customerEntity.setTotalTours(1);
        customerEntity.setTours(new HashSet<>());
        Optional<CustomerEntity> ofResult = Optional.of(customerEntity);
        when(customerRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        when(tourHelper.createReservations(Mockito.<HashMap<HotelEntity, Integer>>any(), Mockito.<CustomerEntity>any()))
                .thenReturn(new HashSet<>());
        when(tourHelper.createTickets(Mockito.<Set<FlyEntity>>any(), Mockito.<CustomerEntity>any()))
                .thenReturn(new HashSet<>());
        doNothing().when(blacklistHelper).isInBlacklist(Mockito.<String>any());
        TourRequest.TourRequestBuilder emailResult = TourRequest.builder().customerId("42").email("jane.doe@example.org");
        TourRequest.TourRequestBuilder flightsResult = emailResult.flights(new HashSet<>());
        TourResponse actualCreateResult = tourService.create(flightsResult.hotels(new HashSet<>()).build());
        assertEquals(1L, actualCreateResult.getId().longValue());
        assertTrue(actualCreateResult.getTicketIds().isEmpty());
        assertEquals(2, actualCreateResult.getReservationIds().size());
        verify(tourRepository).save(Mockito.<TourEntity>any());
        verify(tourEntity).getId();
        verify(tourEntity).getReservations();
        verify(tourEntity).getTickets();
        verify(tourEntity).setCustomer(Mockito.<CustomerEntity>any());
        verify(tourEntity).setId(Mockito.<Long>any());
        verify(tourEntity).setReservations(Mockito.<Set<ReservationEntity>>any());
        verify(tourEntity).setTickets(Mockito.<Set<TicketEntity>>any());
        verify(customerRepository).findById(Mockito.<String>any());
        verify(tourHelper).createReservations(Mockito.<HashMap<HotelEntity, Integer>>any(),
                Mockito.<CustomerEntity>any());
        verify(tourHelper).createTickets(Mockito.<Set<FlyEntity>>any(), Mockito.<CustomerEntity>any());
        verify(blacklistHelper).isInBlacklist(Mockito.<String>any());
    }

    /**
     * Method under test: {@link TourService#create(TourRequest)}
     */
    @Test
    void testCreate8() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCreditCard("Credit Card");
        customer.setDni("Dni");
        customer.setFullName("Dr Jane Doe");
        customer.setPhoneNumber("6625550144");
        customer.setReservations(new HashSet<>());
        customer.setTickets(new HashSet<>());
        customer.setTotalFlights(1);
        customer.setTotalLodgings(1);
        customer.setTotalTours(1);
        customer.setTours(new HashSet<>());

        CustomerEntity customer2 = new CustomerEntity();
        customer2.setCreditCard("Credit Card");
        customer2.setDni("Dni");
        customer2.setFullName("Dr Jane Doe");
        customer2.setPhoneNumber("6625550144");
        customer2.setReservations(new HashSet<>());
        customer2.setTickets(new HashSet<>());
        customer2.setTotalFlights(1);
        customer2.setTotalLodgings(1);
        customer2.setTotalTours(1);
        customer2.setTours(new HashSet<>());

        FlyEntity fly = new FlyEntity();
        fly.setAeroLine(AeroLine.aero_gold);
        fly.setDestinyLat(10.0d);
        fly.setDestinyLng(10.0d);
        fly.setDestinyName("Destiny Name");
        fly.setId(1L);
        fly.setOriginLat(10.0d);
        fly.setOriginLng(10.0d);
        fly.setOriginName("Origin Name");
        fly.setPrice(BigDecimal.valueOf(1L));
        fly.setTickets(new HashSet<>());

        CustomerEntity customer3 = new CustomerEntity();
        customer3.setCreditCard("Credit Card");
        customer3.setDni("Dni");
        customer3.setFullName("Dr Jane Doe");
        customer3.setPhoneNumber("6625550144");
        customer3.setReservations(new HashSet<>());
        customer3.setTickets(new HashSet<>());
        customer3.setTotalFlights(1);
        customer3.setTotalLodgings(1);
        customer3.setTotalTours(1);
        customer3.setTours(new HashSet<>());

        TourEntity tour = new TourEntity();
        tour.setCustomer(customer3);
        tour.setId(1L);
        tour.setReservations(new HashSet<>());
        tour.setTickets(new HashSet<>());

        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setArrivalDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity.setCustomer(customer2);
        ticketEntity.setDepartureDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity.setFly(fly);
        ticketEntity.setId(UUID.randomUUID());
        ticketEntity.setPrice(BigDecimal.valueOf(1L));
        ticketEntity.setPurchaseDate(LocalDate.of(1970, 1, 1));
        ticketEntity.setTour(tour);

        HashSet<TicketEntity> ticketEntitySet = new HashSet<>();
        ticketEntitySet.add(ticketEntity);
        TourEntity tourEntity = mock(TourEntity.class);
        when(tourEntity.getId()).thenReturn(1L);
        when(tourEntity.getReservations()).thenReturn(new HashSet<>());
        when(tourEntity.getTickets()).thenReturn(ticketEntitySet);
        doNothing().when(tourEntity).setCustomer(Mockito.<CustomerEntity>any());
        doNothing().when(tourEntity).setId(Mockito.<Long>any());
        doNothing().when(tourEntity).setReservations(Mockito.<Set<ReservationEntity>>any());
        doNothing().when(tourEntity).setTickets(Mockito.<Set<TicketEntity>>any());
        tourEntity.setCustomer(customer);
        tourEntity.setId(1L);
        tourEntity.setReservations(new HashSet<>());
        tourEntity.setTickets(new HashSet<>());
        when(tourRepository.save(Mockito.<TourEntity>any())).thenReturn(tourEntity);

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCreditCard("Credit Card");
        customerEntity.setDni("Dni");
        customerEntity.setFullName("Dr Jane Doe");
        customerEntity.setPhoneNumber("6625550144");
        customerEntity.setReservations(new HashSet<>());
        customerEntity.setTickets(new HashSet<>());
        customerEntity.setTotalFlights(1);
        customerEntity.setTotalLodgings(1);
        customerEntity.setTotalTours(1);
        customerEntity.setTours(new HashSet<>());
        Optional<CustomerEntity> ofResult = Optional.of(customerEntity);
        when(customerRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        when(tourHelper.createReservations(Mockito.<HashMap<HotelEntity, Integer>>any(), Mockito.<CustomerEntity>any()))
                .thenReturn(new HashSet<>());
        when(tourHelper.createTickets(Mockito.<Set<FlyEntity>>any(), Mockito.<CustomerEntity>any()))
                .thenReturn(new HashSet<>());
        doNothing().when(blacklistHelper).isInBlacklist(Mockito.<String>any());
        TourRequest.TourRequestBuilder emailResult = TourRequest.builder().customerId("42").email("jane.doe@example.org");
        TourRequest.TourRequestBuilder flightsResult = emailResult.flights(new HashSet<>());
        TourResponse actualCreateResult = tourService.create(flightsResult.hotels(new HashSet<>()).build());
        assertEquals(1L, actualCreateResult.getId().longValue());
        assertEquals(1, actualCreateResult.getTicketIds().size());
        assertTrue(actualCreateResult.getReservationIds().isEmpty());
        verify(tourRepository).save(Mockito.<TourEntity>any());
        verify(tourEntity).getId();
        verify(tourEntity).getReservations();
        verify(tourEntity).getTickets();
        verify(tourEntity).setCustomer(Mockito.<CustomerEntity>any());
        verify(tourEntity).setId(Mockito.<Long>any());
        verify(tourEntity).setReservations(Mockito.<Set<ReservationEntity>>any());
        verify(tourEntity).setTickets(Mockito.<Set<TicketEntity>>any());
        verify(customerRepository).findById(Mockito.<String>any());
        verify(tourHelper).createReservations(Mockito.<HashMap<HotelEntity, Integer>>any(),
                Mockito.<CustomerEntity>any());
        verify(tourHelper).createTickets(Mockito.<Set<FlyEntity>>any(), Mockito.<CustomerEntity>any());
        verify(blacklistHelper).isInBlacklist(Mockito.<String>any());
    }

    /**
     * Method under test: {@link TourService#create(TourRequest)}
     */
    @Test
    void testCreate9() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCreditCard("Credit Card");
        customer.setDni("Dni");
        customer.setFullName("Dr Jane Doe");
        customer.setPhoneNumber("6625550144");
        customer.setReservations(new HashSet<>());
        customer.setTickets(new HashSet<>());
        customer.setTotalFlights(1);
        customer.setTotalLodgings(1);
        customer.setTotalTours(1);
        customer.setTours(new HashSet<>());

        CustomerEntity customer2 = new CustomerEntity();
        customer2.setCreditCard("Credit Card");
        customer2.setDni("Dni");
        customer2.setFullName("Dr Jane Doe");
        customer2.setPhoneNumber("6625550144");
        customer2.setReservations(new HashSet<>());
        customer2.setTickets(new HashSet<>());
        customer2.setTotalFlights(1);
        customer2.setTotalLodgings(1);
        customer2.setTotalTours(1);
        customer2.setTours(new HashSet<>());

        FlyEntity fly = new FlyEntity();
        fly.setAeroLine(AeroLine.aero_gold);
        fly.setDestinyLat(10.0d);
        fly.setDestinyLng(10.0d);
        fly.setDestinyName("Destiny Name");
        fly.setId(1L);
        fly.setOriginLat(10.0d);
        fly.setOriginLng(10.0d);
        fly.setOriginName("Origin Name");
        fly.setPrice(BigDecimal.valueOf(1L));
        fly.setTickets(new HashSet<>());

        CustomerEntity customer3 = new CustomerEntity();
        customer3.setCreditCard("Credit Card");
        customer3.setDni("Dni");
        customer3.setFullName("Dr Jane Doe");
        customer3.setPhoneNumber("6625550144");
        customer3.setReservations(new HashSet<>());
        customer3.setTickets(new HashSet<>());
        customer3.setTotalFlights(1);
        customer3.setTotalLodgings(1);
        customer3.setTotalTours(1);
        customer3.setTours(new HashSet<>());

        TourEntity tour = new TourEntity();
        tour.setCustomer(customer3);
        tour.setId(1L);
        tour.setReservations(new HashSet<>());
        tour.setTickets(new HashSet<>());

        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setArrivalDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity.setCustomer(customer2);
        ticketEntity.setDepartureDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity.setFly(fly);
        ticketEntity.setId(UUID.randomUUID());
        ticketEntity.setPrice(BigDecimal.valueOf(1L));
        ticketEntity.setPurchaseDate(LocalDate.of(1970, 1, 1));
        ticketEntity.setTour(tour);

        CustomerEntity customer4 = new CustomerEntity();
        customer4.setCreditCard("ar.com.laboratory.besttravel.domain.entities.jpa.CustomerEntity");
        customer4.setDni("ar.com.laboratory.besttravel.domain.entities.jpa.CustomerEntity");
        customer4.setFullName("Mr John Smith");
        customer4.setPhoneNumber("8605550118");
        customer4.setReservations(new HashSet<>());
        customer4.setTickets(new HashSet<>());
        customer4.setTotalFlights(2);
        customer4.setTotalLodgings(2);
        customer4.setTotalTours(2);
        customer4.setTours(new HashSet<>());

        FlyEntity fly2 = new FlyEntity();
        fly2.setAeroLine(AeroLine.blue_sky);
        fly2.setDestinyLat(0.5d);
        fly2.setDestinyLng(0.5d);
        fly2.setDestinyName("ar.com.laboratory.besttravel.domain.entities.jpa.FlyEntity");
        fly2.setId(2L);
        fly2.setOriginLat(0.5d);
        fly2.setOriginLng(0.5d);
        fly2.setOriginName("ar.com.laboratory.besttravel.domain.entities.jpa.FlyEntity");
        fly2.setPrice(BigDecimal.valueOf(1L));
        fly2.setTickets(new HashSet<>());

        CustomerEntity customer5 = new CustomerEntity();
        customer5.setCreditCard("ar.com.laboratory.besttravel.domain.entities.jpa.CustomerEntity");
        customer5.setDni("ar.com.laboratory.besttravel.domain.entities.jpa.CustomerEntity");
        customer5.setFullName("Mr John Smith");
        customer5.setPhoneNumber("8605550118");
        customer5.setReservations(new HashSet<>());
        customer5.setTickets(new HashSet<>());
        customer5.setTotalFlights(2);
        customer5.setTotalLodgings(2);
        customer5.setTotalTours(2);
        customer5.setTours(new HashSet<>());

        TourEntity tour2 = new TourEntity();
        tour2.setCustomer(customer5);
        tour2.setId(2L);
        tour2.setReservations(new HashSet<>());
        tour2.setTickets(new HashSet<>());

        TicketEntity ticketEntity2 = new TicketEntity();
        ticketEntity2.setArrivalDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity2.setCustomer(customer4);
        ticketEntity2.setDepartureDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity2.setFly(fly2);
        ticketEntity2.setId(UUID.randomUUID());
        ticketEntity2.setPrice(BigDecimal.valueOf(1L));
        ticketEntity2.setPurchaseDate(LocalDate.of(1970, 1, 1));
        ticketEntity2.setTour(tour2);

        HashSet<TicketEntity> ticketEntitySet = new HashSet<>();
        ticketEntitySet.add(ticketEntity2);
        ticketEntitySet.add(ticketEntity);
        TourEntity tourEntity = mock(TourEntity.class);
        when(tourEntity.getId()).thenReturn(1L);
        when(tourEntity.getReservations()).thenReturn(new HashSet<>());
        when(tourEntity.getTickets()).thenReturn(ticketEntitySet);
        doNothing().when(tourEntity).setCustomer(Mockito.<CustomerEntity>any());
        doNothing().when(tourEntity).setId(Mockito.<Long>any());
        doNothing().when(tourEntity).setReservations(Mockito.<Set<ReservationEntity>>any());
        doNothing().when(tourEntity).setTickets(Mockito.<Set<TicketEntity>>any());
        tourEntity.setCustomer(customer);
        tourEntity.setId(1L);
        tourEntity.setReservations(new HashSet<>());
        tourEntity.setTickets(new HashSet<>());
        when(tourRepository.save(Mockito.<TourEntity>any())).thenReturn(tourEntity);

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCreditCard("Credit Card");
        customerEntity.setDni("Dni");
        customerEntity.setFullName("Dr Jane Doe");
        customerEntity.setPhoneNumber("6625550144");
        customerEntity.setReservations(new HashSet<>());
        customerEntity.setTickets(new HashSet<>());
        customerEntity.setTotalFlights(1);
        customerEntity.setTotalLodgings(1);
        customerEntity.setTotalTours(1);
        customerEntity.setTours(new HashSet<>());
        Optional<CustomerEntity> ofResult = Optional.of(customerEntity);
        when(customerRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        when(tourHelper.createReservations(Mockito.<HashMap<HotelEntity, Integer>>any(), Mockito.<CustomerEntity>any()))
                .thenReturn(new HashSet<>());
        when(tourHelper.createTickets(Mockito.<Set<FlyEntity>>any(), Mockito.<CustomerEntity>any()))
                .thenReturn(new HashSet<>());
        doNothing().when(blacklistHelper).isInBlacklist(Mockito.<String>any());
        TourRequest.TourRequestBuilder emailResult = TourRequest.builder().customerId("42").email("jane.doe@example.org");
        TourRequest.TourRequestBuilder flightsResult = emailResult.flights(new HashSet<>());
        TourResponse actualCreateResult = tourService.create(flightsResult.hotels(new HashSet<>()).build());
        assertEquals(1L, actualCreateResult.getId().longValue());
        assertEquals(2, actualCreateResult.getTicketIds().size());
        assertTrue(actualCreateResult.getReservationIds().isEmpty());
        verify(tourRepository).save(Mockito.<TourEntity>any());
        verify(tourEntity).getId();
        verify(tourEntity).getReservations();
        verify(tourEntity).getTickets();
        verify(tourEntity).setCustomer(Mockito.<CustomerEntity>any());
        verify(tourEntity).setId(Mockito.<Long>any());
        verify(tourEntity).setReservations(Mockito.<Set<ReservationEntity>>any());
        verify(tourEntity).setTickets(Mockito.<Set<TicketEntity>>any());
        verify(customerRepository).findById(Mockito.<String>any());
        verify(tourHelper).createReservations(Mockito.<HashMap<HotelEntity, Integer>>any(),
                Mockito.<CustomerEntity>any());
        verify(tourHelper).createTickets(Mockito.<Set<FlyEntity>>any(), Mockito.<CustomerEntity>any());
        verify(blacklistHelper).isInBlacklist(Mockito.<String>any());
    }

    /**
     * Method under test: {@link TourService#create(TourRequest)}
     */
    @Test
    void testCreate10() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCreditCard("Credit Card");
        customer.setDni("Dni");
        customer.setFullName("Dr Jane Doe");
        customer.setPhoneNumber("6625550144");
        customer.setReservations(new HashSet<>());
        customer.setTickets(new HashSet<>());
        customer.setTotalFlights(1);
        customer.setTotalLodgings(1);
        customer.setTotalTours(1);
        customer.setTours(new HashSet<>());
        TourEntity tourEntity = mock(TourEntity.class);
        when(tourEntity.getId()).thenReturn(1L);
        when(tourEntity.getReservations()).thenReturn(new HashSet<>());
        when(tourEntity.getTickets()).thenReturn(new HashSet<>());
        doNothing().when(tourEntity).setCustomer(Mockito.<CustomerEntity>any());
        doNothing().when(tourEntity).setId(Mockito.<Long>any());
        doNothing().when(tourEntity).setReservations(Mockito.<Set<ReservationEntity>>any());
        doNothing().when(tourEntity).setTickets(Mockito.<Set<TicketEntity>>any());
        tourEntity.setCustomer(customer);
        tourEntity.setId(1L);
        tourEntity.setReservations(new HashSet<>());
        tourEntity.setTickets(new HashSet<>());
        when(tourRepository.save(Mockito.<TourEntity>any())).thenReturn(tourEntity);

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCreditCard("Credit Card");
        customerEntity.setDni("Dni");
        customerEntity.setFullName("Dr Jane Doe");
        customerEntity.setPhoneNumber("6625550144");
        customerEntity.setReservations(new HashSet<>());
        customerEntity.setTickets(new HashSet<>());
        customerEntity.setTotalFlights(1);
        customerEntity.setTotalLodgings(1);
        customerEntity.setTotalTours(1);
        customerEntity.setTours(new HashSet<>());
        Optional<CustomerEntity> ofResult = Optional.of(customerEntity);
        when(customerRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        FlyEntity flyEntity = new FlyEntity();
        flyEntity.setAeroLine(AeroLine.aero_gold);
        flyEntity.setDestinyLat(10.0d);
        flyEntity.setDestinyLng(10.0d);
        flyEntity.setDestinyName("Destiny Name");
        flyEntity.setId(1L);
        flyEntity.setOriginLat(10.0d);
        flyEntity.setOriginLng(10.0d);
        flyEntity.setOriginName("Origin Name");
        flyEntity.setPrice(BigDecimal.valueOf(1L));
        flyEntity.setTickets(new HashSet<>());
        Optional<FlyEntity> ofResult2 = Optional.of(flyEntity);
        when(flyRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        when(tourHelper.createReservations(Mockito.<HashMap<HotelEntity, Integer>>any(), Mockito.<CustomerEntity>any()))
                .thenReturn(new HashSet<>());
        when(tourHelper.createTickets(Mockito.<Set<FlyEntity>>any(), Mockito.<CustomerEntity>any()))
                .thenReturn(new HashSet<>());
        doNothing().when(blacklistHelper).isInBlacklist(Mockito.<String>any());

        HashSet<TourFlyRequest> flights = new HashSet<>();
        flights.add(TourFlyRequest.builder().id(1L).build());
        TourRequest.TourRequestBuilder flightsResult = TourRequest.builder()
                .customerId("42")
                .email("jane.doe@example.org")
                .flights(flights);
        TourResponse actualCreateResult = tourService.create(flightsResult.hotels(new HashSet<>()).build());
        assertEquals(1L, actualCreateResult.getId().longValue());
        assertTrue(actualCreateResult.getTicketIds().isEmpty());
        assertTrue(actualCreateResult.getReservationIds().isEmpty());
        verify(tourRepository).save(Mockito.<TourEntity>any());
        verify(tourEntity).getId();
        verify(tourEntity).getReservations();
        verify(tourEntity).getTickets();
        verify(tourEntity).setCustomer(Mockito.<CustomerEntity>any());
        verify(tourEntity).setId(Mockito.<Long>any());
        verify(tourEntity).setReservations(Mockito.<Set<ReservationEntity>>any());
        verify(tourEntity).setTickets(Mockito.<Set<TicketEntity>>any());
        verify(customerRepository).findById(Mockito.<String>any());
        verify(flyRepository).findById(Mockito.<Long>any());
        verify(tourHelper).createReservations(Mockito.<HashMap<HotelEntity, Integer>>any(),
                Mockito.<CustomerEntity>any());
        verify(tourHelper).createTickets(Mockito.<Set<FlyEntity>>any(), Mockito.<CustomerEntity>any());
        verify(blacklistHelper).isInBlacklist(Mockito.<String>any());
    }

    /**
     * Method under test: {@link TourService#read(Long)}
     */
    @Test
    void testRead() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCreditCard("Credit Card");
        customer.setDni("Dni");
        customer.setFullName("Dr Jane Doe");
        customer.setPhoneNumber("6625550144");
        customer.setReservations(new HashSet<>());
        customer.setTickets(new HashSet<>());
        customer.setTotalFlights(1);
        customer.setTotalLodgings(1);
        customer.setTotalTours(1);
        customer.setTours(new HashSet<>());

        TourEntity tourEntity = new TourEntity();
        tourEntity.setCustomer(customer);
        tourEntity.setId(1L);
        tourEntity.setReservations(new HashSet<>());
        tourEntity.setTickets(new HashSet<>());
        Optional<TourEntity> ofResult = Optional.of(tourEntity);
        when(tourRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        TourResponse actualReadResult = tourService.read(1L);
        assertEquals(1L, actualReadResult.getId().longValue());
        assertTrue(actualReadResult.getTicketIds().isEmpty());
        assertTrue(actualReadResult.getReservationIds().isEmpty());
        verify(tourRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link TourService#read(Long)}
     */
    @Test
    void testRead2() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCreditCard("Credit Card");
        customer.setDni("Dni");
        customer.setFullName("Dr Jane Doe");
        customer.setPhoneNumber("6625550144");
        customer.setReservations(new HashSet<>());
        customer.setTickets(new HashSet<>());
        customer.setTotalFlights(1);
        customer.setTotalLodgings(1);
        customer.setTotalTours(1);
        customer.setTours(new HashSet<>());
        TourEntity tourEntity = mock(TourEntity.class);
        when(tourEntity.getId()).thenReturn(1L);
        when(tourEntity.getTickets()).thenReturn(new HashSet<>());
        doNothing().when(tourEntity).setCustomer(Mockito.<CustomerEntity>any());
        doNothing().when(tourEntity).setId(Mockito.<Long>any());
        doNothing().when(tourEntity).setReservations(Mockito.<Set<ReservationEntity>>any());
        doNothing().when(tourEntity).setTickets(Mockito.<Set<TicketEntity>>any());
        tourEntity.setCustomer(customer);
        tourEntity.setId(1L);
        tourEntity.setReservations(new HashSet<>());
        tourEntity.setTickets(new HashSet<>());
        Optional<TourEntity> ofResult = Optional.of(tourEntity);
        when(tourRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        TourResponse actualReadResult = tourService.read(1L);
        assertEquals(1L, actualReadResult.getId().longValue());
        assertTrue(actualReadResult.getTicketIds().isEmpty());
        assertTrue(actualReadResult.getReservationIds().isEmpty());
        verify(tourRepository).findById(Mockito.<Long>any());
        verify(tourEntity).getId();
        verify(tourEntity, atLeast(1)).getTickets();
        verify(tourEntity).setCustomer(Mockito.<CustomerEntity>any());
        verify(tourEntity).setId(Mockito.<Long>any());
        verify(tourEntity).setReservations(Mockito.<Set<ReservationEntity>>any());
        verify(tourEntity).setTickets(Mockito.<Set<TicketEntity>>any());
    }

    /**
     * Method under test: {@link TourService#read(Long)}
     */
    @Test
    void testRead3() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCreditCard("Credit Card");
        customer.setDni("Dni");
        customer.setFullName("Dr Jane Doe");
        customer.setPhoneNumber("6625550144");
        customer.setReservations(new HashSet<>());
        customer.setTickets(new HashSet<>());
        customer.setTotalFlights(1);
        customer.setTotalLodgings(1);
        customer.setTotalTours(1);
        customer.setTours(new HashSet<>());

        CustomerEntity customer2 = new CustomerEntity();
        customer2.setCreditCard("Credit Card");
        customer2.setDni("Dni");
        customer2.setFullName("Dr Jane Doe");
        customer2.setPhoneNumber("6625550144");
        customer2.setReservations(new HashSet<>());
        customer2.setTickets(new HashSet<>());
        customer2.setTotalFlights(1);
        customer2.setTotalLodgings(1);
        customer2.setTotalTours(1);
        customer2.setTours(new HashSet<>());

        FlyEntity fly = new FlyEntity();
        fly.setAeroLine(AeroLine.aero_gold);
        fly.setDestinyLat(10.0d);
        fly.setDestinyLng(10.0d);
        fly.setDestinyName("Destiny Name");
        fly.setId(1L);
        fly.setOriginLat(10.0d);
        fly.setOriginLng(10.0d);
        fly.setOriginName("Origin Name");
        fly.setPrice(BigDecimal.valueOf(1L));
        fly.setTickets(new HashSet<>());

        CustomerEntity customer3 = new CustomerEntity();
        customer3.setCreditCard("Credit Card");
        customer3.setDni("Dni");
        customer3.setFullName("Dr Jane Doe");
        customer3.setPhoneNumber("6625550144");
        customer3.setReservations(new HashSet<>());
        customer3.setTickets(new HashSet<>());
        customer3.setTotalFlights(1);
        customer3.setTotalLodgings(1);
        customer3.setTotalTours(1);
        customer3.setTours(new HashSet<>());

        TourEntity tour = new TourEntity();
        tour.setCustomer(customer3);
        tour.setId(1L);
        tour.setReservations(new HashSet<>());
        tour.setTickets(new HashSet<>());

        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setArrivalDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity.setCustomer(customer2);
        ticketEntity.setDepartureDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity.setFly(fly);
        ticketEntity.setId(UUID.randomUUID());
        ticketEntity.setPrice(BigDecimal.valueOf(1L));
        ticketEntity.setPurchaseDate(LocalDate.of(1970, 1, 1));
        ticketEntity.setTour(tour);

        HashSet<TicketEntity> ticketEntitySet = new HashSet<>();
        ticketEntitySet.add(ticketEntity);
        TourEntity tourEntity = mock(TourEntity.class);
        when(tourEntity.getId()).thenReturn(1L);
        when(tourEntity.getTickets()).thenReturn(ticketEntitySet);
        doNothing().when(tourEntity).setCustomer(Mockito.<CustomerEntity>any());
        doNothing().when(tourEntity).setId(Mockito.<Long>any());
        doNothing().when(tourEntity).setReservations(Mockito.<Set<ReservationEntity>>any());
        doNothing().when(tourEntity).setTickets(Mockito.<Set<TicketEntity>>any());
        tourEntity.setCustomer(customer);
        tourEntity.setId(1L);
        tourEntity.setReservations(new HashSet<>());
        tourEntity.setTickets(new HashSet<>());
        Optional<TourEntity> ofResult = Optional.of(tourEntity);
        when(tourRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        TourResponse actualReadResult = tourService.read(1L);
        assertEquals(1L, actualReadResult.getId().longValue());
        assertEquals(1, actualReadResult.getTicketIds().size());
        assertEquals(1, actualReadResult.getReservationIds().size());
        verify(tourRepository).findById(Mockito.<Long>any());
        verify(tourEntity).getId();
        verify(tourEntity, atLeast(1)).getTickets();
        verify(tourEntity).setCustomer(Mockito.<CustomerEntity>any());
        verify(tourEntity).setId(Mockito.<Long>any());
        verify(tourEntity).setReservations(Mockito.<Set<ReservationEntity>>any());
        verify(tourEntity).setTickets(Mockito.<Set<TicketEntity>>any());
    }

    /**
     * Method under test: {@link TourService#read(Long)}
     */
    @Test
    void testRead4() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCreditCard("Credit Card");
        customer.setDni("Dni");
        customer.setFullName("Dr Jane Doe");
        customer.setPhoneNumber("6625550144");
        customer.setReservations(new HashSet<>());
        customer.setTickets(new HashSet<>());
        customer.setTotalFlights(1);
        customer.setTotalLodgings(1);
        customer.setTotalTours(1);
        customer.setTours(new HashSet<>());

        CustomerEntity customer2 = new CustomerEntity();
        customer2.setCreditCard("Credit Card");
        customer2.setDni("Dni");
        customer2.setFullName("Dr Jane Doe");
        customer2.setPhoneNumber("6625550144");
        customer2.setReservations(new HashSet<>());
        customer2.setTickets(new HashSet<>());
        customer2.setTotalFlights(1);
        customer2.setTotalLodgings(1);
        customer2.setTotalTours(1);
        customer2.setTours(new HashSet<>());

        FlyEntity fly = new FlyEntity();
        fly.setAeroLine(AeroLine.aero_gold);
        fly.setDestinyLat(10.0d);
        fly.setDestinyLng(10.0d);
        fly.setDestinyName("Destiny Name");
        fly.setId(1L);
        fly.setOriginLat(10.0d);
        fly.setOriginLng(10.0d);
        fly.setOriginName("Origin Name");
        fly.setPrice(BigDecimal.valueOf(1L));
        fly.setTickets(new HashSet<>());

        CustomerEntity customer3 = new CustomerEntity();
        customer3.setCreditCard("Credit Card");
        customer3.setDni("Dni");
        customer3.setFullName("Dr Jane Doe");
        customer3.setPhoneNumber("6625550144");
        customer3.setReservations(new HashSet<>());
        customer3.setTickets(new HashSet<>());
        customer3.setTotalFlights(1);
        customer3.setTotalLodgings(1);
        customer3.setTotalTours(1);
        customer3.setTours(new HashSet<>());

        TourEntity tour = new TourEntity();
        tour.setCustomer(customer3);
        tour.setId(1L);
        tour.setReservations(new HashSet<>());
        tour.setTickets(new HashSet<>());

        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setArrivalDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity.setCustomer(customer2);
        ticketEntity.setDepartureDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity.setFly(fly);
        ticketEntity.setId(UUID.randomUUID());
        ticketEntity.setPrice(BigDecimal.valueOf(1L));
        ticketEntity.setPurchaseDate(LocalDate.of(1970, 1, 1));
        ticketEntity.setTour(tour);

        CustomerEntity customer4 = new CustomerEntity();
        customer4.setCreditCard("ar.com.laboratory.besttravel.domain.entities.jpa.CustomerEntity");
        customer4.setDni("ar.com.laboratory.besttravel.domain.entities.jpa.CustomerEntity");
        customer4.setFullName("Mr John Smith");
        customer4.setPhoneNumber("8605550118");
        customer4.setReservations(new HashSet<>());
        customer4.setTickets(new HashSet<>());
        customer4.setTotalFlights(0);
        customer4.setTotalLodgings(0);
        customer4.setTotalTours(0);
        customer4.setTours(new HashSet<>());

        FlyEntity fly2 = new FlyEntity();
        fly2.setAeroLine(AeroLine.blue_sky);
        fly2.setDestinyLat(0.5d);
        fly2.setDestinyLng(0.5d);
        fly2.setDestinyName("ar.com.laboratory.besttravel.domain.entities.jpa.FlyEntity");
        fly2.setId(2L);
        fly2.setOriginLat(0.5d);
        fly2.setOriginLng(0.5d);
        fly2.setOriginName("ar.com.laboratory.besttravel.domain.entities.jpa.FlyEntity");
        fly2.setPrice(BigDecimal.valueOf(1L));
        fly2.setTickets(new HashSet<>());

        CustomerEntity customer5 = new CustomerEntity();
        customer5.setCreditCard("ar.com.laboratory.besttravel.domain.entities.jpa.CustomerEntity");
        customer5.setDni("ar.com.laboratory.besttravel.domain.entities.jpa.CustomerEntity");
        customer5.setFullName("Mr John Smith");
        customer5.setPhoneNumber("8605550118");
        customer5.setReservations(new HashSet<>());
        customer5.setTickets(new HashSet<>());
        customer5.setTotalFlights(0);
        customer5.setTotalLodgings(0);
        customer5.setTotalTours(0);
        customer5.setTours(new HashSet<>());

        TourEntity tour2 = new TourEntity();
        tour2.setCustomer(customer5);
        tour2.setId(2L);
        tour2.setReservations(new HashSet<>());
        tour2.setTickets(new HashSet<>());

        TicketEntity ticketEntity2 = new TicketEntity();
        ticketEntity2.setArrivalDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity2.setCustomer(customer4);
        ticketEntity2.setDepartureDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity2.setFly(fly2);
        ticketEntity2.setId(UUID.randomUUID());
        ticketEntity2.setPrice(BigDecimal.valueOf(1L));
        ticketEntity2.setPurchaseDate(LocalDate.of(1970, 1, 1));
        ticketEntity2.setTour(tour2);

        HashSet<TicketEntity> ticketEntitySet = new HashSet<>();
        ticketEntitySet.add(ticketEntity2);
        ticketEntitySet.add(ticketEntity);
        TourEntity tourEntity = mock(TourEntity.class);
        when(tourEntity.getId()).thenReturn(1L);
        when(tourEntity.getTickets()).thenReturn(ticketEntitySet);
        doNothing().when(tourEntity).setCustomer(Mockito.<CustomerEntity>any());
        doNothing().when(tourEntity).setId(Mockito.<Long>any());
        doNothing().when(tourEntity).setReservations(Mockito.<Set<ReservationEntity>>any());
        doNothing().when(tourEntity).setTickets(Mockito.<Set<TicketEntity>>any());
        tourEntity.setCustomer(customer);
        tourEntity.setId(1L);
        tourEntity.setReservations(new HashSet<>());
        tourEntity.setTickets(new HashSet<>());
        Optional<TourEntity> ofResult = Optional.of(tourEntity);
        when(tourRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        TourResponse actualReadResult = tourService.read(1L);
        assertEquals(1L, actualReadResult.getId().longValue());
        assertEquals(2, actualReadResult.getTicketIds().size());
        assertEquals(2, actualReadResult.getReservationIds().size());
        verify(tourRepository).findById(Mockito.<Long>any());
        verify(tourEntity).getId();
        verify(tourEntity, atLeast(1)).getTickets();
        verify(tourEntity).setCustomer(Mockito.<CustomerEntity>any());
        verify(tourEntity).setId(Mockito.<Long>any());
        verify(tourEntity).setReservations(Mockito.<Set<ReservationEntity>>any());
        verify(tourEntity).setTickets(Mockito.<Set<TicketEntity>>any());
    }

    /**
     * Method under test: {@link TourService#read(Long)}
     */
    @Test
    void testRead5() {
        Optional<TourEntity> emptyResult = Optional.empty();
        when(tourRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        assertThrows(IdNotFoundException.class, () -> tourService.read(1L));
        verify(tourRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link TourService#delete(Long)}
     */
    @Test
    void testDelete() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCreditCard("Credit Card");
        customer.setDni("Dni");
        customer.setFullName("Dr Jane Doe");
        customer.setPhoneNumber("6625550144");
        customer.setReservations(new HashSet<>());
        customer.setTickets(new HashSet<>());
        customer.setTotalFlights(1);
        customer.setTotalLodgings(1);
        customer.setTotalTours(1);
        customer.setTours(new HashSet<>());

        TourEntity tourEntity = new TourEntity();
        tourEntity.setCustomer(customer);
        tourEntity.setId(1L);
        tourEntity.setReservations(new HashSet<>());
        tourEntity.setTickets(new HashSet<>());
        Optional<TourEntity> ofResult = Optional.of(tourEntity);
        doNothing().when(tourRepository).delete(Mockito.<TourEntity>any());
        when(tourRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        tourService.delete(1L);
        verify(tourRepository).findById(Mockito.<Long>any());
        verify(tourRepository).delete(Mockito.<TourEntity>any());
    }

    /**
     * Method under test: {@link TourService#delete(Long)}
     */
    @Test
    void testDelete2() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCreditCard("Credit Card");
        customer.setDni("Dni");
        customer.setFullName("Dr Jane Doe");
        customer.setPhoneNumber("6625550144");
        customer.setReservations(new HashSet<>());
        customer.setTickets(new HashSet<>());
        customer.setTotalFlights(1);
        customer.setTotalLodgings(1);
        customer.setTotalTours(1);
        customer.setTours(new HashSet<>());

        TourEntity tourEntity = new TourEntity();
        tourEntity.setCustomer(customer);
        tourEntity.setId(1L);
        tourEntity.setReservations(new HashSet<>());
        tourEntity.setTickets(new HashSet<>());
        Optional<TourEntity> ofResult = Optional.of(tourEntity);
        doThrow(new IdNotFoundException("Table Name")).when(tourRepository).delete(Mockito.<TourEntity>any());
        when(tourRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(IdNotFoundException.class, () -> tourService.delete(1L));
        verify(tourRepository).findById(Mockito.<Long>any());
        verify(tourRepository).delete(Mockito.<TourEntity>any());
    }

    /**
     * Method under test: {@link TourService#delete(Long)}
     */
    @Test
    void testDelete3() {
        Optional<TourEntity> emptyResult = Optional.empty();
        when(tourRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        assertThrows(IdNotFoundException.class, () -> tourService.delete(1L));
        verify(tourRepository).findById(Mockito.<Long>any());
    }
}

