package pl.pijok;

public class TrainData {

    private String group;
    private Double[] values;

    public TrainData(String group, Double[] values) {
        this.group = group;
        this.values = values;
    }

    public static TrainData createDataFromString(String line){
        try{
            String[] parts = line.split(",");
            Double[] array = new Double[parts.length - 1];

            for(int i = 0; i < array.length; i++){
                array[i] = Double.parseDouble(parts[i]);
            }

            return new TrainData(parts[parts.length - 1], array);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
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
