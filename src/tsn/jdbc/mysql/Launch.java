package tsn.jdbc.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class Launch {

   

public static void main(String[] args) {
        try {
            // Адрес нашей базы данных "tsn_demo" на локальном компьютере (localhost)
            String url = "jdbc:mysql://localhost:3306/ts_disks?serverTimezone=Asia/Almaty&useSSL=false";

            // Создание свойств соединения с базой данных
            Properties authorization = new Properties();
            authorization.put("user", "root"); // Зададим имя пользователя БД
            authorization.put("password", "root"); // Зададим пароль доступа в БД

            // Создание соединения с базой данных
            Connection connection = DriverManager.getConnection(url, authorization);

            // Создание оператора доступа к базе данных
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            // Выполнение запроса к базе данных, получение набора данных
            ResultSet table = statement.executeQuery("SELECT * FROM edktable__6");

            System.out.println("Первичный код:");
            table.first(); // Выведем имена полей
            for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                System.out.print(table.getMetaData().getColumnName(j) + "\t\t");
            }
            System.out.println();

            table.beforeFirst(); // Выведем записи таблицы
            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }

            Scanner sc = new Scanner(System.in);
            System.out.println("Добавьте параметры к новому полю:");
            System.out.print("NAME_ANIMAL - ");
            String scannedname_animal = sc.nextLine();
            System.out.print("COLOR_ANIMAL - ");
            String scannedcolor_animal = sc.nextLine();
            
            System.out.println("После добавления:");
            statement.execute("INSERT edktable__6(NAME_ANIMAL, COLOR_ANIMAL) VALUES ('" + scannedname_animal + "','" + scannedcolor_animal + "')");
            table = statement.executeQuery("SELECT * FROM edktable__6");

            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }

            System.out.println("Какую строку хотите удалить? (Введите ID");
            System.out.print("ID - ");
            int scannedId = sc.nextInt();
            statement.execute("DELETE FROM edktable__6 WHERE Id = " + scannedId);
            
            System.out.println("После удаления:");
            table = statement.executeQuery("SELECT * FROM edktable__6");
            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }
            sc.nextLine();
            
            System.out.println("Как хотите изменить первую строку?");
            System.out.print("NAME_ANIMAL - ");
            String scannedNAME_ANIMALUp = sc.nextLine();
            System.out.print("COLOR_ANIMAL - ");
            String scannedCOLOR_ANIMALUp = sc.nextLine();
            statement.executeUpdate("UPDATE edktable__6 SET NAME_ANIMAL = '" + scannedNAME_ANIMALUp + "' WHERE id = 1");
            statement.executeUpdate("UPDATE edktable__6 SET COLOR_ANIMAL = '" + scannedCOLOR_ANIMALUp + "' WHERE id = 1");
            System.out.println("После изменения:");
            table = statement.executeQuery("SELECT * FROM edktable__6");

            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }

          
            Scanner sca = new Scanner(System.in);
            System.out.println("Условия фильтра - ");
            String scannedFilter = sca.nextLine();
            
            table = statement.executeQuery("SELECT * FROM edktable__6 WHERE " + scannedFilter);
            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }

            if (table != null) {
                table.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            } // Отключение от базы данных

        } catch (Exception e) {
            System.err.println("Error accessing database!");
            e.printStackTrace();
        }
    }

}