package mininic;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Mininic using FXML.
 */
public class MininicApp extends Application {

    private Mininic mininic = new Mininic("data/tasks.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MininicApp.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);

            stage.setTitle("Mininic, Nic's mini task manager");
            stage.setScene(scene);

            fxmlLoader.<MainWindow>getController().setMininic(mininic);
            stage.show();
            
            // Used AI to generate the code for this greeting message
            Platform.runLater(() -> {
                fxmlLoader.<MainWindow>getController().greet("Mininic at your service! How can I help you today?");
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
