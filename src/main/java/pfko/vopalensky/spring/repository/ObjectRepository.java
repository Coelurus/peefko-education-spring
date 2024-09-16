package pfko.vopalensky.spring.repository;

import java.util.List;

/**
 * Interface to provide the contract for repositories to implement.
 *
 * @param <T> Type of items stored in repo
 */
public interface ObjectRepository<T> {

    /**
     * Save item into repo.
     *
     * @param t item to store
     */
    public void store(T t);

    /**
     * Get item from repo at ID.
     *
     * @param id ID of item to return
     * @return found item if item with this ID exists. Otherwise null
     */
    public T get(Long id);

    /**
     * Delete item at ID.
     *
     * @param id ID of item to delete
     * @return Deleted item
     */
    public T delete(Long id);

    /**
     * Get all items from repo
     *
     * @return List of all items in repo
     */
    public List<T> findAll();
}
