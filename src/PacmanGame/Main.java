package PacmanGame;

import javafx.application.Application;
import javafx.scene.Scene;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {




    @Override
    public void start(Stage primaryStage) throws Exception{



            Pane root = new Pane();
            Bane bane = new Bane();
            root.getChildren().add(bane);
            Scene scene = new Scene(root, 500, 575);
            Controller controller = new Controller(bane, root);


            scene.setFill(Color.BLACK);
            primaryStage.setTitle("Pacman");
            primaryStage.setScene(scene);
            primaryStage.show();


            scene.setOnKeyPressed(controller::start);
            scene.setOnMouseClicked(e -> {
                System.out.println(e.getX() + " " + e.getY());
            });



    }


    public static void main(String[] args) {
        launch(args);


    }
}
