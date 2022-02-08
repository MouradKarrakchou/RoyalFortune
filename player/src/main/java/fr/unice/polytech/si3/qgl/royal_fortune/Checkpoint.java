package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.shape.Shape;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.shape.ShapeFactory;

public class Checkpoint {
    private Position position;
    private Shape shape;

    Checkpoint(Position position, Shape shape) {
        this.position = position;
        this.shape = ShapeFactory.createShape(shape.getType(), null);
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
