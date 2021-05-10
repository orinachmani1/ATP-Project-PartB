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
        int hashC= getMaze.toString().hashCode();
        String tempPath = tempDirectoryPath+hashC;
        File file = new File(tempPath);

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

            //ISearchable searchableM= new SearchableMaze(getMaze);
            //Solution solution = searchAlgo.solve(searchableM);
            //tc.writeObject(solution);//2client
            //tc.flush();
            FileInputStream solContent= new FileInputStream(tempPath);
            ObjectInputStream inputSol=new ObjectInputStream(solContent);
            Solution solution;
            solution=(Solution) inputSol.readObject();
            tc.writeObject(solution);
            solContent.close();
//            FileOutputStream tempF = new FileOutputStream(tempPath);
//            ObjectOutputStream writeM = new ObjectOutputStream(tempF);
//            writeM.writeObject(getMaze);
//            String soluPath = tempDirectoryPath +  + getMaze.toString().hashCode() +"solution";
//            FileOutputStream fs = new FileOutputStream(soluPath);
//            ObjectOutputStream writeSolu = new ObjectOutputStream(fs);
//            writeSolu.writeObject(solution);

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


            SearchableMaze sm = new SearchableMaze(getMaze);
            Solution solution;
            solution=searchAlgo.solve(sm);
            FileOutputStream fileOutputStream = new FileOutputStream(tempPath);
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
