package sample.api.test.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import sample.api.domain.entity.DeviceEntity;
import sample.api.domain.enums.device.classification.os.DeviceOsClassification;
import sample.api.domain.enums.device.classification.status.DeviceStatusClassification;
import sample.api.domain.enums.device.classification.type.DeviceTypeClassification;
import sample.api.exception.DatabaseException;
import sample.api.exception.NotFoundException;
import sample.api.repository.DeviceRepository;
import sample.api.service.DeviceService;
import sample.api.service.DeviceServiceImpl;
import sample.api.domain.enums.device.code.status.DeviceStatusCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

/**
 * The type Device service test.
 */
@ExtendWith(MockitoExtension.class)
class DeviceServiceTest extends BaseServiceTest {

  /**
   * The Device repository.
   */
  @Mock
  DeviceRepository deviceRepository;

  /**
   * The type Create device.
   */
  @Nested
  @DisplayName("Create device")
  class createDevice {

    /**
     * The type Success case.
     */
    @Nested
    @DisplayName("Success Case")
    class successCase {

      /**
       * Create device success case 1.
       *
       * @throws DatabaseException the database exception
       */
      @Test
      @DisplayName("Success cases with only required information entered")
      void createDeviceSuccessCase1() throws DatabaseException {

        DeviceEntity result = DeviceEntity.builder()
            .statusClassification(DeviceStatusClassification.NEW_DEVICE)
            .osClassification(DeviceOsClassification.WINDOWS_OS)
            .typeClassification(DeviceTypeClassification.NOTEBOOK)
            .sn("999999").build();
        result.setId(1);
        result.setStatusCode(DeviceStatusCode.POSSESSION);

        when(deviceRepository.save(any((DeviceEntity.class)))).thenReturn(result);

        DeviceService deviceService = new DeviceServiceImpl(deviceRepository);

        DeviceEntity createdDeviceEntity = deviceService.create(result);

        assertThat(createdDeviceEntity.getId()).isEqualTo(1);
        Assertions.assertThat(createdDeviceEntity.getStatusClassification()).isEqualTo(
            DeviceStatusClassification.NEW_DEVICE);
        Assertions.assertThat(createdDeviceEntity.getOsClassification()).isEqualTo(
            DeviceOsClassification.WINDOWS_OS);
        Assertions.assertThat(createdDeviceEntity.getTypeClassification()).isEqualTo(
            DeviceTypeClassification.NOTEBOOK);
        assertThat(createdDeviceEntity.getStatusCode()).isEqualTo(DeviceStatusCode.POSSESSION);
        assertThat(createdDeviceEntity.getSn()).isEqualTo("999999");
        assertThat(createdDeviceEntity.getDeviceUsed()).isNull();
        assertThat(createdDeviceEntity.getEducationInstitution()).isNull();
        assertThat(createdDeviceEntity.getMacAddress()).isNull();
        assertThat(createdDeviceEntity.getModelName()).isNull();
      }

      /**
       * Create device success case 2.
       *
       * @throws DatabaseException the database exception
       */
      @Test
      @DisplayName("Success cases with all information entered")
      void createDeviceSuccessCase2() throws DatabaseException {

        DeviceEntity result = DeviceEntity.builder()
            .statusClassification(DeviceStatusClassification.NEW_DEVICE)
            .osClassification(DeviceOsClassification.WINDOWS_OS)
            .typeClassification(DeviceTypeClassification.NOTEBOOK)
            .sn("999999").build();
        result.setId(1);
        result.setMacAddress("0000-0000-0000-0000");
        result.setModelName("TestModel1");
        result.setStatusCode(DeviceStatusCode.RENTAL);
        result.setDeviceUsed("1");
        result.setEducationInstitution("10");

        when(deviceRepository.save(any((DeviceEntity.class)))).thenReturn(result);

        DeviceService deviceService = new DeviceServiceImpl(deviceRepository);

        DeviceEntity newDeviceEntity = DeviceEntity.builder()
            .statusClassification(DeviceStatusClassification.NEW_DEVICE)
            .osClassification(DeviceOsClassification.WINDOWS_OS)
            .typeClassification(DeviceTypeClassification.NOTEBOOK)
            .sn("999999").build();

        newDeviceEntity.setMacAddress("0000-0000-0000-0000");
        newDeviceEntity.setModelName("TestModel1");
        newDeviceEntity.setStatusCode(DeviceStatusCode.RENTAL);
        newDeviceEntity.setDeviceUsed("1");

        DeviceEntity createdDeviceEntity = deviceService.create(newDeviceEntity);

        assertThat(createdDeviceEntity.getId()).isEqualTo(1);
        Assertions.assertThat(createdDeviceEntity.getStatusClassification()).isEqualTo(
            DeviceStatusClassification.NEW_DEVICE);
        Assertions.assertThat(createdDeviceEntity.getOsClassification()).isEqualTo(
            DeviceOsClassification.WINDOWS_OS);
        Assertions.assertThat(createdDeviceEntity.getTypeClassification()).isEqualTo(
            DeviceTypeClassification.NOTEBOOK);
        assertThat(createdDeviceEntity.getStatusCode()).isEqualTo(DeviceStatusCode.RENTAL);

        assertThat(createdDeviceEntity.getSn()).isEqualTo("999999");
        assertThat(createdDeviceEntity.getMacAddress()).isEqualTo("0000-0000-0000-0000");
        assertThat(createdDeviceEntity.getModelName()).isEqualTo("TestModel1");
        assertThat(createdDeviceEntity.getStatusCode()).isEqualTo(DeviceStatusCode.RENTAL);
        assertThat(createdDeviceEntity.getDeviceUsed()).isEqualTo("1");
        assertThat(createdDeviceEntity.getEducationInstitution()).isEqualTo("10");

      }
    }

    /**
     * The type Fail case.
     */
    @Nested
    @DisplayName("Fail Case")
    class failCase {

      /**
       * Create device fail case 1.
       */
      @Test
      @DisplayName("Fail cases with exception from repository layer")
      void createDeviceFailCase1() {

        when(deviceRepository.save(any((DeviceEntity.class)))).thenThrow(new RuntimeException());

        DeviceService deviceService = new DeviceServiceImpl(deviceRepository);

        DeviceEntity newDeviceEntity = DeviceEntity.builder()
            .statusClassification(DeviceStatusClassification.NEW_DEVICE)
            .osClassification(DeviceOsClassification.WINDOWS_OS)
            .typeClassification(DeviceTypeClassification.NOTEBOOK)
            .sn("999999").build();

        assertThrows(DatabaseException.class, () -> deviceService.create(newDeviceEntity));

      }
    }
  }

  /**
   * The type Read device.
   */
  @Nested
  @DisplayName("Read device")
  class readDevice {

    /**
     * The type Success case.
     */
    @Nested
    @DisplayName("Success Case")
    class successCase {

      /**
       * Read device success case 1.
       *
       * @throws NotFoundException the not found exception
       */
      @Test
      @DisplayName("Success Case to get a device information.")
      void readDeviceSuccessCase1() throws NotFoundException {

        int deviceId = 1;

        DeviceEntity result = DeviceEntity.builder()
            .statusClassification(DeviceStatusClassification.NEW_DEVICE)
            .osClassification(DeviceOsClassification.WINDOWS_OS)
            .typeClassification(DeviceTypeClassification.NOTEBOOK)
            .sn("999999").build();
        result.setId(deviceId);
        result.setMacAddress("0000-0000-0000-0000");
        result.setModelName("TestModel1");
        result.setStatusCode(DeviceStatusCode.POSSESSION);
        result.setDeviceUsed("1");
        result.setEducationInstitution("10");

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(result));

        DeviceService deviceService = new DeviceServiceImpl(deviceRepository);

        DeviceEntity createdDeviceEntity = deviceService.read(deviceId);

        assertThat(createdDeviceEntity.getId()).isEqualTo(1);
        Assertions.assertThat(createdDeviceEntity.getStatusClassification()).isEqualTo(
            DeviceStatusClassification.NEW_DEVICE);
        Assertions.assertThat(createdDeviceEntity.getOsClassification()).isEqualTo(
            DeviceOsClassification.WINDOWS_OS);
        Assertions.assertThat(createdDeviceEntity.getTypeClassification()).isEqualTo(
            DeviceTypeClassification.NOTEBOOK);
        assertThat(createdDeviceEntity.getSn()).isEqualTo("999999");
        assertThat(createdDeviceEntity.getMacAddress()).isEqualTo("0000-0000-0000-0000");
        assertThat(createdDeviceEntity.getModelName()).isEqualTo("TestModel1");
        assertThat(createdDeviceEntity.getStatusCode()).isEqualTo(DeviceStatusCode.POSSESSION);
        assertThat(createdDeviceEntity.getDeviceUsed()).isEqualTo("1");
        assertThat(createdDeviceEntity.getEducationInstitution()).isEqualTo("10");
      }

      /**
       * Read device success case 2.
       */
      @Test
      @DisplayName("Success Case to get all device information.")
      void readDeviceSuccessCase2() {

        DeviceEntity result1 = DeviceEntity.builder()
            .statusClassification(DeviceStatusClassification.NEW_DEVICE)
            .osClassification(DeviceOsClassification.WINDOWS_OS)
            .typeClassification(DeviceTypeClassification.NOTEBOOK)
            .sn("999999").build();
        result1.setId(1);

        DeviceEntity result2 = DeviceEntity.builder()
            .statusClassification(DeviceStatusClassification.NEW_DEVICE)
            .osClassification(DeviceOsClassification.WINDOWS_OS)
            .typeClassification(DeviceTypeClassification.NOTEBOOK)
            .sn("999999").build();
        result2.setId(2);

        List<DeviceEntity> resultList = new ArrayList<>();
        resultList.add(result1);
        resultList.add(result2);

        PageRequest pageRequest = PageRequest.of(0, 10);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), resultList.size());

        when(deviceRepository.findAll(pageRequest)).thenReturn(
            new PageImpl<>(resultList.subList(start, end), pageRequest, resultList.size()));

        DeviceService deviceService = new DeviceServiceImpl(deviceRepository);

        Page<DeviceEntity> deviceEntities = deviceService.readAll(pageRequest);
        assertThat(deviceEntities.getTotalElements()).isEqualTo(2);

      }
    }

    /**
     * The type Fail case.
     */
    @Nested
    @DisplayName("Fail Case")
    class failCase {

      /**
       * Read device fail case 1.
       *
       * @throws NotFoundException the not found exception
       */
      @Test
      @DisplayName("Fail Case to get device information when not found device")
      void readDeviceFailCase1() throws NotFoundException {
        int undefinedId = 5;
        DeviceService deviceService = new DeviceServiceImpl(deviceRepository);
        DeviceEntity d = deviceService.read(undefinedId);
        assertThat(d).isNull();
      }
    }
  }

  /**
   * The type Update device.
   */
// TODO : 추후 구현
  @Nested
  @DisplayName("Update device")
  class updateDevice {

    /**
     * The type Success case.
     */
    @Nested
    @DisplayName("Success Case")
    class successCase {

    }
  }

  /**
   * The type Delete device.
   */
// TODO : 추후 구현
  @Nested
  @DisplayName("Delete device")
  class deleteDevice {

    /**
     * The type Success case.
     */
    @Nested
    @DisplayName("Success Case")
    class successCase {

    }
  }
}