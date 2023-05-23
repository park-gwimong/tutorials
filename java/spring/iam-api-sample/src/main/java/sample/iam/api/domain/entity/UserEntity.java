package sample.iam.api.domain.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import org.hibernate.annotations.DynamicInsert;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@DynamicInsert
@Table(name = "dtn_user")
public class UserEntity extends BaseEntity {

    @Id
    @JsonView({UserEntity.class})
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonView({UserEntity.class})
    @Column(length = 100, nullable = false)
    private String userId;

    @Transient
    private String password;

    @Column(nullable = false)
    private String encryptedPassword;

    @JsonView({UserEntity.class})
    @Column(length = 200, nullable = false)
    private String email;

    @JsonView({UserEntity.class})
    @Column(length = 50, nullable = false)
    private String name;

    @Builder
    public UserEntity(String userId, String password, String email, String name ) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.name = name;
    }

}
