package PacmanGame;

import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Bane extends Pane {
    int CELL = 25;
    int line = 2;
    private static int highScore = 0;
    public static int score = 0;
    File fil;

    static int currScore = 0;

    public static ArrayList<BrettNode> alleNoder = new ArrayList<>();
    BrettNode startNode;
    BrettNode sluttNode;

    public ArrayList <BrettNode> resultat = new ArrayList<>();
    static ArrayList <Rectangle> vegger = new ArrayList<>();
    static ArrayList <Circle> kryssPunkt = new ArrayList<>();
    static ArrayList <Circle> småOrbs = new ArrayList<>();
    static ArrayList <Circle> storeOrbs = new ArrayList<>();
    static ArrayList <Ghost2> listeMedGhosts = new ArrayList<>();

    public static Text highScoreLbl, scoreLbl, game, over;
    public static Label highScoreTxt, scoreTxt;
    Font normal = new Font("Consolas", 20);
    Font normalBig = new Font("Consolas", 50);



    public static Pacman pacman;
    public Ghost2 inky,blinky,pinky,clyde;

    public Bane() throws Exception {
        setStyle("-fx-background-color: black");
        highScoreLbl = new Text(5, 25, "HIGH SCORE:");
        highScoreLbl.setFill(Color.WHITE);
        highScoreLbl.setFont(normal);
        scoreLbl = new Text(150, 25, "SCORE:");
        scoreLbl.setFont(normal);
        scoreLbl.setFill(Color.WHITE);
        scoreTxt = new Label(String.valueOf(0));
        scoreTxt.setLayoutX(150);
        scoreTxt.setLayoutY(25);
        scoreTxt.setFont(normal);
        scoreTxt.setTextFill(Color.WHITE);
        highScoreTxt = new Label(String.valueOf(highScore));
        highScoreTxt.setFont(normal);
        highScoreTxt.setTextFill(Color.WHITE);
        highScoreTxt.setLayoutX(5);
        highScoreTxt.setLayoutY(25);
        game = new Text(112.5,287.5, "");
        over = new Text(312.5, 287.5, "");
        game.setFont(normalBig);
        over.setFont(normalBig);
        game.setFill(Color.RED);
        over.setFill(Color.RED);

        getChildren().addAll(highScoreTxt, highScoreLbl, scoreTxt, scoreLbl,game,over);
        fil = new File("bane.txt");
        getBrett(fil);


    }




    public void getBrett(File f) throws FileNotFoundException {
        Scanner s = new Scanner(f);
        while (s.hasNextLine()) {
            char[] charArr = s.nextLine().toCharArray();
            for (int i = 0; i < charArr.length; i++) {
                switch (Character.toUpperCase(charArr[i])) {
                    case 'X' -> {
                        Rectangle r = new Rectangle(CELL * i, CELL * line, CELL, CELL);
                        r.setFill(Color.BLUE);
                        r.setStroke(Color.TRANSPARENT);
                        vegger.add(r);
                        getChildren().addAll(r);
                    }
                    case 'S' -> {
                        pacman = new Pacman(CELL*i,CELL * line);
                        getChildren().add(pacman);
                        alleNoder.add(new BrettNode( new Point2D(CELL*i,line) ));
                        Circle c = new Circle(CELL*i + 12.5, CELL* line + 12.5,2, Color.YELLOW);
                        kryssPunkt.add(c);
                        Circle litenOrb = new Circle(CELL*i + 12.5, CELL* line + 12.5,2, Color.YELLOW);
                        småOrbs.add(litenOrb);
                        getChildren().addAll(litenOrb);
                    }
                    case 'B' -> {
                        blinky = new Ghost2(CELL*i,CELL * line, Color.RED,"BLINKY");
                        listeMedGhosts.add(blinky);
                        getChildren().add(blinky);
                        alleNoder.add(new BrettNode( new Point2D(CELL*i,line) ));
                        Circle c = new Circle(CELL*i + 12.5, CELL* line + 12.5,2, Color.YELLOW);
                        kryssPunkt.add(c);
                        Circle litenOrb = new Circle(CELL*i + 12.5, CELL* line + 12.5,2, Color.YELLOW);
                        småOrbs.add(litenOrb);
                        getChildren().addAll(litenOrb);
                    }
                    case 'I' -> {
                        inky = new Ghost2(CELL*i,CELL * line, Color.BLUE,"INKY");
                        listeMedGhosts.add(inky);
                        getChildren().add(inky);
                        alleNoder.add(new BrettNode( new Point2D(CELL*i,line) ));
                        Circle c = new Circle(CELL*i + 12.5, CELL* line + 12.5,2, Color.YELLOW);
                        kryssPunkt.add(c);
                        Circle litenOrb = new Circle(CELL*i + 12.5, CELL* line + 12.5,2, Color.YELLOW);
                        småOrbs.add(litenOrb);
                        getChildren().addAll(litenOrb);
                    }
                    case 'P' -> {
                        pinky = new Ghost2(CELL*i,CELL * line, Color.PINK,"PINKY");
                        listeMedGhosts.add(pinky);
                        getChildren().add(pinky);
                        alleNoder.add(new BrettNode( new Point2D(CELL*i,line) ));
                        Circle c = new Circle(CELL*i + 12.5, CELL* line + 12.5,2, Color.YELLOW);
                        kryssPunkt.add(c);
                        Circle litenOrb = new Circle(CELL*i + 12.5, CELL* line + 12.5,2, Color.YELLOW);
                        småOrbs.add(litenOrb);
                        getChildren().addAll(litenOrb);
                    }
                    case 'C' -> {
                        clyde = new Ghost2(CELL*i,CELL * line, Color.ORANGE,"CLYDE");
                        listeMedGhosts.add(clyde);
                        getChildren().add(clyde);
                        alleNoder.add(new BrettNode( new Point2D(CELL*i,line) ));
                        Circle c = new Circle(CELL*i + 12.5, CELL* line + 12.5,2, Color.YELLOW);
                        kryssPunkt.add(c);
                        Circle litenOrb = new Circle(CELL*i + 12.5, CELL* line + 12.5,2, Color.YELLOW);
                        småOrbs.add(litenOrb);
                        getChildren().addAll(litenOrb);
                    }
                    case 'O' -> {
                        alleNoder.add(new BrettNode( new Point2D(CELL*i,line) ));
                        Circle c = new Circle(CELL*i + 12.5, CELL* line + 12.5,2, Color.YELLOW);
                        kryssPunkt.add(c);
                        Circle storOrb = new Circle(CELL*i + 12.5, CELL* line + 12.5,7, Color.YELLOW);
                        storeOrbs.add(storOrb);
                        getChildren().addAll(storOrb);
                    }
                    default -> {
                        alleNoder.add(new BrettNode( new Point2D(CELL*i,line) ));
                        Circle c = new Circle(CELL*i + 12.5, CELL* line + 12.5,2, Color.YELLOW);
                        Circle litenOrb = new Circle(CELL*i + 12.5, CELL* line + 12.5,2, Color.YELLOW);
                        småOrbs.add(litenOrb);
                        kryssPunkt.add(c);
                        getChildren().addAll(litenOrb);
                    }


                }
            }
            line++;
        }
        s.close();
    }




    public Pacman getPacman() {
        int pacmanI = 0;
        for (int i = 0; i < getChildren().size() ; i++) {
            if (getChildren().get(i) instanceof Pacman) {
                pacmanI = i;
            }
        }
        return (Pacman) getChildren().get(pacmanI);
    }




    public static void oppdaterScore(int poeng) {
        score += poeng;
        scoreTxt.setText(String.valueOf(score));
        if(highScore < score) {
            highScore = score;
            highScoreTxt.setText(String.valueOf(score));
        }
    }

    public static void gameOverTxt() {
        game.setText("GAME");
        over.setText("OVER");
        game.toFront();
        over.toFront();
    }



}
