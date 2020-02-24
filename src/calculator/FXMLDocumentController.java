/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public final class FXMLDocumentController implements Initializable {
	boolean check = false;
	private BigDecimal left;
	private String selectedOperator;
	private boolean numberInputting;

	@FXML
	private TextField display;

	public FXMLDocumentController() {
		this.left = BigDecimal.ZERO;
		this.selectedOperator = "";
		this.numberInputting = false;
	}

	@FXML
	public void operateButton(ActionEvent evt) {
		Button button = (Button) evt.getSource();
		String buttonText = button.getText();
		if (buttonText.equals("C")) {
			if (buttonText.equals("C")) {
				left = BigDecimal.ZERO;
			}
			selectedOperator = "";
			numberInputting = false;
			display.setText("0");
			return;
		}
		if (buttonText.matches("[0-9\\.]")) {
			if (!numberInputting) {
				numberInputting = true;
				display.clear();
			}
			display.appendText(buttonText);
			return;
		}
		if (buttonText.matches("[+-/*]")) {
			left = new BigDecimal(display.getText());
			selectedOperator = buttonText;
			numberInputting = false;
			return;
		}
		if (buttonText.equals("sqrt(x)")) {
			left = new BigDecimal(display.getText());
			if (left.floatValue() < 0) {
				display.setText("Inserire un numero positivo");
				numberInputting = false;
				return;
			}
			double num = Math.sqrt(left.floatValue());
			display.setText(Double.toString(num));
			numberInputting = false;
			return;

		}

		if (buttonText.equals("=")) {
			final BigDecimal right = numberInputting ? new BigDecimal(display.getText()) : left;
			left = calculate(selectedOperator, left, right);
			display.setText(left.toString());
			if (check) {
				display.setText("Impossibile divider per zero");
				check = false;
			}
			numberInputting = false;
			return;
		}

	}

	public void displayErrorMessage() {
		display.setText("");
	}

	BigDecimal calculate(String operator, BigDecimal left, BigDecimal right) {
		switch (operator) {
		case "+":
			return left.add(right);
		case "-":
			return left.subtract(right);
		case "*":
			return left.multiply(right);
		case "/":
			if (right.intValue() == 0) {
				display.setText("");
				check = true;
				return new BigDecimal(0);
			}
			return left.divide(right, 2, RoundingMode.HALF_EVEN);
		default:
		}
		return right;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}

}
