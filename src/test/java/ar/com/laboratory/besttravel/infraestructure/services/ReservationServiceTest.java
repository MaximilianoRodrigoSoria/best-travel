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

import ar.com.laboratory.besttravel.api.models.requests.ReservationRequest;
import ar.com.laboratory.besttravel.api.models.responses.HotelResponse;
import ar.com.laboratory.besttravel.api.models.responses.ReservationResponse;
import ar.com.laboratory.besttravel.domain.entities.jpa.CustomerEntity;
import ar.com.laboratory.besttravel.domain.entities.jpa.HotelEntity;
import ar.com.laboratory.besttravel.domain.entities.jpa.ReservationEntity;
import ar.com.laboratory.besttravel.domain.entities.jpa.TourEntity;
import ar.com.laboratory.besttravel.domain.repositories.jpa.CustomerRepository;
import ar.com.laboratory.besttravel.domain.repositories.jpa.HotelRepository;
import ar.com.laboratory.besttravel.domain.repositories.jpa.ReservationRepository;
import ar.com.laboratory.besttravel.infraestructure.abstract_service.helpers.ApiCurrencyConnectorHelper;
import ar.com.laboratory.besttravel.infraestructure.abstract_service.helpers.BlacklistHelper;
import ar.com.laboratory.besttravel.infraestructure.abstract_service.helpers.CustomerHelper;
import ar.com.laboratory.besttravel.infraestructure.abstract_service.helpers.EmailHelper;
import ar.com.laboratory.besttravel.util.exceptions.IdNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ReservationService.class})
@ExtendWith(SpringExtension.class)
class ReservationServiceTest {
    @MockBean
    private ApiCurrencyConnectorHelper apiCurrencyConnectorHelper;

    @MockBean
    private BlacklistHelper blacklistHelper;

    @MockBean
    private CustomerHelper customerHelper;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private EmailHelper emailHelper;

    @MockBean
    private HotelRepository hotelRepository;

    @MockBean
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationService reservationService;

    /**
     * Method under test: {@link ReservationService#created(ReservationRequest)}
     */
    @Test
    void testCreated() {
        doThrow(new IdNotFoundException("Table Name")).when(blacklistHelper).isInBlacklist(Mockito.<String>any());
        assertThrows(IdNotFoundException.class, () -> reservationService.created(new ReservationRequest()));
        verify(blacklistHelper).isInBlacklist(Mockito.<String>any());
    }

    /**
     * Method under test: {@link ReservationService#created(ReservationRequest)}
     */
    @Test
    void testCreated2() {
        Optional<HotelEntity> emptyResult = Optional.empty();
        when(hotelRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

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
        assertThrows(IdNotFoundException.class, () -> reservationService.created(new ReservationRequest()));
        verify(hotelRepository).findById(Mockito.<Long>any());
        verify(blacklistHelper).isInBlacklist(Mockito.<String>any());
    }

    /**
     * Method under test: {@link ReservationService#created(ReservationRequest)}
     */
    @Test
    void testCreated3() {
        HotelEntity hotelEntity = new HotelEntity();
        hotelEntity.setAddress("42 Main St");
        hotelEntity.setId(1L);
        hotelEntity.setName("Name");
        hotelEntity.setPrice(BigDecimal.valueOf(1L));
        hotelEntity.setRating(1);
        hotelEntity.setReservation(new HashSet<>());
        Optional<HotelEntity> ofResult = Optional.of(hotelEntity);
        when(hotelRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        Optional<CustomerEntity> emptyResult = Optional.empty();
        when(customerRepository.findById(Mockito.<String>any())).thenReturn(emptyResult);
        doNothing().when(blacklistHelper).isInBlacklist(Mockito.<String>any());
        assertThrows(IdNotFoundException.class, () -> reservationService.created(new ReservationRequest()));
        verify(hotelRepository).findById(Mockito.<Long>any());
        verify(customerRepository).findById(Mockito.<String>any());
        verify(blacklistHelper).isInBlacklist(Mockito.<String>any());
    }

    /**
     * Method under test: {@link ReservationService#created(ReservationRequest)}
     */
    @Test
    void testCreated4() {
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

        HotelEntity hotel = new HotelEntity();
        hotel.setAddress("42 Main St");
        hotel.setId(1L);
        hotel.setName("Name");
        hotel.setPrice(BigDecimal.valueOf(1L));
        hotel.setRating(1);
        hotel.setReservation(new HashSet<>());

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

        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setCustomer(customer);
        reservationEntity.setDateEnd(LocalDate.of(1970, 1, 1));
        reservationEntity.setDateStart(LocalDate.of(1970, 1, 1));
        reservationEntity.setDateTimeReservation(LocalDate.of(1970, 1, 1).atStartOfDay());
        reservationEntity.setHotel(hotel);
        UUID id = UUID.randomUUID();
        reservationEntity.setId(id);
        reservationEntity.setPrice(BigDecimal.valueOf(1L));
        reservationEntity.setTotalDays(1);
        reservationEntity.setTour(tour);
        when(reservationRepository.save(Mockito.<ReservationEntity>any())).thenReturn(reservationEntity);

        HotelEntity hotelEntity = new HotelEntity();
        hotelEntity.setAddress("42 Main St");
        hotelEntity.setId(1L);
        hotelEntity.setName("Name");
        BigDecimal price = BigDecimal.valueOf(1L);
        hotelEntity.setPrice(price);
        hotelEntity.setRating(1);
        hotelEntity.setReservation(new HashSet<>());
        Optional<HotelEntity> ofResult = Optional.of(hotelEntity);
        when(hotelRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        doNothing().when(blacklistHelper).isInBlacklist(Mockito.<String>any());
        doNothing().when(customerHelper).incrase(Mockito.<String>any(), Mockito.<Class<Object>>any());

        ReservationRequest request = new ReservationRequest();
        request.setTotalDays(1);
        ReservationResponse actualCreatedResult = reservationService.created(request);
        assertEquals("1970-01-01", actualCreatedResult.getDateEnd().toString());
        assertEquals(1, actualCreatedResult.getTotalDays().intValue());
        BigDecimal expectedPrice = price.ONE;
        BigDecimal price2 = actualCreatedResult.getPrice();
        assertSame(expectedPrice, price2);
        assertSame(id, actualCreatedResult.getId());
        assertEquals("1970-01-01", actualCreatedResult.getDateStart().toString());
        assertEquals("1970-01-01", actualCreatedResult.getDateTimeReservation().toLocalDate().toString());
        HotelResponse hotel2 = actualCreatedResult.getHotel();
        assertSame(price2, hotel2.getPrice());
        assertEquals("Name", hotel2.getName());
        assertEquals(1L, hotel2.getId().longValue());
        assertEquals("42 Main St", hotel2.getAddress());
        assertEquals(1, hotel2.getRating().intValue());
        assertEquals("1", price2.toString());
        verify(reservationRepository).save(Mockito.<ReservationEntity>any());
        verify(hotelRepository).findById(Mockito.<Long>any());
        verify(customerRepository).findById(Mockito.<String>any());
        verify(blacklistHelper).isInBlacklist(Mockito.<String>any());
        verify(customerHelper).incrase(Mockito.<String>any(), Mockito.<Class<Object>>any());
    }

    /**
     * Method under test: {@link ReservationService#created(ReservationRequest)}
     */
    @Test
    void testCreated5() {
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

        HotelEntity hotel = new HotelEntity();
        hotel.setAddress("42 Main St");
        hotel.setId(1L);
        hotel.setName("Name");
        hotel.setPrice(BigDecimal.valueOf(1L));
        hotel.setRating(1);
        hotel.setReservation(new HashSet<>());

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

        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setCustomer(customer);
        reservationEntity.setDateEnd(LocalDate.of(1970, 1, 1));
        reservationEntity.setDateStart(LocalDate.of(1970, 1, 1));
        reservationEntity.setDateTimeReservation(LocalDate.of(1970, 1, 1).atStartOfDay());
        reservationEntity.setHotel(hotel);
        reservationEntity.setId(UUID.randomUUID());
        reservationEntity.setPrice(BigDecimal.valueOf(1L));
        reservationEntity.setTotalDays(1);
        reservationEntity.setTour(tour);
        when(reservationRepository.save(Mockito.<ReservationEntity>any())).thenReturn(reservationEntity);

        HotelEntity hotelEntity = new HotelEntity();
        hotelEntity.setAddress("42 Main St");
        hotelEntity.setId(1L);
        hotelEntity.setName("Name");
        hotelEntity.setPrice(BigDecimal.valueOf(1L));
        hotelEntity.setRating(1);
        hotelEntity.setReservation(new HashSet<>());
        Optional<HotelEntity> ofResult = Optional.of(hotelEntity);
        when(hotelRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        doNothing().when(blacklistHelper).isInBlacklist(Mockito.<String>any());
        doThrow(new IdNotFoundException("Table Name")).when(customerHelper)
                .incrase(Mockito.<String>any(), Mockito.<Class<Object>>any());

        ReservationRequest request = new ReservationRequest();
        request.setTotalDays(1);
        assertThrows(IdNotFoundException.class, () -> reservationService.created(request));
        verify(reservationRepository).save(Mockito.<ReservationEntity>any());
        verify(hotelRepository).findById(Mockito.<Long>any());
        verify(customerRepository).findById(Mockito.<String>any());
        verify(blacklistHelper).isInBlacklist(Mockito.<String>any());
        verify(customerHelper).incrase(Mockito.<String>any(), Mockito.<Class<Object>>any());
    }

    /**
     * Method under test: {@link ReservationService#created(ReservationRequest)}
     */
    @Test
    void testCreated6() {
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

        HotelEntity hotel = new HotelEntity();
        hotel.setAddress("42 Main St");
        hotel.setId(1L);
        hotel.setName("Name");
        hotel.setPrice(BigDecimal.valueOf(1L));
        hotel.setRating(1);
        hotel.setReservation(new HashSet<>());

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

        HotelEntity hotelEntity = new HotelEntity();
        hotelEntity.setAddress("42 Main St");
        hotelEntity.setId(1L);
        hotelEntity.setName("Name");
        hotelEntity.setPrice(BigDecimal.valueOf(1L));
        hotelEntity.setRating(1);
        hotelEntity.setReservation(new HashSet<>());
        ReservationEntity reservationEntity = mock(ReservationEntity.class);
        when(reservationEntity.getHotel()).thenReturn(hotelEntity);
        when(reservationEntity.getTotalDays()).thenReturn(1);
        when(reservationEntity.getPrice()).thenReturn(BigDecimal.valueOf(1L));
        when(reservationEntity.getDateEnd()).thenReturn(LocalDate.of(1970, 1, 1));
        when(reservationEntity.getDateStart()).thenReturn(LocalDate.of(1970, 1, 1));
        when(reservationEntity.getDateTimeReservation()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        UUID randomUUIDResult = UUID.randomUUID();
        when(reservationEntity.getId()).thenReturn(randomUUIDResult);
        doNothing().when(reservationEntity).setCustomer(Mockito.<CustomerEntity>any());
        doNothing().when(reservationEntity).setDateEnd(Mockito.<LocalDate>any());
        doNothing().when(reservationEntity).setDateStart(Mockito.<LocalDate>any());
        doNothing().when(reservationEntity).setDateTimeReservation(Mockito.<LocalDateTime>any());
        doNothing().when(reservationEntity).setHotel(Mockito.<HotelEntity>any());
        doNothing().when(reservationEntity).setId(Mockito.<UUID>any());
        doNothing().when(reservationEntity).setPrice(Mockito.<BigDecimal>any());
        doNothing().when(reservationEntity).setTotalDays(Mockito.<Integer>any());
        doNothing().when(reservationEntity).setTour(Mockito.<TourEntity>any());
        reservationEntity.setCustomer(customer);
        reservationEntity.setDateEnd(LocalDate.of(1970, 1, 1));
        reservationEntity.setDateStart(LocalDate.of(1970, 1, 1));
        reservationEntity.setDateTimeReservation(LocalDate.of(1970, 1, 1).atStartOfDay());
        reservationEntity.setHotel(hotel);
        reservationEntity.setId(UUID.randomUUID());
        reservationEntity.setPrice(BigDecimal.valueOf(1L));
        reservationEntity.setTotalDays(1);
        reservationEntity.setTour(tour);
        when(reservationRepository.save(Mockito.<ReservationEntity>any())).thenReturn(reservationEntity);

        HotelEntity hotelEntity2 = new HotelEntity();
        hotelEntity2.setAddress("42 Main St");
        hotelEntity2.setId(1L);
        hotelEntity2.setName("Name");
        BigDecimal price = BigDecimal.valueOf(1L);
        hotelEntity2.setPrice(price);
        hotelEntity2.setRating(1);
        hotelEntity2.setReservation(new HashSet<>());
        Optional<HotelEntity> ofResult = Optional.of(hotelEntity2);
        when(hotelRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        doNothing().when(blacklistHelper).isInBlacklist(Mockito.<String>any());
        doNothing().when(customerHelper).incrase(Mockito.<String>any(), Mockito.<Class<Object>>any());

        ReservationRequest request = new ReservationRequest();
        request.setTotalDays(1);
        ReservationResponse actualCreatedResult = reservationService.created(request);
        assertEquals("1970-01-01", actualCreatedResult.getDateEnd().toString());
        assertEquals(1, actualCreatedResult.getTotalDays().intValue());
        BigDecimal expectedPrice = price.ONE;
        BigDecimal price2 = actualCreatedResult.getPrice();
        assertSame(expectedPrice, price2);
        assertSame(randomUUIDResult, actualCreatedResult.getId());
        assertEquals("1970-01-01", actualCreatedResult.getDateStart().toString());
        assertEquals("1970-01-01", actualCreatedResult.getDateTimeReservation().toLocalDate().toString());
        HotelResponse hotel2 = actualCreatedResult.getHotel();
        assertSame(price2, hotel2.getPrice());
        assertEquals("Name", hotel2.getName());
        assertEquals(1L, hotel2.getId().longValue());
        assertEquals("42 Main St", hotel2.getAddress());
        assertEquals(1, hotel2.getRating().intValue());
        assertEquals("1", price2.toString());
        verify(reservationRepository).save(Mockito.<ReservationEntity>any());
        verify(reservationEntity).getHotel();
        verify(reservationEntity).getTotalDays();
        verify(reservationEntity).getPrice();
        verify(reservationEntity).getDateEnd();
        verify(reservationEntity).getDateStart();
        verify(reservationEntity).getDateTimeReservation();
        verify(reservationEntity).getId();
        verify(reservationEntity).setCustomer(Mockito.<CustomerEntity>any());
        verify(reservationEntity).setDateEnd(Mockito.<LocalDate>any());
        verify(reservationEntity).setDateStart(Mockito.<LocalDate>any());
        verify(reservationEntity).setDateTimeReservation(Mockito.<LocalDateTime>any());
        verify(reservationEntity).setHotel(Mockito.<HotelEntity>any());
        verify(reservationEntity).setId(Mockito.<UUID>any());
        verify(reservationEntity).setPrice(Mockito.<BigDecimal>any());
        verify(reservationEntity).setTotalDays(Mockito.<Integer>any());
        verify(reservationEntity).setTour(Mockito.<TourEntity>any());
        verify(hotelRepository).findById(Mockito.<Long>any());
        verify(customerRepository).findById(Mockito.<String>any());
        verify(blacklistHelper).isInBlacklist(Mockito.<String>any());
        verify(customerHelper).incrase(Mockito.<String>any(), Mockito.<Class<Object>>any());
    }

    /**
     * Method under test: {@link ReservationService#read(UUID)}
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

        HotelEntity hotel = new HotelEntity();
        hotel.setAddress("42 Main St");
        hotel.setId(1L);
        hotel.setName("Name");
        hotel.setPrice(BigDecimal.valueOf(1L));
        hotel.setRating(1);
        hotel.setReservation(new HashSet<>());

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

        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setCustomer(customer);
        reservationEntity.setDateEnd(LocalDate.of(1970, 1, 1));
        reservationEntity.setDateStart(LocalDate.of(1970, 1, 1));
        reservationEntity.setDateTimeReservation(LocalDate.of(1970, 1, 1).atStartOfDay());
        reservationEntity.setHotel(hotel);
        UUID id = UUID.randomUUID();
        reservationEntity.setId(id);
        BigDecimal price = BigDecimal.valueOf(1L);
        reservationEntity.setPrice(price);
        reservationEntity.setTotalDays(1);
        reservationEntity.setTour(tour);
        Optional<ReservationEntity> ofResult = Optional.of(reservationEntity);
        when(reservationRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        ReservationResponse actualReadResult = reservationService.read(UUID.randomUUID());
        assertEquals("1970-01-01", actualReadResult.getDateEnd().toString());
        assertEquals(1, actualReadResult.getTotalDays().intValue());
        BigDecimal expectedPrice = price.ONE;
        BigDecimal price2 = actualReadResult.getPrice();
        assertSame(expectedPrice, price2);
        assertSame(id, actualReadResult.getId());
        assertEquals("1970-01-01", actualReadResult.getDateStart().toString());
        assertEquals("1970-01-01", actualReadResult.getDateTimeReservation().toLocalDate().toString());
        HotelResponse hotel2 = actualReadResult.getHotel();
        assertSame(price2, hotel2.getPrice());
        assertEquals("Name", hotel2.getName());
        assertEquals(1L, hotel2.getId().longValue());
        assertEquals("42 Main St", hotel2.getAddress());
        assertEquals(1, hotel2.getRating().intValue());
        assertEquals("1", price2.toString());
        verify(reservationRepository).findById(Mockito.<UUID>any());
    }

    /**
     * Method under test: {@link ReservationService#read(UUID)}
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

        HotelEntity hotel = new HotelEntity();
        hotel.setAddress("42 Main St");
        hotel.setId(1L);
        hotel.setName("Name");
        hotel.setPrice(BigDecimal.valueOf(1L));
        hotel.setRating(1);
        hotel.setReservation(new HashSet<>());

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

        HotelEntity hotelEntity = new HotelEntity();
        hotelEntity.setAddress("42 Main St");
        hotelEntity.setId(1L);
        hotelEntity.setName("Name");
        hotelEntity.setPrice(BigDecimal.valueOf(1L));
        hotelEntity.setRating(1);
        hotelEntity.setReservation(new HashSet<>());
        ReservationEntity reservationEntity = mock(ReservationEntity.class);
        when(reservationEntity.getHotel()).thenReturn(hotelEntity);
        when(reservationEntity.getTotalDays()).thenReturn(1);
        when(reservationEntity.getPrice()).thenReturn(BigDecimal.valueOf(1L));
        when(reservationEntity.getDateEnd()).thenReturn(LocalDate.of(1970, 1, 1));
        when(reservationEntity.getDateStart()).thenReturn(LocalDate.of(1970, 1, 1));
        when(reservationEntity.getDateTimeReservation()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        UUID randomUUIDResult = UUID.randomUUID();
        when(reservationEntity.getId()).thenReturn(randomUUIDResult);
        doNothing().when(reservationEntity).setCustomer(Mockito.<CustomerEntity>any());
        doNothing().when(reservationEntity).setDateEnd(Mockito.<LocalDate>any());
        doNothing().when(reservationEntity).setDateStart(Mockito.<LocalDate>any());
        doNothing().when(reservationEntity).setDateTimeReservation(Mockito.<LocalDateTime>any());
        doNothing().when(reservationEntity).setHotel(Mockito.<HotelEntity>any());
        doNothing().when(reservationEntity).setId(Mockito.<UUID>any());
        doNothing().when(reservationEntity).setPrice(Mockito.<BigDecimal>any());
        doNothing().when(reservationEntity).setTotalDays(Mockito.<Integer>any());
        doNothing().when(reservationEntity).setTour(Mockito.<TourEntity>any());
        reservationEntity.setCustomer(customer);
        reservationEntity.setDateEnd(LocalDate.of(1970, 1, 1));
        reservationEntity.setDateStart(LocalDate.of(1970, 1, 1));
        reservationEntity.setDateTimeReservation(LocalDate.of(1970, 1, 1).atStartOfDay());
        reservationEntity.setHotel(hotel);
        reservationEntity.setId(UUID.randomUUID());
        BigDecimal price = BigDecimal.valueOf(1L);
        reservationEntity.setPrice(price);
        reservationEntity.setTotalDays(1);
        reservationEntity.setTour(tour);
        Optional<ReservationEntity> ofResult = Optional.of(reservationEntity);
        when(reservationRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        ReservationResponse actualReadResult = reservationService.read(UUID.randomUUID());
        assertEquals("1970-01-01", actualReadResult.getDateEnd().toString());
        assertEquals(1, actualReadResult.getTotalDays().intValue());
        BigDecimal expectedPrice = price.ONE;
        BigDecimal price2 = actualReadResult.getPrice();
        assertSame(expectedPrice, price2);
        assertSame(randomUUIDResult, actualReadResult.getId());
        assertEquals("1970-01-01", actualReadResult.getDateStart().toString());
        assertEquals("1970-01-01", actualReadResult.getDateTimeReservation().toLocalDate().toString());
        HotelResponse hotel2 = actualReadResult.getHotel();
        assertSame(price2, hotel2.getPrice());
        assertEquals("Name", hotel2.getName());
        assertEquals(1L, hotel2.getId().longValue());
        assertEquals("42 Main St", hotel2.getAddress());
        assertEquals(1, hotel2.getRating().intValue());
        assertEquals("1", price2.toString());
        verify(reservationRepository).findById(Mockito.<UUID>any());
        verify(reservationEntity).getHotel();
        verify(reservationEntity).getTotalDays();
        verify(reservationEntity).getPrice();
        verify(reservationEntity).getDateEnd();
        verify(reservationEntity).getDateStart();
        verify(reservationEntity).getDateTimeReservation();
        verify(reservationEntity).getId();
        verify(reservationEntity).setCustomer(Mockito.<CustomerEntity>any());
        verify(reservationEntity).setDateEnd(Mockito.<LocalDate>any());
        verify(reservationEntity).setDateStart(Mockito.<LocalDate>any());
        verify(reservationEntity).setDateTimeReservation(Mockito.<LocalDateTime>any());
        verify(reservationEntity).setHotel(Mockito.<HotelEntity>any());
        verify(reservationEntity).setId(Mockito.<UUID>any());
        verify(reservationEntity).setPrice(Mockito.<BigDecimal>any());
        verify(reservationEntity).setTotalDays(Mockito.<Integer>any());
        verify(reservationEntity).setTour(Mockito.<TourEntity>any());
    }

    /**
     * Method under test: {@link ReservationService#update(ReservationRequest, UUID)}
     */
    @Test
    void testUpdate() {
        when(reservationRepository.findById(Mockito.<UUID>any()))
                .thenThrow(new IdNotFoundException("Record no exist in %s"));

        HotelEntity hotelEntity = new HotelEntity();
        hotelEntity.setAddress("42 Main St");
        hotelEntity.setId(1L);
        hotelEntity.setName("Name");
        hotelEntity.setPrice(BigDecimal.valueOf(1L));
        hotelEntity.setRating(1);
        hotelEntity.setReservation(new HashSet<>());
        Optional<HotelEntity> ofResult = Optional.of(hotelEntity);
        when(hotelRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        ReservationRequest request = new ReservationRequest();
        assertThrows(IdNotFoundException.class, () -> reservationService.update(request, UUID.randomUUID()));
        verify(reservationRepository).findById(Mockito.<UUID>any());
        verify(hotelRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link ReservationService#update(ReservationRequest, UUID)}
     */
    @Test
    void testUpdate2() {
        Optional<ReservationEntity> emptyResult = Optional.empty();
        when(reservationRepository.findById(Mockito.<UUID>any())).thenReturn(emptyResult);

        HotelEntity hotelEntity = new HotelEntity();
        hotelEntity.setAddress("42 Main St");
        hotelEntity.setId(1L);
        hotelEntity.setName("Name");
        hotelEntity.setPrice(BigDecimal.valueOf(1L));
        hotelEntity.setRating(1);
        hotelEntity.setReservation(new HashSet<>());
        Optional<HotelEntity> ofResult = Optional.of(hotelEntity);
        when(hotelRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        ReservationRequest request = new ReservationRequest();
        assertThrows(IdNotFoundException.class, () -> reservationService.update(request, UUID.randomUUID()));
        verify(reservationRepository).findById(Mockito.<UUID>any());
        verify(hotelRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link ReservationService#update(ReservationRequest, UUID)}
     */
    @Test
    void testUpdate3() {
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

        HotelEntity hotel = new HotelEntity();
        hotel.setAddress("42 Main St");
        hotel.setId(1L);
        hotel.setName("Name");
        hotel.setPrice(BigDecimal.valueOf(1L));
        hotel.setRating(1);
        hotel.setReservation(new HashSet<>());

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
        ReservationEntity reservationEntity = mock(ReservationEntity.class);
        doNothing().when(reservationEntity).setCustomer(Mockito.<CustomerEntity>any());
        doNothing().when(reservationEntity).setDateEnd(Mockito.<LocalDate>any());
        doNothing().when(reservationEntity).setDateStart(Mockito.<LocalDate>any());
        doNothing().when(reservationEntity).setDateTimeReservation(Mockito.<LocalDateTime>any());
        doNothing().when(reservationEntity).setHotel(Mockito.<HotelEntity>any());
        doNothing().when(reservationEntity).setId(Mockito.<UUID>any());
        doNothing().when(reservationEntity).setPrice(Mockito.<BigDecimal>any());
        doNothing().when(reservationEntity).setTotalDays(Mockito.<Integer>any());
        doNothing().when(reservationEntity).setTour(Mockito.<TourEntity>any());
        reservationEntity.setCustomer(customer);
        reservationEntity.setDateEnd(LocalDate.of(1970, 1, 1));
        reservationEntity.setDateStart(LocalDate.of(1970, 1, 1));
        reservationEntity.setDateTimeReservation(LocalDate.of(1970, 1, 1).atStartOfDay());
        reservationEntity.setHotel(hotel);
        reservationEntity.setId(UUID.randomUUID());
        reservationEntity.setPrice(BigDecimal.valueOf(1L));
        reservationEntity.setTotalDays(1);
        reservationEntity.setTour(tour);
        Optional<ReservationEntity> ofResult = Optional.of(reservationEntity);
        when(reservationRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        Optional<HotelEntity> emptyResult = Optional.empty();
        when(hotelRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        ReservationRequest request = new ReservationRequest();
        assertThrows(IdNotFoundException.class, () -> reservationService.update(request, UUID.randomUUID()));
        verify(reservationEntity).setCustomer(Mockito.<CustomerEntity>any());
        verify(reservationEntity).setDateEnd(Mockito.<LocalDate>any());
        verify(reservationEntity).setDateStart(Mockito.<LocalDate>any());
        verify(reservationEntity).setDateTimeReservation(Mockito.<LocalDateTime>any());
        verify(reservationEntity).setHotel(Mockito.<HotelEntity>any());
        verify(reservationEntity).setId(Mockito.<UUID>any());
        verify(reservationEntity).setPrice(Mockito.<BigDecimal>any());
        verify(reservationEntity).setTotalDays(Mockito.<Integer>any());
        verify(reservationEntity).setTour(Mockito.<TourEntity>any());
        verify(hotelRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link ReservationService#update(ReservationRequest, UUID)}
     */
    @Test
    void testUpdate4() {
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

        HotelEntity hotel = new HotelEntity();
        hotel.setAddress("42 Main St");
        hotel.setId(1L);
        hotel.setName("Name");
        hotel.setPrice(BigDecimal.valueOf(1L));
        hotel.setRating(1);
        hotel.setReservation(new HashSet<>());

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
        ReservationEntity reservationEntity = mock(ReservationEntity.class);
        doNothing().when(reservationEntity).setCustomer(Mockito.<CustomerEntity>any());
        doNothing().when(reservationEntity).setDateEnd(Mockito.<LocalDate>any());
        doNothing().when(reservationEntity).setDateStart(Mockito.<LocalDate>any());
        doNothing().when(reservationEntity).setDateTimeReservation(Mockito.<LocalDateTime>any());
        doNothing().when(reservationEntity).setHotel(Mockito.<HotelEntity>any());
        doNothing().when(reservationEntity).setId(Mockito.<UUID>any());
        doNothing().when(reservationEntity).setPrice(Mockito.<BigDecimal>any());
        doNothing().when(reservationEntity).setTotalDays(Mockito.<Integer>any());
        doNothing().when(reservationEntity).setTour(Mockito.<TourEntity>any());
        reservationEntity.setCustomer(customer);
        reservationEntity.setDateEnd(LocalDate.of(1970, 1, 1));
        reservationEntity.setDateStart(LocalDate.of(1970, 1, 1));
        reservationEntity.setDateTimeReservation(LocalDate.of(1970, 1, 1).atStartOfDay());
        reservationEntity.setHotel(hotel);
        reservationEntity.setId(UUID.randomUUID());
        reservationEntity.setPrice(BigDecimal.valueOf(1L));
        reservationEntity.setTotalDays(1);
        reservationEntity.setTour(tour);
        Optional<ReservationEntity> ofResult = Optional.of(reservationEntity);

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

        HotelEntity hotel2 = new HotelEntity();
        hotel2.setAddress("42 Main St");
        hotel2.setId(1L);
        hotel2.setName("Name");
        hotel2.setPrice(BigDecimal.valueOf(1L));
        hotel2.setRating(1);
        hotel2.setReservation(new HashSet<>());

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

        ReservationEntity reservationEntity2 = new ReservationEntity();
        reservationEntity2.setCustomer(customer3);
        reservationEntity2.setDateEnd(LocalDate.of(1970, 1, 1));
        reservationEntity2.setDateStart(LocalDate.of(1970, 1, 1));
        reservationEntity2.setDateTimeReservation(LocalDate.of(1970, 1, 1).atStartOfDay());
        reservationEntity2.setHotel(hotel2);
        UUID id = UUID.randomUUID();
        reservationEntity2.setId(id);
        reservationEntity2.setPrice(BigDecimal.valueOf(1L));
        reservationEntity2.setTotalDays(1);
        reservationEntity2.setTour(tour2);
        when(reservationRepository.save(Mockito.<ReservationEntity>any())).thenReturn(reservationEntity2);
        when(reservationRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);

        HotelEntity hotelEntity = new HotelEntity();
        hotelEntity.setAddress("42 Main St");
        hotelEntity.setId(1L);
        hotelEntity.setName("Name");
        BigDecimal price = BigDecimal.valueOf(1L);
        hotelEntity.setPrice(price);
        hotelEntity.setRating(1);
        hotelEntity.setReservation(new HashSet<>());
        Optional<HotelEntity> ofResult2 = Optional.of(hotelEntity);
        when(hotelRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        ReservationRequest request = new ReservationRequest();
        request.setTotalDays(1);
        ReservationResponse actualUpdateResult = reservationService.update(request, UUID.randomUUID());
        assertEquals("1970-01-01", actualUpdateResult.getDateEnd().toString());
        assertEquals(1, actualUpdateResult.getTotalDays().intValue());
        BigDecimal expectedPrice = price.ONE;
        BigDecimal price2 = actualUpdateResult.getPrice();
        assertSame(expectedPrice, price2);
        assertSame(id, actualUpdateResult.getId());
        assertEquals("1970-01-01", actualUpdateResult.getDateStart().toString());
        assertEquals("1970-01-01", actualUpdateResult.getDateTimeReservation().toLocalDate().toString());
        HotelResponse hotel3 = actualUpdateResult.getHotel();
        assertSame(price2, hotel3.getPrice());
        assertEquals("Name", hotel3.getName());
        assertEquals(1L, hotel3.getId().longValue());
        assertEquals("42 Main St", hotel3.getAddress());
        assertEquals(1, hotel3.getRating().intValue());
        assertEquals("1", price2.toString());
        verify(reservationRepository).save(Mockito.<ReservationEntity>any());
        verify(reservationRepository).findById(Mockito.<UUID>any());
        verify(reservationEntity).setCustomer(Mockito.<CustomerEntity>any());
        verify(reservationEntity, atLeast(1)).setDateEnd(Mockito.<LocalDate>any());
        verify(reservationEntity, atLeast(1)).setDateStart(Mockito.<LocalDate>any());
        verify(reservationEntity, atLeast(1)).setDateTimeReservation(Mockito.<LocalDateTime>any());
        verify(reservationEntity, atLeast(1)).setHotel(Mockito.<HotelEntity>any());
        verify(reservationEntity).setId(Mockito.<UUID>any());
        verify(reservationEntity, atLeast(1)).setPrice(Mockito.<BigDecimal>any());
        verify(reservationEntity, atLeast(1)).setTotalDays(Mockito.<Integer>any());
        verify(reservationEntity).setTour(Mockito.<TourEntity>any());
        verify(hotelRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link ReservationService#update(ReservationRequest, UUID)}
     */
    @Test
    void testUpdate5() {
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

        HotelEntity hotel = new HotelEntity();
        hotel.setAddress("42 Main St");
        hotel.setId(1L);
        hotel.setName("Name");
        hotel.setPrice(BigDecimal.valueOf(1L));
        hotel.setRating(1);
        hotel.setReservation(new HashSet<>());

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
        ReservationEntity reservationEntity = mock(ReservationEntity.class);
        doNothing().when(reservationEntity).setCustomer(Mockito.<CustomerEntity>any());
        doNothing().when(reservationEntity).setDateEnd(Mockito.<LocalDate>any());
        doNothing().when(reservationEntity).setDateStart(Mockito.<LocalDate>any());
        doNothing().when(reservationEntity).setDateTimeReservation(Mockito.<LocalDateTime>any());
        doNothing().when(reservationEntity).setHotel(Mockito.<HotelEntity>any());
        doNothing().when(reservationEntity).setId(Mockito.<UUID>any());
        doNothing().when(reservationEntity).setPrice(Mockito.<BigDecimal>any());
        doNothing().when(reservationEntity).setTotalDays(Mockito.<Integer>any());
        doNothing().when(reservationEntity).setTour(Mockito.<TourEntity>any());
        reservationEntity.setCustomer(customer);
        reservationEntity.setDateEnd(LocalDate.of(1970, 1, 1));
        reservationEntity.setDateStart(LocalDate.of(1970, 1, 1));
        reservationEntity.setDateTimeReservation(LocalDate.of(1970, 1, 1).atStartOfDay());
        reservationEntity.setHotel(hotel);
        reservationEntity.setId(UUID.randomUUID());
        reservationEntity.setPrice(BigDecimal.valueOf(1L));
        reservationEntity.setTotalDays(1);
        reservationEntity.setTour(tour);
        Optional<ReservationEntity> ofResult = Optional.of(reservationEntity);

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

        HotelEntity hotel2 = new HotelEntity();
        hotel2.setAddress("42 Main St");
        hotel2.setId(1L);
        hotel2.setName("Name");
        hotel2.setPrice(BigDecimal.valueOf(1L));
        hotel2.setRating(1);
        hotel2.setReservation(new HashSet<>());

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

        HotelEntity hotelEntity = new HotelEntity();
        hotelEntity.setAddress("42 Main St");
        hotelEntity.setId(1L);
        hotelEntity.setName("Name");
        hotelEntity.setPrice(BigDecimal.valueOf(1L));
        hotelEntity.setRating(1);
        hotelEntity.setReservation(new HashSet<>());
        ReservationEntity reservationEntity2 = mock(ReservationEntity.class);
        when(reservationEntity2.getHotel()).thenReturn(hotelEntity);
        when(reservationEntity2.getTotalDays()).thenReturn(1);
        when(reservationEntity2.getPrice()).thenReturn(BigDecimal.valueOf(1L));
        when(reservationEntity2.getDateEnd()).thenReturn(LocalDate.of(1970, 1, 1));
        when(reservationEntity2.getDateStart()).thenReturn(LocalDate.of(1970, 1, 1));
        when(reservationEntity2.getDateTimeReservation()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        UUID randomUUIDResult = UUID.randomUUID();
        when(reservationEntity2.getId()).thenReturn(randomUUIDResult);
        doNothing().when(reservationEntity2).setCustomer(Mockito.<CustomerEntity>any());
        doNothing().when(reservationEntity2).setDateEnd(Mockito.<LocalDate>any());
        doNothing().when(reservationEntity2).setDateStart(Mockito.<LocalDate>any());
        doNothing().when(reservationEntity2).setDateTimeReservation(Mockito.<LocalDateTime>any());
        doNothing().when(reservationEntity2).setHotel(Mockito.<HotelEntity>any());
        doNothing().when(reservationEntity2).setId(Mockito.<UUID>any());
        doNothing().when(reservationEntity2).setPrice(Mockito.<BigDecimal>any());
        doNothing().when(reservationEntity2).setTotalDays(Mockito.<Integer>any());
        doNothing().when(reservationEntity2).setTour(Mockito.<TourEntity>any());
        reservationEntity2.setCustomer(customer3);
        reservationEntity2.setDateEnd(LocalDate.of(1970, 1, 1));
        reservationEntity2.setDateStart(LocalDate.of(1970, 1, 1));
        reservationEntity2.setDateTimeReservation(LocalDate.of(1970, 1, 1).atStartOfDay());
        reservationEntity2.setHotel(hotel2);
        reservationEntity2.setId(UUID.randomUUID());
        reservationEntity2.setPrice(BigDecimal.valueOf(1L));
        reservationEntity2.setTotalDays(1);
        reservationEntity2.setTour(tour2);
        when(reservationRepository.save(Mockito.<ReservationEntity>any())).thenReturn(reservationEntity2);
        when(reservationRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);

        HotelEntity hotelEntity2 = new HotelEntity();
        hotelEntity2.setAddress("42 Main St");
        hotelEntity2.setId(1L);
        hotelEntity2.setName("Name");
        BigDecimal price = BigDecimal.valueOf(1L);
        hotelEntity2.setPrice(price);
        hotelEntity2.setRating(1);
        hotelEntity2.setReservation(new HashSet<>());
        Optional<HotelEntity> ofResult2 = Optional.of(hotelEntity2);
        when(hotelRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        ReservationRequest request = new ReservationRequest();
        request.setTotalDays(1);
        ReservationResponse actualUpdateResult = reservationService.update(request, UUID.randomUUID());
        assertEquals("1970-01-01", actualUpdateResult.getDateEnd().toString());
        assertEquals(1, actualUpdateResult.getTotalDays().intValue());
        BigDecimal expectedPrice = price.ONE;
        BigDecimal price2 = actualUpdateResult.getPrice();
        assertSame(expectedPrice, price2);
        assertSame(randomUUIDResult, actualUpdateResult.getId());
        assertEquals("1970-01-01", actualUpdateResult.getDateStart().toString());
        assertEquals("1970-01-01", actualUpdateResult.getDateTimeReservation().toLocalDate().toString());
        HotelResponse hotel3 = actualUpdateResult.getHotel();
        assertSame(price2, hotel3.getPrice());
        assertEquals("Name", hotel3.getName());
        assertEquals(1L, hotel3.getId().longValue());
        assertEquals("42 Main St", hotel3.getAddress());
        assertEquals(1, hotel3.getRating().intValue());
        assertEquals("1", price2.toString());
        verify(reservationRepository).save(Mockito.<ReservationEntity>any());
        verify(reservationRepository).findById(Mockito.<UUID>any());
        verify(reservationEntity2).getHotel();
        verify(reservationEntity2).getTotalDays();
        verify(reservationEntity2).getPrice();
        verify(reservationEntity2).getDateEnd();
        verify(reservationEntity2).getDateStart();
        verify(reservationEntity2).getDateTimeReservation();
        verify(reservationEntity2, atLeast(1)).getId();
        verify(reservationEntity2).setCustomer(Mockito.<CustomerEntity>any());
        verify(reservationEntity2).setDateEnd(Mockito.<LocalDate>any());
        verify(reservationEntity2).setDateStart(Mockito.<LocalDate>any());
        verify(reservationEntity2).setDateTimeReservation(Mockito.<LocalDateTime>any());
        verify(reservationEntity2).setHotel(Mockito.<HotelEntity>any());
        verify(reservationEntity2).setId(Mockito.<UUID>any());
        verify(reservationEntity2).setPrice(Mockito.<BigDecimal>any());
        verify(reservationEntity2).setTotalDays(Mockito.<Integer>any());
        verify(reservationEntity2).setTour(Mockito.<TourEntity>any());
        verify(reservationEntity).setCustomer(Mockito.<CustomerEntity>any());
        verify(reservationEntity, atLeast(1)).setDateEnd(Mockito.<LocalDate>any());
        verify(reservationEntity, atLeast(1)).setDateStart(Mockito.<LocalDate>any());
        verify(reservationEntity, atLeast(1)).setDateTimeReservation(Mockito.<LocalDateTime>any());
        verify(reservationEntity, atLeast(1)).setHotel(Mockito.<HotelEntity>any());
        verify(reservationEntity).setId(Mockito.<UUID>any());
        verify(reservationEntity, atLeast(1)).setPrice(Mockito.<BigDecimal>any());
        verify(reservationEntity, atLeast(1)).setTotalDays(Mockito.<Integer>any());
        verify(reservationEntity).setTour(Mockito.<TourEntity>any());
        verify(hotelRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link ReservationService#delete(UUID)}
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

        HotelEntity hotel = new HotelEntity();
        hotel.setAddress("42 Main St");
        hotel.setId(1L);
        hotel.setName("Name");
        hotel.setPrice(BigDecimal.valueOf(1L));
        hotel.setRating(1);
        hotel.setReservation(new HashSet<>());

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

        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setCustomer(customer);
        reservationEntity.setDateEnd(LocalDate.of(1970, 1, 1));
        reservationEntity.setDateStart(LocalDate.of(1970, 1, 1));
        reservationEntity.setDateTimeReservation(LocalDate.of(1970, 1, 1).atStartOfDay());
        reservationEntity.setHotel(hotel);
        reservationEntity.setId(UUID.randomUUID());
        reservationEntity.setPrice(BigDecimal.valueOf(1L));
        reservationEntity.setTotalDays(1);
        reservationEntity.setTour(tour);
        Optional<ReservationEntity> ofResult = Optional.of(reservationEntity);
        doNothing().when(reservationRepository).delete(Mockito.<ReservationEntity>any());
        when(reservationRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        reservationService.delete(UUID.randomUUID());
        verify(reservationRepository).findById(Mockito.<UUID>any());
        verify(reservationRepository).delete(Mockito.<ReservationEntity>any());
    }

    /**
     * Method under test: {@link ReservationService#delete(UUID)}
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

        HotelEntity hotel = new HotelEntity();
        hotel.setAddress("42 Main St");
        hotel.setId(1L);
        hotel.setName("Name");
        hotel.setPrice(BigDecimal.valueOf(1L));
        hotel.setRating(1);
        hotel.setReservation(new HashSet<>());

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

        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setCustomer(customer);
        reservationEntity.setDateEnd(LocalDate.of(1970, 1, 1));
        reservationEntity.setDateStart(LocalDate.of(1970, 1, 1));
        reservationEntity.setDateTimeReservation(LocalDate.of(1970, 1, 1).atStartOfDay());
        reservationEntity.setHotel(hotel);
        reservationEntity.setId(UUID.randomUUID());
        reservationEntity.setPrice(BigDecimal.valueOf(1L));
        reservationEntity.setTotalDays(1);
        reservationEntity.setTour(tour);
        Optional<ReservationEntity> ofResult = Optional.of(reservationEntity);
        doThrow(new IdNotFoundException("Table Name")).when(reservationRepository)
                .delete(Mockito.<ReservationEntity>any());
        when(reservationRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        assertThrows(IdNotFoundException.class, () -> reservationService.delete(UUID.randomUUID()));
        verify(reservationRepository).findById(Mockito.<UUID>any());
        verify(reservationRepository).delete(Mockito.<ReservationEntity>any());
    }

    /**
     * Method under test: {@link ReservationService#delete(UUID)}
     */
    @Test
    void testDelete3() {
        Optional<ReservationEntity> emptyResult = Optional.empty();
        when(reservationRepository.findById(Mockito.<UUID>any())).thenReturn(emptyResult);
        assertThrows(IdNotFoundException.class, () -> reservationService.delete(UUID.randomUUID()));
        verify(reservationRepository).findById(Mockito.<UUID>any());
    }

    /**
     * Method under test: {@link ReservationService#findPrice(Long, Currency)}
     */
    @Test
    void testFindPrice() {
        Optional<HotelEntity> emptyResult = Optional.empty();
        when(hotelRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        assertThrows(IdNotFoundException.class, () -> reservationService.findPrice(1L, null));
        verify(hotelRepository).findById(Mockito.<Long>any());
    }
}

