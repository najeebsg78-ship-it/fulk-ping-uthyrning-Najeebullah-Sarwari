package se.fulkoping.uthyrning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.fulkoping.uthyrning.model.Sak;

public interface SakRepository extends JpaRepository<Sak, Long> {
}
