package ar.com.laboratory.besttravel.api.controller;


import ar.com.laboratory.besttravel.api.models.requests.TicketRequest;
import ar.com.laboratory.besttravel.api.models.responses.TicketResponse;
import ar.com.laboratory.besttravel.infraestructure.abstract_service.ITicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping(path = "/api/v1/ticket")
@AllArgsConstructor
public class TicketController {

    private final ITicketService ticketService;


    @PostMapping
    public ResponseEntity<TicketResponse> post(@RequestBody TicketRequest request){
        return ResponseEntity.ok(ticketService.created(request));
    }

   @GetMapping(path = "/{id}")
   public ResponseEntity<TicketResponse> get(@PathVariable UUID id){
        return  ResponseEntity.ok(ticketService.read(id));
   }

    @PutMapping(path = "/{id}")
    public ResponseEntity<TicketResponse> put(@PathVariable UUID id, @RequestBody TicketRequest request){
        return ResponseEntity.ok(ticketService.update(request, id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Map<String, BigDecimal>>getFlyPrice(@RequestParam Long flyId){
        return ResponseEntity.ok(Collections.singletonMap("fly_price", ticketService.findPrice(flyId)));
    }
}
