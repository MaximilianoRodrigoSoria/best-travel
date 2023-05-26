package ar.com.laboratory.besttravel.api.controller;

import ar.com.laboratory.besttravel.api.models.responses.HotelResponse;
import ar.com.laboratory.besttravel.infraestructure.abstract_service.IHotelService;
import ar.com.laboratory.besttravel.util.SortType;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping(path = "/api/v1/hotel")
@AllArgsConstructor
public class HotelController {

    private final IHotelService hotelService;


    @GetMapping
    public ResponseEntity<Page<HotelResponse>> getAll(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam SortType sortType
    ){
        if(Objects.isNull(sortType))sortType=SortType.NONE;
        var response = hotelService.readAll(page, size, sortType);
        return  response.isEmpty()?ResponseEntity.noContent().build():ResponseEntity.ok(response);
    }

    @GetMapping("/less_price")
    public ResponseEntity<Set<HotelResponse>> getLessPrice(@RequestParam BigDecimal price ){
        var response = hotelService.readLessPrice(price);
        return  response.isEmpty()?ResponseEntity.noContent().build():ResponseEntity.ok(response);
    }

    @GetMapping("/between_price")
    public ResponseEntity<Set<HotelResponse>> getBetweenPrice(@RequestParam BigDecimal min, @RequestParam BigDecimal max){
        var response = hotelService.readBetweenPrices(min, max);
        return  response.isEmpty()?ResponseEntity.noContent().build():ResponseEntity.ok(response);
    }

    @GetMapping("/greater_than")
    public ResponseEntity<Set<HotelResponse>> readGreaterThan(@RequestParam Integer rating){
        var response = hotelService.readGreaterThan(rating);
        return  response.isEmpty()?ResponseEntity.noContent().build():ResponseEntity.ok(response);
    }
}
