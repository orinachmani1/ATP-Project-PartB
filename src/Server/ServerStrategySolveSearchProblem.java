package Server;

import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.*;

import java.io.*;
import java.util.ArrayList;

import static Server.Configurations.properties;

public class ServerStrategySolveSearchProblem implements IServerStrategy{
    @Override
    public void serverStrategy(InputStream inputStream, OutputStream outputStream) {
        try
        {
            ObjectInputStream fromClient = new ObjectInputStream(inputStream);
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream);

            System.out.println("start solve maze");
            //Object o = fromClient.readObject();
            System.out.println("Object is OK");
                //Maze maze = new MyDecompressorInputStream(b);
            MyMazeGenerator mg= new MyMazeGenerator();
            Maze maze = mg.generate(50,50);
            SearchableMaze searchableMaze = new SearchableMaze(maze);

            Solution solution;
            ASearchingAlgorithm as= new BestFirstSearch();
            solution = as.solve(searchableMaze);

            ArrayList<AState> mazeSolutionSteps = solution.getSolutionPath();
            AState a= mazeSolutionSteps.get(0);
            System.out.println(a.toString());
            //toClient.writeObject(solution);
            System.out.println("bye");
        }
        catch (Exception e){
            System.out.println("erorr");
        }

        //solution = searchAlgo.solve(searchableMaze);

//        FileOutputStream fileOutputStream = new FileOutputStream(tempPath);
//        ObjectOutputStream objectOutputStream = new ObjectOutputStream((fileOutputStream));
//        objectOutputStream.flush();
//
//        objectOutputStream.writeObject(solution);
//        objectOutputStream.flush();
//        fileOutputStream.close();
//        objectOutputStream.close();

        //toClient.writeObject(solution);
        //toClient.close();

        /*
        int hashC =  maze.toString().hashCode();
        String tempDirectoryPath = System.getProperty("java.io.tmpdir");
        String tempPath = tempDirectoryPath + hashC;
        File file = new File(tempPath);

        if(file.exists()) // if the maze already solved  - return the calculated solution
        {
            FileInputStream fileInput = new FileInputStream(tempPath);
            ObjectInputStream fileOutput = new ObjectInputStream(fileInput);

            Solution solution;
            solution = (Solution)fileOutput.readObject();
            toClient.writeObject(solution);
            fileInput.close();
            fileOutput.close();
        }

        else //if the maze still unsolved
            {
            String nameOfAlgo = properties.getProperty("SearchingAlgorithm");
            ASearchingAlgorithm searchAlgo;

            if (nameOfAlgo.equals("DeptFirstSearch")) {
                searchAlgo = new DepthFirstSearch();
            }
            else if(nameOfAlgo.equals("BreadthFirstSearch")) {
                searchAlgo = new BreadthFirstSearch();
            }
            else if (nameOfAlgo.equals("BestFirstSearch")){
                searchAlgo = new BestFirstSearch();
            }
            else{
                searchAlgo = new BestFirstSearch();
            }

            //SearchableMaze sm = new SearchableMaze(maze);
            Solution solution;
            solution = searchAlgo.solve(searchableMaze);

            FileOutputStream fileOutputStream = new FileOutputStream(tempPath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream((fileOutputStream));
            objectOutputStream.flush();

            objectOutputStream.writeObject(solution);
            objectOutputStream.flush();
            fileOutputStream.close();
            objectOutputStream.close();

            toClient.writeObject(solution);
        }*/

    }
}
