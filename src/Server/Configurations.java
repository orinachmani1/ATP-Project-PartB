package Server;

import algorithms.search.BestFirstSearch;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.DepthFirstSearch;
import algorithms.search.ISearchingAlgorithm;

import java.io.*;
import java.util.Properties;

public class Configurations {
    static Properties properties = new Properties();
    private static InputStream inputStream;


    private static void createConf() throws IOException {
        OutputStream outputStream;
        outputStream= new FileOutputStream("");
        properties.setProperty(("NumberOfThreads"), ("5"));
        properties.setProperty(("MazeGenerator"),("MyMazeGenerator"));
        properties.setProperty("SearchingAlgorithm", "DeptFirstSearch");
        properties.store(outputStream,"");
    }
    public static String solveAlgo() throws IOException {
        inputStream= new FileInputStream("");
        properties.load(inputStream);
        return properties.getProperty("SolveAlgo");


        //return "";
    }

    public static ISearchingAlgorithm getSearchAlgo(){
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


//    public static ISearchingAlgorithm getSolveAlgo(){
//        FileInputStream fromFile = new FileInputStream("");
//        properties.load(fromFile);
//
//
//
//    }
}