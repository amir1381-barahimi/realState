package rastak.train.ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rastak.train.ws.model.entity.UserEntity;
import rastak.train.ws.model.enums.Role;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByPublicId(String publicId);

    UserEntity findByUsername(String userName);
    List<UserEntity> findByRole(Role role);

    List<UserEntity> findAll();

    int deleteByPublicId(String publicId);

}