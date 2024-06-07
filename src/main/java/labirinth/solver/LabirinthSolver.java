package labirinth.solver;

import labirinth.state.LabirinthState;
import labirinth.state.Position;
import puzzle.solver.BreadthFirstSearch;

/**
 * The main class use's a solving algorithm to find a solution
 */
public class LabirinthSolver{
    public static void main(String[] args) {
        LabirinthState state = new LabirinthState();
        BreadthFirstSearch<Position.Direction> bfs = new BreadthFirstSearch<>();

        bfs.solveAndPrintSolution(state);
    }
}
