package PacmanGame;


import javafx.event.Event;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Controller {
    static Pacman pacman;
    static ArrayList<Ghost2> spøkelser;
    public Bane currBane;
    public Pane root;


    public Controller(Bane bane, Pane root) {
        currBane = bane;
        this.root = root;
        pacman = currBane.getPacman();
        spøkelser = Bane.listeMedGhosts;

    }

   public void start(Event e)  {

        pacman.handle((KeyEvent) e);

        if(pacman.gameOver){
            spøkelser.clear();
            Bane.score = 0 ;
            try {
                 currBane.getChildren().clear();
                 currBane.getChildren().removeAll();
                 root.getChildren().clear();
                 root.getChildren().removeAll();
                 currBane = new Bane();
                 root.getChildren().add(currBane);
                 pacman = currBane.getPacman();

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }






    }








}
