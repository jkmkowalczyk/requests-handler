package jkmkowalczyk.coreservicestask.request;

import jkmkowalczyk.coreservicestask.exception.ClientNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This interface provides methods for saving and database interactions.
 */
@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

    /**
     * Finds requests based on provided clientId.
     *
     * @param clientId clientId to query by
     * @return list of requests for specified client
     * @throws ClientNotFoundException when no requests are found for specified client
     */
    List<Request> findByClientId(final String clientId) throws ClientNotFoundException;
}
