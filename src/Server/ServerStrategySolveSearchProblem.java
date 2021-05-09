package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;
import static Server.Configurations.properties;

public class ServerStrategySolveSearchProblem implements IServerStrategy{
    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) throws IOException, ClassNotFoundException {
        String tempDirectoryPath = System.getProperty("java.io.tmpdir");
        ObjectInputStream fc = new ObjectInputStream((inFromClient));
        ObjectOutputStream tc = new ObjectOutputStream((outToClient));
        tc.flush();

        Maze getMaze= (Maze)fc.readObject();
        String tempPath = tempDirectoryPath+getMaze.toString().hashCode();
        File file = new File(tempPath);
        int hashC= getMaze.toString().hashCode();

        if(file.exists())
        {
//            String nameOfAlgo = properties.getProperty("SearchAlgo");
//            String solvingAlgorithm = "DFS";
//            String tempath=  tempDirectoryPath+hashC;//W
//            ISearchingAlgorithm searchAlgo;
//            if (nameOfAlgo=="DFS");
//            {
//                 searchAlgo = new DepthFirstSearch();
//
//            }
//             if  (nameOfAlgo=="BFS"){
//                   searchAlgo = new BreadthFirstSearch();
//
//
//             }
//            if  (nameOfAlgo=="BestFirstSearch"){
//                  searchAlgo = new BestFirstSearch();
//
//
//            }

            ISearchable searchableM= new SearchableMaze(getMaze);
            Solution solution = searchAlgo.solve(searchableM);
            tc.writeObject(solution);//2client
            tc.flush();
            FileOutputStream tempF = new FileOutputStream(tempPath);
            ObjectOutputStream writeM = new ObjectOutputStream(tempF);
            writeM.writeObject(getMaze);
            String soluPath = tempDirectoryPath +  + getMaze.toString().hashCode() +"solution";
            FileOutputStream fs = new FileOutputStream(soluPath);
            ObjectOutputStream writeSolu = new ObjectOutputStream(fs);
            writeSolu.writeObject(solution);

        }


        else{
            SearchableMaze sm = new SearchableMaze(getMaze);
            Solution solution;
            BreadthFirstSearch bfs = new BreadthFirstSearch();
            solution=bfs.solve(sm);
            FileOutputStream fileOutputStream = new FileOutputStream("");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream((fileOutputStream));
            objectOutputStream.flush();
            objectOutputStream.writeObject(solution);
            objectOutputStream.flush();
            fileOutputStream.close();
            objectOutputStream.close();
            tc.writeObject(solution);
        }

    }
}
