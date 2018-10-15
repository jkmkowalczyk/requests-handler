package jkmkowalczyk.coreservicestask.request;

import jkmkowalczyk.coreservicestask.exception.ClientNotFoundException;
import jkmkowalczyk.coreservicestask.testutils.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RequestServiceTest {

    private RequestService requestService;
    private TestUtil testUtil;

    @Mock
    private RequestRepository requestRepository;

    @Before
    public void setUp() {
        requestService = new RequestService(requestRepository);
        testUtil = new TestUtil();
    }

    @Test
    public void shouldSaveRequest() {
        //given
        Request request = testUtil.generateRandomRequest();
        //when
        when(requestRepository.save(request)).thenReturn(request);

        //then
        assertEquals(requestService.save(request.toDto()), request);
    }

    @Test
    public void shouldFindAllRequests() {
        //given
        Request request = testUtil.generateRandomRequest();
        Request request2 = testUtil.generateRandomRequest();
        List<Request> requests = Arrays.asList(request, request2);
        //when
        when(requestRepository.findAll()).thenReturn(requests);
        //then
        assertEquals(requestService.findAll().stream().map(RequestDto::toEntity).collect(Collectors.toList()), requests);
    }

    @Test
    public void whenFindingByExistingClientIdShouldFindOneRequest() throws ClientNotFoundException {
        //given
        Request request = testUtil.generateRandomRequest();
        List<Request> requestsForClient = Arrays.asList(request);
        //when
        when(requestRepository.findByClientId(request.getClientId())).thenReturn(requestsForClient);
        //then
        assertEquals(requestService.findByClientId(request.getClientId()).stream()
                        .map(RequestDto::toEntity).collect(Collectors.toList())
                , requestsForClient);
    }

    @Test
    public void whenFindingByNonExistingClientIdShouldThrowExceptionWithCorrectMessage() throws ClientNotFoundException {
        //when
        when(requestRepository.findByClientId("2")).thenReturn(new ArrayList<>());
        //then
        ClientNotFoundException exception = assertThrows(ClientNotFoundException.class,
                () -> requestService.findByClientId("2"));
        assertEquals("No requests found for client: 2", exception.getMessage());
    }
}
