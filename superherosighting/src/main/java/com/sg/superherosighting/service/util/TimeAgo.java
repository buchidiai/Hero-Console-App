/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 *
 * @author louie
 */
public class TimeAgo {

    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;

    private static final String ONE_SECOND_AGO = " Second ago";
    private static final String ONE_SECONDS_AGO = " Seconds ago";
    private static final String ONE_MINUTE_AGO = " minute ago";
    private static final String ONE_MINUTES_AGO = " minutes ago";
    private static final String ONE_HOUR_AGO = " hour ago";
    private static final String ONE_HOURS_AGO = " hours ago";
    private static final String ONE_DAY_AGO = " Day ago";
    private static final String ONE_DAYS_AGO = " Days ago";
    private static final String ONE_MONTH_AGO = " Month ago";
    private static final String ONE_MONTHS_AGO = " Months ago";
    private static final String ONE_YEAR_AGO = " Year ago";
    private static final String ONE_YEARS_AGO = " Years ago";

    public static String format(LocalDateTime createTime) {

        long compareTime = getTimestampOfDateTime(createTime);

        LocalDateTime now = LocalDateTime.now();
        long rightTime = getTimestampOfDateTime(now);

        long delta = rightTime - compareTime;
        if (delta < 1L * ONE_MINUTE) {

            long seconds = toSeconds(delta);

            long newSeconds = (seconds <= 0 ? 1 : seconds);

            return newSeconds == 1 ? newSeconds + ONE_SECOND_AGO : newSeconds + ONE_SECONDS_AGO;
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);

            long newMinutes = (minutes <= 0 ? 1 : minutes);

            return newMinutes == 1 ? newMinutes + ONE_MINUTE_AGO : newMinutes + ONE_MINUTES_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);

            long newHours = (hours <= 0 ? 1 : hours);

            return newHours == 1 ? newHours + ONE_HOUR_AGO : newHours + ONE_HOURS_AGO;
        }
        if (delta < 48L * ONE_HOUR) {
            return "yesterday";
        }
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);

            long newDays = (days <= 0 ? 1 : days);

            return newDays == 1 ? newDays + ONE_DAY_AGO : newDays + ONE_DAYS_AGO;
        }
        if (delta < 12L * 4L * ONE_WEEK) {
            long months = toMonths(delta);

            long newMonths = (months <= 0 ? 1 : months);

            return newMonths == 1 ? newMonths + ONE_MONTH_AGO : newMonths + ONE_MONTHS_AGO;
        } else {
            long years = toYears(delta);
            long newYears = (years <= 0 ? 1 : years);
            return newYears == 1 ? newYears + ONE_YEAR_AGO : newYears + ONE_YEARS_AGO;
        }
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }

    public static long getTimestampOfDateTime(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }

}
