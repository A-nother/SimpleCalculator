package com.exercises;

import java.util.Random;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class SimpleAdder extends Application {
    private static Random random = new Random();

    private TextField textFieldA;
    private TextField textFieldB;
    private Label labelA;
    private Label labelB;
    private Label outputLabel;
    private Label warningLabel1;
    private Label warningLabel2;
    private Node outputRow;
    private ComboBox<String> comboBox;
    private Label operationLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        var scene = new Scene(createMainView(), 500, 200);

        stage.setScene(scene);
        stage.setTitle("Simple Adder");
        stage.show();
    }

    private Region createMainView() {
        var view = new BorderPane();
        view.getStylesheets().add(getClass().getResource("/css/simple-adder.css").toExternalForm());
        view.setTop(createHeading());
        view.setCenter(createCenterContent());
        view.setBottom(createButtonRow());
        return view;
    }

    private Node createHeading() {
        var heading = new Label("Simple Adder");
        HBox.setHgrow(heading, Priority.ALWAYS);
        heading.setMaxWidth(Double.MAX_VALUE);
        heading.setAlignment(Pos.CENTER);
        heading.getStyleClass().add("heading-label");
        return heading;
    }

    private Node createCenterContent() {
        var inputRow = createInputRow();
        var outputPane = createOutputPane();

        var centerContent = new VBox(20, inputRow, outputPane);
        centerContent.setPadding(new Insets(20));
        centerContent.setAlignment(Pos.CENTER);

        return centerContent;
    }

    private Node createInputRow() {
        textFieldA = new TextField("0");
        textFieldB = new TextField("0");
        comboBox = new ComboBox<>();
        comboBox.getItems().addAll("+", "-", "x", "/");
        comboBox.setValue("+");
        var inputRow = new HBox(20, new Label("A:"), textFieldA, comboBox, new Label("B:"), textFieldB);
        inputRow.setAlignment(Pos.CENTER);
        return inputRow;
    }

    private Node createOutputPane() {
        outputRow = createOutputRow();
        var warningLabel1 = createWarningLabel();
        warningLabel1.setVisible(false);
        var warningLabel2 = createWaringLabelDivibyZero();
        warningLabel2.setVisible(false);
        var outputPane = new StackPane(outputRow, warningLabel1, warningLabel2);
        return outputPane;
    }

    private Node createOutputRow() {
        labelA = new Label("0");
        labelB = new Label("0");
        operationLabel = new Label("+");
        outputLabel = new Label("0");
        outputLabel.getStyleClass().add("output-label");
        var outputRow = new HBox(10, labelA, operationLabel, labelB, new Label("="), outputLabel);
        outputRow.setAlignment(Pos.CENTER);
        return outputRow;
    }

    private Node createWarningLabel() {
        warningLabel1 = new Label("Invalid input format.");
        warningLabel1.getStyleClass().add("warning");
        return warningLabel1;
    }

    private Node createWaringLabelDivibyZero() {
        warningLabel2 = new Label("Division by zero");
        warningLabel2.getStyleClass().add("warning");
        return warningLabel2;
    }

    private Node createButtonRow() {
        var buttonRow = new HBox(20, createRandomizeButton(), createAddButton());
        buttonRow.setPadding(new Insets(0, 0, 20, 0));
        buttonRow.setAlignment(Pos.CENTER);
        return buttonRow;
    }

    private Node createRandomizeButton() {
        var randomizeButton = new Button("Randomize");
        randomizeButton.setOnAction(evt -> {
            textFieldA.setText(String.valueOf(rangeRandomInt(-1000, 1000)));
            textFieldB.setText(String.valueOf(rangeRandomInt(-1000, 1000)));
        });
        return randomizeButton;
    }

    private Node createAddButton() {
        var addButton = new Button("Add");
        addButton.setOnAction(evt -> {
            String valueA = textFieldA.getText();
            String valueB = textFieldB.getText();
            String operation = comboBox.getValue();
            labelA.setText(valueA);
            labelB.setText(valueB);
            operationLabel.setText(operation);
            try {
                int a = Integer.parseInt(valueA);
                int b = Integer.parseInt(valueB);
                int result = 0;

                switch (operation) {
                    case "+":
                        result = a + b;
                        break;
                    case "-":
                        result = a - b;
                        break;
                    case "x":
                        result = a * b;
                        break;
                    case "/":
                        result = a / b;
                        break;
                }

                outputLabel.setText(String.valueOf(result));
                showOutput();
            } catch (NumberFormatException e) {
                showWarning();
            } catch (ArithmeticException e) {
                showWarningDivibyZero();
            }
        });
        return addButton;
    }

    private void showOutput() {
        outputRow.setVisible(true);
        warningLabel1.setVisible(false);
        warningLabel2.setVisible(false);
    }

    private void showWarning() {
        outputRow.setVisible(false);
        warningLabel2.setVisible(false);
        warningLabel1.setVisible(true);
    }

    private void showWarningDivibyZero() {
        outputRow.setVisible(false);
        warningLabel1.setVisible(false);
        warningLabel2.setVisible(true);
    }

    private int rangeRandomInt(int start, int end) {
        return random.nextInt(start, end-1);
    }
}
