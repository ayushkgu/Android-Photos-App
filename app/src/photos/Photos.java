

package app;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import controller.LoginController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

/**
 * The Photos class is the main entry point for the Photos11 application.
 * It initializes the application by loading user data, setting up the login scene,
 * and displaying the main application window.
 *
 * @author Ayush Gupta and Yash Shah
 */

public class Photos extends Application {

    /**
     * The start method is called by the JavaFX application thread to start the
     * application.
     * It loads user data from a file, initializes the login scene, and displays the
     * main application window.
     *
     * @param primaryStage The primary stage for the application window.
     * @throws Exception if an error occurs during initialization.
     */

    @Override
    public void start(Stage primaryStage) throws Exception {
        List<User> data = new ArrayList<>();

        // Add admin user if no data exists
        data.add(new User("admin"));

        // Attempt to read user data from file
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("users.ser") );
            List<User> list = (List<User>) ois.readObject();
            data = list;
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Load the login scene
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Login.fxml"));
        AnchorPane root = (AnchorPane) loader.load();

        // Pass user data to the login controller
        LoginController loginController = loader.getController();
        loginController.setData(data);

        // Set up the primary stage
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Photos11");
        primaryStage.show();
    }

    /**
     * The main method is the entry point of the application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}