package jkmkowalczyk.coreservicestask.testutils;

import jkmkowalczyk.coreservicestask.request.Request;
import jkmkowalczyk.coreservicestask.request.RequestDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class TestUtil {

    private Random random = new Random();
    private BigDecimal bigDecimal = new BigDecimal(random.nextDouble() * 10);

    private Request request = new Request.Builder()
            .clientId(randomAlphanumeric(5))
            .price(bigDecimal.setScale(2, RoundingMode.CEILING).doubleValue())
            .name(randomAlphabetic(5))
            .quantity(random.nextInt(10) + 1)
            .requestId((long) random.nextInt(1000))
            .build();

    private RequestDto requestDto = new RequestDto.Builder()
            .clientId(randomAlphanumeric(5))
            .price(bigDecimal.setScale(2, RoundingMode.CEILING).doubleValue())
            .name(randomAlphabetic(5))
            .quantity(random.nextInt(10) + 1)
            .requestId((long) random.nextInt(1000))
            .build();


    private NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);

    public NumberFormat getNumberFormat() {
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);
        return numberFormat;
    }


    public Request generateRandomRequest() {
        return request;
    }

    public RequestDto generateRandomRequestDto() {
        return requestDto;
    }
}
