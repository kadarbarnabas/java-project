import labirinth.state.LabirinthState;
import labirinth.state.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class LabirinthStateTest {


    private LabirinthState labirinthState;

    @BeforeEach
    void setUp() {
        labirinthState = new LabirinthState();
    }

    @Test
    void testPlayerPosition(){
        Position playerPosition = labirinthState.getPlayerPosition();
        assertEquals(3, playerPosition.getRow());
        assertEquals(0, playerPosition.getCol());
    }

    @Test
    void testStartPosition(){
        Position startPosition = labirinthState.getStartPoint();
        assertEquals(3, startPosition.getRow());
        assertEquals(0, startPosition.getCol());
        assertEquals(LabirinthState.STARTER_POINT, startPosition.getObject());
    }

    @Test
    void testEndPosition(){
        Position endPosition = labirinthState.getEndPoint();
        assertEquals(10, endPosition.getRow());
        assertEquals(LabirinthState.BOARD_SIZE - 1, endPosition.getCol());
    }

    @Test
    void testMovePlayer(){
        labirinthState.movePlayer(Position.Direction.RIGHT);
        Position playerPosition = labirinthState.getPlayerPosition();
        assertEquals(3, playerPosition.getRow());
        assertEquals(1, playerPosition.getCol());
    }

    @Test
    void testMovePlayerIntoWall(){
        labirinthState.movePlayer(Position.Direction.RIGHT);
        labirinthState.movePlayer(Position.Direction.RIGHT);
        labirinthState.movePlayer(Position.Direction.RIGHT);
        Position playerPosition = labirinthState.getPlayerPosition();
        assertEquals(3, playerPosition.getRow());
        assertEquals(2, playerPosition.getCol());
    }

    @Test
    void testMovePlayerForward(){
        labirinthState.movePlayer(Position.Direction.RIGHT);
        labirinthState.moveForward();
        Position playerPosition = labirinthState.getPlayerPosition();
        assertEquals(3, playerPosition.getRow());
        assertEquals(2, playerPosition.getCol());
    }

    @Test
    void testTurnAndMoveRight(){
        labirinthState.moveForward();
        labirinthState.turnAndMoveRight();
        Position playerPosition = labirinthState.getPlayerPosition();
        assertEquals(4, playerPosition.getRow());
        assertEquals(1, playerPosition.getCol());
    }

    @Test
    void testGetLegalMoves(){
        List<Position.Direction> legalMoves = labirinthState.getLegalMoves();
        assertTrue(legalMoves.contains(Position.Direction.RIGHT));
        assertFalse(legalMoves.contains(Position.Direction.UP));
    }

    @Test
    void testIsGoal(){
        assertFalse(labirinthState.isGoal());
    }

}
