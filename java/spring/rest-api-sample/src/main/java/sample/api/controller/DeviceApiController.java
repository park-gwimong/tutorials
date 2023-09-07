package sample.api.controller;

import org.springframework.web.bind.annotation.*;
import sample.api.domain.dto.RequestDeviceDTO;
import sample.api.domain.dto.ResponseDeviceDTO;
import sample.api.domain.entity.DeviceEntity;
import sample.api.response.RestApiResponse;
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

    @PostMapping(value = {""})
    public ResponseEntity<ResponseDeviceDTO> createDevice(@RequestBody final RequestDeviceDTO requestDeviceDto,
                                                          BindingResult bindingResult)
            throws NullPointerException, UnknownException, DatabaseException {

        ResponseDeviceDTO responseDeviceDto;
        deviceValidator.validate(requestDeviceDto, bindingResult);
        try {
            if (bindingResult.hasErrors()) {
                throw new BindException(bindingResult);
            }
            DeviceEntity sourceDeviceEntity = requestDeviceDto.toEntity();
            DeviceEntity createdDeviceEntity = deviceService.create(sourceDeviceEntity);
            responseDeviceDto = new ResponseDeviceDTO().fromEntity(createdDeviceEntity);
        } catch (NullPointerException | DatabaseException e) {
            throw e;
        } catch (Exception e) {
            throw new UnknownException(e);
        }

        log.debug("Account {} creation successful...", responseDeviceDto.getId());

        return RestApiResponse.success(responseDeviceDto);
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
    @GetMapping(value = {""})
    public ResponseEntity<Page<ResponseDeviceDTO>> readAllDevices(final Pageable pageable)
            throws NullPointerException, UnknownException {

        Page<ResponseDeviceDTO> deviceList;
        try {
            Page<DeviceEntity> deviceEntities = deviceService.readAll(pageable);
            deviceList = new ResponseDeviceDTO().toDtoList(deviceEntities);

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
    @GetMapping(value = {"/{deviceId}"})
    public ResponseEntity<ResponseDeviceDTO> readDevice(
            @PathVariable("deviceId") final int deviceId)
            throws UnknownException {

        ResponseDeviceDTO responseDeviceDTO;
        try {
            DeviceEntity targetDeviceEntity = deviceService.read(deviceId);
            if (targetDeviceEntity == null) {
                return RestApiResponse.successWithNoContent();
            }
            responseDeviceDTO = new ResponseDeviceDTO().fromEntity(targetDeviceEntity);
        } catch (NotFoundException e) {
            return RestApiResponse.successWithNoContent();
        } catch (Exception e) {
            throw new UnknownException(e);
        }

        log.debug("A device {} information read successful...", deviceId);
        return RestApiResponse.success(responseDeviceDTO);
    }

    // ==========================================================================================
    // Update
    // ==========================================================================================

    /**
     * Patch device response entity.
     *
     * @param deviceId         the device id
     * @param requestDeviceDTO the device entity form
     * @param bindingResult    the binding result
     * @return the response entity
     * @throws Throwable the throwable
     */
    @PatchMapping(value = {"/{deviceId}"})
    public ResponseEntity<ResponseDeviceDTO> patchDevice(
            @PathVariable("deviceId") final int deviceId,
            @RequestBody final RequestDeviceDTO requestDeviceDTO, BindingResult bindingResult)
            throws Throwable {

        ResponseDeviceDTO responseDeviceDTO;
        try {

            deviceValidator.validate(requestDeviceDTO, bindingResult);
            if (bindingResult.hasErrors()) {
                throw new BindException(bindingResult);
            }
            DeviceEntity sourceDeviceEntity = requestDeviceDTO.toEntity();
            DeviceEntity pachtedDeviceEntity = deviceService.patch(deviceId, sourceDeviceEntity);
            responseDeviceDTO = new ResponseDeviceDTO().fromEntity(pachtedDeviceEntity);

        } catch (NotFoundException | DatabaseException | NullPointerException e) {
            throw e;
        } catch (Exception e) {
            throw new UnknownException(e);
        }

        log.debug("A device {} information patch successful...", deviceId);
        return RestApiResponse.success(responseDeviceDTO);
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
    @DeleteMapping(value = {"/{deviceId}"})
    public ResponseEntity<ResponseDeviceDTO> deleteDevice(
            @PathVariable("deviceId") final int deviceId)
            throws DatabaseException, NullPointerException, UnknownException {

        ResponseDeviceDTO responseDeviceDTO;
        try {
            DeviceEntity targetDeviceEntity = deviceService.read(deviceId);
            responseDeviceDTO = new ResponseDeviceDTO().fromEntity(targetDeviceEntity);
            if (responseDeviceDTO == null) {
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
    @DeleteMapping(value = {""})
    public ResponseEntity<ResponseDeviceDTO> deleteAllDevice() throws Exception {
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
