package ru.itsjava.services;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MessageInputServiceImpl implements MessageInputService {//сервис который занимается
    //приемом сообщений
    private final BufferedReader bufferedReader;

    public MessageInputServiceImpl(InputStream inputStream) {
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    }


    @SneakyThrows
    @Override
    public String getMessage() {
        return bufferedReader.readLine();
    }
}
