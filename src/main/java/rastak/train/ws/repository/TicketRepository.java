package rastak.train.ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rastak.train.ws.model.entity.TicketEntity;
import rastak.train.ws.model.entity.UserEntity;
import rastak.train.ws.model.enums.Role;
import rastak.train.ws.model.enums.Status;

import java.util.List;

import static rastak.train.ws.model.enums.Status.OPEN;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

    TicketEntity findByPublicId(String publicId);
    List<TicketEntity> findAll();
    int deleteByPublicId(String publicId);
    List<TicketEntity> findByUser(UserEntity user);
    int countBySupportAndStatus(UserEntity user, Status status);

    List<TicketEntity> findBySupport(Role role);
}
