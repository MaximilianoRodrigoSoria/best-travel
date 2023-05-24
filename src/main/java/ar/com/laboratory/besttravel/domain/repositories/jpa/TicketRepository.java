package ar.com.laboratory.besttravel.domain.repositories.jpa;

import ar.com.laboratory.besttravel.domain.entities.jpa.TicketEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;


public interface TicketRepository extends CrudRepository<TicketEntity, UUID> {
}
