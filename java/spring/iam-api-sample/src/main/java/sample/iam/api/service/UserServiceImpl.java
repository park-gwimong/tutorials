package sample.iam.api.service;

import sample.iam.api.domain.entity.UserEntity;
import sample.iam.api.exception.DatabaseException;
import sample.iam.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  // ==========================================================================================
  // Create
  // ==========================================================================================

  @Override
  public UserEntity create(UserEntity userEntity) throws DatabaseException {

    UserEntity createdUserEntity;

    UserEntity alreadyUserEntity = userRepository.findFirstUserByUserIdOrderByIdAsc(
        userEntity.getUserId()).orElse(null);
    if (alreadyUserEntity != null) {
      throw new RuntimeException("이미 등록된 아이디 입니다.");
    }

    userEntity.setEncryptedPassword(passwordEncoder.encode(userEntity.getPassword()));
    try {
      createdUserEntity = userRepository.save(userEntity);

    } catch (Exception e) {
      throw new DatabaseException(e);
    }

    return createdUserEntity;
  }

  // ==========================================================================================
  // Read
  // ==========================================================================================

  @Override
  public Page<UserEntity> readAll(Pageable pageable) {
    return userRepository.findAll(pageable);
  }

  @Override
  public UserEntity read(int id) {
    return userRepository.findById(id).orElse(null);
  }

  // ==========================================================================================
  // Update
  // ==========================================================================================

  @Override
  public UserEntity update(UserEntity deviceEntity) throws DatabaseException {
    UserEntity updatedUserEntity;
    try {
      updatedUserEntity = userRepository.save(deviceEntity);
    } catch (Exception e) {
      throw new DatabaseException(e);
    }
    return updatedUserEntity;
  }

  @Override
  public UserEntity patch(int id, UserEntity fetchedUserEntity)
      throws DatabaseException {

    UserEntity targetUserEntity = userRepository.getReferenceById(id);



    try {

      if (fetchedUserEntity.getEmail() != null) {
        targetUserEntity.setEmail(fetchedUserEntity.getEmail());
      }

      if (fetchedUserEntity.getName() != null) {
        targetUserEntity.setName(fetchedUserEntity.getName());
      }

      fetchedUserEntity = userRepository.save(targetUserEntity);
    } catch (Exception e) {
      throw new DatabaseException(e);
    }

    return fetchedUserEntity;
  }

  // ==========================================================================================
  // Delete
  // ==========================================================================================

  @Override
  public void delete(int id) throws DatabaseException {
    try {
      userRepository.deleteById(id);
    } catch (Exception e) {
      throw new DatabaseException(e);
    }
  }


  @Override
  public void deleteAll() throws DatabaseException {
    try {
      userRepository.deleteAll();
    } catch (Exception e) {
      throw new DatabaseException(e);
    }
  }

}
