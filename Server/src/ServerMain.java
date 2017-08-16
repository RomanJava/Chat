import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Администратор on 13.08.2017.
 */
public class ServerMain implements ConnectionListener{
    private ArrayList<Connection> connections=new ArrayList<>();
    private static ServerMain serverMain=new ServerMain();

    public static void main(String[] args) {
        while (true){
            try (ServerSocket serverSocket=new ServerSocket(9991)){
                Socket socket=serverSocket.accept();
                Connection connection=new Connection(socket,serverMain);

            } catch (IOException e) {
                System.out.println("Сервер сдох");
                return;
            }


        }
    }


    @Override
    public synchronized void onOpenConnection(Connection connection) {
        serverMain.connections.add(connection);
        System.out.println("Присоединился: "+connection);
    }

    @Override
    public synchronized void onCloseConnection(Connection connection) {
        serverMain.connections.remove(connection);
        System.out.println("Отвалился:" +connection);
        connection.closeConnection();
    }

    @Override
    public synchronized void onRecievedMessage(Connection connection, String message) {
        sendAll(message);
    }

    @Override
    public synchronized void onException(Connection connection, Exception e) {
        System.out.println("Отвалился по эксепшену: "+connection+" "+e.getMessage());
        e.printStackTrace();

    }

    private void sendAll(String message){
        for (Connection con:connections) {
            con.writeMessage(message+"\r\n");
        }

    }
}
