package jkmkowalczyk.coreservicestask.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.text.NumberFormat;

import static jkmkowalczyk.coreservicestask.utils.NumberFormatUtil.*;

public class NumberFormatUtilTest {



    @Test
    public void shouldReturnValueWithTwoDecimalPointsWhenGivenInteger() {
        //given
        NumberFormat numberFormat = setUpNumberFormat();
        int test = 1;

        //when
        String formatted = numberFormat.format(test);

        //then
        Assertions.assertEquals("1.00", formatted);
    }

    @Test
    public void shouldReturnValueWithTwoDecimalPointsWhenGivenDoubleWithOneDecimalPoint() {
        //given
        NumberFormat numberFormat = setUpNumberFormat();
        double test = 1.0;

        //when
        String formatted = numberFormat.format(test);

        //then
        Assertions.assertEquals("1.00", formatted);
    }

    @Test
    public void shouldReturnValueWithTwoDecimalPointsWhenGivenDoubleWithMoreThanTwoDecimalPoint() {
        //given
        NumberFormat numberFormat = setUpNumberFormat();
        double test = 1.01234;

        //when
        String formatted = numberFormat.format(test);

        //then
        Assertions.assertEquals("1.01", formatted);
    }
}
