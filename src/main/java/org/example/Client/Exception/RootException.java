package org.example.Client.Exception;

/**
 * Класс обработки исключения с некорректными правами доступа
 */
public class RootException extends Exception{
    public RootException(String str){
        super(str);
    }
}
