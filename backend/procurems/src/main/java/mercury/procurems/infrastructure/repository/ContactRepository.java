package mercury.procurems.infrastructure.repository;

import mercury.procurems.domain.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {

}