package pl.pijok;

public class KNNResult {

    private int k;
    private double precision;
    private String group;

    public KNNResult(int k, double precision, String group) {
        this.k = k;
        this.precision = precision;
        this.group = group;
    }

    public double getPrecision() {
        return precision;
    }

    public String getGroup() {
        return group;
    }

    public int getK() {
        return k;
    }
}
