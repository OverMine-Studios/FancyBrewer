package dev.risas.fancybrewer.utilities;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JavaUtil {

    public Integer tryParseInt(String string) {
        try {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException exception) {
            return null;
        }
    }
}
