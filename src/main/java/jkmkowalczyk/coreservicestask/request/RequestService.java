package jkmkowalczyk.coreservicestask.request;

import jkmkowalczyk.coreservicestask.exception.ClientNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class provides methods for saving and retrieving data for Requests from the database.
 */
@Service
public class RequestService {
    private RequestRepository requestRepository;

    RequestService(final RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    /**
     * Gets RequestDto, maps it to Request and saves it in the database.
     *
     * @param request request to be saved
     * @return request that was saved
     */
    public final Request save(final RequestDto request) {
        return requestRepository.save(request.toEntity());
    }

    /**
     * Finds all Requests from the database, maps them to RequestDto and returns it as List.
     *
     * @return all saved requests
     */
    public final List<RequestDto> findAll() {
        return requestRepository.findAll().stream()
                .map(Request::toDto).collect(Collectors.toList());
    }

    /**
     * Finds all Requests for provided client from the database, maps them to RequestDto and returns it as List.
     *
     * @param clientId client to filter by
     * @return all saved requests for provided client
     * @throws ClientNotFoundException when no requests are found for specified client
     */
    public final List<RequestDto> findByClientId(final String clientId) throws ClientNotFoundException {
        List<RequestDto> requests = requestRepository.findByClientId(clientId).stream()
                .map(Request::toDto).collect(Collectors.toList());
        if (requests.size() == 0) {
            throw new ClientNotFoundException("No requests found for client: " + clientId);
        }
        return requests;
    }
}