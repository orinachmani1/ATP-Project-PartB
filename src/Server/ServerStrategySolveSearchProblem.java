package Server;

        import algorithms.mazeGenerators.Maze;
        import algorithms.search.*;

        import java.io.*;
        import static Server.Configurations.properties;

public class ServerStrategySolveSearchProblem implements IServerStrategy{
    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) throws IOException, ClassNotFoundException {

        ObjectInputStream fromClient = new ObjectInputStream((inFromClient));
        ObjectOutputStream toClient = new ObjectOutputStream((outToClient));
        toClient.flush();

        Maze maze = (Maze)fromClient.readObject();
        SearchableMaze searchableMaze = new SearchableMaze(maze);

        int hashC =  maze.toString().hashCode();
        String tempDirectoryPath = System.getProperty("java.io.tmpdir");
        String tempPath = tempDirectoryPath+hashC;
        File file = new File(tempPath);

        if(file.exists()) // if the maze already solved  - return the calculated solution
        {
            FileInputStream fileInput = new FileInputStream(tempPath);
            ObjectInputStream fileOutput = new ObjectInputStream(fileInput);

            Solution sol;
            sol = (Solution)fileOutput.readObject();
            toClient.writeObject(sol);
            fileInput.close();
            fileOutput.close();
        }

        else{
            String nameOfAlgo = properties.getProperty("SearchingAlgorithm");
            ISearchingAlgorithm searchAlgo;
            if (nameOfAlgo=="DeptFirstSearch");
            {
                searchAlgo = new DepthFirstSearch();

            }
            if(nameOfAlgo=="BreadthFirstSearch"){
                searchAlgo = new BreadthFirstSearch();

            }
            if (nameOfAlgo=="BestFirstSearch"){
                searchAlgo = new BestFirstSearch();
            }

            SearchableMaze sm = new SearchableMaze(maze);
            Solution solution;
            solution=searchAlgo.solve(sm);
            FileOutputStream fileOutputStream = new FileOutputStream(tempPath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream((fileOutputStream));
            objectOutputStream.flush();
            objectOutputStream.writeObject(solution);
            objectOutputStream.flush();
            fileOutputStream.close();
            objectOutputStream.close();
            toClient.writeObject(solution);
        }

    }
}
