package demo.audit.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends RevisionRepository<Product, Long, Long>, JpaRepository<Product, Long> {
}
