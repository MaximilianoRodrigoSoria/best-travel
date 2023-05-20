package ar.com.laboratory.besttravel.api.controller;


import ar.com.laboratory.besttravel.api.models.requests.TicketRequest;
import ar.com.laboratory.besttravel.api.models.responses.TicketResponse;
import ar.com.laboratory.besttravel.infraestructure.abstract_service.ITicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/ticket")
@AllArgsConstructor
public class TicketController {

    private final ITicketService ticketService;


    @PostMapping
    public ResponseEntity<TicketResponse> post(@RequestBody TicketRequest request){
        return ResponseEntity.ok(ticketService.created(request));
    }

   @GetMapping(path = "/{uuid}")
   public ResponseEntity<TicketResponse> get(@PathVariable UUID uuid){
        return  ResponseEntity.ok(ticketService.read(uuid));
   }

}
