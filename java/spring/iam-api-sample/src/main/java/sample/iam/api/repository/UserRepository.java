package sample.iam.api.repository;

import java.util.Optional;
import sample.iam.api.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

  Optional<UserEntity> findFirstUserByUserIdOrderByIdAsc(String username);
}
