package ar.com.laboratory.besttravel.domain.repositories.jpa;

import ar.com.laboratory.besttravel.domain.entities.TourEntity;
import org.springframework.data.repository.CrudRepository;


public interface TourRepository extends CrudRepository<TourEntity, Long> {
}
