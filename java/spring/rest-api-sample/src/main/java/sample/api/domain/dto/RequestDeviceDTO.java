package sample.api.domain.dto;

import lombok.*;
import sample.api.domain.entity.DeviceEntity;
import sample.api.domain.enums.device.classification.os.DeviceOsClassification;
import sample.api.domain.enums.device.classification.status.DeviceStatusClassification;
import sample.api.domain.enums.device.classification.type.DeviceTypeClassification;
import sample.api.domain.enums.device.code.status.DeviceStatusCode;
import sample.api.domain.mapper.RequestDeviceMapper;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDeviceDTO extends BaseRequestDTO<DeviceEntity, RequestDeviceDTO> {

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

    @Override
    public DeviceEntity toEntity() throws IllegalAccessException {
        return RequestDeviceMapper.MAPPER.toEntity(this);
    }

}
