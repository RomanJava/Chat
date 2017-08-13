/**
 * Created by Администратор on 13.08.2017.
 */
public interface ConnectionListener {

    public void onOpenConnection();

    public void onCloseConnection();

    public void onRecievedMessage(String mesage);

    public void onException(Exception e);
}
