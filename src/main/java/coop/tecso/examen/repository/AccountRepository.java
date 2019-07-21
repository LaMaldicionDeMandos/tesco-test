package coop.tecso.examen.repository;

import coop.tecso.examen.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
