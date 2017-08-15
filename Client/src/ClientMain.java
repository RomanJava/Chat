import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by Администратор on 13.08.2017.
 */
public class ClientMain {


    public static void main(String[] args) {
        try (Socket socket=new Socket("127.0.0.1",9991)){

            /*final BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8")));
            Thread thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    String s="1";
                    System.out.println("Запустили клиента из консоли");
                    while (true){
                        try {
                            System.out.println("aaa");
                            s=bufferedReader.readLine();
                            System.out.println("bbb");
                            System.out.println(s);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(s!=null) System.out.println(s);
                    }
                }
            });
//            thread.setDaemon(true);
            thread.start();*/
            BufferedReader cons=new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8")));
            while (true){
                System.out.println("Введите ченить");
                String s=cons.readLine();
                bufferedWriter.write(s+"\r\n");
                bufferedWriter.flush();
                System.out.println("отправлено");
                BufferedReader bf=new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
                System.out.println(bf.readLine());
            }

        } catch (IOException e) {
            System.out.println("Эксепшн");
        }

    }
}
