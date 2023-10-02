package ar.com.laboratory.besttravel.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ar.com.laboratory.besttravel.api.models.responses.FlyResponse;
import ar.com.laboratory.besttravel.domain.entities.jpa.FlyEntity;
import ar.com.laboratory.besttravel.domain.repositories.jpa.FlyRepository;
import ar.com.laboratory.besttravel.infraestructure.abstract_service.IFlyService;
import ar.com.laboratory.besttravel.infraestructure.services.FlyService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {FlyController.class})
@ExtendWith(SpringExtension.class)
class  FlyControllerTest {
    @Autowired
    private FlyController flyController;

    @MockBean
    private IFlyService iFlyService;

    /**
     * Method under test: {@link FlyController#getAll(Integer, Integer, SortType)}
     */
    @Test
    void testGetAll() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        FlyRepository flyRepository = mock(FlyRepository.class);
        when(flyRepository.findAll(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        ResponseEntity<Page<FlyResponse>> actualAll = (new FlyController(new FlyService(flyRepository))).getAll(1, 3,
                SortType.LOWER);
        assertNull(actualAll.getBody());
        assertEquals(204, actualAll.getStatusCodeValue());
        assertTrue(actualAll.getHeaders().isEmpty());
        verify(flyRepository).findAll(Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link FlyController#getAll(Integer, Integer, SortType)}
     */
    @Test
    void testGetAll2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

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
        FlyRepository flyRepository = mock(FlyRepository.class);
        when(flyRepository.findAll(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(content));
        ResponseEntity<Page<FlyResponse>> actualAll = (new FlyController(new FlyService(flyRepository))).getAll(1, 3,
                SortType.LOWER);
        assertTrue(actualAll.hasBody());
        assertEquals(200, actualAll.getStatusCodeValue());
        assertEquals(1, actualAll.getBody().toList().size());
        assertTrue(actualAll.getHeaders().isEmpty());
        verify(flyRepository).findAll(Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link FlyController#getAll(Integer, Integer, SortType)}
     */
    @Test
    void testGetAll3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        IFlyService flyService = mock(IFlyService.class);
        when(flyService.readAll(Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<SortType>any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));
        ResponseEntity<Page<FlyResponse>> actualAll = (new FlyController(flyService)).getAll(1, 3, SortType.UPPER);
        assertNull(actualAll.getBody());
        assertEquals(204, actualAll.getStatusCodeValue());
        assertTrue(actualAll.getHeaders().isEmpty());
        verify(flyService).readAll(Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<SortType>any());
    }

    /**
     * Method under test: {@link FlyController#getAll(Integer, Integer, SortType)}
     */
    @Test
    void testGetAll4() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        IFlyService flyService = mock(IFlyService.class);
        when(flyService.readAll(Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<SortType>any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));
        ResponseEntity<Page<FlyResponse>> actualAll = (new FlyController(flyService)).getAll(1, 3, SortType.NONE);
        assertNull(actualAll.getBody());
        assertEquals(204, actualAll.getStatusCodeValue());
        assertTrue(actualAll.getHeaders().isEmpty());
        verify(flyService).readAll(Mockito.<Integer>any(), Mockito.<Integer>any(), Mockito.<SortType>any());
    }

    /**
     * Method under test: {@link FlyController#getBetweenPrice(BigDecimal, BigDecimal)}
     */
    @Test
    void testGetBetweenPrice() throws Exception {
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/fly/between_price");
        MockHttpServletRequestBuilder paramResult = getResult.param("max", String.valueOf((Object) null));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("min", String.valueOf((Object) null));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(flyController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test: {@link FlyController#getLessPrice(BigDecimal)}
     */
    @Test
    void testGetLessPrice() throws Exception {
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/fly/less_price");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("price", String.valueOf((Object) null));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(flyController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test: {@link FlyController#getLessPrice(BigDecimal)}
     */
    @Test
    void testGetLessPrice2() throws Exception {
        when(iFlyService.readLessPrice(Mockito.<BigDecimal>any())).thenReturn(new HashSet<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/fly/less_price");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("price", String.valueOf("42"));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(flyController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link FlyController#getLessPrice(BigDecimal)}
     */
    @Test
    void testGetLessPrice3() throws Exception {
        HashSet<FlyResponse> flyResponseSet = new HashSet<>();
        FlyResponse.FlyResponseBuilder originNameResult = FlyResponse.builder()
                .aeroLine(AeroLine.aero_gold)
                .destinyLat(10.0d)
                .destinyLng(10.0d)
                .destinyName("Destiny Name")
                .id(1L)
                .originLat(10.0d)
                .originLng(10.0d)
                .originName("Origin Name");
        flyResponseSet.add(originNameResult.price(BigDecimal.valueOf(1L)).build());
        when(iFlyService.readLessPrice(Mockito.<BigDecimal>any())).thenReturn(flyResponseSet);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/fly/less_price");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("price", String.valueOf("42"));
        MockMvcBuilders.standaloneSetup(flyController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":1,\"originLat\":10.0,\"originLng\":10.0,\"destinyLat\":10.0,\"destinyLng\":10.0,\"originName\":\"Origin"
                                        + " Name\",\"destinyName\":\"Destiny Name\",\"price\":1,\"aeroLine\":\"aero_gold\"}]"));
    }

    /**
     * Method under test: {@link FlyController#getOriginDestiny(String, String)}
     */
    @Test
    void testGetOriginDestiny() throws Exception {
        when(iFlyService.readByOriginDestiny(Mockito.<String>any(), Mockito.<String>any())).thenReturn(new HashSet<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/fly/origin_destiny")
                .param("destiny", "foo")
                .param("origin", "foo");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(flyController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link FlyController#getOriginDestiny(String, String)}
     */
    @Test
    void testGetOriginDestiny2() throws Exception {
        HashSet<FlyResponse> flyResponseSet = new HashSet<>();
        FlyResponse.FlyResponseBuilder originNameResult = FlyResponse.builder()
                .aeroLine(AeroLine.aero_gold)
                .destinyLat(10.0d)
                .destinyLng(10.0d)
                .destinyName("Destiny Name")
                .id(1L)
                .originLat(10.0d)
                .originLng(10.0d)
                .originName("Origin Name");
        flyResponseSet.add(originNameResult.price(BigDecimal.valueOf(1L)).build());
        when(iFlyService.readByOriginDestiny(Mockito.<String>any(), Mockito.<String>any())).thenReturn(flyResponseSet);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/fly/origin_destiny")
                .param("destiny", "foo")
                .param("origin", "foo");
        MockMvcBuilders.standaloneSetup(flyController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":1,\"originLat\":10.0,\"originLng\":10.0,\"destinyLat\":10.0,\"destinyLng\":10.0,\"originName\":\"Origin"
                                        + " Name\",\"destinyName\":\"Destiny Name\",\"price\":1,\"aeroLine\":\"aero_gold\"}]"));
    }
}

