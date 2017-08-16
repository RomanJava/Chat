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
    private ConnectionListener connectionListener;

    public Connection(String ip, int port, ConnectionListener connectionListener) throws IOException {
        this(new Socket(ip,port),connectionListener);
    }

    public Connection(final Socket socket, final ConnectionListener connectionListener) throws IOException {
        this.socket = socket;
        this.connectionListener = connectionListener;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8")));

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String s="";
                try {
                    connectionListener.onOpenConnection(Connection.this);
                    while (!thread.isInterrupted()){
                        if(socket.isConnected())s=in.readLine();
                        if(s==null||s.equals("EXIT"))return;
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            System.out.println("3");
                            e.printStackTrace();
                        }
                        if(s!=null) connectionListener.onRecievedMessage(Connection.this, s);
                    }
                } catch (IOException e) {
                    closeConnection();
                    System.out.println("6");
                    connectionListener.onException(Connection.this, e);
                } finally {
                    connectionListener.onCloseConnection(Connection.this);
                }

            }
        });
        thread.start();
    }

    public void writeMessage(String message) {
        try {
            out.write(message/*+"\r\n"*/);
            out.flush();
        } catch (IOException e) {
            System.out.println("2");
            connectionListener.onException(Connection.this, e);
            closeConnection();
        }
    }

    public void closeConnection(){
        thread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("1");
            connectionListener.onCloseConnection(Connection.this);
        }

    }


}
