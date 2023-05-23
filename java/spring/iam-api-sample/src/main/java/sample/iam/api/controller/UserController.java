package sample.iam.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import sample.iam.api.domain.entity.UserEntity;
import sample.iam.api.domain.response.RestApiResponse;
import sample.iam.api.exception.DatabaseException;
import sample.iam.api.exception.NotFoundException;
import sample.iam.api.exception.UnknownException;
import sample.iam.api.service.UserService;
import sample.iam.api.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  private final UserValidator userValidator;

  // ==========================================================================================
  // Create
  // ==========================================================================================

  @JsonView({UserEntity.class})
  @RequestMapping(value = {""}, method = RequestMethod.POST)
  public ResponseEntity<?> createUser(
      @RequestBody final UserEntity userEntity,
      BindingResult bindingResult)
      throws NullPointerException, UnknownException, DatabaseException, BindException {
    UserEntity createdUserEntity;
    userValidator.validate(userEntity, bindingResult);
    try {
      if (bindingResult.hasErrors()) {
        throw new BindException(bindingResult);
      }
      createdUserEntity = userService.create(userEntity);
    } catch (BindException | NullPointerException | DatabaseException e) {
      throw e;
    } catch (Exception e) {
      throw new UnknownException(e);
    }

    log.debug("Account {} creation successful...", createdUserEntity.getId());

    return RestApiResponse.success(createdUserEntity);
  }

  // ==========================================================================================
  // Read
  // ==========================================================================================

  @RequestMapping(value = {""}, method = RequestMethod.GET)
  public ResponseEntity<?> readAllUsers(final Pageable pageable)
      throws NullPointerException, UnknownException {

    Page<UserEntity> userList;
    try {
      userList = userService.readAll(pageable);

      if ((userList == null) || (userList.isEmpty())) {
        return RestApiResponse.successWithNoContent();
      }
    } catch (NullPointerException e) {
      throw e;
    } catch (Exception e) {
      throw new UnknownException(e);
    }

    log.debug("All users information read successful...");
    return RestApiResponse.success(userList);
  }

  @JsonView({UserEntity.class})
  @RequestMapping(value = {"/{userId}"}, method = RequestMethod.GET)
  public ResponseEntity<?> readUser(
      @PathVariable("userId") final int userId)
      throws UnknownException {

    UserEntity userEntity;
    try {
      userEntity = userService.read(userId);
    } catch (NotFoundException e) {
      return RestApiResponse.successWithNoContent();
    } catch (Exception e) {
      throw new UnknownException(e);
    }

    log.debug("A user {} information read successful...", userId);
    return RestApiResponse.success(userEntity);
  }

  // ==========================================================================================
  // Update
  // ==========================================================================================

  @JsonView(UserEntity.class)
  @RequestMapping(value = {"/{userId}"}, method = RequestMethod.PATCH)
  public ResponseEntity<?> patchUser(
      @PathVariable("userId") final int userId,
      @RequestBody final UserEntity userEntityForm, BindingResult bindingResult)
      throws Throwable {

    UserEntity targetUserEntity;
    try {

      userValidator.validate(userEntityForm, bindingResult);
      if (bindingResult.hasErrors()) {
        throw new BindException(bindingResult);
      }
      targetUserEntity = userService.patch(userId, userEntityForm);

    } catch (NotFoundException | DatabaseException | NullPointerException e) {
      throw e;
    } catch (Exception e) {
      throw new UnknownException(e);
    }

    log.debug("A user {} information patch successful...", userId);
    return RestApiResponse.success(targetUserEntity);
  }

  // ==========================================================================================
  // Delete
  // ==========================================================================================

  @JsonView(UserEntity.class)
  @RequestMapping(value = {"/{userId}"}, method = RequestMethod.DELETE)
  public ResponseEntity<?> deleteUser(
      @PathVariable("userId") final int userId)
      throws DatabaseException, NullPointerException, UnknownException {

    UserEntity userEntity;
    try {
      userEntity = userService.read(userId);
      if (userEntity == null) {
        throw new NotFoundException("Not found userId [" + userId + "]");
      }

      userService.delete(userId);
      log.debug("A User {} delete successful...", userId);

    } catch (DatabaseException | NullPointerException e) {
      throw e;
    } catch (Exception e) {
      throw new UnknownException(e);
    }

    return RestApiResponse.successWithNoContent();
  }

  @JsonView(UserEntity.class)
  @RequestMapping(value = {""}, method = RequestMethod.DELETE)
  public ResponseEntity<?> deleteAllUser() throws Exception {
    try {
      userService.deleteAll();
    } catch (DatabaseException | NullPointerException e) {
      throw e;
    } catch (Exception e) {
      throw new UnknownException(e);
    }

    log.debug("All Account delete successful...");
    return RestApiResponse.successWithNoContent();
  }

}
