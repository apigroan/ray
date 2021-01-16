package com.blindskipper.ray.common.helper;

public class StringHelper {
    
    /**
     * Convert index to String
     * maxIndex index result
     *   9        8    #8
     *  15        8    #08
     * 120       12    #012
     * 120      120    #120
     */
    public static String formatIndex(int maxIndex, int index) {
        int idxWidth = String.valueOf(maxIndex).length();
        String fmtStr = "#%0" + idxWidth + "d";
        return String.format(fmtStr, index);
    }
    
    public static String chipAndAppendEllipsis(String original, int maxLength) {
        original = original.replaceAll("\\r|\\n", "");
        
        if (original.length() <= maxLength) {
            return original;
        }
        
        int chipPosition= maxLength - 3;
        char openingChipChar = original.charAt(chipPosition);
        
        if (Character.isLowSurrogate(openingChipChar)) {
            return original.substring(0, chipPosition - 1) + "...";
        } else {
            return original.substring(0, chipPosition) + "...";
        }
    }
}
