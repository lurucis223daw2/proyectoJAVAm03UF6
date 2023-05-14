package edu.fje.provajavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SumaController {
    @FXML
    private Label resultat;
    @FXML
    private TextField num1, num2;
    @FXML
    private Button botoSuma, botoResta, botoProducte, botoDivisio;

    @FXML
    protected void onSumaButtonClick() {
        operarDades("+");
    }
    @FXML
    protected void onRestaButtonClick() {
        operarDades("-");
    }
    @FXML
    protected void onProducteButtonClick() {
        operarDades("*");
    }
    @FXML
    protected void onDivisioButtonClick() {
        operarDades("/");
    }

    protected void operarDades(String operacio) {
        if (num1.getText().isEmpty() || num2.getText().isEmpty()) {
            resultat.setText("Introdueix dos números");
            return;
        }

        int n1 = Integer.parseInt(num1.getText());
        int n2 = Integer.parseInt(num2.getText());
        int res=0;
        switch (operacio) {
            case "+":
                res = n1 + n2;
                break;
            case "-":
                res = n1 - n2;
                break;
            case "*":
                res = n1 * n2;
                break;
            case "/":
                res = n1 / n2;
                break;
        }
        resultat.setText("el resultat és " + String.valueOf(res));
    }

}