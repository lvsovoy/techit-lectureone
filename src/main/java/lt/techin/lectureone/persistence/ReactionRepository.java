package lt.techin.lectureone.persistence;

import lt.techin.lectureone.persistence.model.ReactionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReactionRepository extends JpaRepository<ReactionRecord, String> {

    List<ReactionRecord> findByUuid(String uuid);

}
