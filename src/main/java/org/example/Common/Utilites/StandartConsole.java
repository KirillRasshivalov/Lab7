package org.example.Common.Utilites;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Класс интерфейса, поддерживает некоторые методы, такие как печать на экран или чтение из файла.
 */
public class StandartConsole implements Console {
    public static String file_path_xml = "tst.xml";
    private static Scanner fileScanner = null;
    private static Scanner defScanner = new Scanner(System.in);
    @Override
    public void print(Object obj){
        System.out.print(obj);
    }
    @Override
    public void println(Object obj) {
        System.out.println(obj);
    }
    @Override
    public String readline() throws NoSuchElementException, IllegalStateException {
        return (fileScanner!=null?fileScanner:defScanner).nextLine();
    }
}
