package com.toolrent.toolrentcheckout;

import java.math.BigDecimal;

public class ToolType {

    private String toolType;
    private BigDecimal dailyCharge;
    private boolean isChargedOnWeekdays;
    private boolean isChargedOnWeekends;
    private boolean isChargedOnHolidays;

    // add no-args constructor?

    public ToolType(String toolType, String dailyCharge, boolean isChargedOnWeekdays, boolean isChargedOnWeekends, boolean isChargedOnHolidays) {
        this.toolType = toolType;
        this.dailyCharge = new BigDecimal(dailyCharge);
        this.isChargedOnWeekdays = isChargedOnWeekdays;
        this.isChargedOnWeekends = isChargedOnWeekends;
        this.isChargedOnHolidays = isChargedOnHolidays;
    }

    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    public BigDecimal getDailyCharge() {
        return dailyCharge;
    }

    public void setDailyCharge(BigDecimal dailyCharge) {
        this.dailyCharge = dailyCharge;
    }

    public boolean isChargedOnWeekdays() {
        return isChargedOnWeekdays;
    }

    public void setChargedOnWeekdays(boolean chargedOnWeekdays) {
        isChargedOnWeekdays = chargedOnWeekdays;
    }

    public boolean isChargedOnWeekends() {
        return isChargedOnWeekends;
    }

    public void setChargedOnWeekends(boolean chargedOnWeekends) {
        isChargedOnWeekends = chargedOnWeekends;
    }

    public boolean isChargedOnHolidays() {
        return isChargedOnHolidays;
    }

    public void setChargedOnHolidays(boolean chargedOnHolidays) {
        isChargedOnHolidays = chargedOnHolidays;
    }
}
