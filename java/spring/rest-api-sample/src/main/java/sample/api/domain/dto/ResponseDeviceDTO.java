package sample.api.domain.dto;

import lombok.*;
import org.springframework.data.domain.Page;
import sample.api.domain.entity.DeviceEntity;
import sample.api.domain.enums.device.classification.os.DeviceOsClassification;
import sample.api.domain.enums.device.classification.status.DeviceStatusClassification;
import sample.api.domain.enums.device.classification.type.DeviceTypeClassification;
import sample.api.domain.enums.device.code.status.DeviceStatusCode;
import sample.api.domain.mapper.ResponseDeviceMapper;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDeviceDTO extends BaseResponseDTO<DeviceEntity, ResponseDeviceDTO> {

    private int id;

    private DeviceStatusClassification statusClassification;

    private DeviceTypeClassification typeClassification;

    private DeviceOsClassification osClassification;

    private String sn;

    private String modelName;

    private String educationInstitution;

    private String macAddress;

    @Builder.Default
    private DeviceStatusCode statusCode = DeviceStatusCode.POSSESSION;

    private String deviceUsed;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Override
    public ResponseDeviceDTO fromEntity(DeviceEntity e) {
        return ResponseDeviceMapper.MAPPER.toDto(e);
    }

    @Override
    public Page<ResponseDeviceDTO> toDtoList(Page<DeviceEntity> deviceEntityList) {
        return deviceEntityList.map(ResponseDeviceMapper.MAPPER::toDto);
    }
}
