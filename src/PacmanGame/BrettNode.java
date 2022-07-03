package PacmanGame;


import javafx.geometry.Point2D;
import java.util.ArrayList;

class BrettNode  {
    Point2D pos;
    ArrayList<BrettNode> naboer = new ArrayList<>();
    BrettNode parent;
    double f,g,h;

    public BrettNode(Point2D pos) {
        this.pos = pos;

    }

    void getNaboer() {
        for (BrettNode brettNode : Bane.alleNoder) {
            if (brettNode.pos.getX() == pos.getX() + 25 && brettNode.pos.getY() == pos.getY()) naboer.add(brettNode);
            if (brettNode.pos.getX() == pos.getX() - 25 && brettNode.pos.getY() == pos.getY()) naboer.add(brettNode);
            if (brettNode.pos.getX() == pos.getX() && brettNode.pos.getY() == pos.getY() + 25) naboer.add(brettNode);
            if (brettNode.pos.getX() == pos.getX() && brettNode.pos.getY() == pos.getY() - 25)  naboer.add(brettNode);
        }
    }

}