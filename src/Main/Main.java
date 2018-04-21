package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../UI_FXML/UI_Main.fxml"));
        loader.setController(CtrlUtil.getController());
        Parent root = loader.load();
        primaryStage.setTitle("Personnel Information");
        primaryStage.setResizable(false);

        primaryStage.setScene(new Scene(root, 690, 490));
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
