package sample.api.domain.dto;

import java.io.Serializable;

public abstract class BaseDTO<E, T extends BaseDTO<E, T>> implements Serializable {
}
