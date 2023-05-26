package ar.com.laboratory.besttravel.api.controller;

import ar.com.laboratory.besttravel.api.models.requests.TourRequest;
import ar.com.laboratory.besttravel.api.models.responses.TourResponse;
import ar.com.laboratory.besttravel.infraestructure.abstract_service.ITourService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/tour")
@AllArgsConstructor
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
    public ResponseEntity<TourResponse>delete(@PathVariable Long id){
        this.tourService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
