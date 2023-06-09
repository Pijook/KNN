package pl.pijok;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class KNN {
    private static List<TrainData> trainingData;
    private static List<TrainData> testData;
    private static int k;
    private static String trainFilePath;
    private static String testFilePath;

    public static void main(String[] args){
        k = Integer.parseInt(args[0]);
        trainFilePath = args[1];
        testFilePath = args[2];

        trainingData = loadDataFromFile(trainFilePath);

        if(trainingData == null){
            return;
        }

        testData = loadDataFromFile(testFilePath);

        if(testData == null){
            return;
        }

        AtomicInteger scanned = new AtomicInteger();
        AtomicInteger correct = new AtomicInteger();
        testData.forEach(e -> {
            List<DataDistance> countedDistance = countDistance(trainingData, e);
            sort(countedDistance);

            String predicted = findGroup(countedDistance);

            System.out.println(Arrays.toString(e.getValues()) + ", " + e.getGroup() + " predicted: " + predicted);

            boolean isCorrect = e.getGroup().equalsIgnoreCase(predicted);

            if(isCorrect) {
                correct.getAndIncrement();
            }
            scanned.getAndIncrement();
        });

        double precision = (double) correct.get() / scanned.get();
        precision = precision * 100;
        System.out.println("Precision: " + precision);
    }

    private static void sort(List<DataDistance> toSort){
        toSort.sort((Comparator.comparing(DataDistance::getDistance)));
    }

    public static String findGroup(List<DataDistance> sortedData){
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

    private static List<DataDistance> countDistance(List<TrainData> trainedData, TrainData toFind) {
        List<DataDistance> result = new ArrayList<>();
        trainedData.forEach(e -> {
            result.add(new DataDistance(
                    e.getGroup(),
                    countDistance(e.getValues(), toFind.getValues())
            ));
        });

        return result;
    }

    private static Double countDistance(Double[] a, Double[] b) {
        double result = 0.0;
        for(int i = 0; i < a.length; i++){
            result += Math.pow(a[i] - b[i], 2);
        }
        return result;
    }

    private static List<TrainData> loadDataFromFile(String path){
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
            String[] parts = line.split(",");
            Double[] array = new Double[parts.length - 1];

            for(int i = 0; i < array.length; i++){
                array[i] = Double.parseDouble(parts[i]);
            }

            result.add(new TrainData(parts[parts.length - 1], array));
        }

        return result;
    }


}
