package org.example.Server.Managers;

import java.util.ArrayList;

/**
 * Класс который осуществляет все операции над id в моей программе.
 */
public class OperationWithId {
    private static ArrayList<Long> ListOfId = new ArrayList<>();
    public OperationWithId(){
        ListOfId = new ArrayList<>();
    }

    /**
     * В этом методе происходит генерация случайного id из диавазона [-10000, 100000]ю
     * @return - возвращает случайный id
     */
    public static Long generateId(){
        long min_value = -10000;
        long max_value = 100000;
        Long id = 0L;
        while (true){
            id = min_value + (int) (Math.random() * (max_value - min_value + 1));
            if (!ListOfId.contains(id)){
                ListOfId.add(id);
                break;
            }
        }
        return id;
    }

    /**
     * Удаляет уже использованный id тем самым помечая его как неиспоьзованный.
     * @param id
     */
    public static void deleateId(Long id){
        if(ListOfId.contains(id)) {
            ListOfId.remove(id);
        }
    }

    /**
     * обновляет все id дела их "неиспользованными"
     */
    public static void deleatAllId(){
        try{
            ListOfId.clear();
        }catch (Exception e){
            System.out.println("Произошла ошибка во время удаления id.");
        }
    }

    /**
     * добавляет id в использованные
     * @param id
     */
    public static void add(Long id){
        ListOfId.add(id);
    }

}
