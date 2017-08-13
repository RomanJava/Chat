import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by Администратор on 13.08.2017.
 */
public class Connection {
    private Socket socket;
    private BufferedReader in;
    private Thread thread;
    private BufferedWriter out;
    private  ConnectionListener connectionListener;

    public Connection(Socket socket, final ConnectionListener connectionListener) throws IOException {
        this.socket = socket;
        this.connectionListener = connectionListener;
        in=new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
        out=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8")));

        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    connectionListener.onOpenConnection(Connection.this);
                    while (!thread.isInterrupted()) connectionListener.onRecievedMessage(Connection.this,in.readLine());
                } catch (IOException e) {
                    connectionListener.onException(e);
                }
                finally {
                    connectionListener.onCloseConnection(Connection.this);
                }

            }
        });
        thread.start();
    }


}
