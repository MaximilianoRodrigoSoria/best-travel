package ar.com.laboratory.besttravel.api.controller;

import ar.com.laboratory.besttravel.api.models.requests.TourRequest;
import ar.com.laboratory.besttravel.api.models.responses.TourResponse;
import ar.com.laboratory.besttravel.infraestructure.abstract_service.ITourService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/tour")
@AllArgsConstructor
@Tag(name="Tour")
public class TourController {

    private ITourService tourService;
    @PostMapping
    public ResponseEntity<TourResponse>post(@RequestBody TourRequest request){
        return ResponseEntity.ok(this.tourService.create(request));
    }
    @GetMapping({"/{id}"})
    public ResponseEntity<TourResponse>getById(@PathVariable Long id){
        return ResponseEntity.ok(this.tourService.read(id));
    }
    @DeleteMapping({"/{id}"})
    public ResponseEntity<Void>delete(@PathVariable Long id){
        this.tourService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "/{tourId}/remove_ticket/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long tourId, @PathVariable UUID ticketId) {
        this.tourService.removeTicket(tourId, ticketId);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping(path = "/{tourId}/add_ticket/{flyId}")
    public ResponseEntity<Map<String, UUID>> postTicket(@PathVariable Long tourId, @PathVariable Long flyId) {
        var response = Collections.singletonMap("ticketId", this.tourService.addTicket(tourId, flyId));
        return ResponseEntity.ok(response);
    }


    @PatchMapping(path = "/{tourId}/remove_reservation/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long tourId, @PathVariable UUID reservationId) {
        this.tourService.removeReservation(tourId,reservationId);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping(path = "/{tourId}/add_reservation/{hotelId}")
    public ResponseEntity<Map<String, UUID>> postTicket(
            @PathVariable Long tourId,
            @PathVariable Long hotelId,
            @RequestParam Integer totalDays) {
        var response = Collections.singletonMap("ticketId", this.tourService.addReservation(tourId, hotelId, totalDays));
        return ResponseEntity.ok(response);
    }
}
