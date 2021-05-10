package Server;

import algorithms.search.BestFirstSearch;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.DepthFirstSearch;
import algorithms.search.ISearchingAlgorithm;

import java.awt.*;
import java.io.*;
import java.util.Properties;

public class Configurations {

    private static Configurations instance = null;
    static Properties properties = new Properties();
    private static InputStream inputStream;

    public static Configurations getInstance(){
        if (instance==null)
        {
            instance=new Configurations();
        }
        return instance;
    }
    private void configurations() throws IOException {
        OutputStream outputStream;
        outputStream= new FileOutputStream("resources/config.properties");
        properties.setProperty(("NumberOfThreads"), ("5"));
        properties.setProperty(("MazeGenerator"),("MyMazeGenerator"));
        properties.setProperty("SearchingAlgorithm", "DeptFirstSearch");
        properties.store(outputStream,null);
    }
    public static String solveAlgo() throws IOException {

        inputStream = new FileInputStream("resources/config.properties");
        properties.load(inputStream);
        return properties.getProperty("SolveAlgo");


        //return "";
    }
    public static ISearchingAlgorithm getSearchAlgo() throws IOException {
        inputStream = new FileInputStream("resources/config.properties");
        properties.load(inputStream);
        String searchAlgo= properties.getProperty("SearchingAlgorithm");
        if(searchAlgo=="DeptFirstSearch"){
            DepthFirstSearch dfs = new DepthFirstSearch();
            return dfs;
        }
        else if (searchAlgo=="BreadthFirstSearch"){
            BreadthFirstSearch bfs = new BreadthFirstSearch();
            return bfs;
        }
        else{
            BestFirstSearch bestFirst = new BestFirstSearch();
            return bestFirst;

        }

    }
    public static int numberOfThreads() throws IOException {
        inputStream = new FileInputStream("resources/config.properties");
        properties.load(inputStream);
        String numOfThreads = properties.getProperty("NumberOfThreads");
        int numOfThreadsInt = Integer.parseInt(numOfThreads);
        return numOfThreadsInt;
    }


}