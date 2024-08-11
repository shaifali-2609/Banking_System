package Banking.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import Banking.entity.Client;

public interface ClientRepos extends JpaRepository<Client, Long> {

	@Query("SELECT c FROM Client c WHERE " +
            "(:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:phone IS NULL OR :phone MEMBER OF c.phoneNumbers) AND " +
            "(:email IS NULL OR :email MEMBER OF c.emails) AND " +
            "(:dateOfBirth IS NULL OR c.dateOfBirth = :dateOfBirth)")
    Page<Client> searchClients(String name, String phone, String email, LocalDate dateOfBirth, Pageable pageable);
	   Optional<Client> findByUsername(String username);


}






