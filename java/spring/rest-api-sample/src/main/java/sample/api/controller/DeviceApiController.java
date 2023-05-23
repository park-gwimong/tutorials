package sample.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import sample.api.domain.entity.DeviceEntity;
import sample.api.domain.response.RestApiResponse;
import sample.api.exception.DatabaseException;
import sample.api.exception.NotFoundException;
import sample.api.exception.UnknownException;
import sample.api.service.DeviceService;
import sample.api.validator.DeviceValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * The type Device api controller.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/devices")
public class DeviceApiController {

  private final DeviceService deviceService;

  private final DeviceValidator deviceValidator;

  // ==========================================================================================
  // Create
  // ==========================================================================================

  /**
   * Create device response entity.
   *
   * @param deviceEntity  the device entity
   * @param bindingResult the binding result
   * @return the response entity
   * @throws NullPointerException the null pointer exception
   * @throws UnknownException     the unknown exception
   * @throws DatabaseException    the database exception
   */
  @JsonView({DeviceEntity.class})
  @RequestMapping(value = {""}, method = RequestMethod.POST)
  public ResponseEntity<?> createDevice(
      @RequestBody final DeviceEntity deviceEntity,
      BindingResult bindingResult)
      throws NullPointerException, UnknownException, DatabaseException {
    DeviceEntity createdDeviceEntity;
    deviceValidator.validate(deviceEntity, bindingResult);
    try {
      if (bindingResult.hasErrors()) {
        throw new BindException(bindingResult);
      }
      createdDeviceEntity = deviceService.create(deviceEntity);
    } catch (NullPointerException | DatabaseException e) {
      throw e;
    } catch (Exception e) {
      throw new UnknownException(e);
    }

    log.debug("Account {} creation successful...", createdDeviceEntity.getId());

    return RestApiResponse.success(createdDeviceEntity);
  }

  // ==========================================================================================
  // Read
  // ==========================================================================================

  /**
   * Read all devices response entity.
   *
   * @param pageable the pageable
   * @return the response entity
   * @throws NullPointerException the null pointer exception
   * @throws UnknownException     the unknown exception
   */
  @RequestMapping(value = {""}, method = RequestMethod.GET)
  public ResponseEntity<?> readAllDevices(final Pageable pageable)
      throws NullPointerException, UnknownException {

    Page<DeviceEntity> deviceList;
    try {
      deviceList = deviceService.readAll(pageable);

      if ((deviceList == null) || (deviceList.isEmpty())) {
        return RestApiResponse.successWithNoContent();
      }
    } catch (NullPointerException e) {
      throw e;
    } catch (Exception e) {
      throw new UnknownException(e);
    }

    log.debug("All devices information read successful...");
    return RestApiResponse.success(deviceList);
  }

  /**
   * Read device response entity.
   *
   * @param deviceId the device id
   * @return the response entity
   * @throws UnknownException the unknown exception
   */
  @JsonView({DeviceEntity.class})
  @RequestMapping(value = {"/{deviceId}"}, method = RequestMethod.GET)
  public ResponseEntity<?> readDevice(
      @PathVariable("deviceId") final int deviceId)
      throws UnknownException {

    DeviceEntity deviceEntity;
    try {
      deviceEntity = deviceService.read(deviceId);
    } catch (NotFoundException e) {
      return RestApiResponse.successWithNoContent();
    } catch (Exception e) {
      throw new UnknownException(e);
    }

    log.debug("A device {} information read successful...", deviceId);
    return RestApiResponse.success(deviceEntity);
  }

  // ==========================================================================================
  // Update
  // ==========================================================================================

  /**
   * Patch device response entity.
   *
   * @param deviceId         the device id
   * @param deviceEntityForm the device entity form
   * @param bindingResult    the binding result
   * @return the response entity
   * @throws Throwable the throwable
   */
  @JsonView(DeviceEntity.class)
  @RequestMapping(value = {"/{deviceId}"}, method = RequestMethod.PATCH)
  public ResponseEntity<?> patchDevice(
      @PathVariable("deviceId") final int deviceId,
      @RequestBody final DeviceEntity deviceEntityForm, BindingResult bindingResult)
      throws Throwable {

    DeviceEntity targetDeviceEntity;
    try {

      deviceValidator.validate(deviceEntityForm, bindingResult);
      if (bindingResult.hasErrors()) {
        throw new BindException(bindingResult);
      }
      targetDeviceEntity = deviceService.patch(deviceId, deviceEntityForm);

    } catch (NotFoundException | DatabaseException | NullPointerException e) {
      throw e;
    } catch (Exception e) {
      throw new UnknownException(e);
    }

    log.debug("A device {} information patch successful...", deviceId);
    return RestApiResponse.success(targetDeviceEntity);
  }

  // ==========================================================================================
  // Delete
  // ==========================================================================================

  /**
   * Delete device response entity.
   *
   * @param deviceId the device id
   * @return the response entity
   * @throws DatabaseException    the database exception
   * @throws NullPointerException the null pointer exception
   * @throws UnknownException     the unknown exception
   */
  @JsonView(DeviceEntity.class)
  @RequestMapping(value = {"/{deviceId}"}, method = RequestMethod.DELETE)
  public ResponseEntity<?> deleteDevice(
      @PathVariable("deviceId") final int deviceId)
      throws DatabaseException, NullPointerException, UnknownException {

    DeviceEntity deviceEntity;
    try {
      deviceEntity = deviceService.read(deviceId);
      if (deviceEntity == null) {
        throw new NotFoundException("Not found deviceId [" + deviceId + "]");
      }

      deviceService.delete(deviceId);
      log.debug("A Device {} delete successful...", deviceId);

    } catch (DatabaseException | NullPointerException e) {
      throw e;
    } catch (Exception e) {
      throw new UnknownException(e);
    }

    return RestApiResponse.successWithNoContent();
  }

  /**
   * Delete all device response entity.
   *
   * @return the response entity
   * @throws Exception the exception
   */
  @JsonView(DeviceEntity.class)
  @RequestMapping(value = {""}, method = RequestMethod.DELETE)
  public ResponseEntity<?> deleteAllDevice() throws Exception {
    try {
      deviceService.deleteAll();
    } catch (DatabaseException | NullPointerException e) {
      throw e;
    } catch (Exception e) {
      throw new UnknownException(e);
    }

    log.debug("All Account delete successful...");
    return RestApiResponse.successWithNoContent();
  }
}
