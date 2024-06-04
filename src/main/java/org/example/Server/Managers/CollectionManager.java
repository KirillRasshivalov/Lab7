package org.example.Server.Managers;

import org.example.Common.Models.Vehicle;
import org.example.Common.Response;
import org.example.Common.Utilites.Console;
import org.example.Common.Utilites.StandartConsole;
import org.example.Server.Exeptions.NumberValueExeption;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Класс отвечающий за работу с коллекцией и все методы
 */
public class CollectionManager {
    private int currid = 1;
    public static LinkedHashMap<String, Vehicle> table = new LinkedHashMap<>();
    private ZonedDateTime previous;
    private ZonedDateTime last;
    public static ZonedDateTime zonedDateTime;
    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private static final Lock writeLock = lock.writeLock();

    public CollectionManager() {
        table = new LinkedHashMap<>();
        zonedDateTime = ZonedDateTime.now();
        new OperationWithId();
    }

    /**
     * Добавление нового траспорта в коллекцию
     * @param key - ключ
     * @param vehicle - транспорт
     */
    public static void add(String key, Response response, Vehicle vehicle) {
        if (table == null) {
            table = new LinkedHashMap<>();
        }
        writeLock.lock();
        try{
            table.put(key, vehicle);
        } finally{
            writeLock.unlock();
        }
        vehicle.setId(vehicle.getId());
        response.setAnswer("Элемент успешно добавлен.");
    }

    /**
     * Функция подающая коллекцию на выход.
     * @return - возвращает коллекцию
     */
    public static LinkedHashMap<String, Vehicle> getTable() {
        return table;
    }

    public Vehicle getVehbyID(int id){
        readLock.lock();
        try{
            return table.get(id);
        } finally {
            readLock.unlock();
        }
    }

    public int getId(){
        while(getVehbyID(currid++)!= null){
            return currid;
        }
        return 0;
    }
    public static ZonedDateTime getZonedDateTime(){
        return zonedDateTime;
    }

    /**
     * Функция котороя обновляет значения коллекции id которого равен заданному
     * @param key - ключ
     * @throws
     * @throws NumberValueExeption
     */
    public static void updateIdEquels(String key, Response response, Vehicle vehicle, String LogPas) throws NumberValueExeption {
        Console console = new StandartConsole();
        boolean fl = false;
        writeLock.lock();
        try {
            for (String keys : table.keySet()) {
                if (key.equals(table.get(keys).getId().toString()) && table.get(keys).getLogPass().equals(LogPas)) {
                    CollectionManager.add(keys, response, vehicle);
                    fl = true;
                    response.setAnswer("Элемент успешно заменен.");
                    break;
                }
            }
            if (!fl) response.setAnswer("По указанному id ничего не лежит или коллекция не ваша.");
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * Удаляет значения элементов по заданному ключу
     * @param key - ключ
     */
    public static void deleteElementByKey(String key, Response response, String LogPas){
        Console console = new StandartConsole();
        boolean fl = false;
        writeLock.lock();
        try {
            for (String keys : table.keySet()) {
                if (key.equals(keys) && table.get(keys).getLogPass().equals(LogPas)) {
                    OperationWithId.deleateId(table.get(key).getId());
                    table.remove(key);
                    fl = true;
                    response.setAnswer("Элемент коллекции успешно удален.");
                    break;
                }
            }
            if (!fl) response.setAnswer("По данному ключу ничего не лежит или данная коллекция не ваша.");
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * Функция которая осуществляет отчистку коллекции
     */
    public static void clearCollection(Response response, String LogPas){
        Console console = new StandartConsole();
        writeLock.lock();
        try{
            for(String keys: table.keySet()){
                if (table.get(keys).getLogPass().equals(LogPas)){
                    OperationWithId.deleateId(table.get(keys).getId());
                    table.remove(keys);
                }
            }
            response.setAnswer("Коллекция успешно очищена.");
        }
        catch (Exception e){
            response.setAnswer("Произошла ошибка во время очистки коллекции, проверьте правильность введенных вами данных.");
        }
        finally {
            writeLock.unlock();
        }
    }

    /**
     * Фyнкция кторая удаляет из коллекции элементы id которых больше заданного
     * @param id
     */
    public static void removeGreaterElem(String id, Response response, String LogPas){
        Console console = new StandartConsole();
        int counter_of_del = 0;
        writeLock.lock();
        try {
            for (String key : table.keySet()) {
                Long currId = Long.parseLong(id);
                if (table.get(key).getId() > currId && table.get(key).getLogPass().equals(LogPas)) {
                    counter_of_del++;
                    table.remove(key);
                    OperationWithId.deleateId(table.get(key).getId());
                }
            }
            if (counter_of_del > 0) {
                response.setAnswer("Все элементы успешно удалены.");
            } else {
                response.setAnswer("Элементов больше данного нет или они не принадлежали вам.");
            }
        }finally {
            writeLock.unlock();
        }
    }

    /**
     * Фyнкция кторая удаляет из коллекции элементы id которых меньше заданного
     * @param id
     */
    public static void removeLowerElem(String id, Response response, String LogPas){
        Console console = new StandartConsole();
        int counter_of_del = 0;
        writeLock.lock();
        try {
            for (String key : table.keySet()) {
                Long currId = Long.parseLong(id);
                if (table.get(key).getId() < currId && table.get(key).getLogPass().equals(LogPas)) {
                    counter_of_del++;
                    table.remove(key);
                    OperationWithId.deleateId(table.get(key).getId());
                }
            }
            if (counter_of_del > 0) {
                response.setAnswer("Все элементы успешно удалены.");
            } else {
                response.setAnswer("Элементов меньше данного нет или они пренадлежат другому пользователю.");
            }
        }finally {
            writeLock.unlock();
        }
    }

    /**
     * Функция которая заменяет элемент если его id меньше заданного
     * @param id
     * @throws NumberValueExeption
     */
    public static void replaceIfLower(String id, Response response, Vehicle vehicle, String LogPas) throws NumberValueExeption {
        Console console = new StandartConsole();
        boolean fl = false;
        writeLock.lock();
        try {
            for (String key : table.keySet()) {
                Long currId = Long.parseLong(id);
                if (table.get(key).getId() < currId && table.get(key).getLogPass().equals(LogPas)) {
                    OperationWithId.deleateId(table.get(key).getId());
                    CollectionManager.add(key, response, vehicle);
                    OperationWithId.deleateId(table.get(key).getId());
                    table.get(key).setId(currId);
                    OperationWithId.add(currId);
                    fl = true;
                    response.setAnswer("Элемент коллекции успешно заменен.");
                    break;
                }
            }
            if (!fl) {
                response.setAnswer("Нету id меньше данного или он принадлежит другому пользователю.");
            }
        }finally {
            writeLock.unlock();
        }
    }

    /**
     * Функция которая удаляет из коллекции один элемент, значение поля fuelConsumption которого эквивалентно заданному
     * @param fuel
     */
    public static void removeByFuel(String fuel, Response response, String LogPas){
        Console console = new StandartConsole();
        long currFuel = Long.parseLong(fuel);
        boolean fl = false;
        writeLock.lock();
        try {
            for (String key : table.keySet()) {
                if (table.get(key).getFuelConsumption() == currFuel && table.get(key).getLogPass().equals(LogPas)) {
                    OperationWithId.deleateId(table.get(key).getId());
                    table.remove(key);
                    fl = true;
                    response.setAnswer("Элемент успешно удален.");
                    break;
                }
            }
            if (!fl) {
                response.setAnswer("Поля равного данному в колеекции не существет или оно принадлжеит другому пользователю.");
            }
        }finally {
            writeLock.unlock();
        }
    }

    /**
     * Функция которая  группирует элементы коллекции по значению поля fuelConsumption и выводит количетсво элементов в каждой группе
     */
    public static void groupByFuel(Response response){
        Console console = new StandartConsole();
        writeLock.lock();
        try {
            LinkedHashMap<Long, Integer> map = new LinkedHashMap<>();
            for (String key : table.keySet()) {
                map.put(table.get(key).getFuelConsumption(), +1);
            }
            String answer = "";
            answer += "Количество элементов в каждой группе: ";
            for (Long key : map.keySet()) {
                answer += map.get(key) + " ";
            }
            response.setAnswer(answer);
        }finally {
            writeLock.unlock();
        }
    }

    /**
     * Функция которая выводит элементы, значение поля enginePower которых больше заданного
     * @param engine
     */
    public static void filterByEngine(String engine, Response response){
        Console console = new StandartConsole();
        writeLock.lock();
        try {
            ArrayList<Vehicle> listOFVehicles = new ArrayList<>();
            Double currEngine = Double.parseDouble(engine);
            for (String key : table.keySet()) {
                if (table.get(key).getEnginePower() > currEngine) {
                    listOFVehicles.add(table.get(key));
                }
            }
            String answer = "";
            answer += ("Отсортированный список:");
            for (int i = 0; i < listOFVehicles.size(); i++) {
                answer += listOFVehicles.get(i) + "\n";
            }
            response.setAnswer(answer);
        }finally {
            writeLock.unlock();
        }
    }
}
