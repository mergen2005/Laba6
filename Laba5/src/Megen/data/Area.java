package Megen.data;

public class Area extends Organization {
    private String benefit;

    public Area(String name, int id, String benefit) {
        super(name, id);
        this.benefit = benefit;
    }

    public String getBenefit() {
        return benefit;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    @Override
    public void sail() {
    }
}
