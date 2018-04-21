package Main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.web.HTMLEditor;
import javafx.util.StringConverter;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private final String pattern = "YYYY-MM-dd";
    List<CheckBox> cb_list = new ArrayList<>();
    private StringBuilder chbxResponse = new StringBuilder();
    @FXML
    DatePicker dater;
    @FXML
    HTMLEditor theHTML;
    @FXML
    Button submit;
    @FXML
    TextField tf_fname;
    @FXML
    TextField tf_lname;
    @FXML
    TextField tf_phone;
    @FXML
    TextField tf_age;
    @FXML
    TextField tf_occupation;
    @FXML
    TextField tf_company;
    @FXML
    TextField tf_industry;
    @FXML
    TextField tf_street;
    @FXML
    TextField tf_postalcode;
    @FXML
    ChoiceBox cb_city;
    @FXML
    CheckBox chk_7;
    @FXML
    CheckBox chk_8;
    @FXML
    CheckBox chk_9;
    @FXML
    CheckBox chk_10;
    @FXML
    CheckBox chk_11;
    @FXML
    CheckBox chk_12;
    @FXML
    GridPane gp;

    public void initialize() throws SQLException {

        connectToDB();
        formatDate();
        addCBToList(chk_7,chk_8,chk_9,chk_10,chk_11,chk_12);

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                connectionMessage();
                checkValues();
                chbxResponse.setLength(0);
                boolean addEntry = checkValues();

                try {
                    if (addEntry) {
                        return;
                     } else {
                        Connector.createStmt(tf_fname.getText(),
                                tf_lname.getText(),tf_phone.getText(),tf_age.getText(),tf_company.getText(),
                                dater.getValue().toString(),tf_street.getText(),cb_city.getValue().toString(),
                                tf_postalcode.getText(),tf_industry.getText());
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                for (CheckBox box: cb_list) {
                    if (box.isSelected()) {
                        chbxResponse.append(box.getText() + ", ");
                    }
                }

                System.out.println(chbxResponse.toString());
                clearFields();
            }
        });
        CtrlUtil.myController(this);
    }


    public void addCBToList(CheckBox... box) {
        for (CheckBox bx: box) {
            cb_list.add(bx);
        }
    }

    public boolean checkValues() {
        int counter = 0;
        for (Node n: gp.getChildren()) {
            if (n instanceof TextField) {
                if (((TextField) n).getText().equalsIgnoreCase("")) {
                    ++counter;
                }
            }
        }
        if (counter > 0) {
            System.out.println("xxx");
            return true;
        }
        System.out.println("false");
        return false;
    }

    public void clearFields() {
        for (Node n: gp.getChildren()) {
            if (n instanceof TextField) {
                ((TextField) n).clear();
            }
        }

//        for (Node n: gp.getChildren()) {
//            if (n instanceof DatePicker) {
//                (DatePicker)((DatePicker) n).setValue(LocalDate.of());
//            }
//        }
    }

    private void connectToDB() {
        try {
            Connector.getTheconnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void connectionMessage() {
        theHTML.setHtmlText(Connector.getMessage());
    }

    private void formatDate() {
        StringConverter<LocalDate> convert = new StringConverter<LocalDate>() {

            DateTimeFormatter dFormat = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate object) {

                if (object != null) {
                    return dFormat.format(object);
                } else {

                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {

                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string,dFormat);
                } else {

                    return null;
                }
            }
        };

        dater.setConverter(convert);
        dater.setPromptText(pattern.toLowerCase());

    }
}
