/**
 * Created by Администратор on 13.08.2017.
 */
public interface ConnectionListener {

    public void onOpenConnection(Connection connection);

    public void onCloseConnection(Connection connection);

    public void onRecievedMessage(Connection connection, String message);

    public void onException(Connection connection, Exception e);
}
