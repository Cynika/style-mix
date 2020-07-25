package com.Util;

import java.io.IOException;
import java.math.BigDecimal;

public class valueFilter {
    public static double filter2f(float Min, float Max, float min, float max, float in) {
        double result;
        double Min_ = Min;
        double Max_ = Max;
        double min_ = min;
        double max_ = max;
        double in_ = in;

        if (in > max) {
            return Max_;
        }
        if (in < min) {
            return Min_;
        }
        result = (((Max_ - Min_) * (in_ - min_)) / (max_ - min_)) + Min_;
        return result;
    }

    public static String filter2s(float Min, float Max, float min, float max, float in) {
        double fresult = filter2f(Min, Max, min, max, in);
        String result = Double.toString(fresult);
        return result;
    }
}
