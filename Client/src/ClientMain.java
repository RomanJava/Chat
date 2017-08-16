import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by Администратор on 13.08.2017.
 */
public class ClientMain {


    public static void main(String[] args) {
        try (Socket socket=new Socket("127.0.0.1",9991)){

            final BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8")));
            final Thread thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    String s="1";
                    System.out.println("Запустили клиента из консоли");
                    while (!Thread.currentThread().isInterrupted()){
                        try {
                            s=bufferedReader.readLine();
                            if(s!=null) System.out.println(s);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.setDaemon(true);
            thread.start();
            BufferedReader cons=new BufferedReader(new InputStreamReader(System.in));
            BufferedReader bf=new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
            while (true){
                System.out.println("Введите ченить");
                String s=cons.readLine();
                if(s.equals("EXIT")){
                    socket.close();
                    thread.interrupt();
                    return;
                }
                bufferedWriter.write(s+"\r\n");
                bufferedWriter.flush();
            }

        } catch (IOException e) {
            System.out.println("Эксепшн");
        }

    }
}
