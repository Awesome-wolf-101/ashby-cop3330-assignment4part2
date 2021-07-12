/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Joshua Ashby
 */
package ucf.assignments;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {

    public static void main(String[] args) {
        //launcg using args
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //put a try and catch inside of here
        //inside the try, use the fxml loader to load the fxml file
        //pass root into a new scene and load said scene
        //set the scene then show it
        try{

            Parent root = FXMLLoader.load(getClass().getResource("ListManager.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("ListManager");
            primaryStage.show();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

    }


}
