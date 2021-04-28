package JUnit;
import algorithms.search.AState;
import algorithms.search.BestFirstSearch;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import algorithms.mazeGenerators.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BestFirstSearchTest {
    private BestFirstSearch bestFirstS= new BestFirstSearch();
    private Maze testedMaze;
    private SearchableMaze searchableMaze;

    private MyMazeGenerator myG = new MyMazeGenerator();
    private SearchableMaze nullSearchableMaze;
    //private Maze nullMaze= null;


    public void createMaze(){
        testedMaze = myG.generate(100,300);
        searchableMaze = new SearchableMaze(testedMaze);
    }


    @Test
    public void testSizes() throws Exception{
        createMaze();
        Assertions.assertEquals(100, testedMaze.getGrid().length);
        Assertions.assertEquals(300, testedMaze.getGrid()[0].length);
    }


    @Test
    public void boundsOfEvaluated() throws Exception{
        testedMaze = myG.generate(100,300);
        searchableMaze = new SearchableMaze(testedMaze);
        bestFirstS.solve(searchableMaze);
        int i=bestFirstS.getNumberOfNodesEvaluated();
        assertTrue(0 < i );

    }

    @Test
    public void myMazeSolution() throws Exception{ //Testing empty maze solution by comparing between the end and start positions of the problem  to the solution positions
        createMaze();
        Solution solveMaze = bestFirstS.solve(searchableMaze);
        ArrayList<AState> solution = solveMaze.getSolutionPath();
        Assertions.assertEquals(testedMaze.getGoalPosition().toString(),solution.get(solution.size()-1).toString());
        Assertions.assertEquals(testedMaze.getStartPosition().toString(),solution.get(0).toString());

    }

    @Test
    public void simpleMazeSolution() throws Exception{  //Testing simple maze solution by comparing between the end and start positions of the problem  to the solution positions
        SimpleMazeGenerator mySimpleMaze = new SimpleMazeGenerator();
        Maze simpleMaze = mySimpleMaze.generate(1000,1000);
        searchableMaze = new SearchableMaze(simpleMaze);
        Solution solveEmptyMaze = bestFirstS.solve(searchableMaze);
        ArrayList<AState> solution = solveEmptyMaze.getSolutionPath();
        Assertions.assertEquals(simpleMaze.getGoalPosition().toString(),solution.get(solution.size()-1).toString());
        Assertions.assertEquals(simpleMaze.getStartPosition().toString(),solution.get(0).toString());

    }

    @Test
    public void emptyMazeSolution() throws Exception{  //Testing empty maze solution by comparing between the end and start positions of the problem  to the solution positions
        EmptyMazeGenerator emptyW = new EmptyMazeGenerator();
        Maze emptyM = emptyW.generate(150,200);
        searchableMaze = new SearchableMaze(emptyM);
        Solution solveEmptyMaze = bestFirstS.solve(searchableMaze);
        ArrayList<AState> solution = solveEmptyMaze.getSolutionPath();
        Assertions.assertEquals(emptyM.getGoalPosition().toString(),solution.get(solution.size()-1).toString());
        Assertions.assertEquals(emptyM.getStartPosition().toString(),solution.get(0).toString());
    }


    @Test
    public void checkName() throws Exception{ //Testing the name
        Assertions.assertEquals("BestFirstSearch.java",bestFirstS.getName());
    }

}
