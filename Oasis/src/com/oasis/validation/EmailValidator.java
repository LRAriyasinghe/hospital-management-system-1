package com.oasis.validation;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements Validator {
    private final TextField textField;
    private StringProperty value = new SimpleStringProperty();
    private boolean valid = false;

    public EmailValidator(TextField textField) {
        this.textField = textField;
        value.bind(textField.textProperty());

        Platform.runLater(() -> textField.getStyleClass().remove("text-field-invalid"));

        value.addListener((observable, oldValue, newValue) -> {
            if(null != newValue){
                Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(newValue);

                if (matcher.matches()){
                    valid = true;
                    textField.getStyleClass().remove("text-field-invalid");
                }else {
                    valid = false;
                    textField.getStyleClass().add("text-field-invalid");
                }
            }
        });
    }

    @Override
    public boolean isValid() {
        return valid;
    }

    @Override
    public void setStateForce() {
        valid = false;
        textField.getStyleClass().add("text-field-invalid");
    }
}
