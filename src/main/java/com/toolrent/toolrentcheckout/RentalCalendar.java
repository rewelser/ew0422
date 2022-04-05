package com.toolrent.toolrentcheckout;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class RentalCalendar {
    private final Set<String> holidays = new HashSet<>();

    public void addHoliday(final String holiday) {
        holidays.add(holiday.toUpperCase(Locale.ROOT));
    }

    public LocalDate observeIndependenceDayOn(LocalDate localDate) {
        LocalDate independenceDay = LocalDate.of(localDate.getYear(), 7, 4);

        if (independenceDay.getDayOfWeek() == DayOfWeek.SATURDAY) {
            independenceDay = independenceDay.minusDays(1);
        } else if (independenceDay.getDayOfWeek() == DayOfWeek.SUNDAY) {
            independenceDay = independenceDay.plusDays(1);
        }
        return independenceDay;
    }

    public LocalDate observeLaborDayOn(LocalDate localDate) {
        LocalDate laborDay = LocalDate.of(localDate.getYear(), 9, 1);

        for (int i = 0; i < 7; i++) {
            if (laborDay.plusDays(i).getDayOfWeek() == DayOfWeek.MONDAY) {
                laborDay = laborDay.plusDays(i);
            }
        }
        return laborDay;
    }

    public boolean isHoliday(LocalDate localDate) {
        LocalDate independenceDay = observeIndependenceDayOn(localDate);
        LocalDate laborDay = observeLaborDayOn(localDate);
        return localDate.isEqual(independenceDay) || localDate.isEqual(laborDay);
    }

    public boolean isWeekend(LocalDate localDate) {
        return localDate.getDayOfWeek() == DayOfWeek.SATURDAY || localDate.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    public LocalDate calculateCheckoutDate(LocalDate checkoutDate) {
        return checkoutDate;
    }

    public LocalDate calculateCheckoutDate(String checkoutDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return LocalDate.parse(checkoutDate, formatter);
    }

    public int calculateChargeDays(LocalDate checkoutDate, int rentalDuration, ToolType toolType) {
        int chargeDays = 0;
        checkoutDate = checkoutDate.plusDays(1); // starting count on day following checkout date

        for (int i = 0; i < rentalDuration; i++) {
            if (isWeekend(checkoutDate.plusDays(i))) {
                if (toolType.isChargedOnWeekends()) {
                    chargeDays++;
                }
            } else if (isHoliday(checkoutDate.plusDays(i))) {
                if (toolType.isChargedOnHolidays()) {
                    chargeDays++;
                }
            } else if (!isWeekend(checkoutDate.plusDays(i))) {
                if (toolType.isChargedOnWeekdays()) {
                    chargeDays++;
                }
            }
        }
        return chargeDays;
    }

    public LocalDate calculateDueDate(int rentalDuration, LocalDate checkoutDate) {
        return checkoutDate.plusDays(rentalDuration);
    }

}

