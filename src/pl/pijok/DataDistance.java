package pl.pijok;

public class DataDistance {

    private String group;
    private Double distance;

    public DataDistance(String group, Double distance) {
        this.group = group;
        this.distance = distance;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
