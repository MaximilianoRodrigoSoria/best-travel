package ar.com.laboratory.besttravel.infraestructure.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ar.com.laboratory.besttravel.api.models.responses.HotelResponse;
import ar.com.laboratory.besttravel.domain.entities.jpa.HotelEntity;
import ar.com.laboratory.besttravel.domain.repositories.jpa.HotelRepository;
import ar.com.laboratory.besttravel.util.enums.SortType;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {HotelService.class})
@ExtendWith(SpringExtension.class)
class HotelServiceTest {
    @MockBean
    private HotelRepository hotelRepository;

    @Autowired
    private HotelService hotelService;

    /**
     * Method under test: {@link HotelService#readAll(Integer, Integer, SortType)}
     */
    @Test
    void testReadAll() {
        when(hotelRepository.findAll(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        assertTrue(hotelService.readAll(1, 3, SortType.LOWER).toList().isEmpty());
        verify(hotelRepository).findAll(Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link HotelService#readAll(Integer, Integer, SortType)}
     */
    @Test
    void testReadAll2() {
        HotelEntity hotelEntity = new HotelEntity();
        hotelEntity.setAddress("42 Main St");
        hotelEntity.setId(1L);
        hotelEntity.setName("price");
        BigDecimal price = BigDecimal.valueOf(1L);
        hotelEntity.setPrice(price);
        hotelEntity.setRating(1);
        hotelEntity.setReservation(new HashSet<>());

        ArrayList<HotelEntity> content = new ArrayList<>();
        content.add(hotelEntity);
        PageImpl<HotelEntity> pageImpl = new PageImpl<>(content);
        when(hotelRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);
        List<HotelResponse> toListResult = hotelService.readAll(1, 3, SortType.LOWER).toList();
        assertEquals(1, toListResult.size());
        HotelResponse getResult = toListResult.get(0);
        assertEquals("42 Main St", getResult.getAddress());
        assertEquals(1, getResult.getRating().intValue());
        BigDecimal expectedPrice = price.ONE;
        BigDecimal price2 = getResult.getPrice();
        assertSame(expectedPrice, price2);
        assertEquals("price", getResult.getName());
        assertEquals(1L, getResult.getId().longValue());
        assertEquals("1", price2.toString());
        verify(hotelRepository).findAll(Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link HotelService#readAll(Integer, Integer, SortType)}
     */
    @Test
    void testReadAll3() {
        HotelEntity hotelEntity = new HotelEntity();
        hotelEntity.setAddress("42 Main St");
        hotelEntity.setId(1L);
        hotelEntity.setName("price");
        BigDecimal price = BigDecimal.valueOf(1L);
        hotelEntity.setPrice(price);
        hotelEntity.setRating(1);
        hotelEntity.setReservation(new HashSet<>());

        HotelEntity hotelEntity2 = new HotelEntity();
        hotelEntity2.setAddress("17 High St");
        hotelEntity2.setId(2L);
        hotelEntity2.setName("Name");
        hotelEntity2.setPrice(BigDecimal.valueOf(1L));
        hotelEntity2.setRating(2);
        hotelEntity2.setReservation(new HashSet<>());

        ArrayList<HotelEntity> content = new ArrayList<>();
        content.add(hotelEntity2);
        content.add(hotelEntity);
        PageImpl<HotelEntity> pageImpl = new PageImpl<>(content);
        when(hotelRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);
        List<HotelResponse> toListResult = hotelService.readAll(1, 3, SortType.LOWER).toList();
        assertEquals(2, toListResult.size());
        HotelResponse getResult = toListResult.get(0);
        assertEquals(2, getResult.getRating().intValue());
        HotelResponse getResult2 = toListResult.get(1);
        assertEquals(1, getResult2.getRating().intValue());
        BigDecimal expectedPrice = price.ONE;
        BigDecimal price2 = getResult2.getPrice();
        assertSame(expectedPrice, price2);
        assertEquals("price", getResult2.getName());
        assertEquals("17 High St", getResult.getAddress());
        assertEquals("Name", getResult.getName());
        assertEquals(1L, getResult2.getId().longValue());
        assertEquals(2L, getResult.getId().longValue());
        assertEquals("42 Main St", getResult2.getAddress());
        assertEquals("1", getResult.getPrice().toString());
        assertEquals("1", price2.toString());
        verify(hotelRepository).findAll(Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link HotelService#readAll(Integer, Integer, SortType)}
     */
    @Test
    void testReadAll4() {
        when(hotelRepository.findAll(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        assertTrue(hotelService.readAll(1, 3, SortType.UPPER).toList().isEmpty());
        verify(hotelRepository).findAll(Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link HotelService#readAll(Integer, Integer, SortType)}
     */
    @Test
    void testReadAll5() {
        when(hotelRepository.findAll(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        assertTrue(hotelService.readAll(1, 3, SortType.NONE).toList().isEmpty());
        verify(hotelRepository).findAll(Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link HotelService#readLessPrice(BigDecimal)}
     */
    @Test
    void testReadLessPrice() {
        when(hotelRepository.findByPriceLessThan(Mockito.<BigDecimal>any())).thenReturn(new HashSet<>());
        assertTrue(hotelService.readLessPrice(BigDecimal.valueOf(1L)).isEmpty());
        verify(hotelRepository).findByPriceLessThan(Mockito.<BigDecimal>any());
    }

    /**
     * Method under test: {@link HotelService#readLessPrice(BigDecimal)}
     */
    @Test
    void testReadLessPrice2() {
        HotelEntity hotelEntity = new HotelEntity();
        hotelEntity.setAddress("42 Main St");
        hotelEntity.setId(1L);
        hotelEntity.setName("Name");
        hotelEntity.setPrice(BigDecimal.valueOf(1L));
        hotelEntity.setRating(1);
        hotelEntity.setReservation(new HashSet<>());

        HashSet<HotelEntity> hotelEntitySet = new HashSet<>();
        hotelEntitySet.add(hotelEntity);
        when(hotelRepository.findByPriceLessThan(Mockito.<BigDecimal>any())).thenReturn(hotelEntitySet);
        assertEquals(1, hotelService.readLessPrice(BigDecimal.valueOf(1L)).size());
        verify(hotelRepository).findByPriceLessThan(Mockito.<BigDecimal>any());
    }

    /**
     * Method under test: {@link HotelService#readLessPrice(BigDecimal)}
     */
    @Test
    void testReadLessPrice3() {
        HotelEntity hotelEntity = new HotelEntity();
        hotelEntity.setAddress("42 Main St");
        hotelEntity.setId(1L);
        hotelEntity.setName("Name");
        hotelEntity.setPrice(BigDecimal.valueOf(1L));
        hotelEntity.setRating(1);
        hotelEntity.setReservation(new HashSet<>());

        HotelEntity hotelEntity2 = new HotelEntity();
        hotelEntity2.setAddress("17 High St");
        hotelEntity2.setId(2L);
        hotelEntity2.setName("ar.com.laboratory.besttravel.domain.entities.jpa.HotelEntity");
        hotelEntity2.setPrice(BigDecimal.valueOf(59L));
        hotelEntity2.setRating(1);
        hotelEntity2.setReservation(new HashSet<>());

        HashSet<HotelEntity> hotelEntitySet = new HashSet<>();
        hotelEntitySet.add(hotelEntity2);
        hotelEntitySet.add(hotelEntity);
        when(hotelRepository.findByPriceLessThan(Mockito.<BigDecimal>any())).thenReturn(hotelEntitySet);
        assertEquals(2, hotelService.readLessPrice(BigDecimal.valueOf(1L)).size());
        verify(hotelRepository).findByPriceLessThan(Mockito.<BigDecimal>any());
    }

    /**
     * Method under test: {@link HotelService#readBetweenPrices(BigDecimal, BigDecimal)}
     */
    @Test
    void testReadBetweenPrices() {
        when(hotelRepository.findByPriceBetween(Mockito.<BigDecimal>any(), Mockito.<BigDecimal>any()))
                .thenReturn(new HashSet<>());
        BigDecimal min = BigDecimal.valueOf(1L);
        assertTrue(hotelService.readBetweenPrices(min, BigDecimal.valueOf(1L)).isEmpty());
        verify(hotelRepository).findByPriceBetween(Mockito.<BigDecimal>any(), Mockito.<BigDecimal>any());
    }

    /**
     * Method under test: {@link HotelService#readBetweenPrices(BigDecimal, BigDecimal)}
     */
    @Test
    void testReadBetweenPrices2() {
        HotelEntity hotelEntity = new HotelEntity();
        hotelEntity.setAddress("42 Main St");
        hotelEntity.setId(1L);
        hotelEntity.setName("Name");
        hotelEntity.setPrice(BigDecimal.valueOf(1L));
        hotelEntity.setRating(1);
        hotelEntity.setReservation(new HashSet<>());

        HashSet<HotelEntity> hotelEntitySet = new HashSet<>();
        hotelEntitySet.add(hotelEntity);
        when(hotelRepository.findByPriceBetween(Mockito.<BigDecimal>any(), Mockito.<BigDecimal>any()))
                .thenReturn(hotelEntitySet);
        BigDecimal min = BigDecimal.valueOf(1L);
        assertEquals(1, hotelService.readBetweenPrices(min, BigDecimal.valueOf(1L)).size());
        verify(hotelRepository).findByPriceBetween(Mockito.<BigDecimal>any(), Mockito.<BigDecimal>any());
    }

    /**
     * Method under test: {@link HotelService#readBetweenPrices(BigDecimal, BigDecimal)}
     */
    @Test
    void testReadBetweenPrices3() {
        HotelEntity hotelEntity = new HotelEntity();
        hotelEntity.setAddress("42 Main St");
        hotelEntity.setId(1L);
        hotelEntity.setName("Name");
        hotelEntity.setPrice(BigDecimal.valueOf(1L));
        hotelEntity.setRating(1);
        hotelEntity.setReservation(new HashSet<>());

        HotelEntity hotelEntity2 = new HotelEntity();
        hotelEntity2.setAddress("17 High St");
        hotelEntity2.setId(2L);
        hotelEntity2.setName("ar.com.laboratory.besttravel.domain.entities.jpa.HotelEntity");
        hotelEntity2.setPrice(BigDecimal.valueOf(59L));
        hotelEntity2.setRating(1);
        hotelEntity2.setReservation(new HashSet<>());

        HashSet<HotelEntity> hotelEntitySet = new HashSet<>();
        hotelEntitySet.add(hotelEntity2);
        hotelEntitySet.add(hotelEntity);
        when(hotelRepository.findByPriceBetween(Mockito.<BigDecimal>any(), Mockito.<BigDecimal>any()))
                .thenReturn(hotelEntitySet);
        BigDecimal min = BigDecimal.valueOf(1L);
        assertEquals(2, hotelService.readBetweenPrices(min, BigDecimal.valueOf(1L)).size());
        verify(hotelRepository).findByPriceBetween(Mockito.<BigDecimal>any(), Mockito.<BigDecimal>any());
    }

    /**
     * Method under test: {@link HotelService#readGreaterThan(Integer)}
     */
    @Test
    void testReadGreaterThan() {
        when(hotelRepository.findByRatingGreaterThan(Mockito.<Integer>any())).thenReturn(new HashSet<>());
        assertTrue(hotelService.readGreaterThan(1).isEmpty());
        verify(hotelRepository).findByRatingGreaterThan(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link HotelService#readGreaterThan(Integer)}
     */
    @Test
    void testReadGreaterThan2() {
        HotelEntity hotelEntity = new HotelEntity();
        hotelEntity.setAddress("42 Main St");
        hotelEntity.setId(1L);
        hotelEntity.setName("Name");
        hotelEntity.setPrice(BigDecimal.valueOf(1L));
        hotelEntity.setRating(1);
        hotelEntity.setReservation(new HashSet<>());

        HashSet<HotelEntity> hotelEntitySet = new HashSet<>();
        hotelEntitySet.add(hotelEntity);
        when(hotelRepository.findByRatingGreaterThan(Mockito.<Integer>any())).thenReturn(hotelEntitySet);
        assertEquals(1, hotelService.readGreaterThan(1).size());
        verify(hotelRepository).findByRatingGreaterThan(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link HotelService#readGreaterThan(Integer)}
     */
    @Test
    void testReadGreaterThan3() {
        HotelEntity hotelEntity = new HotelEntity();
        hotelEntity.setAddress("42 Main St");
        hotelEntity.setId(1L);
        hotelEntity.setName("Name");
        hotelEntity.setPrice(BigDecimal.valueOf(1L));
        hotelEntity.setRating(1);
        hotelEntity.setReservation(new HashSet<>());

        HotelEntity hotelEntity2 = new HotelEntity();
        hotelEntity2.setAddress("17 High St");
        hotelEntity2.setId(2L);
        hotelEntity2.setName("ar.com.laboratory.besttravel.domain.entities.jpa.HotelEntity");
        hotelEntity2.setPrice(BigDecimal.valueOf(59L));
        hotelEntity2.setRating(1);
        hotelEntity2.setReservation(new HashSet<>());

        HashSet<HotelEntity> hotelEntitySet = new HashSet<>();
        hotelEntitySet.add(hotelEntity2);
        hotelEntitySet.add(hotelEntity);
        when(hotelRepository.findByRatingGreaterThan(Mockito.<Integer>any())).thenReturn(hotelEntitySet);
        assertEquals(2, hotelService.readGreaterThan(1).size());
        verify(hotelRepository).findByRatingGreaterThan(Mockito.<Integer>any());
    }
}

