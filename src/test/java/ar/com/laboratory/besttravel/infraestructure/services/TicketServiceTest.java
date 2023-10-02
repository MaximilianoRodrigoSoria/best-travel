package ar.com.laboratory.besttravel.infraestructure.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ar.com.laboratory.besttravel.api.models.requests.TicketRequest;
import ar.com.laboratory.besttravel.api.models.responses.FlyResponse;
import ar.com.laboratory.besttravel.api.models.responses.TicketResponse;
import ar.com.laboratory.besttravel.domain.entities.jpa.CustomerEntity;
import ar.com.laboratory.besttravel.domain.entities.jpa.FlyEntity;
import ar.com.laboratory.besttravel.domain.entities.jpa.TicketEntity;
import ar.com.laboratory.besttravel.domain.entities.jpa.TourEntity;
import ar.com.laboratory.besttravel.domain.repositories.jpa.CustomerRepository;
import ar.com.laboratory.besttravel.domain.repositories.jpa.FlyRepository;
import ar.com.laboratory.besttravel.domain.repositories.jpa.TicketRepository;
import ar.com.laboratory.besttravel.infraestructure.abstract_service.helpers.BlacklistHelper;
import ar.com.laboratory.besttravel.util.enums.AeroLine;
import ar.com.laboratory.besttravel.util.exceptions.IdNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

@ContextConfiguration(classes = {TicketService.class})
@ExtendWith(SpringExtension.class)
class TicketServiceTest {
    @MockBean
    private BlacklistHelper blacklistHelper;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private FlyRepository flyRepository;

    @MockBean
    private TicketRepository ticketRepository;

    @Autowired
    private TicketService ticketService;

    /**
     * Method under test: {@link TicketService#created(TicketRequest)}
     */
    @Test
    void testCreated() {
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
        Optional<FlyEntity> ofResult = Optional.of(flyEntity);
        when(flyRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        Optional<CustomerEntity> ofResult2 = Optional.of(customerEntity);
        when(customerRepository.findById(Mockito.<String>any())).thenReturn(ofResult2);

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

        TourEntity tour = new TourEntity();
        tour.setCustomer(customer2);
        tour.setId(1L);
        tour.setReservations(new HashSet<>());
        tour.setTickets(new HashSet<>());

        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setArrivalDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity.setCustomer(customer);
        ticketEntity.setDepartureDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity.setFly(fly);
        ticketEntity.setId(UUID.randomUUID());
        BigDecimal price = BigDecimal.valueOf(1L);
        ticketEntity.setPrice(price);
        ticketEntity.setPurchaseDate(LocalDate.of(1970, 1, 1));
        ticketEntity.setTour(tour);
        when(ticketRepository.save(Mockito.<TicketEntity>any())).thenReturn(ticketEntity);
        doNothing().when(blacklistHelper).isInBlacklist(Mockito.<String>any());
        TicketResponse actualCreatedResult = ticketService.created(new TicketRequest());
        FlyResponse fly2 = actualCreatedResult.getFly();
        assertEquals("Origin Name", fly2.getOriginName());
        assertEquals(10.0d, fly2.getOriginLng().doubleValue());
        assertEquals(10.0d, fly2.getOriginLat().doubleValue());
        assertEquals(1L, fly2.getId().longValue());
        assertEquals("Destiny Name", fly2.getDestinyName());
        assertEquals(10.0d, fly2.getDestinyLng().doubleValue());
        assertEquals(10.0d, fly2.getDestinyLat().doubleValue());
        assertEquals(AeroLine.aero_gold, fly2.getAeroLine());
        assertEquals("1.25", actualCreatedResult.getPrice().toString());
        BigDecimal expectedPrice = price.ONE;
        BigDecimal price2 = fly2.getPrice();
        assertSame(expectedPrice, price2);
        assertEquals("1", price2.toString());
        verify(flyRepository).findById(Mockito.<Long>any());
        verify(customerRepository).findById(Mockito.<String>any());
        verify(ticketRepository).save(Mockito.<TicketEntity>any());
        verify(blacklistHelper).isInBlacklist(Mockito.<String>any());
    }

    /**
     * Method under test: {@link TicketService#created(TicketRequest)}
     */
    @Test
    void testCreated2() {
        doThrow(new IdNotFoundException("Ticket saved id: {}")).when(blacklistHelper)
                .isInBlacklist(Mockito.<String>any());
        assertThrows(IdNotFoundException.class, () -> ticketService.created(new TicketRequest()));
        verify(blacklistHelper).isInBlacklist(Mockito.<String>any());
    }

    /**
     * Method under test: {@link TicketService#created(TicketRequest)}
     */
    @Test
    void testCreated3() {
        Optional<FlyEntity> emptyResult = Optional.empty();
        when(flyRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        new IdNotFoundException("Table Name");
        new IdNotFoundException("Table Name");
        new IdNotFoundException("Table Name");
        new IdNotFoundException("Table Name");
        new IdNotFoundException("Table Name");
        new IdNotFoundException("Table Name");
        new IdNotFoundException("Table Name");
        new IdNotFoundException("Table Name");

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
        doNothing().when(blacklistHelper).isInBlacklist(Mockito.<String>any());
        assertThrows(IdNotFoundException.class, () -> ticketService.created(new TicketRequest()));
        verify(flyRepository).findById(Mockito.<Long>any());
        verify(blacklistHelper).isInBlacklist(Mockito.<String>any());
    }

    /**
     * Method under test: {@link TicketService#created(TicketRequest)}
     */
    @Test
    void testCreated4() {
        FlyEntity flyEntity = mock(FlyEntity.class);
        doNothing().when(flyEntity).setAeroLine(Mockito.<AeroLine>any());
        doNothing().when(flyEntity).setDestinyLat(Mockito.<Double>any());
        doNothing().when(flyEntity).setDestinyLng(Mockito.<Double>any());
        doNothing().when(flyEntity).setDestinyName(Mockito.<String>any());
        doNothing().when(flyEntity).setId(Mockito.<Long>any());
        doNothing().when(flyEntity).setOriginLat(Mockito.<Double>any());
        doNothing().when(flyEntity).setOriginLng(Mockito.<Double>any());
        doNothing().when(flyEntity).setOriginName(Mockito.<String>any());
        doNothing().when(flyEntity).setPrice(Mockito.<BigDecimal>any());
        doNothing().when(flyEntity).setTickets(Mockito.<Set<TicketEntity>>any());
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
        Optional<FlyEntity> ofResult = Optional.of(flyEntity);
        when(flyRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        Optional<CustomerEntity> emptyResult = Optional.empty();
        when(customerRepository.findById(Mockito.<String>any())).thenReturn(emptyResult);
        doNothing().when(blacklistHelper).isInBlacklist(Mockito.<String>any());
        assertThrows(IdNotFoundException.class, () -> ticketService.created(new TicketRequest()));
        verify(flyRepository).findById(Mockito.<Long>any());
        verify(flyEntity).setAeroLine(Mockito.<AeroLine>any());
        verify(flyEntity).setDestinyLat(Mockito.<Double>any());
        verify(flyEntity).setDestinyLng(Mockito.<Double>any());
        verify(flyEntity).setDestinyName(Mockito.<String>any());
        verify(flyEntity).setId(Mockito.<Long>any());
        verify(flyEntity).setOriginLat(Mockito.<Double>any());
        verify(flyEntity).setOriginLng(Mockito.<Double>any());
        verify(flyEntity).setOriginName(Mockito.<String>any());
        verify(flyEntity).setPrice(Mockito.<BigDecimal>any());
        verify(flyEntity).setTickets(Mockito.<Set<TicketEntity>>any());
        verify(customerRepository).findById(Mockito.<String>any());
        verify(blacklistHelper).isInBlacklist(Mockito.<String>any());
    }

    /**
     * Method under test: {@link TicketService#read(UUID)}
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

        TourEntity tour = new TourEntity();
        tour.setCustomer(customer2);
        tour.setId(1L);
        tour.setReservations(new HashSet<>());
        tour.setTickets(new HashSet<>());

        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setArrivalDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity.setCustomer(customer);
        ticketEntity.setDepartureDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity.setFly(fly);
        UUID id = UUID.randomUUID();
        ticketEntity.setId(id);
        BigDecimal price = BigDecimal.valueOf(1L);
        ticketEntity.setPrice(price);
        ticketEntity.setPurchaseDate(LocalDate.of(1970, 1, 1));
        ticketEntity.setTour(tour);
        Optional<TicketEntity> ofResult = Optional.of(ticketEntity);
        when(ticketRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        TicketResponse actualReadResult = ticketService.read(UUID.randomUUID());
        assertEquals("1970-01-01", actualReadResult.getPurchaseDate().toString());
        assertEquals("00:00", actualReadResult.getArrivalDate().toLocalTime().toString());
        BigDecimal expectedPrice = price.ONE;
        BigDecimal price2 = actualReadResult.getPrice();
        assertSame(expectedPrice, price2);
        assertSame(id, actualReadResult.getId());
        assertEquals("1970-01-01", actualReadResult.getDepartureDate().toLocalDate().toString());
        FlyResponse fly2 = actualReadResult.getFly();
        assertEquals("Origin Name", fly2.getOriginName());
        assertEquals(10.0d, fly2.getOriginLng().doubleValue());
        assertEquals(10.0d, fly2.getOriginLat().doubleValue());
        assertEquals(1L, fly2.getId().longValue());
        assertEquals("Destiny Name", fly2.getDestinyName());
        assertEquals(10.0d, fly2.getDestinyLng().doubleValue());
        assertEquals(10.0d, fly2.getDestinyLat().doubleValue());
        assertEquals(AeroLine.aero_gold, fly2.getAeroLine());
        assertEquals("1", price2.toString());
        assertSame(price2, fly2.getPrice());
        verify(ticketRepository).findById(Mockito.<UUID>any());
    }

    /**
     * Method under test: {@link TicketService#read(UUID)}
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

        TourEntity tour = new TourEntity();
        tour.setCustomer(customer2);
        tour.setId(1L);
        tour.setReservations(new HashSet<>());
        tour.setTickets(new HashSet<>());

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
        TicketEntity ticketEntity = mock(TicketEntity.class);
        when(ticketEntity.getFly()).thenReturn(flyEntity);
        when(ticketEntity.getPrice()).thenReturn(BigDecimal.valueOf(1L));
        when(ticketEntity.getPurchaseDate()).thenReturn(LocalDate.of(1970, 1, 1));
        when(ticketEntity.getArrivalDate()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(ticketEntity.getDepartureDate()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        UUID randomUUIDResult = UUID.randomUUID();
        when(ticketEntity.getId()).thenReturn(randomUUIDResult);
        doNothing().when(ticketEntity).setArrivalDate(Mockito.<LocalDateTime>any());
        doNothing().when(ticketEntity).setCustomer(Mockito.<CustomerEntity>any());
        doNothing().when(ticketEntity).setDepartureDate(Mockito.<LocalDateTime>any());
        doNothing().when(ticketEntity).setFly(Mockito.<FlyEntity>any());
        doNothing().when(ticketEntity).setId(Mockito.<UUID>any());
        doNothing().when(ticketEntity).setPrice(Mockito.<BigDecimal>any());
        doNothing().when(ticketEntity).setPurchaseDate(Mockito.<LocalDate>any());
        doNothing().when(ticketEntity).setTour(Mockito.<TourEntity>any());
        ticketEntity.setArrivalDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity.setCustomer(customer);
        ticketEntity.setDepartureDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity.setFly(fly);
        ticketEntity.setId(UUID.randomUUID());
        BigDecimal price = BigDecimal.valueOf(1L);
        ticketEntity.setPrice(price);
        ticketEntity.setPurchaseDate(LocalDate.of(1970, 1, 1));
        ticketEntity.setTour(tour);
        Optional<TicketEntity> ofResult = Optional.of(ticketEntity);
        when(ticketRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        TicketResponse actualReadResult = ticketService.read(UUID.randomUUID());
        assertEquals("1970-01-01", actualReadResult.getPurchaseDate().toString());
        assertEquals("00:00", actualReadResult.getArrivalDate().toLocalTime().toString());
        BigDecimal expectedPrice = price.ONE;
        BigDecimal price2 = actualReadResult.getPrice();
        assertSame(expectedPrice, price2);
        assertSame(randomUUIDResult, actualReadResult.getId());
        assertEquals("1970-01-01", actualReadResult.getDepartureDate().toLocalDate().toString());
        FlyResponse fly2 = actualReadResult.getFly();
        assertEquals("Origin Name", fly2.getOriginName());
        assertEquals(10.0d, fly2.getOriginLng().doubleValue());
        assertEquals(10.0d, fly2.getOriginLat().doubleValue());
        assertEquals(1L, fly2.getId().longValue());
        assertEquals("Destiny Name", fly2.getDestinyName());
        assertEquals(10.0d, fly2.getDestinyLng().doubleValue());
        assertEquals(10.0d, fly2.getDestinyLat().doubleValue());
        assertEquals(AeroLine.aero_gold, fly2.getAeroLine());
        assertEquals("1", price2.toString());
        assertSame(price2, fly2.getPrice());
        verify(ticketRepository).findById(Mockito.<UUID>any());
        verify(ticketEntity).getFly();
        verify(ticketEntity).getPrice();
        verify(ticketEntity).getPurchaseDate();
        verify(ticketEntity).getArrivalDate();
        verify(ticketEntity).getDepartureDate();
        verify(ticketEntity).getId();
        verify(ticketEntity).setArrivalDate(Mockito.<LocalDateTime>any());
        verify(ticketEntity).setCustomer(Mockito.<CustomerEntity>any());
        verify(ticketEntity).setDepartureDate(Mockito.<LocalDateTime>any());
        verify(ticketEntity).setFly(Mockito.<FlyEntity>any());
        verify(ticketEntity).setId(Mockito.<UUID>any());
        verify(ticketEntity).setPrice(Mockito.<BigDecimal>any());
        verify(ticketEntity).setPurchaseDate(Mockito.<LocalDate>any());
        verify(ticketEntity).setTour(Mockito.<TourEntity>any());
    }

    /**
     * Method under test: {@link TicketService#update(TicketRequest, UUID)}
     */
    @Test
    void testUpdate() {
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
        Optional<FlyEntity> ofResult = Optional.of(flyEntity);
        when(flyRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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

        TourEntity tour = new TourEntity();
        tour.setCustomer(customer2);
        tour.setId(1L);
        tour.setReservations(new HashSet<>());
        tour.setTickets(new HashSet<>());

        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setArrivalDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity.setCustomer(customer);
        ticketEntity.setDepartureDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity.setFly(fly);
        ticketEntity.setId(UUID.randomUUID());
        BigDecimal price = BigDecimal.valueOf(1L);
        ticketEntity.setPrice(price);
        ticketEntity.setPurchaseDate(LocalDate.of(1970, 1, 1));
        ticketEntity.setTour(tour);
        Optional<TicketEntity> ofResult2 = Optional.of(ticketEntity);

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

        FlyEntity fly2 = new FlyEntity();
        fly2.setAeroLine(AeroLine.aero_gold);
        fly2.setDestinyLat(10.0d);
        fly2.setDestinyLng(10.0d);
        fly2.setDestinyName("Destiny Name");
        fly2.setId(1L);
        fly2.setOriginLat(10.0d);
        fly2.setOriginLng(10.0d);
        fly2.setOriginName("Origin Name");
        fly2.setPrice(BigDecimal.valueOf(1L));
        fly2.setTickets(new HashSet<>());

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

        TourEntity tour2 = new TourEntity();
        tour2.setCustomer(customer4);
        tour2.setId(1L);
        tour2.setReservations(new HashSet<>());
        tour2.setTickets(new HashSet<>());

        TicketEntity ticketEntity2 = new TicketEntity();
        ticketEntity2.setArrivalDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity2.setCustomer(customer3);
        ticketEntity2.setDepartureDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity2.setFly(fly2);
        UUID id = UUID.randomUUID();
        ticketEntity2.setId(id);
        ticketEntity2.setPrice(BigDecimal.valueOf(1L));
        ticketEntity2.setPurchaseDate(LocalDate.of(1970, 1, 1));
        ticketEntity2.setTour(tour2);
        when(ticketRepository.save(Mockito.<TicketEntity>any())).thenReturn(ticketEntity2);
        when(ticketRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult2);
        TicketRequest request = new TicketRequest();
        TicketResponse actualUpdateResult = ticketService.update(request, UUID.randomUUID());
        assertEquals("1970-01-01", actualUpdateResult.getPurchaseDate().toString());
        assertEquals("00:00", actualUpdateResult.getArrivalDate().toLocalTime().toString());
        BigDecimal expectedPrice = price.ONE;
        BigDecimal price2 = actualUpdateResult.getPrice();
        assertSame(expectedPrice, price2);
        assertSame(id, actualUpdateResult.getId());
        assertEquals("1970-01-01", actualUpdateResult.getDepartureDate().toLocalDate().toString());
        FlyResponse fly3 = actualUpdateResult.getFly();
        assertEquals("Origin Name", fly3.getOriginName());
        assertEquals(10.0d, fly3.getOriginLng().doubleValue());
        assertEquals(10.0d, fly3.getOriginLat().doubleValue());
        assertEquals(1L, fly3.getId().longValue());
        assertEquals("Destiny Name", fly3.getDestinyName());
        assertEquals(10.0d, fly3.getDestinyLng().doubleValue());
        assertEquals(10.0d, fly3.getDestinyLat().doubleValue());
        assertEquals(AeroLine.aero_gold, fly3.getAeroLine());
        assertEquals("1", price2.toString());
        assertSame(price2, fly3.getPrice());
        verify(flyRepository).findById(Mockito.<Long>any());
        verify(ticketRepository).save(Mockito.<TicketEntity>any());
        verify(ticketRepository).findById(Mockito.<UUID>any());
    }

    /**
     * Method under test: {@link TicketService#update(TicketRequest, UUID)}
     */
    @Test
    void testUpdate2() {
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
        Optional<FlyEntity> ofResult = Optional.of(flyEntity);
        when(flyRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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

        TourEntity tour = new TourEntity();
        tour.setCustomer(customer2);
        tour.setId(1L);
        tour.setReservations(new HashSet<>());
        tour.setTickets(new HashSet<>());

        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setArrivalDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity.setCustomer(customer);
        ticketEntity.setDepartureDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity.setFly(fly);
        ticketEntity.setId(UUID.randomUUID());
        ticketEntity.setPrice(BigDecimal.valueOf(1L));
        ticketEntity.setPurchaseDate(LocalDate.of(1970, 1, 1));
        ticketEntity.setTour(tour);
        Optional<TicketEntity> ofResult2 = Optional.of(ticketEntity);
        when(ticketRepository.save(Mockito.<TicketEntity>any()))
                .thenThrow(new IdNotFoundException("Ticket updated with id {}"));
        when(ticketRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult2);
        TicketRequest request = new TicketRequest();
        assertThrows(IdNotFoundException.class, () -> ticketService.update(request, UUID.randomUUID()));
        verify(flyRepository).findById(Mockito.<Long>any());
        verify(ticketRepository).save(Mockito.<TicketEntity>any());
        verify(ticketRepository).findById(Mockito.<UUID>any());
    }

    /**
     * Method under test: {@link TicketService#update(TicketRequest, UUID)}
     */
    @Test
    void testUpdate3() {
        Optional<FlyEntity> emptyResult = Optional.empty();
        when(flyRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

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

        TourEntity tour = new TourEntity();
        tour.setCustomer(customer2);
        tour.setId(1L);
        tour.setReservations(new HashSet<>());
        tour.setTickets(new HashSet<>());

        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setArrivalDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity.setCustomer(customer);
        ticketEntity.setDepartureDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity.setFly(fly);
        ticketEntity.setId(UUID.randomUUID());
        ticketEntity.setPrice(BigDecimal.valueOf(1L));
        ticketEntity.setPurchaseDate(LocalDate.of(1970, 1, 1));
        ticketEntity.setTour(tour);
        Optional<TicketEntity> ofResult = Optional.of(ticketEntity);
        when(ticketRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        TicketRequest request = new TicketRequest();
        assertThrows(IdNotFoundException.class, () -> ticketService.update(request, UUID.randomUUID()));
        verify(flyRepository).findById(Mockito.<Long>any());
        verify(ticketRepository).findById(Mockito.<UUID>any());
    }

    /**
     * Method under test: {@link TicketService#update(TicketRequest, UUID)}
     */
    @Test
    void testUpdate4() {
        FlyEntity flyEntity = mock(FlyEntity.class);
        when(flyEntity.getPrice()).thenReturn(BigDecimal.valueOf(1L));
        doNothing().when(flyEntity).setAeroLine(Mockito.<AeroLine>any());
        doNothing().when(flyEntity).setDestinyLat(Mockito.<Double>any());
        doNothing().when(flyEntity).setDestinyLng(Mockito.<Double>any());
        doNothing().when(flyEntity).setDestinyName(Mockito.<String>any());
        doNothing().when(flyEntity).setId(Mockito.<Long>any());
        doNothing().when(flyEntity).setOriginLat(Mockito.<Double>any());
        doNothing().when(flyEntity).setOriginLng(Mockito.<Double>any());
        doNothing().when(flyEntity).setOriginName(Mockito.<String>any());
        doNothing().when(flyEntity).setPrice(Mockito.<BigDecimal>any());
        doNothing().when(flyEntity).setTickets(Mockito.<Set<TicketEntity>>any());
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
        Optional<FlyEntity> ofResult = Optional.of(flyEntity);
        when(flyRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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

        TourEntity tour = new TourEntity();
        tour.setCustomer(customer2);
        tour.setId(1L);
        tour.setReservations(new HashSet<>());
        tour.setTickets(new HashSet<>());

        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setArrivalDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity.setCustomer(customer);
        ticketEntity.setDepartureDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity.setFly(fly);
        ticketEntity.setId(UUID.randomUUID());
        BigDecimal price = BigDecimal.valueOf(1L);
        ticketEntity.setPrice(price);
        ticketEntity.setPurchaseDate(LocalDate.of(1970, 1, 1));
        ticketEntity.setTour(tour);
        Optional<TicketEntity> ofResult2 = Optional.of(ticketEntity);

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

        FlyEntity fly2 = new FlyEntity();
        fly2.setAeroLine(AeroLine.aero_gold);
        fly2.setDestinyLat(10.0d);
        fly2.setDestinyLng(10.0d);
        fly2.setDestinyName("Destiny Name");
        fly2.setId(1L);
        fly2.setOriginLat(10.0d);
        fly2.setOriginLng(10.0d);
        fly2.setOriginName("Origin Name");
        fly2.setPrice(BigDecimal.valueOf(1L));
        fly2.setTickets(new HashSet<>());

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

        TourEntity tour2 = new TourEntity();
        tour2.setCustomer(customer4);
        tour2.setId(1L);
        tour2.setReservations(new HashSet<>());
        tour2.setTickets(new HashSet<>());

        FlyEntity flyEntity2 = new FlyEntity();
        flyEntity2.setAeroLine(AeroLine.aero_gold);
        flyEntity2.setDestinyLat(10.0d);
        flyEntity2.setDestinyLng(10.0d);
        flyEntity2.setDestinyName("Destiny Name");
        flyEntity2.setId(1L);
        flyEntity2.setOriginLat(10.0d);
        flyEntity2.setOriginLng(10.0d);
        flyEntity2.setOriginName("Origin Name");
        flyEntity2.setPrice(BigDecimal.valueOf(1L));
        flyEntity2.setTickets(new HashSet<>());
        TicketEntity ticketEntity2 = mock(TicketEntity.class);
        when(ticketEntity2.getFly()).thenReturn(flyEntity2);
        when(ticketEntity2.getPrice()).thenReturn(BigDecimal.valueOf(1L));
        when(ticketEntity2.getPurchaseDate()).thenReturn(LocalDate.of(1970, 1, 1));
        when(ticketEntity2.getArrivalDate()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(ticketEntity2.getDepartureDate()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        UUID randomUUIDResult = UUID.randomUUID();
        when(ticketEntity2.getId()).thenReturn(randomUUIDResult);
        doNothing().when(ticketEntity2).setArrivalDate(Mockito.<LocalDateTime>any());
        doNothing().when(ticketEntity2).setCustomer(Mockito.<CustomerEntity>any());
        doNothing().when(ticketEntity2).setDepartureDate(Mockito.<LocalDateTime>any());
        doNothing().when(ticketEntity2).setFly(Mockito.<FlyEntity>any());
        doNothing().when(ticketEntity2).setId(Mockito.<UUID>any());
        doNothing().when(ticketEntity2).setPrice(Mockito.<BigDecimal>any());
        doNothing().when(ticketEntity2).setPurchaseDate(Mockito.<LocalDate>any());
        doNothing().when(ticketEntity2).setTour(Mockito.<TourEntity>any());
        ticketEntity2.setArrivalDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity2.setCustomer(customer3);
        ticketEntity2.setDepartureDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity2.setFly(fly2);
        ticketEntity2.setId(UUID.randomUUID());
        ticketEntity2.setPrice(BigDecimal.valueOf(1L));
        ticketEntity2.setPurchaseDate(LocalDate.of(1970, 1, 1));
        ticketEntity2.setTour(tour2);
        when(ticketRepository.save(Mockito.<TicketEntity>any())).thenReturn(ticketEntity2);
        when(ticketRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult2);
        TicketRequest request = new TicketRequest();
        TicketResponse actualUpdateResult = ticketService.update(request, UUID.randomUUID());
        assertEquals("1970-01-01", actualUpdateResult.getPurchaseDate().toString());
        assertEquals("00:00", actualUpdateResult.getArrivalDate().toLocalTime().toString());
        BigDecimal expectedPrice = price.ONE;
        BigDecimal price2 = actualUpdateResult.getPrice();
        assertSame(expectedPrice, price2);
        assertSame(randomUUIDResult, actualUpdateResult.getId());
        assertEquals("1970-01-01", actualUpdateResult.getDepartureDate().toLocalDate().toString());
        FlyResponse fly3 = actualUpdateResult.getFly();
        assertEquals("Origin Name", fly3.getOriginName());
        assertEquals(10.0d, fly3.getOriginLng().doubleValue());
        assertEquals(10.0d, fly3.getOriginLat().doubleValue());
        assertEquals(1L, fly3.getId().longValue());
        assertEquals("Destiny Name", fly3.getDestinyName());
        assertEquals(10.0d, fly3.getDestinyLng().doubleValue());
        assertEquals(10.0d, fly3.getDestinyLat().doubleValue());
        assertEquals(AeroLine.aero_gold, fly3.getAeroLine());
        assertEquals("1", price2.toString());
        assertSame(price2, fly3.getPrice());
        verify(flyRepository).findById(Mockito.<Long>any());
        verify(flyEntity, atLeast(1)).getPrice();
        verify(flyEntity).setAeroLine(Mockito.<AeroLine>any());
        verify(flyEntity).setDestinyLat(Mockito.<Double>any());
        verify(flyEntity).setDestinyLng(Mockito.<Double>any());
        verify(flyEntity).setDestinyName(Mockito.<String>any());
        verify(flyEntity).setId(Mockito.<Long>any());
        verify(flyEntity).setOriginLat(Mockito.<Double>any());
        verify(flyEntity).setOriginLng(Mockito.<Double>any());
        verify(flyEntity).setOriginName(Mockito.<String>any());
        verify(flyEntity).setPrice(Mockito.<BigDecimal>any());
        verify(flyEntity).setTickets(Mockito.<Set<TicketEntity>>any());
        verify(ticketRepository).save(Mockito.<TicketEntity>any());
        verify(ticketRepository).findById(Mockito.<UUID>any());
        verify(ticketEntity2).getFly();
        verify(ticketEntity2).getPrice();
        verify(ticketEntity2).getPurchaseDate();
        verify(ticketEntity2).getArrivalDate();
        verify(ticketEntity2).getDepartureDate();
        verify(ticketEntity2, atLeast(1)).getId();
        verify(ticketEntity2).setArrivalDate(Mockito.<LocalDateTime>any());
        verify(ticketEntity2).setCustomer(Mockito.<CustomerEntity>any());
        verify(ticketEntity2).setDepartureDate(Mockito.<LocalDateTime>any());
        verify(ticketEntity2).setFly(Mockito.<FlyEntity>any());
        verify(ticketEntity2).setId(Mockito.<UUID>any());
        verify(ticketEntity2).setPrice(Mockito.<BigDecimal>any());
        verify(ticketEntity2).setPurchaseDate(Mockito.<LocalDate>any());
        verify(ticketEntity2).setTour(Mockito.<TourEntity>any());
    }

    /**
     * Method under test: {@link TicketService#delete(UUID)}
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

        TourEntity tour = new TourEntity();
        tour.setCustomer(customer2);
        tour.setId(1L);
        tour.setReservations(new HashSet<>());
        tour.setTickets(new HashSet<>());

        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setArrivalDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity.setCustomer(customer);
        ticketEntity.setDepartureDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity.setFly(fly);
        ticketEntity.setId(UUID.randomUUID());
        ticketEntity.setPrice(BigDecimal.valueOf(1L));
        ticketEntity.setPurchaseDate(LocalDate.of(1970, 1, 1));
        ticketEntity.setTour(tour);
        Optional<TicketEntity> ofResult = Optional.of(ticketEntity);
        doNothing().when(ticketRepository).delete(Mockito.<TicketEntity>any());
        when(ticketRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        ticketService.delete(UUID.randomUUID());
        verify(ticketRepository).findById(Mockito.<UUID>any());
        verify(ticketRepository).delete(Mockito.<TicketEntity>any());
    }

    /**
     * Method under test: {@link TicketService#delete(UUID)}
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

        TourEntity tour = new TourEntity();
        tour.setCustomer(customer2);
        tour.setId(1L);
        tour.setReservations(new HashSet<>());
        tour.setTickets(new HashSet<>());

        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setArrivalDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity.setCustomer(customer);
        ticketEntity.setDepartureDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity.setFly(fly);
        ticketEntity.setId(UUID.randomUUID());
        ticketEntity.setPrice(BigDecimal.valueOf(1L));
        ticketEntity.setPurchaseDate(LocalDate.of(1970, 1, 1));
        ticketEntity.setTour(tour);
        Optional<TicketEntity> ofResult = Optional.of(ticketEntity);
        doThrow(new IdNotFoundException("Ticket {}")).when(ticketRepository).delete(Mockito.<TicketEntity>any());
        when(ticketRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        assertThrows(IdNotFoundException.class, () -> ticketService.delete(UUID.randomUUID()));
        verify(ticketRepository).findById(Mockito.<UUID>any());
        verify(ticketRepository).delete(Mockito.<TicketEntity>any());
    }

    /**
     * Method under test: {@link TicketService#delete(UUID)}
     */
    @Test
    void testDelete3() {
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

        TourEntity tour = new TourEntity();
        tour.setCustomer(customer2);
        tour.setId(1L);
        tour.setReservations(new HashSet<>());
        tour.setTickets(new HashSet<>());
        TicketEntity ticketEntity = mock(TicketEntity.class);
        when(ticketEntity.getId()).thenReturn(UUID.randomUUID());
        doNothing().when(ticketEntity).setArrivalDate(Mockito.<LocalDateTime>any());
        doNothing().when(ticketEntity).setCustomer(Mockito.<CustomerEntity>any());
        doNothing().when(ticketEntity).setDepartureDate(Mockito.<LocalDateTime>any());
        doNothing().when(ticketEntity).setFly(Mockito.<FlyEntity>any());
        doNothing().when(ticketEntity).setId(Mockito.<UUID>any());
        doNothing().when(ticketEntity).setPrice(Mockito.<BigDecimal>any());
        doNothing().when(ticketEntity).setPurchaseDate(Mockito.<LocalDate>any());
        doNothing().when(ticketEntity).setTour(Mockito.<TourEntity>any());
        ticketEntity.setArrivalDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity.setCustomer(customer);
        ticketEntity.setDepartureDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        ticketEntity.setFly(fly);
        ticketEntity.setId(UUID.randomUUID());
        ticketEntity.setPrice(BigDecimal.valueOf(1L));
        ticketEntity.setPurchaseDate(LocalDate.of(1970, 1, 1));
        ticketEntity.setTour(tour);
        Optional<TicketEntity> ofResult = Optional.of(ticketEntity);
        doNothing().when(ticketRepository).delete(Mockito.<TicketEntity>any());
        when(ticketRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        ticketService.delete(UUID.randomUUID());
        verify(ticketRepository).findById(Mockito.<UUID>any());
        verify(ticketRepository).delete(Mockito.<TicketEntity>any());
        verify(ticketEntity).getId();
        verify(ticketEntity).setArrivalDate(Mockito.<LocalDateTime>any());
        verify(ticketEntity).setCustomer(Mockito.<CustomerEntity>any());
        verify(ticketEntity).setDepartureDate(Mockito.<LocalDateTime>any());
        verify(ticketEntity).setFly(Mockito.<FlyEntity>any());
        verify(ticketEntity).setId(Mockito.<UUID>any());
        verify(ticketEntity).setPrice(Mockito.<BigDecimal>any());
        verify(ticketEntity).setPurchaseDate(Mockito.<LocalDate>any());
        verify(ticketEntity).setTour(Mockito.<TourEntity>any());
    }

    /**
     * Method under test: {@link TicketService#delete(UUID)}
     */
    @Test
    void testDelete4() {
        Optional<TicketEntity> emptyResult = Optional.empty();
        when(ticketRepository.findById(Mockito.<UUID>any())).thenReturn(emptyResult);
        assertThrows(IdNotFoundException.class, () -> ticketService.delete(UUID.randomUUID()));
        verify(ticketRepository).findById(Mockito.<UUID>any());
    }

    /**
     * Method under test: {@link TicketService#findPrice(Long)}
     */
    @Test
    void testFindPrice() {
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
        Optional<FlyEntity> ofResult = Optional.of(flyEntity);
        when(flyRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertEquals("1.25", ticketService.findPrice(1L).toString());
        verify(flyRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link TicketService#findPrice(Long)}
     */
    @Test
    void testFindPrice2() {
        Optional<FlyEntity> emptyResult = Optional.empty();
        when(flyRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        assertThrows(IdNotFoundException.class, () -> ticketService.findPrice(1L));
        verify(flyRepository).findById(Mockito.<Long>any());
    }
}

