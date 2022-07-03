package PacmanGame;

import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;


import java.util.ArrayList;


public class Pacman extends Group {
    Color farge;
    Arc kropp ;
    Rettning rettning;
    AnimationTimer timer;
    boolean gameOver= false;
    boolean uovervinnelig;
    final int INVTIME = 500;



    public Pacman(int startX, int startY) {
        farge = Color.YELLOW;
        double radius = 7.5;
        kropp = new Arc(startX + 12.5 ,startY + 12.5, radius,7.5,30,300);
        kropp.setStroke(Color.BLACK);
        kropp.setFill(farge);
        kropp.setType(ArcType.ROUND);
        getChildren().addAll(kropp);
        rettning = Rettning.INGEN;




        timer = new AnimationTimer() {
            int teller = 0;
            @Override
            public void handle(long now) {

                if(truffetAvSpøkelse()) {
                    timer.stop();
                    gameOver= true;
                    kropp.setFill(Color.RED);
                    Bane.gameOverTxt();
                }

                if(uovervinnelig) {
                    teller++ ;
                    System.out.println("Teller = " + teller);
                }

                if(teller >= INVTIME) {
                    teller = 0;
                    System.out.println("Back to normal");
                    uovervinnelig = false;
                }

                int deltaX = 0, deltaY = 0;
                if (rettning == Rettning.OPP) deltaY -= 1;
                if (rettning == Rettning.NED) deltaY += 1;
                if (rettning == Rettning.HØYRE)  deltaX += 1;
                if (rettning == Rettning.VENSTRE)  deltaX -= 1;
                bevegMed(deltaX,deltaY);
            }
        };
        //spiller animasjonstimeren
        timer.start();
        //Spiller gape animasjonen
        gapeAnimasjon(true);
    }
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case W -> {
                rettning = Rettning.OPP;
                roter(270);
            }
            case S -> {
                rettning = Rettning.NED;
                roter(90);
            }
            case A ->  {
                rettning = Rettning.VENSTRE;
                roter(180);
            }
            case D -> {
                rettning = Rettning.HØYRE;
                roter(360);
            }
        }
    }


    public void bevegMed(int dx, int dy) {
        ArrayList<Shape> orbÅFjerne = new ArrayList<>();
        ArrayList<Shape> storOrbÅFjerne = new ArrayList<>();
        int nyScore = 1;

        if (dx == 0 && dy == 0) return;
        kropp.setLayoutX(kropp.getLayoutX() + dx);
        kropp.setLayoutY(kropp.getLayoutY() + dy);
        // Går ut til venstre
        if(kropp.getLayoutX()> 280) {
            kropp.setLayoutX(-250);
        }
        // Går ut til høyre
        if(kropp.getLayoutX() < -250) {
            kropp.setLayoutX(280);
        }
        //Hvis pacman overlapper en vegg(rectangle) så stoper han
        for(Shape vegg : Bane.vegger) {
            if (kropp.getBoundsInParent().intersects(vegg.getBoundsInParent()))
                stopp();
        }
        //Hvis pacman treffer orb fjerner vi den og legger til Poeng
        for(Shape orb : Bane.småOrbs) {
            if(kropp.getBoundsInParent().intersects(orb.getBoundsInParent())) {
                orb.setVisible(false);
                orbÅFjerne.add(orb);
                nyScore += 100;
                }
            }

        for(Shape orb : Bane.storeOrbs) {
            if(kropp.getBoundsInParent().intersects(orb.getBoundsInParent())) {
                orb.setVisible(false);
                storOrbÅFjerne.add(orb);
                nyScore += 500;
                uovervinnelig = true;
            }
        }

        if(!orbÅFjerne.isEmpty()) {
            Bane.småOrbs.removeAll(orbÅFjerne);
        }

        if(!orbÅFjerne.isEmpty()) {
            Bane.storeOrbs.removeAll(storOrbÅFjerne);
        }
        Bane.oppdaterScore(nyScore);
    }



    public void stopp()  {
        int bounce = 1;
        if(rettning.equals(Rettning.HØYRE)) {
            rettning = Rettning.INGEN;
            kropp.setLayoutX(kropp.getLayoutX() - bounce);
        }
        if(rettning.equals(Rettning.VENSTRE)) {
            rettning = Rettning.INGEN;
            kropp.setLayoutX(kropp.getLayoutX() + bounce);
        }
        if(rettning.equals(Rettning.OPP)) {
            rettning = Rettning.INGEN;
            kropp.setLayoutY(kropp.getLayoutY() + bounce);
        }
        if(rettning.equals(Rettning.NED)) {
            rettning = Rettning.INGEN;
            kropp.setLayoutY(kropp.getLayoutY() - bounce);
        }
    }

    public void roter(int grader ) {
        RotateTransition roter = new RotateTransition(Duration.millis(50), kropp);
        roter.setToAngle(grader);
        roter.setCycleCount(1);
        roter.play();
    }



    public KeyFrame åpnerMunnen() {
        Timeline gaping = new Timeline();
        gaping.setCycleCount(Timeline.INDEFINITE);
        gaping.setAutoReverse(true);
        KeyValue vinkel = new KeyValue(kropp.startAngleProperty(), 0);
        KeyValue bue = new KeyValue(kropp.lengthProperty(), 360);
        return new KeyFrame(Duration.millis(500), vinkel, bue);
    }

    public void gapeAnimasjon(boolean play) {
        Timeline gaping = new Timeline();
        gaping.setCycleCount(Timeline.INDEFINITE);
        gaping.setAutoReverse(true);
        gaping.getKeyFrames().add(åpnerMunnen());
        ParallelTransition gapingAnimasjon = new ParallelTransition();
        gapingAnimasjon.getChildren().addAll(gaping);
        if(play)
            gapingAnimasjon.play();
        if(!play)
            gapingAnimasjon.stop();
    }

    public int hentScore() {
        return Bane.score;
    }

    public boolean truffetAvSpøkelse() {
        for (Ghost2 spøkelse : Bane.listeMedGhosts) {
            if(kropp.getBoundsInParent().intersects(spøkelse.sirkel.getBoundsInParent()) && !uovervinnelig) {

                return true;
            }
        }
        return false;
    }

    public void gameOverTxt() {
        Font normal = new Font("Consolas", 20);
        Text game = new Text(162.5,287.5, "GAME");
        Text over = new Text(312.5, 287.5, "OVER");
        game.setFont(normal);
        over.setFont(normal);
        game.setFill(Color.RED);
        over.setFill(Color.RED);
        getChildren().addAll(game,over);

    }




}
