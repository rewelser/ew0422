package com.toolrent.toolrentcheckout;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutTest {

    private Checkout checkout;
    private DecimalFormat decimalFormat;
    private DateTimeFormatter dateTimeFormatter;

    @BeforeEach
    void init() throws IOException {
        checkout = new Checkout(new Inventory(), new RentalCalendar());
        decimalFormat = new DecimalFormat("#,###.00");
        dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
    }

    @Test
    void testValidGetToolByToolCode() {
        assertNotNull(checkout.getToolByToolCode("JAKR"));
    }

    @Test
    void testInvalidToolCodeException() {
        assertThrows(InvalidToolCodeException.class, () -> checkout.getToolByToolCode("Invalid tool code string"));
    }

    @Test
    void testInvalidDaysException() {
        checkout.setTool(this.checkout.getToolByToolCode("JAKR"));
        checkout.setRentalDuration(0);
        checkout.setDiscountPercentHundreds(new BigDecimal(10));
        checkout.setCheckoutDate(LocalDate.parse("09/03/15", dateTimeFormatter));

        assertThrows(InvalidDaysException.class, () -> checkout.generateRentalAgreement());
    }

    // (Test 1)
    @Test
    void testInvalidPercentException() {
        checkout.setTool(this.checkout.getToolByToolCode("JAKR"));
        checkout.setRentalDuration(5);
        checkout.setDiscountPercentHundreds(new BigDecimal(101));
        checkout.setCheckoutDate(LocalDate.parse("09/03/15", dateTimeFormatter));

        assertThrows(InvalidPercentException.class, () -> checkout.generateRentalAgreement());
    }

    // (Test 2)
    @Test
    void testRentLadderOnIndependenceDayWeekend() {
        checkout.setTool(this.checkout.getToolByToolCode("LADW"));
        checkout.setRentalDuration(3);
        checkout.setDiscountPercentHundreds(new BigDecimal(10));
        checkout.setCheckoutDate(LocalDate.parse("07/02/20", dateTimeFormatter));

        assertAll(() -> assertEquals("LADW", checkout.generateRentalAgreement().getToolCode()),
                () -> assertEquals("Ladder", checkout.generateRentalAgreement().getToolType()),
                () -> assertEquals("Werner", checkout.generateRentalAgreement().getToolBrand()),
                () -> assertEquals("3", String.valueOf(checkout.generateRentalAgreement().getRentalDuration())),
                () -> assertEquals("07/02/20", checkout.generateRentalAgreement().getCheckoutDate().format(dateTimeFormatter)),
                () -> assertEquals("07/05/20", checkout.generateRentalAgreement().getDueDate().format(dateTimeFormatter)),
                () -> assertEquals("$1.99", "$" + decimalFormat.format(checkout.generateRentalAgreement().getDailyCharge())),
                () -> assertEquals("2", String.valueOf(checkout.generateRentalAgreement().getChargeDays())),
                () -> assertEquals("$3.98", "$" + decimalFormat.format(checkout.generateRentalAgreement().getPreDiscountCharge())),
                () -> assertEquals("10%", checkout.generateRentalAgreement().getDiscountPercentHundreds() + "%"),
                () -> assertEquals("$.40", "$" + decimalFormat.format(checkout.generateRentalAgreement().getDiscountAmount())),
                () -> assertEquals("$3.58", "$" + decimalFormat.format(checkout.generateRentalAgreement().getFinalCharge())));
    }

    // (Test 3)
    @Test
    void testRentChainsawThroughIndependenceDayWeekend() {
        checkout.setTool(this.checkout.getToolByToolCode("CHNS"));
        checkout.setRentalDuration(5);
        checkout.setDiscountPercentHundreds(new BigDecimal(25));
        checkout.setCheckoutDate(LocalDate.parse("07/02/15", dateTimeFormatter));

        assertAll(() -> assertEquals("CHNS", checkout.generateRentalAgreement().getToolCode()),
                () -> assertEquals("Chainsaw", checkout.generateRentalAgreement().getToolType()),
                () -> assertEquals("Stihl", checkout.generateRentalAgreement().getToolBrand()),
                () -> assertEquals("5", String.valueOf(checkout.generateRentalAgreement().getRentalDuration())),
                () -> assertEquals("07/02/15", checkout.generateRentalAgreement().getCheckoutDate().format(dateTimeFormatter)),
                () -> assertEquals("07/07/15", checkout.generateRentalAgreement().getDueDate().format(dateTimeFormatter)),
                () -> assertEquals("$1.49", "$" + decimalFormat.format(checkout.generateRentalAgreement().getDailyCharge())),
                () -> assertEquals("3", String.valueOf(checkout.generateRentalAgreement().getChargeDays())),
                () -> assertEquals("$4.47", "$" + decimalFormat.format(checkout.generateRentalAgreement().getPreDiscountCharge())),
                () -> assertEquals("25%", checkout.generateRentalAgreement().getDiscountPercentHundreds() + "%"),
                () -> assertEquals("$1.12", "$" + decimalFormat.format(checkout.generateRentalAgreement().getDiscountAmount())),
                () -> assertEquals("$3.35", "$" + decimalFormat.format(checkout.generateRentalAgreement().getFinalCharge())));
    }

    // (Test 4)
    @Test
    void testRentJackhammerThroughLaborDayWeekend() {
        checkout.setTool(this.checkout.getToolByToolCode("JAKD"));
        checkout.setRentalDuration(6);
        checkout.setDiscountPercentHundreds(new BigDecimal(0));
        checkout.setCheckoutDate(LocalDate.parse("09/03/15", dateTimeFormatter));

        assertAll(() -> assertEquals("JAKD", checkout.generateRentalAgreement().getToolCode()),
                () -> assertEquals("Jackhammer", checkout.generateRentalAgreement().getToolType()),
                () -> assertEquals("DeWalt", checkout.generateRentalAgreement().getToolBrand()),
                () -> assertEquals("6", String.valueOf(checkout.generateRentalAgreement().getRentalDuration())),
                () -> assertEquals("09/03/15", checkout.generateRentalAgreement().getCheckoutDate().format(dateTimeFormatter)),
                () -> assertEquals("09/09/15", checkout.generateRentalAgreement().getDueDate().format(dateTimeFormatter)),
                () -> assertEquals("$2.99", "$" + decimalFormat.format(checkout.generateRentalAgreement().getDailyCharge())),
                () -> assertEquals("3", String.valueOf(checkout.generateRentalAgreement().getChargeDays())),
                () -> assertEquals("$8.97", "$" + decimalFormat.format(checkout.generateRentalAgreement().getPreDiscountCharge())),
                () -> assertEquals("0%", checkout.generateRentalAgreement().getDiscountPercentHundreds() + "%"),
                () -> assertEquals("$.00", "$" + decimalFormat.format(checkout.generateRentalAgreement().getDiscountAmount())),
                () -> assertEquals("$8.97", "$" + decimalFormat.format(checkout.generateRentalAgreement().getFinalCharge())));
    }

    // (Test 5)
    @Test
    void testRentJackhammerThroughIndependenceDayWeekendIntoFollowingWeekend2015() {
        checkout.setTool(this.checkout.getToolByToolCode("JAKR"));
        checkout.setRentalDuration(9);
        checkout.setDiscountPercentHundreds(new BigDecimal(0));
        checkout.setCheckoutDate(LocalDate.parse("07/02/15", dateTimeFormatter));

        assertAll(() -> assertEquals("JAKR", checkout.generateRentalAgreement().getToolCode()),
                () -> assertEquals("Jackhammer", checkout.generateRentalAgreement().getToolType()),
                () -> assertEquals("Rigid", checkout.generateRentalAgreement().getToolBrand()),
                () -> assertEquals("9", String.valueOf(checkout.generateRentalAgreement().getRentalDuration())),
                () -> assertEquals("07/02/15", checkout.generateRentalAgreement().getCheckoutDate().format(dateTimeFormatter)),
                () -> assertEquals("07/11/15", checkout.generateRentalAgreement().getDueDate().format(dateTimeFormatter)),
                () -> assertEquals("$2.99", "$" + decimalFormat.format(checkout.generateRentalAgreement().getDailyCharge())),
                () -> assertEquals("5", String.valueOf(checkout.generateRentalAgreement().getChargeDays())),
                () -> assertEquals("$14.95", "$" + decimalFormat.format(checkout.generateRentalAgreement().getPreDiscountCharge())),
                () -> assertEquals("0%", checkout.generateRentalAgreement().getDiscountPercentHundreds() + "%"),
                () -> assertEquals("$.00", "$" + decimalFormat.format(checkout.generateRentalAgreement().getDiscountAmount())),
                () -> assertEquals("$14.95", "$" + decimalFormat.format(checkout.generateRentalAgreement().getFinalCharge())));
    }

    // (Test 6)
    @Test
    void testRentJackhammerThroughIndependenceDayWeekend2020() {
        checkout.setTool(this.checkout.getToolByToolCode("JAKD"));
        checkout.setRentalDuration(4);
        checkout.setDiscountPercentHundreds(new BigDecimal(50));
        checkout.setCheckoutDate(LocalDate.parse("07/02/20", dateTimeFormatter));

        assertAll(() -> assertEquals("JAKD", checkout.generateRentalAgreement().getToolCode()),
                () -> assertEquals("Jackhammer", checkout.generateRentalAgreement().getToolType()),
                () -> assertEquals("DeWalt", checkout.generateRentalAgreement().getToolBrand()),
                () -> assertEquals("4", String.valueOf(checkout.generateRentalAgreement().getRentalDuration())),
                () -> assertEquals("07/02/20", checkout.generateRentalAgreement().getCheckoutDate().format(dateTimeFormatter)),
                () -> assertEquals("07/06/20", checkout.generateRentalAgreement().getDueDate().format(dateTimeFormatter)),
                () -> assertEquals("$2.99", "$" + decimalFormat.format(checkout.generateRentalAgreement().getDailyCharge())),
                () -> assertEquals("1", String.valueOf(checkout.generateRentalAgreement().getChargeDays())),
                () -> assertEquals("$2.99", "$" + decimalFormat.format(checkout.generateRentalAgreement().getPreDiscountCharge())),
                () -> assertEquals("50%", checkout.generateRentalAgreement().getDiscountPercentHundreds() + "%"),
                () -> assertEquals("$1.50", "$" + decimalFormat.format(checkout.generateRentalAgreement().getDiscountAmount())),
                () -> assertEquals("$1.49", "$" + decimalFormat.format(checkout.generateRentalAgreement().getFinalCharge())));
    }

}