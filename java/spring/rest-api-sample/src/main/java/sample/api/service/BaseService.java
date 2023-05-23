package sample.api.service;

import sample.api.domain.entity.BaseEntity;
import sample.api.exception.DatabaseException;
import sample.api.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The interface Base service.
 *
 * @param <T> the type parameter
 */
public interface BaseService<T extends BaseEntity> {

  /**
   * Read t.
   *
   * @param id the id
   * @return the t
   * @throws NotFoundException the not found exception
   */
  T read(int id) throws NotFoundException;

  /**
   * Read all page.
   *
   * @param pageable the pageable
   * @return the page
   */
  Page<T> readAll(Pageable pageable);

  /**
   * Create t.
   *
   * @param domain the domain
   * @return the t
   * @throws DatabaseException the database exception
   */
  T create(T domain) throws DatabaseException;

  /**
   * Update t.
   *
   * @param domain the domain
   * @return the t
   * @throws DatabaseException the database exception
   */
  T update(T domain) throws DatabaseException;

  /**
   * Patch t.
   *
   * @param id     the id
   * @param domain the domain
   * @return the t
   * @throws DatabaseException the database exception
   * @throws NotFoundException the not found exception
   */
  T patch(int id, T domain) throws DatabaseException, NotFoundException;

  /**
   * Delete.
   *
   * @param id the id
   * @throws DatabaseException the database exception
   */
  void delete(int id) throws DatabaseException;

  /**
   * Delete all.
   *
   * @throws DatabaseException the database exception
   */
  void deleteAll() throws DatabaseException;

}
