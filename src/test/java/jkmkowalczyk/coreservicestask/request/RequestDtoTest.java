package jkmkowalczyk.coreservicestask.request;

import jkmkowalczyk.coreservicestask.testutils.TestUtil;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestDtoTest {

    private TestUtil testUtil;

    @Before
    public void setUp() {
        testUtil = new TestUtil();
    }

    @Test
    public void shouldCreateRequestFromRequestDto() {
        RequestDto requestDto = testUtil.generateRandomRequestDto();

        Request request = requestDto.toEntity();

        assertThat(requestDto.getClientId()).isEqualTo(request.getClientId());
        assertThat(requestDto.getRequestId()).isEqualTo(request.getRequestId());
        assertThat(requestDto.getName()).isEqualTo(request.getName());
        assertThat(requestDto.getQuantity()).isEqualTo(request.getQuantity());
        assertThat(requestDto.getPrice()).isEqualTo(request.getPrice());
    }
}
