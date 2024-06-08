package de.jensostertag;

import java.util.Map;

public class MathUtils {
    public static double percentile(Map<Integer, Integer> histogram, double percentile) {
        int total = 0;
        for(Map.Entry<Integer, Integer> entry : histogram.entrySet()) {
            total += entry.getValue();
        }
        int target = (int) Math.ceil(total * percentile);
        int sum = 0;
        for(Map.Entry<Integer, Integer> entry : histogram.entrySet()) {
            sum += entry.getValue();
            if(sum >= target) {
                return entry.getKey();
            }
        }

        return 0;
    }
}
