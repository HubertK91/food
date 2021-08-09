package pl.hk.app.part;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartRepository extends JpaRepository<Part, Long> {

    List<Part> findAllBySelected(boolean selected);
}
