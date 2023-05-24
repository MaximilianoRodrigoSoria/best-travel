package ar.com.laboratory.besttravel.api.controller;


import ar.com.laboratory.besttravel.api.models.requests.ReservationRequest;
import ar.com.laboratory.besttravel.api.models.requests.TicketRequest;
import ar.com.laboratory.besttravel.api.models.responses.ReservationResponse;
import ar.com.laboratory.besttravel.api.models.responses.TicketResponse;
import ar.com.laboratory.besttravel.infraestructure.abstract_service.IReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/reservation")
@AllArgsConstructor
public class ReservationController {


    private final IReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> post(@RequestBody ReservationRequest request){
        return ResponseEntity.ok(reservationService.created(request));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ReservationResponse> get(@PathVariable UUID id){
        return  ResponseEntity.ok(reservationService.read(id));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ReservationResponse> put(@PathVariable UUID id, @RequestBody ReservationRequest request){
        return ResponseEntity.ok(reservationService.update(request, id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Map<String, BigDecimal>>getFlyPrice(@RequestParam Long hotelId){
        return ResponseEntity.ok(Collections.singletonMap("fly_price", reservationService.findPrice(hotelId)));
    }
}
