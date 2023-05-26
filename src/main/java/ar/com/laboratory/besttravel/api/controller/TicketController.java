package ar.com.laboratory.besttravel.api.controller;


import ar.com.laboratory.besttravel.api.models.requests.TicketRequest;
import ar.com.laboratory.besttravel.api.models.responses.ErrorsResponse;
import ar.com.laboratory.besttravel.api.models.responses.TicketResponse;
import ar.com.laboratory.besttravel.infraestructure.abstract_service.ITicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping(path = "/api/v1/ticket")
@AllArgsConstructor
@Tag(name="Ticket")
public class TicketController {

    private final ITicketService ticketService;

    @ApiResponse(
            responseCode = "400",
            description = "When the request have a field invalid we response this",
            content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))
            }
    )
    @Operation(summary = "Save in system un ticket with the fly passed in parameter")

    @PostMapping
    public ResponseEntity<TicketResponse> post(@RequestBody TicketRequest request){
        return ResponseEntity.ok(ticketService.created(request));
    }
    @Operation(summary = "Return a ticket with of passed")
   @GetMapping(path = "/{id}")
   public ResponseEntity<TicketResponse> get(@PathVariable UUID id){
        return  ResponseEntity.ok(ticketService.read(id));
   }
    @Operation(summary = "Update ticket")
    @PutMapping(path = "/{id}")
    public ResponseEntity<TicketResponse> put(@PathVariable UUID id, @RequestBody TicketRequest request){
        return ResponseEntity.ok(ticketService.update(request, id));
    }
    @Operation(summary = "Delete a ticket with of passed")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Get fly with fly")
    @GetMapping
    public ResponseEntity<Map<String, BigDecimal>>getFlyPrice(@RequestParam Long flyId){
        return ResponseEntity.ok(Collections.singletonMap("fly_price", ticketService.findPrice(flyId)));
    }
}
