package ru.itsjava.services;

import lombok.SneakyThrows;
import ru.itsjava.domain.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientServiceImpl implements ClientService {
    public final static int PORT = 8081;//на каком порту сервер слушает
    public final static String HOST = "localhost";//где находится сервер
    private User user;


    @SneakyThrows
    @Override
    public void start() {
        Socket socket = new Socket(HOST, PORT);

        if (socket.isConnected()) { //если сокет подключился
            new Thread(new SocketRunnable(socket)).start();

            PrintWriter serverWriter = new PrintWriter(socket.getOutputStream());//сообщения которые
            // отправляются на сервер
            MessageInputServiceImpl messageInputService = //сообщения которые считываются
                    new MessageInputServiceImpl(System.in);// с консоли

            System.out.println("Введите свой логин:");
            String login = messageInputService.getMessage();

            System.out.println("Введите свой пароль:");
            String password = messageInputService.getMessage();

            //!autho!login:password
            serverWriter.println("!autho!" + login + ":" + password);
            serverWriter.flush();//flush - скинуть буферизированные данные в поток(сразу пишем
            // и отправляем на сервер)

            while (true) {
                String consoleMessage = messageInputService.getMessage();
                serverWriter.println(consoleMessage);
                serverWriter.flush();
                if (messageInputService.getMessage().equals("exit")) {
                    System.out.println("Вы покинули чат");
                    break;
                }
            }


        }
    }
}
