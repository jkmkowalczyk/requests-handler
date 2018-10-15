package jkmkowalczyk.coreservicestask.utils;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * This class provides methods for NumberFormat object
 */
public class NumberFormatUtil {

    private NumberFormatUtil() {
    }


    /**
     * Sets up NumberFormat to have always two decimal points.
     *
     * @return formatted NumberFormat object to contain two decimal points
     */
    public static NumberFormat setUpNumberFormat() {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);
        return numberFormat;
    }

}
