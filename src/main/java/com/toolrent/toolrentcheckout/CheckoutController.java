package com.toolrent.toolrentcheckout;

import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Locale;

public class CheckoutController {
    @FXML
    private Label toolCodeErrorText;

    @FXML
    private Label rentalDurationErrorText;

    @FXML
    private Label discountPercentHundredsErrorText;

    @FXML
    private TextField toolCodeTextField;

    @FXML
    private TextField rentalDurationTextField;

    @FXML
    private TextField discountPercentHundredsTextField;

    @FXML
    private DatePicker checkoutDatePicker;

    @FXML
    private Button checkoutButton;

    private Checkout checkout;

    @FXML
    void initialize() {
        // Ensure checkoutDatePicker cannot select past dates
        checkoutDatePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0 );
            }
        });

        // Ensure all fields have values before checkoutButton is pressed
        BooleanBinding bb = new BooleanBinding() {
            {
                super.bind(toolCodeTextField.textProperty(),
                        rentalDurationTextField.textProperty(),
                        discountPercentHundredsTextField.textProperty(),
                        checkoutDatePicker.valueProperty()
                );
            }

            @Override
            protected boolean computeValue() {
                return (toolCodeTextField.getText().isEmpty()
                        || rentalDurationTextField.getText().isEmpty()
                        || discountPercentHundredsTextField.getText().isEmpty()
                        || checkoutDatePicker.getValue() == null
                );
            }
        };
        checkoutButton.disableProperty().bind(bb);
    }

    public void initCheckout(Checkout checkout) {
        if (this.checkout != null) {
            throw new IllegalStateException("Checkout can only be initialized once");
        }
        this.checkout = checkout;
    }

    @FXML
    protected void onCheckoutButtonClick() {
        toolCodeErrorText.setText("");
        rentalDurationErrorText.setText("");
        discountPercentHundredsErrorText.setText("");

        boolean toolCodeIsVerified = verifyToolCode();
        boolean rentalDurationCanParse = doesRentalDurationParse(rentalDurationTextField.getText());
        boolean discountPercentHundredsCanParse = doesDiscountPercentHundredsParse(discountPercentHundredsTextField.getText());

        if (toolCodeIsVerified && rentalDurationCanParse && discountPercentHundredsCanParse) {
            this.checkout.setTool(this.checkout.getToolByToolCode(toolCodeTextField.getText().toUpperCase(Locale.ROOT)));
            this.checkout.setRentalDuration(Integer.parseInt(rentalDurationTextField.getText()));
            this.checkout.setDiscountPercentHundreds(new BigDecimal(Integer.parseInt(discountPercentHundredsTextField.getText())));
            this.checkout.setCheckoutDate(checkoutDatePicker.getValue());

            try {
                RentalAgreement agreement = this.checkout.generateRentalAgreement();
                agreement.print();
            } catch (InvalidDaysException e) {
                rentalDurationErrorText.setText(e.getMessage());
            } catch (InvalidPercentException e) {
                discountPercentHundredsErrorText.setText(e.getMessage());
            }
        }
    }

    public boolean verifyToolCode() {
        try {
            Tool tool = this.checkout.getToolByToolCode(toolCodeTextField.getText().toUpperCase(Locale.ROOT));
            return true;
        } catch (InvalidToolCodeException e) {
            System.err.println(e.getClass().getCanonicalName() + ": " + e.getMessage());
            toolCodeErrorText.setText(e.getMessage());
            return false;
        }
    }

    public boolean doesRentalDurationParse(String rentalDurationText) {
        if (rentalDurationText.matches("\\d+")) {
            return true;
        } else {
            rentalDurationErrorText.setText("Rental duration must be one or more days.");
            return false;
        }
    }

    public boolean doesDiscountPercentHundredsParse(String discountPercentText) {
        if (discountPercentText.matches("\\d+")) {
            return true;
        } else {
            discountPercentHundredsErrorText.setText("discount percent must be 0-100.");
            return false;
        }
    }
}