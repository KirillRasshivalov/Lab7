package org.example.Server.Commands;

import org.example.Server.Managers.CollectionManager;
import org.example.Server.Managers.CommandManager;

import java.io.File;
import java.util.Stack;

/**
 * Команда которая читает скрипт  из файла
 */
public class Execute_script extends Command {
    private static Stack<File> st = new Stack<>();
    private final CommandManager commandManager;
    private final CollectionManager collectionManager;

    public Execute_script(CommandManager commandManager, CollectionManager collectionManager) {
        super("execute_script", "осуществляет чтение команд из файла.");
        this.commandManager = commandManager;
        this.collectionManager = collectionManager;
    }
}
