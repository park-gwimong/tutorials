package sample.api.service;

import javax.transaction.Transactional;
import sample.api.domain.entity.DeviceEntity;
import sample.api.exception.DatabaseException;
import sample.api.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * The type Device service.
 */
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {


  private final DeviceRepository deviceRepository;

  // ==========================================================================================
  // Create
  // ==========================================================================================

  @Override
  public DeviceEntity create(DeviceEntity deviceEntity) throws DatabaseException {

    DeviceEntity createdDeviceEntity;
    try {
      createdDeviceEntity = deviceRepository.save(deviceEntity);

    } catch (Exception e) {
      throw new DatabaseException(e);
    }

    return createdDeviceEntity;
  }

  // ==========================================================================================
  // Read
  // ==========================================================================================

  @Override
  public Page<DeviceEntity> readAll(Pageable pageable) {
    return deviceRepository.findAll(pageable);
  }

  @Override
  public DeviceEntity read(int id) {
    return deviceRepository.findById(id).orElse(null);
  }

  // ==========================================================================================
  // Update
  // ==========================================================================================

  @Override
  public DeviceEntity update(DeviceEntity deviceEntity) throws DatabaseException {
    DeviceEntity updatedDeviceEntity;
    try {
      updatedDeviceEntity = deviceRepository.save(deviceEntity);
    } catch (Exception e) {
      throw new DatabaseException(e);
    }
    return updatedDeviceEntity;
  }

  @Override
  public DeviceEntity patch(int id, DeviceEntity fetchedDeviceEntity)
      throws DatabaseException {

    DeviceEntity targetDeviceEntity = deviceRepository.getReferenceById(id);

    try {

      if (fetchedDeviceEntity.getSn() != null) {
        targetDeviceEntity.setSn(fetchedDeviceEntity.getSn());
      }

      if (fetchedDeviceEntity.getModelName() != null) {
        targetDeviceEntity.setModelName(fetchedDeviceEntity.getModelName());
      }

      if (fetchedDeviceEntity.getEducationInstitution() != null) {
        targetDeviceEntity.setEducationInstitution(fetchedDeviceEntity.getEducationInstitution());
      }

      if (fetchedDeviceEntity.getMacAddress() != null) {
        targetDeviceEntity.setMacAddress(fetchedDeviceEntity.getMacAddress());
      }

      if (fetchedDeviceEntity.getStatusCode() != null) {
        targetDeviceEntity.setStatusCode(fetchedDeviceEntity.getStatusCode());
      }

      if (fetchedDeviceEntity.getOsClassification() != null) {
        targetDeviceEntity.setOsClassification(fetchedDeviceEntity.getOsClassification());
      }

      if (fetchedDeviceEntity.getStatusClassification() != null) {
        targetDeviceEntity.setStatusClassification(fetchedDeviceEntity.getStatusClassification());
      }

      if (fetchedDeviceEntity.getTypeClassification() != null) {
        targetDeviceEntity.setTypeClassification(fetchedDeviceEntity.getTypeClassification());
      }

      fetchedDeviceEntity = deviceRepository.save(targetDeviceEntity);
    } catch (Exception e) {
      throw new DatabaseException(e);
    }

    return fetchedDeviceEntity;
  }

  // ==========================================================================================
  // Delete
  // ==========================================================================================

  @Override
  public void delete(int id) throws DatabaseException {
    try {
      deviceRepository.deleteById(id);
    } catch (Exception e) {
      throw new DatabaseException(e);
    }
  }

  @Override
  public void deleteAll() throws DatabaseException {
    try {
      deviceRepository.deleteAll();
    } catch (Exception e) {
      throw new DatabaseException(e);
    }
  }

}
