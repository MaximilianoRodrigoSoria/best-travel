package ar.com.laboratory.besttravel.infraestructure.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ar.com.laboratory.besttravel.domain.entities.jpa.FlyEntity;
import ar.com.laboratory.besttravel.domain.repositories.jpa.FlyRepository;
import ar.com.laboratory.besttravel.util.enums.AeroLine;
import ar.com.laboratory.besttravel.util.enums.SortType;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {FlyService.class})
@ExtendWith(SpringExtension.class)
class FlyServiceTest {
    @MockBean
    private FlyRepository flyRepository;

    @Autowired
    private FlyService flyService;

    /**
     * Method under test: {@link FlyService#readAll(Integer, Integer, SortType)}
     */
    @Test
    void testReadAll() {
        when(flyRepository.findAll(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        assertTrue(flyService.readAll(1, 3, SortType.LOWER).toList().isEmpty());
        verify(flyRepository).findAll(Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link FlyService#readAll(Integer, Integer, SortType)}
     */
    @Test
    void testReadAll2() {
        FlyEntity flyEntity = new FlyEntity();
        flyEntity.setAeroLine(AeroLine.aero_gold);
        flyEntity.setDestinyLat(10.0d);
        flyEntity.setDestinyLng(10.0d);
        flyEntity.setDestinyName("price");
        flyEntity.setId(1L);
        flyEntity.setOriginLat(10.0d);
        flyEntity.setOriginLng(10.0d);
        flyEntity.setOriginName("price");
        flyEntity.setPrice(BigDecimal.valueOf(1L));
        flyEntity.setTickets(new HashSet<>());

        ArrayList<FlyEntity> content = new ArrayList<>();
        content.add(flyEntity);
        PageImpl<FlyEntity> pageImpl = new PageImpl<>(content);
        when(flyRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);
        assertEquals(1, flyService.readAll(1, 3, SortType.LOWER).toList().size());
        verify(flyRepository).findAll(Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link FlyService#readAll(Integer, Integer, SortType)}
     */
    @Test
    void testReadAll3() {
        FlyEntity flyEntity = new FlyEntity();
        flyEntity.setAeroLine(AeroLine.aero_gold);
        flyEntity.setDestinyLat(10.0d);
        flyEntity.setDestinyLng(10.0d);
        flyEntity.setDestinyName("price");
        flyEntity.setId(1L);
        flyEntity.setOriginLat(10.0d);
        flyEntity.setOriginLng(10.0d);
        flyEntity.setOriginName("price");
        flyEntity.setPrice(BigDecimal.valueOf(1L));
        flyEntity.setTickets(new HashSet<>());

        FlyEntity flyEntity2 = new FlyEntity();
        flyEntity2.setAeroLine(AeroLine.blue_sky);
        flyEntity2.setDestinyLat(0.5d);
        flyEntity2.setDestinyLng(0.5d);
        flyEntity2.setDestinyName("Destiny Name");
        flyEntity2.setId(2L);
        flyEntity2.setOriginLat(0.5d);
        flyEntity2.setOriginLng(0.5d);
        flyEntity2.setOriginName("Origin Name");
        flyEntity2.setPrice(BigDecimal.valueOf(1L));
        flyEntity2.setTickets(new HashSet<>());

        ArrayList<FlyEntity> content = new ArrayList<>();
        content.add(flyEntity2);
        content.add(flyEntity);
        PageImpl<FlyEntity> pageImpl = new PageImpl<>(content);
        when(flyRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);
        assertEquals(2, flyService.readAll(1, 3, SortType.LOWER).toList().size());
        verify(flyRepository).findAll(Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link FlyService#readAll(Integer, Integer, SortType)}
     */
    @Test
    void testReadAll4() {
        when(flyRepository.findAll(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        assertTrue(flyService.readAll(1, 3, SortType.UPPER).toList().isEmpty());
        verify(flyRepository).findAll(Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link FlyService#readAll(Integer, Integer, SortType)}
     */
    @Test
    void testReadAll5() {
        when(flyRepository.findAll(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        assertTrue(flyService.readAll(1, 3, SortType.NONE).toList().isEmpty());
        verify(flyRepository).findAll(Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link FlyService#readLessPrice(BigDecimal)}
     */
    @Test
    void testReadLessPrice() {
        when(flyRepository.selectLessPrice(Mockito.<BigDecimal>any())).thenReturn(new HashSet<>());
        assertTrue(flyService.readLessPrice(BigDecimal.valueOf(1L)).isEmpty());
        verify(flyRepository).selectLessPrice(Mockito.<BigDecimal>any());
    }

    /**
     * Method under test: {@link FlyService#readLessPrice(BigDecimal)}
     */
    @Test
    void testReadLessPrice2() {
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

        HashSet<FlyEntity> flyEntitySet = new HashSet<>();
        flyEntitySet.add(flyEntity);
        when(flyRepository.selectLessPrice(Mockito.<BigDecimal>any())).thenReturn(flyEntitySet);
        assertEquals(1, flyService.readLessPrice(BigDecimal.valueOf(1L)).size());
        verify(flyRepository).selectLessPrice(Mockito.<BigDecimal>any());
    }

    /**
     * Method under test: {@link FlyService#readLessPrice(BigDecimal)}
     */
    @Test
    void testReadLessPrice3() {
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

        FlyEntity flyEntity2 = new FlyEntity();
        flyEntity2.setAeroLine(AeroLine.blue_sky);
        flyEntity2.setDestinyLat(0.5d);
        flyEntity2.setDestinyLng(0.5d);
        flyEntity2.setDestinyName("ar.com.laboratory.besttravel.domain.entities.jpa.FlyEntity");
        flyEntity2.setId(2L);
        flyEntity2.setOriginLat(0.5d);
        flyEntity2.setOriginLng(0.5d);
        flyEntity2.setOriginName("ar.com.laboratory.besttravel.domain.entities.jpa.FlyEntity");
        flyEntity2.setPrice(BigDecimal.valueOf(59L));
        flyEntity2.setTickets(new HashSet<>());

        HashSet<FlyEntity> flyEntitySet = new HashSet<>();
        flyEntitySet.add(flyEntity2);
        flyEntitySet.add(flyEntity);
        when(flyRepository.selectLessPrice(Mockito.<BigDecimal>any())).thenReturn(flyEntitySet);
        assertEquals(2, flyService.readLessPrice(BigDecimal.valueOf(1L)).size());
        verify(flyRepository).selectLessPrice(Mockito.<BigDecimal>any());
    }

    /**
     * Method under test: {@link FlyService#readBetweenPrices(BigDecimal, BigDecimal)}
     */
    @Test
    void testReadBetweenPrices() {
        when(flyRepository.selectBetWeenPrice(Mockito.<BigDecimal>any(), Mockito.<BigDecimal>any()))
                .thenReturn(new HashSet<>());
        BigDecimal min = BigDecimal.valueOf(1L);
        assertTrue(flyService.readBetweenPrices(min, BigDecimal.valueOf(1L)).isEmpty());
        verify(flyRepository).selectBetWeenPrice(Mockito.<BigDecimal>any(), Mockito.<BigDecimal>any());
    }

    /**
     * Method under test: {@link FlyService#readBetweenPrices(BigDecimal, BigDecimal)}
     */
    @Test
    void testReadBetweenPrices2() {
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

        HashSet<FlyEntity> flyEntitySet = new HashSet<>();
        flyEntitySet.add(flyEntity);
        when(flyRepository.selectBetWeenPrice(Mockito.<BigDecimal>any(), Mockito.<BigDecimal>any()))
                .thenReturn(flyEntitySet);
        BigDecimal min = BigDecimal.valueOf(1L);
        assertEquals(1, flyService.readBetweenPrices(min, BigDecimal.valueOf(1L)).size());
        verify(flyRepository).selectBetWeenPrice(Mockito.<BigDecimal>any(), Mockito.<BigDecimal>any());
    }

    /**
     * Method under test: {@link FlyService#readBetweenPrices(BigDecimal, BigDecimal)}
     */
    @Test
    void testReadBetweenPrices3() {
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

        FlyEntity flyEntity2 = new FlyEntity();
        flyEntity2.setAeroLine(AeroLine.blue_sky);
        flyEntity2.setDestinyLat(0.5d);
        flyEntity2.setDestinyLng(0.5d);
        flyEntity2.setDestinyName("ar.com.laboratory.besttravel.domain.entities.jpa.FlyEntity");
        flyEntity2.setId(2L);
        flyEntity2.setOriginLat(0.5d);
        flyEntity2.setOriginLng(0.5d);
        flyEntity2.setOriginName("ar.com.laboratory.besttravel.domain.entities.jpa.FlyEntity");
        flyEntity2.setPrice(BigDecimal.valueOf(59L));
        flyEntity2.setTickets(new HashSet<>());

        HashSet<FlyEntity> flyEntitySet = new HashSet<>();
        flyEntitySet.add(flyEntity2);
        flyEntitySet.add(flyEntity);
        when(flyRepository.selectBetWeenPrice(Mockito.<BigDecimal>any(), Mockito.<BigDecimal>any()))
                .thenReturn(flyEntitySet);
        BigDecimal min = BigDecimal.valueOf(1L);
        assertEquals(2, flyService.readBetweenPrices(min, BigDecimal.valueOf(1L)).size());
        verify(flyRepository).selectBetWeenPrice(Mockito.<BigDecimal>any(), Mockito.<BigDecimal>any());
    }

    /**
     * Method under test: {@link FlyService#readByOriginDestiny(String, String)}
     */
    @Test
    void testReadByOriginDestiny() {
        when(flyRepository.selectOriginDestiny(Mockito.<String>any(), Mockito.<String>any())).thenReturn(new HashSet<>());
        assertTrue(flyService.readByOriginDestiny("Origin", "Destiny").isEmpty());
        verify(flyRepository).selectOriginDestiny(Mockito.<String>any(), Mockito.<String>any());
    }

    /**
     * Method under test: {@link FlyService#readByOriginDestiny(String, String)}
     */
    @Test
    void testReadByOriginDestiny2() {
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

        HashSet<FlyEntity> flyEntitySet = new HashSet<>();
        flyEntitySet.add(flyEntity);
        when(flyRepository.selectOriginDestiny(Mockito.<String>any(), Mockito.<String>any())).thenReturn(flyEntitySet);
        assertEquals(1, flyService.readByOriginDestiny("Origin", "Destiny").size());
        verify(flyRepository).selectOriginDestiny(Mockito.<String>any(), Mockito.<String>any());
    }

    /**
     * Method under test: {@link FlyService#readByOriginDestiny(String, String)}
     */
    @Test
    void testReadByOriginDestiny3() {
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

        FlyEntity flyEntity2 = new FlyEntity();
        flyEntity2.setAeroLine(AeroLine.blue_sky);
        flyEntity2.setDestinyLat(0.5d);
        flyEntity2.setDestinyLng(0.5d);
        flyEntity2.setDestinyName("ar.com.laboratory.besttravel.domain.entities.jpa.FlyEntity");
        flyEntity2.setId(2L);
        flyEntity2.setOriginLat(0.5d);
        flyEntity2.setOriginLng(0.5d);
        flyEntity2.setOriginName("ar.com.laboratory.besttravel.domain.entities.jpa.FlyEntity");
        flyEntity2.setPrice(BigDecimal.valueOf(59L));
        flyEntity2.setTickets(new HashSet<>());

        HashSet<FlyEntity> flyEntitySet = new HashSet<>();
        flyEntitySet.add(flyEntity2);
        flyEntitySet.add(flyEntity);
        when(flyRepository.selectOriginDestiny(Mockito.<String>any(), Mockito.<String>any())).thenReturn(flyEntitySet);
        assertEquals(2, flyService.readByOriginDestiny("Origin", "Destiny").size());
        verify(flyRepository).selectOriginDestiny(Mockito.<String>any(), Mockito.<String>any());
    }
}

