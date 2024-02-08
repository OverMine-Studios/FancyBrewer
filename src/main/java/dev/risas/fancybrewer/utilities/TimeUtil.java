package dev.risas.fancybrewer.utilities;

import lombok.experimental.UtilityClass;

import java.util.concurrent.TimeUnit;

@UtilityClass
public class TimeUtil {

    public String formatInteger(int time) {
        int millis = time;

        if (millis <= 0) return "0s";

        long days = TimeUnit.SECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);

        long hours = TimeUnit.SECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);

        long minutes = TimeUnit.SECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);

        long seconds = TimeUnit.SECONDS.toSeconds(millis);

        StringBuilder builder = new StringBuilder();

        if (days > 0L) {
            builder.append(days).append("d ");
        }
        if (hours > 0L) {
            builder.append(hours).append("h ");
        }
        if (minutes > 0L) {
            builder.append(minutes).append("m ");
        }
        if (seconds > 0L) {
            builder.append(seconds).append("s ");
        }
        return builder.toString().trim();
    }

    public String formatMillis(long time) {
        long millis = time;

        if (millis <= 0L) return "0s";

        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);

        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);

        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);

        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        StringBuilder builder = new StringBuilder();

        if (days > 0L) {
            builder.append(days).append("d ");
        }
        if (hours > 0L) {
            builder.append(hours).append("h ");
        }
        if (minutes > 0L) {
            builder.append(minutes).append("m ");
        }
        if (seconds > 0L) {
            builder.append(seconds).append("s ");
        }
        return builder.toString().trim();
    }
}
