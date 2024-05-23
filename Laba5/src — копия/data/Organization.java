package Megen.data;

public abstract class Organization {
    private String name;
    private int id;

    public Organization(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public abstract void sail();

    public int getId() {
        return id;
    }
    public static int getOrganizationType_Capital(Organization org) {
        if (org instanceof Capital) {
            return 1;
        } else if (org instanceof City) {
            return 2;
        } else if (org instanceof Area) {
            return 3;
        } else {
            return 0;
        }
    }
    public static int getOrganizationType_City(Organization org) {
        if (org instanceof City) {
            return 1;
        } else if (org instanceof Capital) {
            return 2;
        } else if (org instanceof Area) {
            return 3;
        } else {
            return 0;
        }
    }
    public static int getOrganizationType_Area(Organization org) {
        if (org instanceof Area) {
            return 1;
        } else if (org instanceof Capital) {
            return 2;
        } else if (org instanceof City) {
            return 3;
        } else {
            return 0;
        }
    }
}

