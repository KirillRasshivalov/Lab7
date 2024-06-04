package org.example.Server;

import org.example.Common.Models.Vehicle;
import org.example.Common.Request;
import org.example.Common.Response;
import org.example.Server.Exeptions.RootException;
import org.example.Server.Managers.CommandManager;
import org.example.Server.Managers.DataBaseManager;
import org.example.Server.Managers.Parser;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;


public class server {

    public static void main(String[] args) {
        try {
            Selector selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress("localhost", 2432));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("Сервер успешно запущен.");


            DataBaseManager.getConnect();
            DataBaseManager.fillTable();
            DataBaseManager.fillUsers();
            DataBaseManager.loadInfromation();
            DataBaseManager.loadUsers();


            while (true) {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    keyIterator.remove();

                    if (key.isAcceptable()) {
                        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                        SocketChannel clientChannel = serverChannel.accept();
                        clientChannel.configureBlocking(false);
                        clientChannel.register(selector, SelectionKey.OP_READ);

                        System.out.println("Разрешено подключение из: " + clientChannel.getRemoteAddress());
                    } else if (key.isReadable()) {
                        SocketChannel clientChannel = (SocketChannel) key.channel();
                        // Создаем новый поток для обработки клиента
                        Thread clientThread = new Thread(new ClientHandler(clientChannel));
                        clientThread.start();
                    }
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private SocketChannel clientChannel;

    public ClientHandler(SocketChannel clientChannel) {
        this.clientChannel = clientChannel;
    }

    @Override
    public void run() {
        try {
            handleClient(clientChannel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void handleClient(SocketChannel clientChannel) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            int bytesRead;
            while ((bytesRead = clientChannel.read(buffer)) > 0) {
                buffer.flip();
                outputStream.write(buffer.array(), buffer.position(), buffer.remaining());
                buffer.clear();
            }

            if (bytesRead == -1) {
                clientChannel.close();
            } else {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                Request request = new Request(null, null, null, null);
                CommandManager commandManager = new CommandManager();

                Response response = new Response();

                //Parser.read("Parse.xml", false, response);
                request = (Request) objectInputStream.readObject();
                Vehicle vehicle = (Vehicle) request.getVehicle();
                commandManager.startExecuting(request.getCommand().toLowerCase() + " " + request.getKey(), response, vehicle, request.getLogPass());
                DataBaseManager.insertionUser();
                DataBaseManager.insertInformation();
                String answer = response.getAnswer();
                ByteBuffer responseBuffer = ByteBuffer.wrap(answer.getBytes());
                responseBuffer.wrap(answer.getBytes());
                clientChannel.write(responseBuffer);
                //Parser.write("Parse.xml");
                responseBuffer.clear();
                buffer.clear();
            }
        } catch (ParserConfigurationException e) {
            //System.out.println("Проверьте конфигурацию вашего парсера.");
        } catch (RootException e) {
           // System.out.println("Ошибка в доступе к файлу, проверьте права.");
        } catch (ClassNotFoundException e) {
            //System.out.println("Не найден класс");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
