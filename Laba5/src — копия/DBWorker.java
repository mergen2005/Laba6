import Megen.data.Area;
import Megen.data.Capital;
import Megen.data.City;
import Megen.data.Organization;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBWorker {
    private final String URL = "jdbc:sqlite:lol.db";
    private final String DBTITLE = "lol";
    private Connection connection;

    public DBWorker(){
        initDB();
    }
    private void initDB(){
        try {
            connection = DriverManager.getConnection(URL);
            //createDB();

        } catch (SQLException e) {
            System.out.println("initDB-незагрузилась бд");
        }
    }
    public void createDB(){
        try {
            Statement statement = connection.createStatement();
            // Удалил автоинкремент and PRIMARY KEY
            statement.execute("CREATE TABLE IF NOT EXISTS organization_type ( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT UNIQUE )");
            statement.execute("INSERT INTO organization_type (name) VALUES (\"City\")");
            statement.execute("INSERT INTO organization_type (name) VALUES (\"Capital\")");
            statement.execute("INSERT INTO organization_type (name) VALUES (\"Area\")");



            statement.execute("CREATE TABLE IF NOT EXISTS '" + DBTITLE + "' ( 'id' INTEGER, 'name' TEXT, 'organization_type_id' INTEGER, 'ext_data' Text, FOREIGN KEY (organization_type_id)  REFERENCES organization_type (id) )");

            statement.close();
        } catch (SQLException e) {
            System.out.println("createDB-проблема с созданием базы данных");
        }
    }
    public void closeDB(){
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("closeDB-не закрылось 0_0");
        }
    }
    public void showDataBase() {
        try {
            Statement statement = connection.createStatement();

            HashMap<Integer, String> map = new HashMap<>();
            ResultSet organization_type = statement.executeQuery("select * from organization_type");
            while (organization_type.next())
            {
                map.put(organization_type.getInt(1), organization_type.getString(2));

                //System.out.println(map.get(person_type.getInt(1)));
            }

            ResultSet rs = statement.executeQuery("select * from " + DBTITLE);
            while(rs.next())
            {
                String message = rs.getInt(1) + " | " + rs.getString(2) + " | ";

                int organization_type_id =rs. getInt(3);

                message += map.get(organization_type_id);

                message += " | " + rs.getString(4);

                System.out.println(message);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Ошибка в getStudentsList при попытке получить данные из бд");
        }
    }
    public ArrayList<Organization> getList(){
        ArrayList<Organization> data = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();

            HashMap<Integer, String> map = new HashMap<>();
            ResultSet organization_type = statement.executeQuery("select * from organization_type");
            while (organization_type.next())
            {
                map.put(organization_type.getInt(1), organization_type.getString(2));

                //System.out.println(map.get(person_type.getInt(1)));
            }

            ResultSet rs = statement.executeQuery("select * from " + DBTITLE);
            while(rs.next())
            {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String ext_data = rs.getString(4);


                int organization_type_id = rs. getInt(3);

                switch (organization_type_id)
                {
                    case 1:
                        data.add(new City(name, id, ext_data));
                        break;
                    case 2:
                        data.add(new Capital(name, id, ext_data));
                        break;
                    case 3:
                        data.add(new Area(name, id, ext_data));
                        break;
                }
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Ошибка в getList при попытке получить данные из бд в ArrayList");
        }
        return data;
    }
    public void addOrganization(Organization organization){
        try {
            ////////////////
            if (connection.isClosed()){
                connection = DriverManager.getConnection(URL);
            }
            ///////////////

            //PreparedStatement statement = connection.prepareStatement("INSERT INTO '" + DBTITLE + "' (id, name, type, salary, quality)" +  " VALUES (?, ?, ?, ?, ?)");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO '" + DBTITLE + "' (id, name, organization_type_id, ext_data) VALUES (?, ?, ?, ?)");
            statement.setObject(1, organization.getId());
            statement.setObject(2, organization.getName());

            if (organization instanceof City) {
                statement.setObject(3, 1);
                statement.setObject(4, ((City)organization).getBenefit());
            }
            else if (organization instanceof Capital) {
                statement.setObject(3, 2);
                statement.setObject(4, ((Capital)organization).getBenefit());
            }
            else if (organization instanceof Area) {
                statement.setObject(3, 3);
                statement.setObject(4, ((Area)organization).getBenefit());
            }

            statement.execute();

            statement.close();
        } catch (SQLException e) {
            System.out.println("Какая-то ошибка с добавлением в базу");
            System.out.println(e);
        }
    }
    public void exterminateDB(){
        try {
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE organization_type");
            statement.execute("DROP TABLE " + DBTITLE);

            statement.close();
        } catch (SQLException e) {
            System.out.println("Ошибка в clearDB при полной очистке бд");
        }
    }
    public void rewrite(List<Organization> data){
        exterminateDB();
        createDB();
        for (Organization organization : data){
            addOrganization(organization);
        }
    }
}