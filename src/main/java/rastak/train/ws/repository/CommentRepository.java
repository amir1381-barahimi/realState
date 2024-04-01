package rastak.train.ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rastak.train.ws.model.entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
