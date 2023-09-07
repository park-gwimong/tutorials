package sample.api.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;
import sample.api.domain.dto.ResponseDeviceDTO;
import sample.api.domain.entity.DeviceEntity;


@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ResponseDeviceMapper extends EntityMapper<DeviceEntity, ResponseDeviceDTO> {
    ResponseDeviceMapper MAPPER = Mappers.getMapper(ResponseDeviceMapper.class);
}
