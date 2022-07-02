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

            MessageInputService serverReader =
                    new MessageInputServiceImpl(socket.getInputStream());


//            String authorizationOrRegistration = messageInputService.getMessage();
//            serverWriter.println(authorization);
//            serverWriter.flush();

//            String registration = messageInputService.getMessage();
//            serverWriter.println(registration);
//            serverWriter.flush();


            //!autho!login:password

            //flush - скинуть буферизированные данные в поток(сразу пишем
            // и отправляем на сервер)

            printMenu();
            System.out.println("Введите номер меню");
            while (true) {
                String consoleMessage = messageInputService.getMessage();
                serverWriter.println(consoleMessage);
                serverWriter.flush();
                if (consoleMessage.equals("1")) {
                    System.out.println("Вы выбрали авторизацию");
                    serverWriter.println("1");
                    serverWriter.flush();
                    System.out.println("Введите свой логин:");
                    String login = messageInputService.getMessage();
                    System.out.println("Введите свой пароль:");
                    String password = messageInputService.getMessage();
                    serverWriter.println("!autho!" + login + ":" + password);
                    serverWriter.flush();
//                    System.out.println("Вы успешно авторизованы");
//                } else if (serverReader.getMessage().equals("Неправильный логин или пароль")) {
//                    System.out.println("Неправильный логин или пароль");
                } else if (consoleMessage.equals("2")) {
                    System.out.println("Вы выбрали регистрацию");
                    serverWriter.println("2");
                    serverWriter.flush();
                    System.out.println("Введите свой логин:");
                    String login = messageInputService.getMessage();
                    System.out.println("Введите свой пароль:");
                    String password = messageInputService.getMessage();
                    serverWriter.println("!regis!" + login + ":" + password);
                    serverWriter.flush();
                    System.out.println("Вы успешно зарегистрированы");
                    break;
                } else if (consoleMessage.equals("3")) {
                    System.out.println("Вы выбрали личную переписку");
                    serverWriter.println("3");
                    serverWriter.flush();
                    System.out.println("Введите свой логин:");
                    String login = messageInputService.getMessage();
                    System.out.println("Введите свой пароль:");
                    String password = messageInputService.getMessage();
                    serverWriter.println("!autho!" + login + ":" + password);
                    serverWriter.flush();
                } else if (consoleMessage.equals("exit")) {
                    System.out.println("Вы покинули чат");
                    break;
                }
            }
        }
    }

    public void printMenu() {
        System.out.println("1 - для входа, 2 - для регистрации, " +
                "3 - личная переписка, exit - выход");
    }
}
