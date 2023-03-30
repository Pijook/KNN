package pl.pijok;

import java.util.Arrays;

public class TrainData {

    private String group;
    private Double[] values;

    public TrainData(String group, Double[] values) {
        this.group = group;
        this.values = values;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Double[] getValues() {
        return values;
    }

    public void setValues(Double[] values) {
        this.values = values;
    }
}
