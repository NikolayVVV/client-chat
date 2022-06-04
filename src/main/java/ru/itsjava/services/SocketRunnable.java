package ru.itsjava.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

@RequiredArgsConstructor
public class SocketRunnable implements Runnable {
    private final Socket socket;

    @SneakyThrows
    @Override
    public void run() {
        MessageInputService serverReader =
                new MessageInputServiceImpl(socket.getInputStream());//принимаем сообщения с сервера

        while (true) {
            System.out.println(serverReader.getMessage());
        }
    }
}
