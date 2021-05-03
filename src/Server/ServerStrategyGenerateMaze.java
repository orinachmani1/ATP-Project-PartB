package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;

import java.io.*;
import java.util.ArrayList;

public class ServerStrategyGenerateMaze implements IServerStrategy {

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            ByteArrayOutputStream b = new ByteArrayOutputStream();

            Object mazeSizeObj = fromClient.readObject();
            int[] mazeSize = (int[])mazeSizeObj;
            IMazeGenerator generator = new MyMazeGenerator();//TODO enable according to configuration

            Maze maze = generator.generate(mazeSize[0],mazeSize[1]);
            byte[] mazeAsBytes = maze.toByteArray();
            MyCompressorOutputStream myCompressor = new MyCompressorOutputStream(b);
            myCompressor.write(mazeAsBytes);

            toClient.write(b.toByteArray());//or just b?
            toClient.flush();

            fromClient.close();
            toClient.close();
        }

        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
