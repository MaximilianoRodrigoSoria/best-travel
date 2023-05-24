package ar.com.laboratory.besttravel.domain.repositories.jpa;

import ar.com.laboratory.besttravel.domain.entities.jpa.ReservationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;


public interface ReservationRepository extends CrudRepository<ReservationEntity, UUID> {
}
