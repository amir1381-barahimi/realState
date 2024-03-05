package rastak.train.ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rastak.train.ws.model.entity.UserEntity;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByPublicId(String publicId);

    UserEntity findByUsername(String userName);

    List<UserEntity> findAll();

    int deleteByPublicId(String publicId);
}
