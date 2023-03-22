package pl.pijok;

import java.util.List;

public class KNN {

    private static KNNFrame knnFrame;
    private static KNNAlgorithm algorithm;

    private static int k;
    private static String trainFilePath;
    private static String testFilePath;

    private static List<KNNResult> multipleResults = null;

    public static void main(String[] args){
        knnFrame = new KNNFrame();

        try{
            k = Integer.parseInt(args[0]);
            trainFilePath = args[1];
            testFilePath = args[2];
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Invalid number of arguments!");
        }


        algorithm = new KNNAlgorithm(trainFilePath, testFilePath, k);

        knnFrame.getGuiPanel().getTrainingDataLabel().setText("Training Data: " + algorithm.getTrainingData().size());
        knnFrame.getGuiPanel().getTestDataLabel().setText("Test Data: " + algorithm.getTestData().size());
        knnFrame.getGuiPanel().getTrainFilePathField().setText(trainFilePath);
        knnFrame.getGuiPanel().getTestFilePathField().setText(testFilePath);
    }

    public static void find(String data){
        KNNResult result;
        if(data == null){
            result = algorithm.countProblem();
        }
        else{
            result = algorithm.countSpecificProblem(TrainData.createDataFromString(data));
        }
    }

    public static KNNAlgorithm getAlgorithm() {
        return algorithm;
    }

    public static List<KNNResult> getMultipleResults() {
        return multipleResults;
    }

    public static void setMultipleResults(List<KNNResult> multipleResults) {
        KNN.multipleResults = multipleResults;
    }
}
