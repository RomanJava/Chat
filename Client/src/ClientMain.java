import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

/**
 * Created by Администратор on 13.08.2017.
 */
public class ClientMain implements ConnectionListener{
    Connection connection;

    public ClientMain() {
        try  {
            connection=new Connection("127.0.0.1", 9991,this);
/*
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8")));
*/

        } catch (UnknownHostException e1) {
            System.out.println("Нет такого хоста");;
        } catch (IOException e1) {
            System.out.println("IOException");;
        }
    }

    public static void main(String[] args) {
        ClientMain clientMain=new ClientMain();
        BufferedReader consoleRead=new BufferedReader(new InputStreamReader(System.in, Charset.forName("UTF-8")));
        String message="";
        while (true){
            try {
                message=consoleRead.readLine();
            } catch (IOException e) {
                System.out.println("Ексепшн на вводе текста "+clientMain);
            }
            clientMain.connection.writeMessage(message+"\r\n");
            if(message.equals("EXIT")){
                return;
            }

        }
    }

    @Override
    public synchronized void onOpenConnection(Connection connection) {
        System.out.println("Конектимся..."+this);

    }

    @Override
    public synchronized  void onCloseConnection(Connection connection) {
        System.out.println("Закрываемся..."+this);

    }

    @Override
    public synchronized void onRecievedMessage(Connection connection, String message) {
        System.out.println(message);

    }

    @Override
    public synchronized  void onException(Connection connection, Exception e) {
        System.out.println("Эксепшн в клиенте "+this);
        e.printStackTrace();

    }
}
