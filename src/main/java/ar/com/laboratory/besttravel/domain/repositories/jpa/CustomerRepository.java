package ar.com.laboratory.besttravel.domain.repositories.jpa;

import ar.com.laboratory.besttravel.domain.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<CustomerEntity,String> {
}
