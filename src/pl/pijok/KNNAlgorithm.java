package pl.pijok;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class KNNAlgorithm {

    private List<TrainData> trainingData;
    private List<TrainData> testData;
    private String trainFilePath;
    private String testFilePath;
    private int k;

    public KNNAlgorithm(String trainFilePath, String testFilePath, int k) {
        trainingData = loadDataFromFile(trainFilePath);
        this.trainFilePath = trainFilePath;
        this.testFilePath = testFilePath;

        if(trainingData == null){
            return;
        }

        testData = loadDataFromFile(testFilePath);

        if(testData == null){
            return;
        }

        this.k = k;
    }

    public KNNResult countProblem() {
        AtomicInteger scanned = new AtomicInteger();
        AtomicInteger correct = new AtomicInteger();
        AtomicReference<String> result = new AtomicReference<>();
        testData.forEach(e -> {
            List<DataDistance> countedDistance = countDistance(trainingData, e);
            sort(countedDistance);

            String predicted = findGroup(countedDistance);

            System.out.println(Arrays.toString(e.getValues()) + ", " + e.getGroup() + " predicted: " + predicted);

            result.set(predicted);

            boolean isCorrect = e.getGroup().equalsIgnoreCase(predicted);

            if(isCorrect) {
                correct.getAndIncrement();
            }
            scanned.getAndIncrement();
        });

        double precision = (double) correct.get() / scanned.get();
        precision = precision * 100;

        return new KNNResult(k, precision, result.get());
    }

    public KNNResult countSpecificProblem(TrainData trainData){
        List<DataDistance> countedDistance = countDistance(trainingData, trainData);
        sort(countedDistance);
        String predicted = findGroup(countedDistance);

        return new KNNResult(k, -1.0, predicted);
    }

    private void sort(List<DataDistance> toSort){
        toSort.sort((Comparator.comparing(DataDistance::getDistance)));
    }

    public String findGroup(List<DataDistance> sortedData){
        HashMap<String, Integer> finalData = new HashMap<>();

        for(int i = 0; i < k; i++){
            DataDistance data = sortedData.get(i);
            if(!finalData.containsKey(data.getGroup())){
                finalData.put(data.getGroup(), 1);
            }
            else{
                finalData.put(data.getGroup(), finalData.get(data.getGroup()) + 1);
            }
        }

        AtomicReference<String> finalGroup = new AtomicReference<>("");
        AtomicInteger max = new AtomicInteger(-1);

        finalData.forEach((key, value) -> {
            if(value > max.get()) {
                finalGroup.set(key);
                max.set(value);
            }
        });

        return finalGroup.get();
    }

    private List<DataDistance> countDistance(List<TrainData> trainedData, TrainData toFind) {
        List<DataDistance> result = new ArrayList<>();
        trainedData.forEach(e -> {
            result.add(new DataDistance(
                    e.getGroup(),
                    countDistance(e.getValues(), toFind.getValues())
            ));
        });

        return result;
    }

    private Double countDistance(Double[] a, Double[] b) {
        double result = 0.0;
        for(int i = 0; i < a.length; i++){
            result += Math.pow(a[i] - b[i], 2);
        }
        return result;
    }

    public void loadData(){
        this.trainingData = loadDataFromFile(trainFilePath);
        this.testData = loadDataFromFile(testFilePath);
    }

    private List<TrainData> loadDataFromFile(String path){
        File file = new File(path);

        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found! (" + path + ")");
            return null;
        }

        List<TrainData> result = new ArrayList<>();

        while(scanner.hasNext()){
            String line = scanner.nextLine();
            result.add(TrainData.createDataFromString(line));
        }

        return result;
    }

    public List<TrainData> getTrainingData() {
        return trainingData;
    }

    public List<TrainData> getTestData() {
        return testData;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public String getTrainFilePath() {
        return trainFilePath;
    }

    public String getTestFilePath() {
        return testFilePath;
    }

    public void setTrainFilePath(String trainFilePath) {
        this.trainFilePath = trainFilePath;
    }

    public void setTestFilePath(String testFilePath) {
        this.testFilePath = testFilePath;
    }
}
