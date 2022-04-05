package com.toolrent.toolrentcheckout;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RentalAgreement {
    private String toolCode;
    private String toolType;
    private String toolBrand;
    private int rentalDuration;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private BigDecimal dailyCharge;
    private int chargeDays;
    private BigDecimal preDiscountCharge;
    private BigDecimal discountPercentHundreds;
    private BigDecimal discountAmount;
    private BigDecimal finalCharge;

    public void print() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        System.out.println("Tool code: " + toolCode);
        System.out.println("Tool type: " + toolType);
        System.out.println("Tool brand: " + toolBrand);
        System.out.println("Rental days: " + rentalDuration);
        System.out.println("Check out date: " + checkoutDate.format(dateTimeFormatter));
        System.out.println("Due date: " + dueDate.format(dateTimeFormatter));
        System.out.println("Daily rental charge: $" + decimalFormat.format(dailyCharge));
        System.out.println("Charge days: " + chargeDays);
        System.out.println("Pre-discount charge: $" + decimalFormat.format(preDiscountCharge));
        System.out.println("Discount percent: " + discountPercentHundreds + "%");
        System.out.println("Discount amount: $" + decimalFormat.format(discountAmount));
        System.out.println("Final charge: $" + decimalFormat.format(finalCharge));
    }

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    public String getToolBrand() {
        return toolBrand;
    }

    public void setToolBrand(String toolBrand) {
        this.toolBrand = toolBrand;
    }

    public int getRentalDuration() {
        return rentalDuration;
    }

    public void setRentalDuration(int rentalDuration) {
        this.rentalDuration = rentalDuration;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getDiscountPercentHundreds() {
        return discountPercentHundreds;
    }

    public void setDiscountPercentHundreds(BigDecimal discountPercentHundreds) {
        this.discountPercentHundreds = discountPercentHundreds;
    }

    public BigDecimal getDailyCharge() {
        return dailyCharge;
    }

    public void setDailyCharge(BigDecimal dailyCharge) {
        this.dailyCharge = dailyCharge;
    }

    public int getChargeDays() {
        return chargeDays;
    }

    public void setChargeDays(int chargeDays) {
        this.chargeDays = chargeDays;
    }

    public BigDecimal getPreDiscountCharge() {
        return preDiscountCharge;
    }

    public void setPreDiscountCharge(BigDecimal preDiscountCharge) {
        this.preDiscountCharge = preDiscountCharge;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getFinalCharge() {
        return finalCharge;
    }

    public void setFinalCharge(BigDecimal finalCharge) {
        this.finalCharge = finalCharge;
    }

}
