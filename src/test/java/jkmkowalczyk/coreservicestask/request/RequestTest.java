package jkmkowalczyk.coreservicestask.request;

import jkmkowalczyk.coreservicestask.testutils.TestUtil;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestTest {
    private TestUtil testUtil;

    @Before
    public void setUp() {
        testUtil = new TestUtil();
    }

    @Test
    public void shouldCreateRequestDtoFromRequest() {
        Request request = testUtil.generateRandomRequest();

        RequestDto requestDto = request.toDto();

        assertThat(request.getClientId()).isEqualTo(requestDto.getClientId());
        assertThat(request.getRequestId()).isEqualTo(requestDto.getRequestId());
        assertThat(request.getName()).isEqualTo(requestDto.getName());
        assertThat(request.getQuantity()).isEqualTo(requestDto.getQuantity());
        assertThat(request.getPrice()).isEqualTo(requestDto.getPrice());
    }
}
