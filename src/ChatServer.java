import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
    //создаем лист с клиентами для того, чтобы клиенты могли общаться
    ArrayList<Client> clients = new ArrayList<>();//2. экземпляр клиента помещаем в лист
    //напишем конструктор класса
    ServerSocket serverSocket;

    //метод создания серверного сокета
    ChatServer() throws IOException {
        //создаем серверный сокет на пару 1234
        serverSocket = new ServerSocket(1234);
    }

    void sendAll(String message) {
        //отправляем всем сообщение
        for (Client client : clients) {
            client.receive(message);
        }
    }

    //создадим метод ран и перенесем туда while
    public void run() {
        while (true) {
            System.out.println("Waiting...");
            try {
                //ждем клиента Из сети
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");
                //можно так написать
                //создаем клиента на своей стороне
//            Client client = new Client(socket);
//            //запускаем поток
//            Thread thread = new Thread(client);
//            thread.start();//pfgecrftv
                //можно короче
//            Thread thread = new Thread(new Client(socket));
//            thread.start();
                //можно еще короче
                //new Thread(new Client(socket)).start();
                clients.add(new Client(socket, this));//передаем в клиент сокет клиента и сервер
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new ChatServer().run();//запускаем чат
    }
}
