package ar.com.laboratory.besttravel.api.controller;


import ar.com.laboratory.besttravel.api.models.requests.ReservationRequest;
import ar.com.laboratory.besttravel.api.models.requests.TicketRequest;
import ar.com.laboratory.besttravel.api.models.responses.ErrorsResponse;
import ar.com.laboratory.besttravel.api.models.responses.ReservationResponse;
import ar.com.laboratory.besttravel.api.models.responses.TicketResponse;
import ar.com.laboratory.besttravel.infraestructure.abstract_service.IReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="Reservation")
public class ReservationController {


    private final IReservationService reservationService;

    @ApiResponse(
            responseCode = "400",
            description = "When the request have a field invalid we response this",
            content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))
            }
    )
    @Operation(summary = "Save in system un reservation with the fly passed in parameter")
    @PostMapping
    public ResponseEntity<ReservationResponse> post(@RequestBody ReservationRequest request){
        return ResponseEntity.ok(reservationService.created(request));
    }

    @Operation(summary = "Return a reservation with of passed")
    @GetMapping(path = "/{id}")
    public ResponseEntity<ReservationResponse> get(@PathVariable UUID id){
        return  ResponseEntity.ok(reservationService.read(id));
    }
    @Operation(summary = "Update reservation")
    @PutMapping(path = "/{id}")
    public ResponseEntity<ReservationResponse> put(@PathVariable UUID id, @RequestBody ReservationRequest request){
        return ResponseEntity.ok(reservationService.update(request, id));
    }
    @Operation(summary = "Delete a reservation with of passed")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "return a reservation price given a hotel id")
    @GetMapping
    public ResponseEntity<Map<String, BigDecimal>>getFlyPrice(@RequestParam Long hotelId){
        return ResponseEntity.ok(Collections.singletonMap("fly_price", reservationService.findPrice(hotelId)));
    }
}
