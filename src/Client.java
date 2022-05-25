import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.Scanner;

public class Client implements Runnable {
    //1. реализуем интерфейс в классе клиент
    Socket socket;
    Scanner in;
    PrintStream out;
    ChatServer server;

    //конструктор клиента
    public Client(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
        //запуск потока
        new Thread(this).start();//3. создаем элемент класса Thread  и 4. запускаем при помощи метода start()
    }

    void receive(String massage) {
        out.println(massage);//
    }

    public void run() {
        try {
            //получаем потоки ввода и вывода
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            //создаем удобное средство ввода вывода
            in = new Scanner(is);
            out = new PrintStream(os);

            //читаем из сети и пишем в сеть
            out.println("Welcome to chat!");
            String input = in.nextLine();
            while (!input.equals("exit")) {
                server.sendAll(input);
                input = in.nextLine();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
