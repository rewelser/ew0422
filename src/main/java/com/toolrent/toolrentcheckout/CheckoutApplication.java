package com.toolrent.toolrentcheckout;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;

public class CheckoutApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CheckoutApplication.class.getResource("checkout-view.fxml"));
        Parent root = fxmlLoader.load();

        CheckoutController checkoutController = fxmlLoader.getController();
        Checkout checkout = new Checkout(new Inventory(), new RentalCalendar());
        checkoutController.initCheckout(checkout);

        Scene scene = new Scene(root);
        stage.setTitle("Tool Rental Checkout");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}