package com.toolrent.toolrentcheckout;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class Checkout {
    private final Inventory toolsInventory;
    private final RentalCalendar calendar;
    private Tool tool;
    private int rentalDuration;
    private BigDecimal discountPercentHundreds;
    private LocalDate checkoutDate;

    public Checkout(Inventory toolsInventory, RentalCalendar calendar) {
        this.toolsInventory = toolsInventory;
        this.calendar = calendar;
    }

    public RentalAgreement generateRentalAgreement() throws InvalidDaysException, InvalidPercentException {

        try {
            verifyRentalDuration(this.rentalDuration);
        } catch (InvalidDaysException e) {
            System.err.println(e.getClass().getCanonicalName() + ": " + e.getMessage());
            throw e;
        }

        try {
            verifyDiscountPercentHundreds(this.discountPercentHundreds);
        } catch (InvalidPercentException e) {
            System.err.println(e.getClass().getCanonicalName() + ": " + e.getMessage());
            throw e;
        }

        RentalAgreement agreement = new RentalAgreement();
        agreement.setToolCode(this.tool.getToolCode());
        agreement.setToolType(this.tool.getToolType().getToolType());
        agreement.setToolBrand(this.tool.getBrand());
        agreement.setRentalDuration(this.rentalDuration);
        agreement.setCheckoutDate(this.checkoutDate);
        agreement.setDiscountPercentHundreds(this.discountPercentHundreds);
        agreement.setDailyCharge(this.tool.getToolType().getDailyCharge());
        BigDecimal discountPercent = calculateDiscountPercent(this.discountPercentHundreds);
        agreement.setDueDate(calendar.calculateDueDate(this.rentalDuration, this.checkoutDate));
        agreement.setChargeDays(calendar.calculateChargeDays(this.checkoutDate, this.rentalDuration, tool.getToolType()));
        agreement.setPreDiscountCharge(calculatePreDiscountCharge(agreement.getDailyCharge(), agreement.getChargeDays()));
        agreement.setDiscountAmount(calculateDiscountAmount(agreement.getPreDiscountCharge(), discountPercent));
        agreement.setFinalCharge(calculateFinalCharge(agreement.getPreDiscountCharge(), agreement.getDiscountAmount()));
        return agreement;
    }

    public Tool getToolByToolCode(String toolCode) throws InvalidToolCodeException {
        if (this.toolsInventory.getTools().get(toolCode) != null) {
            return this.toolsInventory.getTools().get(toolCode);
        } else {
            throw new InvalidToolCodeException("Tool code is invalid.");
        }
    }

    public void verifyRentalDuration(int rentalDuration) {
        if (!(rentalDuration > 0)) {
            throw new InvalidDaysException("Rental duration must be one or more days.");
        }
    }

    public void verifyDiscountPercentHundreds(BigDecimal discountPercentHundreds) {
        if (!(discountPercentHundreds.compareTo(BigDecimal.ZERO) >= 0 && discountPercentHundreds.compareTo(new BigDecimal(100)) <= 0)) {
            throw new InvalidPercentException("discount percent must be 0-100.");
        }
    }

    private BigDecimal calculateDiscountPercent(BigDecimal discountPercentHundreds) {
        return discountPercentHundreds.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculatePreDiscountCharge(BigDecimal dailyCharge, int chargeDays) {
        return dailyCharge.multiply(new BigDecimal(chargeDays)).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateDiscountAmount(BigDecimal preDiscountCharge, BigDecimal discountPercent) {
        return preDiscountCharge.multiply(discountPercent).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateFinalCharge(BigDecimal preDiscountCharge, BigDecimal discountAmount) {
        return preDiscountCharge.subtract(discountAmount).setScale(2, RoundingMode.HALF_UP);
    }

    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public void setRentalDuration(int rentalDuration) {
        this.rentalDuration = rentalDuration;
    }

    public void setDiscountPercentHundreds(BigDecimal discountPercentHundreds) {
        this.discountPercentHundreds = discountPercentHundreds;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }
}

