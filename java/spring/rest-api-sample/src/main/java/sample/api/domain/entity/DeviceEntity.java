package sample.api.domain.entity;


import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import sample.api.domain.enums.device.classification.os.DeviceOsClassification;
import sample.api.domain.enums.device.classification.os.DeviceOsClassificationConverter;
import sample.api.domain.enums.device.classification.status.DeviceStatusClassification;
import sample.api.domain.enums.device.classification.status.DeviceStatusClassificationConverter;
import sample.api.domain.enums.device.classification.type.DeviceTypeClassification;
import sample.api.domain.enums.device.classification.type.DeviceTypeClassificationConverter;
import sample.api.domain.enums.device.code.status.DeviceStatusCode;
import sample.api.domain.enums.device.code.status.DeviceStatusCodeConverter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;

/**
 * The type Device entity.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@DynamicInsert
@Table(name = "dtn_device")
public class DeviceEntity extends BaseEntity {

    @JsonView({DeviceEntity.class})
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    @Comment("단말기 요청 ID")
    private int id;


    @JsonView({DeviceEntity.class})
    @Column(name = "sttus_clsf")
    @NotNull
    @Comment("상태 분류")
    @Convert(converter = DeviceStatusClassificationConverter.class)
    private DeviceStatusClassification statusClassification;


    @JsonView({DeviceEntity.class})
    @Column(name = "type_clsf")
    @NotNull
    @Comment("타입 분류")
    @Convert(converter = DeviceTypeClassificationConverter.class)
    private DeviceTypeClassification typeClassification;


    @JsonView({DeviceEntity.class})
    @Column(name = "os_clsf")
    @NotNull
    @Comment("OS 분류")
    @Convert(converter = DeviceOsClassificationConverter.class)
    private DeviceOsClassification osClassification;

    @JsonView({DeviceEntity.class})
    @Column(name = "sn")
    @Comment("시리얼 번호")
    private String sn;


    @JsonView({DeviceEntity.class})
    @Column(name = "model_nm")
    @Comment("모델명")
    private String modelName;


    @JsonView({DeviceEntity.class})
    @Column(name = "ednst")
    @Comment("교육기관")
    private String educationInstitution;


    @JsonView({DeviceEntity.class})
    @Column(name = "mac_addr")
    @Comment("Mac 주소")
    private String macAddress;


    @JsonView({DeviceEntity.class})
    @Column(name = "sttus_code")
    @ColumnDefault("00")
    @Comment("상태 코드")
    @Convert(converter = DeviceStatusCodeConverter.class)
    private DeviceStatusCode statusCode = DeviceStatusCode.POSSESSION;


    @JsonView({DeviceEntity.class})
    @Column(name = "trmnl_used_id")
    @Comment("단말기 사용 이력 ID")
    private String deviceUsed;

    @Builder
    public DeviceEntity(DeviceTypeClassification typeClassification,
                        DeviceOsClassification osClassification,
                        DeviceStatusClassification statusClassification,
                        String sn,
                        String modelName,
                        String educationInstitution,
                        String macAddress,
                        DeviceStatusCode statusCode,
                        String deviceUsed
    ) {
        this.typeClassification = typeClassification;
        this.osClassification = osClassification;
        this.statusClassification = statusClassification;
        this.sn = sn;
        this.modelName = modelName;
        this.educationInstitution = educationInstitution;
        this.macAddress = macAddress;
        if (statusCode != null) {
            this.statusCode = statusCode;
        }

        this.deviceUsed = deviceUsed;
    }

}
