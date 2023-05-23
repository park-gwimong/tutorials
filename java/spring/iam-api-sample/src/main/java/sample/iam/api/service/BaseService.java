package sample.iam.api.service;

import sample.iam.api.domain.entity.BaseEntity;
import sample.iam.api.exception.DatabaseException;
import sample.iam.api.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseService<T extends BaseEntity> {

  T read(int id) throws NotFoundException;

  Page<T> readAll(Pageable pageable);

  T create(T domain) throws DatabaseException;

  T update(T domain) throws DatabaseException;

  T patch(int id, T domain) throws DatabaseException, NotFoundException;

  void delete(int id) throws DatabaseException;

  void deleteAll() throws DatabaseException;

}

