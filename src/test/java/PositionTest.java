import labirinth.state.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {

    @Test
    void testConstructorAndGetter() {
        Position p = new Position(5, 10, 1);

        assertEquals(5, p.getRow());
        assertEquals(10, p.getCol());
        assertEquals(1, p.getObject());
    }

    @Test
    void testSetObject() {
        Position p = new Position(5, 10, 1);
        p.setObject(2);
        assertEquals(2, p.getObject());
    }

    @Test
    void testGetNeighbourUp(){
        Position p = new Position(5, 10, 1);
        Position neighbour = p.getNeighbour(Position.Direction.UP);

        assertEquals(4, neighbour.getRow());
        assertEquals(10, neighbour.getCol());
        assertEquals(1, neighbour.getObject());
    }

    @Test
    void testGetNeighbourDown(){
        Position p = new Position(5, 10, 1);
        Position neighbour = p.getNeighbour(Position.Direction.DOWN);

        assertEquals(6, neighbour.getRow());
        assertEquals(10, neighbour.getCol());
        assertEquals(1, neighbour.getObject());
    }

    @Test
    void testGetNeighbourLeft(){
        Position p = new Position(5, 10, 1);
        Position neighbour = p.getNeighbour(Position.Direction.LEFT);

        assertEquals(5, neighbour.getRow());
        assertEquals(9, neighbour.getCol());
        assertEquals(1, neighbour.getObject());
    }

    @Test
    void testGetNeighbourRight(){
        Position p = new Position(5, 10, 1);
        Position neighbour = p.getNeighbour(Position.Direction.RIGHT);

        assertEquals(5, neighbour.getRow());
        assertEquals(11, neighbour.getCol());
        assertEquals(1, neighbour.getObject());
    }

    @Test
    void testGetNeighbourDefault(){
        Position p = new Position(5, 10, 1);
        Position neighbour = p.getNeighbour(null);

        assertEquals(5, neighbour.getRow());
        assertEquals(10, neighbour.getCol());
        assertEquals(1, neighbour.getObject());
    }
}
