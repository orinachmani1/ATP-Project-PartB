package Server;

import java.io.InputStream;
import java.io.OutputStream;

public interface IServerStrategy {

    void serverStrategy(InputStream inFromServer, OutputStream outToServer);
}
