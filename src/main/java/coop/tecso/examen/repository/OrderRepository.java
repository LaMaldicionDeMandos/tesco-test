package coop.tecso.examen.repository;

import coop.tecso.examen.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
