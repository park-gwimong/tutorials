package sample.api.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;
import sample.api.domain.dto.RequestDeviceDTO;
import sample.api.domain.entity.DeviceEntity;


@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface RequestDeviceMapper extends EntityMapper<DeviceEntity, RequestDeviceDTO> {
    RequestDeviceMapper MAPPER = Mappers.getMapper(RequestDeviceMapper.class);
}
