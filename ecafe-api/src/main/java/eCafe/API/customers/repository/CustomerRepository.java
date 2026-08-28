package eCafe.API.customers.repository;

import eCafe.API.customers.entity.Customer;
import jakarta.annotation.Nonnull;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer>findByKeycloakUserId(String keycloakUserId);

}
