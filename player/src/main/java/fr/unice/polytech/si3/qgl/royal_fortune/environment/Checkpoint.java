package fr.unice.polytech.si3.qgl.royal_fortune.environment;

import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Shape;

public class Checkpoint {
    private Position position;
    private Shape shape;

    public Checkpoint(Position position, Shape shape) {
        this.position = position;
        this.shape = shape;
        shape.setPosition(position);
    }

    Checkpoint(){}

    public Shape getShape() {
        return shape;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public Position getPosition() { return position; }
}
