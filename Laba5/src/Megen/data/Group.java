package Megen.data;

import Megen.DB.DBWorker;

import java.util.ArrayList;
import java.util.Comparator;

public class Group {
    private static ArrayList<Organization> organizations = new ArrayList<>();
    private static DBWorker db;

    public Group() {
        db = new DBWorker();
        organizations = db.getList();
    }

    public static boolean addCapital(String name, int id, String type_of_benefit) {
        for (Organization organization : organizations) {
            if (organization.getId() == id) {
                return false; // Завершаем метод
            }
            // DBWorker

        }
        Capital capital = new Capital(name, id, type_of_benefit);
        organizations.add(capital);
        db.addOrganization(capital);
        System.out.println("OK");
        return true;
    }
    public static boolean addCity(String name, int id, String type_of_benefit) {
        for (Organization organization : organizations) {
            if (organization.getId() == id) {
                return false; // Завершаем метод
            }
        }
        City city = new City(name, id, type_of_benefit);
        organizations.add(city);
        db.addOrganization(city);
        return true;
    }
    public static boolean addArea(String name, int id, String type_of_benefit) {

        for (Organization organization : organizations) {
            if (organization.getId() == id) {
                return false; // Завершаем метод
            }
        }
        Area area = new Area(name, id, type_of_benefit);
        organizations.add(area);
        db.addOrganization(area);
        return true;
    }

    public int getCount() {
        return this.organizations.size();
    }

    public static Organization getorganization(int index) {
        return organizations.get(index);
    }
    public static Organization getorganizationByID(int index) {
        for (Organization organization : organizations) {
            if (organization.getId() == index)
                return organization;
        }
        return null;
    }

    public void remove(int ind) {
        int id = this.organizations.get(ind).getId();
        this.organizations.remove(ind);
        db.delete(id);
    }
    public static String getNextAvailableId() {
        ArrayList<Integer> ids = new ArrayList<>();
        for (Organization organization : organizations) {
            ids.add(organization.getId());
        }
        int nextId = 1;
        while (ids.contains(nextId)) {
            nextId++;
        }
        return String.valueOf(nextId);
    }

    public static boolean searchMethod(int id) {
        for (int i = 0; i < organizations.size(); i++) {
            Organization organization = organizations.get(i);
            if (organization.getId() == id) {
                // Обменять местами объекты
                Organization firstorganization = organizations.get(0);
                organizations.set(0, organization);
                organizations.set(i, firstorganization);
                //System.out.println(organizations.get(0));
                return true; // Завершить метод после перемещения объекта
            }
        }
        return false;
    }

    public static boolean str_is_null(String str) {
        return str.isEmpty();
    }
    public static void sortList(int sort) {
        switch (sort) {
            case 1:
                organizations.sort(Comparator.comparingInt(Organization::getOrganizationType_Area));
                break;
            case 2:
                organizations.sort(Comparator.comparingInt(Organization::getOrganizationType_Capital));
                break;
            case 3:
                organizations.sort(Comparator.comparingInt(Organization::getOrganizationType_City));
                break;
        }
    }
}
