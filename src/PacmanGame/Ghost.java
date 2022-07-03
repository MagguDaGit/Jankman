package PacmanGame;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public  class Ghost extends Group {
    Color farge;
    Circle sirkel;
    Rettning rettning;
    double radius = 7.5;
    AnimationTimer timer;

    public Ghost (int x, int y, Color farge ) {
        this.farge = farge;
        sirkel = new Circle(x+12.5, y +12.5 ,radius,farge);
        getChildren().add(sirkel);



        timer = new AnimationTimer() {
            int frame = 0;
            @Override
            public void handle(long now) {
                frame++;
                if(frame == 100) {
                    rettning = randomRettning();
                    frame = 0;
                }
                int deltaX = 0, deltaY = 0;
                if (rettning == Rettning.OPP) deltaY -= 1;
                if (rettning == Rettning.NED) deltaY += 1;
                if (rettning == Rettning.HØYRE)  deltaX += 1;
                if (rettning == Rettning.VENSTRE)  deltaX -= 1;
                bevegMed(deltaX,deltaY);
            }
        };
        timer.start();
    }


    public void bevegMed(int dx, int dy) {
        if (dx == 0 && dy == 0) return;
        sirkel.setLayoutX(sirkel.getLayoutX() + dx);
        sirkel.setLayoutY(sirkel.getLayoutY() + dy);
        // Går ut til venstre
        if(sirkel.getLayoutX()> 80) {
            stopp();

        }
        // Går ut til høyre
        if(sirkel.getLayoutX() < -30) {
            stopp();
        }
        //Hvis pacman overlapper en vegg(rectangle) så stoper han
        for(Shape vegg : Bane.vegger) {
            if (sirkel.getBoundsInParent().intersects(vegg.getBoundsInParent()))
                stopp();
        }


    }


    public void stopp()  {
        int bounce = 1;
        if(rettning.equals(Rettning.HØYRE)) {
            rettning = Rettning.INGEN;
            sirkel.setLayoutX(sirkel.getLayoutX() - bounce);
        }
        if(rettning.equals(Rettning.VENSTRE)) {
            rettning = Rettning.INGEN;
            sirkel.setLayoutX(sirkel.getLayoutX() + bounce);

        }
        if(rettning.equals(Rettning.OPP)) {
            rettning = Rettning.INGEN;
            sirkel.setLayoutY(sirkel.getLayoutY() + bounce);

        }
        if(rettning.equals(Rettning.NED)) {
            rettning = Rettning.INGEN;
            sirkel.setLayoutY(sirkel.getLayoutY() - bounce);

        }
    }


    Rettning randomRettning() {
        int i = (int) (Math.random() * 4);
        switch (i) {
            case 0 -> {return Rettning.VENSTRE;}
            case 1 -> {return Rettning.HØYRE;}
            case 2 -> {return Rettning.NED;}
            case 3 -> {return Rettning.OPP;}
        }
        return Rettning.INGEN;
    }






}
