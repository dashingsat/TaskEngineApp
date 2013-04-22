package happinessTodo;

import com.happiness.db.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("A to do list @ Happiness");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();




    }


    @Override
    public void stop() throws Exception {

        super.stop();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public static void main(String[] args) {
        launch(args);
    }
}
