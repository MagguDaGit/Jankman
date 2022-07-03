package PacmanGame;

import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Locale;

public  class Ghost2 extends Group {
    Color farge;
    Circle sirkel;
    Rettning rettning;
    double radius = 7;
    AnimationTimer timer;
    ImageView bilde;
    String navn;
    Image redd = new Image("SCARED.png",25,25,false,false) ;
    Image spist = new Image("EATEN.png",25,25,false,false) ;



    public Ghost2(int x, int y, Color farge, String navn) {
        this.farge = farge;
        this.navn = navn;
        sirkel = new Circle(x + 12.5, y + 12.5, radius, farge);
        bilde = new ImageView(new Image(navn+".png",20,20,false,false));
        getChildren().addAll(sirkel,bilde);
        setRettning(Rettning.HØYRE);
        sirkel.setVisible(false);

        timer = new AnimationTimer() {
            int frame= 0;
            public void handle(long now) {
                if(Bane.pacman.uovervinnelig && bilde.getImage() != spist) {
                        setBilde(redd);
                    }
                if(Bane.pacman.uovervinnelig && Bane.pacman.kropp.getBoundsInParent().intersects(sirkel.getBoundsInParent())) {
                    setBilde(spist);
                    Bane.oppdaterScore(1000);
                }
                if(!Bane.pacman.uovervinnelig) {
                    if(bilde.getImage() == redd || bilde.getImage() == spist )
                        setVanlig();
                }



                rettning = getRettning();
                int deltaX = 0, deltaY = 0;
                if (rettning == Rettning.OPP) deltaY -= 1;
                if (rettning == Rettning.NED) deltaY += 1;
                if (rettning == Rettning.HØYRE) deltaX += 1;
                if (rettning == Rettning.VENSTRE) deltaX -= 1;
                bevegMed(deltaX, deltaY);





                frame++;
            }
        };
        timer.start();
    }


    public void bevegMed(int dx, int dy) {
        if (dx == 0 && dy == 0) return;
        sirkel.setCenterX(sirkel.getCenterX() + dx);
        sirkel.setCenterY(sirkel.getCenterY() + dy);
        bilde.setX(sirkel.getCenterX()-12.5 + dx);
        bilde.setY(sirkel.getCenterY()-12 + dy);


        //Hvis spøkelse beveger set utenfor banen flytt til motsatt side
        for(Circle krysspunkt : Bane.kryssPunkt)
        if(sirkel.getCenterX() == krysspunkt.getCenterX() && sirkel.getCenterY() == krysspunkt.getCenterY() ) {
            timer.stop();
            rettning = finnLedigSti();
            timer.start();
        }
    }


    public void setRettning(Rettning rettning) {
        this.rettning = rettning;
    }

    public Rettning getRettning() {
        return rettning;
    }


    Rettning finnLedigSti() {
        Rettning tidligereRettning = rettning;
        ArrayList<Rettning> muligeRettnigner = new ArrayList<>();
        for(Circle krysspunkt : Bane.kryssPunkt) {
            if(sirkel.getCenterX() - 25 == krysspunkt.getCenterX()  && sirkel.getCenterY() == krysspunkt.getCenterY() ) {
            muligeRettnigner.add(Rettning.VENSTRE);
        }
            if (sirkel.getCenterX() + 25 == krysspunkt.getCenterX() && sirkel.getCenterY() == krysspunkt.getCenterY()) {
                muligeRettnigner.add(Rettning.HØYRE);
            }
            if(sirkel.getCenterY() - 25 == krysspunkt.getCenterY()  && sirkel.getCenterX() == krysspunkt.getCenterX() ) {
                muligeRettnigner.add(Rettning.OPP);
            }
            if(sirkel.getCenterY() + 25 == krysspunkt.getCenterY() && sirkel.getCenterX() == krysspunkt.getCenterX() ) {
                muligeRettnigner.add(Rettning.NED);
            }

        }


//Hvis spøkelset er til venstre kant,flytt til høyre
        if(sirkel.getCenterX() == 12.5 && sirkel.getCenterY() == 287.5 && rettning == Rettning.VENSTRE) {
            sirkel.setCenterX(487.5);
            sirkel.setCenterY(287.5);
            return  Rettning.VENSTRE;
        }

        //Hvis spøkelset er til høyre kant, flytt til venstre
        if(sirkel.getCenterX() == 487.5 && sirkel.getCenterY() == 287.5 &&rettning == Rettning.HØYRE) {
            sirkel.setCenterX(12.5);
            sirkel.setCenterY(287.5);
            return  Rettning.HØYRE;
        }

        //Spøkelse kan ikke snu. Så vi sjekker tidligere rettning for å forsikre oss om at motsatt rettnign ikke blir brukt
        boolean godkjent = false;
        int nyRettning = (int) (Math.random()*muligeRettnigner.size());
        while(!godkjent) {
            nyRettning = (int) (Math.random()*muligeRettnigner.size());
            if(tidligereRettning != Rettning.OPP && muligeRettnigner.get(nyRettning) == Rettning.NED) {
                godkjent = true;
            }
            if(tidligereRettning != Rettning.HØYRE && muligeRettnigner.get(nyRettning) == Rettning.VENSTRE){
                godkjent = true;
            }
            if(tidligereRettning != Rettning.NED && muligeRettnigner.get(nyRettning) == Rettning.OPP) {
                godkjent = true;
            }
            if(tidligereRettning != Rettning.VENSTRE && muligeRettnigner.get(nyRettning) == Rettning.HØYRE) {
                godkjent = true;
            }
        }
        return muligeRettnigner.get(nyRettning);
    }


    public void setBilde(Image bilde) {
        this.bilde.setImage(bilde);
    }

    public void setVanlig() {
        this.bilde.setImage(new Image(navn+".png",20,20,false,false));
    }
}
