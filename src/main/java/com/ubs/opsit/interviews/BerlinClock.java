package com.ubs.opsit.interviews;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.stream.IntStream;

import static com.ubs.opsit.interviews.TimeConverterConstants.*;

/**
 * This class is used to convert time string(HH:mm:ss) to Berlin clock time.
 */
public class BerlinClock implements TimeConverter {

    private static final int HOUR_LAMPS_COUNT = 4;
    private static final int MINUTE_TOP_ROW_LAMPS_COUNT = 11;
    private static final int MINUTE_BOTTOM_ROW_LAMPS_COUNT = HOUR_LAMPS_COUNT;
    private static final int ROW_DIVISOR_UNIT = 5;
    private static final String OFF_LAMP = "O";

    /**
     * The logger instance
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BerlinClock.class);

    /**
     * the hours field
     */
    private transient Integer hours;

    /**
     * the minutes field
     */
    private transient Integer minutes;

    /**
     * the seconds field
     */
    private transient Integer seconds;

    /**
     * It is used to convert string time(HH:mm:ss) to Berlin clock time.
     *
     * @param pTime - the time parameter
     * @return Berlin clock time
     */
    @Override
    public String convertTime(String pTime) {
        String convertedTime = null;
        try {
            // initialize instance fields: hours, minutes and seconds
            initialize(pTime);
            // get time using instance fields: hours, minutes and seconds
            convertedTime = getTime();
        } catch (TimeConverterException e) {
            LOGGER.error(e.getMessage());
        }

        return convertedTime;
    }

    /**
     * This method converts hours, minutes and seconds to Berlin clock lamp rows which is equivalent to hours, minutes and seconds.
     *
     * @return Berlin clock time
     * @throws TimeConverterException - the custom exception
     */
    private String getTime() throws TimeConverterException {
        // throw exception when any of the instance fields: hours, minutes and seconds are null
        if (this.hours == null || this.minutes == null || this.seconds == null) {
            throw new TimeConverterException(INVALID_TIME);
        }

        // return Berlin clock seconds, hours and minutes lamps representation
        return getSecond(this.seconds) + NEW_LINE +
                getHour(this.hours) + NEW_LINE +
                getMinute(this.minutes);
    }

    /**
     * It is used to get representation of Berlin clock seconds lamps row.
     *
     * @param pSeconds - the seconds parameter
     * @return representation of Berlin clock seconds lamps row
     */
    private String getSecond(int pSeconds) {
        // return Yellow color lamp if second is not even
        return pSeconds % 2 != 0 ? OFF_LAMP : String.valueOf(Color.YELLOW.getCode());
    }

    /**
     * It is used to get representation of Berlin clock hours lamps row.
     *
     * @param pHours - the hours parameter
     * @return representation of Berlin clock seconds lamps row
     */
    private String getHour(int pHours) {
        // return representation of hour lamps top and bottom rows
        return getTopHourRow(pHours) + NEW_LINE + getBottomHourRow(pHours);
    }

    /**
     * It returns top hour lamps representation.
     *
     * @param pHours - the hours parameter
     * @return representation of top hour lamps
     */
    private String getTopHourRow(int pHours) {
        // return representation of hour top lamps row
        return getLampsRow(HOUR_LAMPS_COUNT, pHours / ROW_DIVISOR_UNIT, Color.RED);
    }

    /**
     * It returns bottom hour lamps representation.
     *
     * @param pHours - the hours parameter
     * @return representation of bottom hour lamps
     */
    private String getBottomHourRow(int pHours) {
        // return representation of hour bottom lamps row
        return getLampsRow(HOUR_LAMPS_COUNT, pHours % ROW_DIVISOR_UNIT, Color.RED);
    }

    /**
     * It is used to get representation of Berlin clock minutes lamps row.
     *
     * @param pMinutes - the minutes parameter
     * @return representation of Berlin clock minutes lamps row
     */
    private String getMinute(int pMinutes) {
        // return representation of minute lamps top and bottom rows
        return getTopMinuteRow(pMinutes) + NEW_LINE + getBottomMinuteRow(pMinutes);
    }

    /**
     * It returns top minutes lamps representation.
     *
     * @param pMinutes - the minutes parameter
     * @return representation of top minutes lamps
     */
    private String getTopMinuteRow(int pMinutes) {
        // return representation of minute top lamps row
        String lamps = getLampsRow(MINUTE_TOP_ROW_LAMPS_COUNT, pMinutes / ROW_DIVISOR_UNIT, Color.YELLOW);
        // replace all YYY with YYR in order to represent 15, 30 and 45 minutes with Red color.
        lamps = lamps.replace("YYY", "YYR");
        return lamps;
    }

    /**
     * It returns bottom minutes lamps representation.
     *
     * @param pMinutes - the minutes parameter
     * @return representation of bottom minutes lamps
     */
    private String getBottomMinuteRow(int pMinutes) {
        // return representation of minute bottom lamps row
        return getLampsRow(MINUTE_BOTTOM_ROW_LAMPS_COUNT, pMinutes % ROW_DIVISOR_UNIT, Color.YELLOW);
    }

    /**
     * This method returns representation of hour/minutes lamps rows.
     *
     * @param count - the total lamps present in a row
     * @param litLamps - the total lit lamps in a row
     * @param color - the color of lamps in a row
     * @return representation of hour/minutes lamps rows
     */
    private String getLampsRow(int count, int litLamps, Color color) {
        StringBuilder lamps = new StringBuilder("");
        IntStream.rangeClosed(1, count)
                .forEach(i -> lamps.append(i <= litLamps ? color.getCode() : OFF_LAMP));

        return lamps.toString();
    }

    /**
     * This method validates input time string and as well initializes hours, minutes and seconds field.
     *
     * @param pTime - the time parameter
     * @throws TimeConverterException - the custom exception
     */
    private void initialize(String pTime) throws TimeConverterException {
        if (StringUtils.isEmpty(pTime)) {
            throw new TimeConverterException(MessageFormat.format(BLANK_TIME, pTime));
        }

        String[] timeArr = pTime.split(":");
        if (timeArr.length != 3) {
            throw new TimeConverterException(MessageFormat.format(INVALID_TIME, pTime));
        }

        try {
            int hours = Integer.valueOf(timeArr[0]);
            int minutes = Integer.valueOf(timeArr[1]);
            int seconds = Integer.valueOf(timeArr[2]);

            if (hours < 0 || hours > 24) throw new TimeConverterException(MessageFormat.format(INVALID_HOUR, hours));
            if (minutes < 0 || minutes > 60)
                throw new TimeConverterException(MessageFormat.format(INVALID_MINUTE, minutes));
            if (seconds < 0 || seconds > 60)
                throw new TimeConverterException(MessageFormat.format(INVALID_SECOND, seconds));

            this.hours = hours;
            this.minutes = minutes;
            this.seconds = seconds;
        } catch (NumberFormatException e) {
            throw new TimeConverterException(MessageFormat.format(INVALID_TIME, pTime));
        }
    }

}