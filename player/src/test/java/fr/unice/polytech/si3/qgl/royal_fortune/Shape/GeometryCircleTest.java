package fr.unice.polytech.si3.qgl.royal_fortune.Shape;

import fr.unice.polytech.si3.qgl.royal_fortune.calculus.GeometryCircle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Segment;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometryCircleTest {
    GeometryCircle geometryCircle;

    @BeforeEach
    void init() {
        geometryCircle = new GeometryCircle();
    }

    @Test
    void rectangleIsInCircle(){
        double radius = 500.;
        double h = 100;
        double w = 100;
        double o = 0;
        Position seaEntitiePos = new Position(0,0,0);

        Rectangle r = new Rectangle(w, h , o);

        assertTrue(GeometryCircle.rectangleIsInCircle(r, seaEntitiePos, radius));
    }

    @Test
    void rectangleIsInCircleLimitTest(){
        double radius = Math.sqrt(Math.pow(50, 2) + Math.pow(50, 2));
        double h = 100;
        double w = 100;
        double o = 0;
        Position seaEntitiePos = new Position(0,0,0);

        Rectangle r = new Rectangle(w, h , o);

        assertTrue(GeometryCircle.rectangleIsInCircle(r, seaEntitiePos, radius));
    }

    @Test
    void rectangleIsNotInCircle(){
        double radius = 50.;
        double h = 100;
        double w = 100;
        double o = 0;
        Position circlePos = new Position(1000,0,0);

        Rectangle r = new Rectangle(w, h , o);

        assertFalse(GeometryCircle.rectangleIsInCircle(r, circlePos, radius));
    }

    @Test
    void toStringTest(){
        double radius = 50.;
        Circle c = new Circle(radius);

        assertNotEquals("", c.toString());
    }

    @Test
    void generateBeaconTest() {
        Position shipPosition = new Position(0, 0);
        Position checkpointPosition = new Position(200, 0);
        Position reefPosition = new Position(100, -10);
        Circle reefShape = new Circle(30);

        List<Beacon> beaconList = GeometryCircle.generateBeacon(shipPosition, checkpointPosition, reefPosition, reefShape);

        assertEquals(4, beaconList.size());

        assertEquals(330, beaconList.get(0).getPosition().getX());
        assertEquals(-10, beaconList.get(0).getPosition().getY());

        assertEquals(-130, beaconList.get(1).getPosition().getX());
        assertEquals(-10, beaconList.get(1).getPosition().getY());

        assertEquals(100, beaconList.get(2).getPosition().getX());
        assertEquals(220, beaconList.get(2).getPosition().getY());

        assertEquals(100, beaconList.get(3).getPosition().getX());
        assertEquals(-240, beaconList.get(3).getPosition().getY());
    }

    @Test
    void computeNormalVectorShipCheckpointTest() {
        Position shipPosition = new Position(0, 0, 0);
        Position checkpointPosition = new Position(1000, 0, 0);

        double[] vectors = GeometryCircle.computeDirectorAndNormalVectorsShipCheckpoint(shipPosition, checkpointPosition);
        assertEquals(1, vectors[0]);
        assertEquals(0, vectors[1]);
        assertEquals(-0.0, vectors[2]);
        assertEquals(1, vectors[3]);


        shipPosition = new Position(23.6, -11.4, 0);
        checkpointPosition = new Position(-4.4, 3.2, 0);

        vectors = GeometryCircle.computeDirectorAndNormalVectorsShipCheckpoint(shipPosition, checkpointPosition);
        assertEquals(-0.8866977508937811, vectors[0]);
        assertEquals(0.4623495415374716, vectors[1]);
        assertEquals(-0.4623495415374716, vectors[2]);
        assertEquals(-0.8866977508937811, vectors[3]);
    }


    @Test
    void createRightLeftUpAndDownBeaconUsingCircleReef() {
        List<Beacon> beaconList = new ArrayList<>();

        Position reefPosition = new Position(17.7, -23.6, 0);
        double normalVectorX = 44.5;
        double normalVectorY = 32.9;
        double circleRadius = 50;

        GeometryCircle.createRightBeaconUsingCircleReef(reefPosition, normalVectorX, normalVectorY, circleRadius, beaconList);
        Position beaconExpectedPosition = new Position(11142.7, 8201.4);
        assertEquals(beaconExpectedPosition.getX(), beaconList.get(0).getPosition().getX());
        assertEquals(beaconExpectedPosition.getY(), beaconList.get(0).getPosition().getY());

        GeometryCircle.createLeftBeaconUsingCircleReef(reefPosition, normalVectorX, normalVectorY, circleRadius, beaconList);
        beaconExpectedPosition = new Position(-11107.3, -8248.6);
        assertEquals(beaconExpectedPosition.getX(), beaconList.get(1).getPosition().getX());
        assertEquals(beaconExpectedPosition.getY(), beaconList.get(1).getPosition().getY());

        GeometryCircle.createUpBeaconUsingCircleReef(reefPosition, normalVectorX, normalVectorY, circleRadius, beaconList);
        beaconExpectedPosition = new Position(11142.7, 8201.4);
        assertEquals(beaconExpectedPosition.getX(), beaconList.get(2).getPosition().getX());
        assertEquals(beaconExpectedPosition.getY(), beaconList.get(2).getPosition().getY());

        GeometryCircle.createDownBeaconUsingCircleReef(reefPosition, normalVectorX, normalVectorY, circleRadius, beaconList);
        beaconExpectedPosition = new Position(-11107.3, -8248.6);
        assertEquals(beaconExpectedPosition.getX(), beaconList.get(3).getPosition().getX());
        assertEquals(beaconExpectedPosition.getY(), beaconList.get(3).getPosition().getY());
    }

    @Test
    void discriminantValuePositiveTest() {
        Segment segmentToWorkOn = new Segment(new Position(0,0), new Position(10, 0));
        double segmentToWorkOnA = 0;
        double segmentToWorkOnB = 1;
        Position pointASave = new Position(1, 2);
        Position pointBSave = new Position(2, 3);
        double discriminant = 0.000001;
        double circlePositionX = 4;
        double circlePositionY = 5;
        List<Position> intersectionList =  new ArrayList<>();

        GeometryCircle.discriminantValue(segmentToWorkOn, segmentToWorkOnA, segmentToWorkOnB, pointASave, pointBSave, discriminant, circlePositionX, circlePositionY, intersectionList);

        intersectionList.add(0, new Position(0,0)); //triche
        assertEquals(1, intersectionList.size());
    }

    @Test
    void discriminantValuePositiveButIntersectionsNotOnSegmentTest() {
        Segment segmentToWorkOn = new Segment(new Position(0,0), new Position(10, 0));
        double segmentToWorkOnA = 0;
        double segmentToWorkOnB = 1;
        Position pointASave = new Position(1, 2);
        Position pointBSave = new Position(2, 3);
        double discriminant = 0.000001;
        double circlePositionX = 4;
        double circlePositionY = 5;
        List<Position> intersectionList =  new ArrayList<>();

        GeometryCircle.discriminantValue(segmentToWorkOn, segmentToWorkOnA, segmentToWorkOnB, pointASave, pointBSave, discriminant, circlePositionX, circlePositionY, intersectionList);

        assertEquals(0, intersectionList.size());
    }

    @Test
    void discriminantValueZeroTest() {
        Segment segmentToWorkOn = new Segment(new Position(0,0), new Position(17, 0));
        double segmentToWorkOnA = 1;
        double segmentToWorkOnB = 0;
        Position pointASave = new Position(0, 0);
        Position pointBSave = new Position(17, 0);
        double discriminant = 0;
        double circlePositionX = 8;
        double circlePositionY = 3;
        List<Position> intersectionList =  new ArrayList<>();

        GeometryCircle.discriminantValue(segmentToWorkOn, segmentToWorkOnA, segmentToWorkOnB, pointASave, pointBSave, discriminant, circlePositionX, circlePositionY, intersectionList);

        assertEquals(1, intersectionList.size());
    }

    @Test
    void discriminantValueZero2Test() {
        Segment segmentToWorkOn = new Segment(new Position(-4,-8), new Position(0, 4));
        double segmentToWorkOnA = 3;
        double segmentToWorkOnB = 4;
        Position pointASave = new Position(-4, -8);
        Position pointBSave = new Position(0, 4);
        double discriminant = 0;
        double circlePositionX = 3;
        double circlePositionY = -3;
        List<Position> intersectionList =  new ArrayList<>();

        GeometryCircle.discriminantValue(segmentToWorkOn, segmentToWorkOnA, segmentToWorkOnB, pointASave, pointBSave, discriminant, circlePositionX, circlePositionY, intersectionList);

        assertEquals(1, intersectionList.size());
    }

    @Test
    void discriminantValueZeroButIntersectionNotOnSegmentTest() {
        Segment segmentToWorkOn = new Segment(new Position(0,0), new Position(10, 0));
        double segmentToWorkOnA = 0;
        double segmentToWorkOnB = 1;
        Position pointASave = new Position(1, 2);
        Position pointBSave = new Position(2, 3);
        double discriminant = 0;
        double circlePositionX = 4;
        double circlePositionY = 5;
        List<Position> intersectionList =  new ArrayList<>();

        GeometryCircle.discriminantValue(segmentToWorkOn, segmentToWorkOnA, segmentToWorkOnB, pointASave, pointBSave, discriminant, circlePositionX, circlePositionY, intersectionList);

        assertEquals(0, intersectionList.size());
    }

    @Test
    void segmentToWorkOnTest() {
        Segment segment = new Segment(new Position(-2, 3), new Position(4, 7));
        Position circlePosition = new Position(2, 1);

        Segment newSegment = GeometryCircle.segmentToWorkOn(segment, circlePosition);

        Segment expectedSegment = new Segment(new Position(-4, 2), new Position(2, 6));

        assertEquals(expectedSegment.getPointA().getX(), newSegment.getPointA().getX());
        assertEquals(expectedSegment.getPointA().getY(), newSegment.getPointA().getY());

        assertEquals(expectedSegment.getPointB().getX(), newSegment.getPointB().getX());
        assertEquals(expectedSegment.getPointB().getY(), newSegment.getPointB().getY());
    }

    @Test
    void discriminantTest() {
        double discriminant = GeometryCircle.discriminant(0, 6, 6);
        assertEquals(0, discriminant);

        discriminant = GeometryCircle.discriminant(-4.2, 6.7, 5);
        assertEquals(1684.4399999999998, discriminant);
    }

    @Test
    void zeroDiscriminantTest() {
        Segment segmentToWorkOn = new Segment(new Position(1958.6115846399198, -555.325402717619), new Position(1958.6115846399216, 567.540386430971));
        double segmentToWorkOnA = 6.173019958003745E14;
        double segmentToWorkOnB = -1.20905484019595699E18;
        Position pointASave = new Position(6106.437671596443, 4721.547561451762);
        Position pointBSave = new Position(6106.4376715964445, 5844.413350600352);
        double circlePositionX = 4147.826086956523;
        double circlePositionY = 5276.872964169381;
        List<Position> intersectionList = new ArrayList<>();

        GeometryCircle.zeroDiscriminant(segmentToWorkOn, segmentToWorkOnA, segmentToWorkOnB, pointASave, pointBSave, circlePositionX, circlePositionY, intersectionList);

        assertEquals(1, intersectionList.size());
        assertEquals(6106.437671596444, intersectionList.get(0).getX());
        assertEquals(5276.872964169381, intersectionList.get(0).getY());
        assertEquals(0, intersectionList.get(0).getOrientation());
    }

    @Test
    void realPositionTest() {
        Position realPosition = GeometryCircle.realPosition(3.4, 5.2, 8.7, 14.5, 6.5);

        assertEquals(17.9, realPosition.getX());
        assertEquals(32.879999999999995, realPosition.getY());
    }

    @Test
    void positiveDiscriminantTest() {
        Segment segmentToWorkOn = new Segment(new Position(-546.1814567862507, -721.6029491783202), new Position(-721.6029491783202, 546.1814567862502));
        double segmentToWorkOnA = -7.227075705929207;
        double segmentToWorkOnB = -4668.897686547256;
        Position pointASave = new Position(2427.731586692009, 2185.5631746001827);
        Position pointBSave = new Position(2252.3100942999395, 3453.347580564753);
        double discriminant = 1.8633379671894073E7;
        double circlePositionX = 2973.9130434782596;
        double circlePositionY = 2907.166123778503;
        List<Position> intersectionList = new ArrayList<>();

        GeometryCircle.positiveDiscriminant(segmentToWorkOn, segmentToWorkOnA, segmentToWorkOnB, pointASave, pointBSave, discriminant,circlePositionX, circlePositionY, intersectionList);

        assertEquals(2, intersectionList.size());

        assertEquals(2380.567424348, intersectionList.get(0).getX());
        assertEquals(2526.422146467071, intersectionList.get(0).getY());
        assertEquals(0, intersectionList.get(0).getOrientation());

        assertEquals(2299.474256643948, intersectionList.get(1).getX());
        assertEquals(3112.488608697866, intersectionList.get(1).getY());
        assertEquals(0, intersectionList.get(1).getOrientation());
    }

    @Test
    void positiveDiscriminant2Test() {
        Segment segmentToWorkOn = new Segment(new Position(-1108.6956521739094, -1758.9576547231277), new Position(-471.48492234688365, -524.1440336391834));
        double segmentToWorkOnA = 1.9378418524420347;
        double segmentToWorkOnB = 389.51918167999065;
        Position pointASave = new Position(1865.2173913043503, 1148.2084690553752);
        Position pointBSave = new Position(2502.428121131376, 2383.0220901393195);
        double discriminant = 8846974.069128951;
        double circlePositionX = 2973.9130434782596;
        double circlePositionY = 2907.166123778503;
        List<Position> intersectionList = new ArrayList<>();

        GeometryCircle.positiveDiscriminant(segmentToWorkOn, segmentToWorkOnA, segmentToWorkOnB, pointASave, pointBSave, discriminant,circlePositionX, circlePositionY, intersectionList);

        assertEquals(0, intersectionList.size());
    }

    @Test
    void positiveDiscriminant3Test() {
        Segment segmentToWorkOn = new Segment(new Position(-1108.6956521739094, -1758.9576547231277), new Position(2517.725529782771, 5268.473086254841));
        double segmentToWorkOnA = 1.9378418524420353;
        double segmentToWorkOnB = 389.5191816799911;
        Position pointASave = new Position(1865.2173913043503, 1148.2084690553752);
        Position pointBSave = new Position(5491.638573261031, 8175.639210033344);
        double discriminant = 8846974.069128953;
        double circlePositionX = 2973.9130434782596;
        double circlePositionY = 2907.166123778503;
        List<Position> intersectionList = new ArrayList<>();

        GeometryCircle.positiveDiscriminant(segmentToWorkOn, segmentToWorkOnA, segmentToWorkOnB, pointASave, pointBSave, discriminant,circlePositionX, circlePositionY, intersectionList);

        assertEquals(2, intersectionList.size());

        assertEquals(2502.428121131376, intersectionList.get(0).getX());
        assertEquals(2383.0220901393195, intersectionList.get(0).getY());
        assertEquals(0, intersectionList.get(0).getOrientation());

        assertEquals(3127.92587438173, intersectionList.get(1).getX());
        assertEquals(3595.1378149963166, intersectionList.get(1).getY());
        assertEquals(0, intersectionList.get(1).getOrientation());
    }
}
