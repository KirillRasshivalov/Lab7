package org.example.Server.Managers;

import org.example.Common.Models.Vehicle;
import org.example.Common.Response;
import org.example.Common.Utilites.Validator;
import org.example.Server.Exeptions.RootException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Класс который осуществляет парсинг исходного файла в обе стороны.
 */
public class Parser {
    /**
     * Класс для записи сохраненной коллекции в файл.
     * @param fileName - имя файла в который будет осушествляться запись.
     * @throws IOException
     * @throws RootException
     */
    public static void write(String fileName) throws IOException, RootException {
        File file = new File(fileName);
        if (!file.canRead()){
            throw new RootException("У вас недостаточно прав для чтения этого файла.");
        }
        StringBuilder xml = new StringBuilder("""
                <?xml version="1.0" encoding="UTF-8" ?>

                <collection>
                \t<vehicles>
                """);

        Map<String, Vehicle> table = CollectionManager.getTable();
        for (String key : table.keySet()) {
            xml.append("\t\t<vehicle ");
            xml.append("key=\"").append(key).append("\" ");
            Vehicle vehicle = table.get(key);
            xml.append(vehicle.toXML()).append("/>\n");
        }

        xml.append("\t</vehicles>\n" + "</collection>");

        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fileName));
        bufferedOutputStream.write(xml.toString().getBytes());
        try {
            bufferedOutputStream.close();
        }catch(Exception e){
            System.out.println("Возникла ошибка при закрытие файла.");
        }
    }

    /**
     * Класс для чтения предыдещей сессии пользователя обратно в коллекцию.
     * @param path - путь к файлу в котором лежит сохраненная предыдущая сессия.
     * @param firstStart - проверка на то сохранялось ли что то до этого момента в файл или нет.
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     * @throws RootException
     */
    public static void read(String path, boolean firstStart, Response response) throws ParserConfigurationException, IOException, SAXException, RootException {
        try {
            File file = new File(path);
            if (!file.canRead()) {
                response.setAnswer("Файл не удалось прочитать, проверьте что у вас достаточно прав для чтения и что файл не побился.");
            }
            // Чтение из файла
            var br = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
            String line;
            StringBuilder text = new StringBuilder();
            while ((line = br.readLine()) != null) {
                text.append(line);
            }
            if (text.isEmpty()) {
                response.setAnswer("Ваша коллекция пуста.");
                return;
            }

            InputSource in = new InputSource(new StringReader(text.toString()));

            // Получение фабрики, чтобы после получить билдер документов.
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Получили из фабрики билдер, который парсит XML, создает структуру Document в виде иерархического дерева.
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Запарсили XML, создав структуру Document. Теперь у нас есть доступ ко всем элементам, каким нам нужно.
            Document document = builder.parse(in);

            NodeList vehicleElements = document.getDocumentElement().getElementsByTagName("vehicle");
            if (firstStart) {
                if (vehicleElements.getLength() == 0) {
                    response.setAnswer("В вашей коллекци нет элементов.");
                }
            } else {
                try {
                    // Перебор всех элементов vehicle
                    for (int i = 0; i < vehicleElements.getLength(); i++) {
                        Node organisation = vehicleElements.item(i);

                        // Получение атрибутов каждого элемента

                        NamedNodeMap attributes = organisation.getAttributes();

                        Validator.checkId(attributes.getNamedItem("id").getNodeValue());
                        Validator.notEmpty(attributes.getNamedItem("name").getNodeValue());
                        Validator.checkCoordenateX(attributes.getNamedItem("x").getNodeValue());
                        Validator.checkCoordinatesY(attributes.getNamedItem("y").getNodeValue());
                        Validator.checkPower(attributes.getNamedItem("enginePower").getNodeValue());
                        Validator.checkWheels(attributes.getNamedItem("numberOfWheels").getNodeValue());
                        Validator.checkType(attributes.getNamedItem("type").getNodeValue());
                        Validator.fuelComp(attributes.getNamedItem("fuelConsumption").getNodeValue());

                        String[] data = {attributes.getNamedItem("key").getNodeValue(), attributes.getNamedItem("id").getNodeValue(),
                                attributes.getNamedItem("name").getNodeValue(), attributes.getNamedItem("x").getNodeValue(),
                                attributes.getNamedItem("y").getNodeValue(), attributes.getNamedItem("creationDate").getNodeValue(),
                                attributes.getNamedItem("enginePower").getNodeValue(), attributes.getNamedItem("numberOfWheels").getNodeValue(),
                                attributes.getNamedItem("type").getNodeValue(), attributes.getNamedItem("fuelConsumption").getNodeValue()};

                        String key = data[0];
                        try {
                            Vehicle vehicle = new Vehicle(data);
                            CollectionManager.add(key, response, vehicle);
                        } catch (Exception e) {
                            response.setAnswer("Произошла ошибка во время добавления в коллекцию нового элемента.");
                        }
                    }
                    response.setAnswer("Коллекция успешно загружена.");
                } catch (Exception e) {
                    response.setAnswer("Произошла ошибка! Не удалось выгрузить информацию в коллекцию.");
                }
            }
        } catch (Exception e){
            response.setAnswer("Произошла ошибка во время выгрузки файла.");
        }
    }
}
