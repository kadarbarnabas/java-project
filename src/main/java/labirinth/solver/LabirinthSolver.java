package labirinth.solver;

import labirinth.state.LabirinthState;
import labirinth.state.Position;
import puzzle.solver.BreadthFirstSearch;

/**
 * Class to solve the labirinth puzzle
 */
public class LabirinthSolver{


    /**
     * Creates a new state and set sets the player position to closer value
     * to find a shorter solution.
     * The solver and use's a solving algorithm to find a solution
     *
     * @param args (not used)
     */
    public static void main(String[] args) {
        LabirinthState state = new LabirinthState();

        Position newPlayerPosition = new Position(9, 9, LabirinthState.PLAYER_POSITION);
        state.setPlayerPositionToSpecifiedPosition(newPlayerPosition, Position.Direction.DOWN);

        BreadthFirstSearch<Position.Direction> bfs = new BreadthFirstSearch<>();
        bfs.solveAndPrintSolution(state);
    }
}
