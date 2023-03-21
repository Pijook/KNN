package pl.pijok;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GUIPanel extends JPanel{

    private final JButton startUserTestButton;
    private final JTextField testInputData;
    private final JLabel userTitleLabel;
    private final JLabel trainingDataLabel;
    private final JLabel testDataLabel;
    private final JSlider kSlider;
    private final JLabel kSliderLabel;
    private final JLabel predictedLabel;
    private final JTextField userKTextField;
    private final JLabel userKLabel;
    private final JLabel userDataLabel;
    private final JTextField trainFilePathField;
    private final JLabel trainFilePathLabel;
    private final JLabel testFilePathLabel;
    private final JTextField testFilePathField;
    private final JToggleButton loadFilesButton;
    private final JToggleButton startFileTestButton;
    private final JToggleButton showGraphButton;

    public GUIPanel() {
        //construct components
        startUserTestButton = new JButton ("Test");
        testInputData = new JTextField (5);
        userTitleLabel = new JLabel ("User");
        trainingDataLabel = new JLabel ("Training Data: 0");
        testDataLabel = new JLabel ("Tested Data: 0");
        kSlider = new JSlider (1, 21);
        kSliderLabel = new JLabel ("Max K: 10");
        predictedLabel = new JLabel ("Predicted: null");
        userKTextField = new JTextField (5);
        userKLabel = new JLabel ("K");
        userDataLabel = new JLabel ("Data");
        trainFilePathField = new JTextField (5);
        trainFilePathLabel = new JLabel ("Train File Path");
        testFilePathLabel = new JLabel ("Test File Path");
        testFilePathField = new JTextField (5);
        loadFilesButton = new JToggleButton ("Load Files", false);
        startFileTestButton = new JToggleButton ("Test", false);
        showGraphButton = new JToggleButton ("Show Graph", false);

        //set components properties
        kSlider.setOrientation (JSlider.HORIZONTAL);
        kSlider.setMinorTickSpacing (1);
        kSlider.setMajorTickSpacing (5);
        kSlider.setPaintTicks (true);
        kSlider.setPaintLabels (true);

        //adjust size and set layout
        setPreferredSize (new Dimension (604, 356));
        setLayout (null);

        //add components
        add (startUserTestButton);
        add (testInputData);
        add (userTitleLabel);
        add (trainingDataLabel);
        add (testDataLabel);
        add (kSlider);
        add (kSliderLabel);
        add (predictedLabel);
        add (userKTextField);
        add (userKLabel);
        add (userDataLabel);
        add (trainFilePathField);
        add (trainFilePathLabel);
        add (testFilePathLabel);
        add (testFilePathField);
        add (loadFilesButton);
        add (startFileTestButton);
        add (showGraphButton);

        //set component bounds (only needed by Absolute Positioning)
        startUserTestButton.setBounds (15, 220, 100, 20);
        testInputData.setBounds (15, 105, 200, 25);
        userTitleLabel.setBounds (20, 45, 100, 25);
        trainingDataLabel.setBounds (20, 15, 200, 25);
        testDataLabel.setBounds (275, 245, 100, 25);
        kSlider.setBounds (375, 220, 100, 50);
        kSliderLabel.setBounds (275, 220, 100, 25);
        predictedLabel.setBounds (20, 250, 400, 25);
        userKTextField.setBounds (15, 175, 100, 25);
        userKLabel.setBounds (20, 140, 100, 25);
        userDataLabel.setBounds (20, 75, 100, 25);
        trainFilePathField.setBounds (275, 50, 200, 25);
        trainFilePathLabel.setBounds (275, 20, 100, 25);
        testFilePathLabel.setBounds (275, 80, 100, 25);
        testFilePathField.setBounds (275, 110, 195, 25);
        loadFilesButton.setBounds (275, 150, 100, 25);
        startFileTestButton.setBounds (275, 185, 100, 25);
        showGraphButton.setBounds (275, 295, 140, 30);

        createGUILogic();
    }

    private void createGUILogic(){
        loadFilesButton.addActionListener(e -> {
            String testFilePath = testFilePathField.getText();
            loadFilesButton.setSelected(false);

            File file = new File(testFilePath);

            if(!file.exists()){
                JOptionPane.showMessageDialog(null, testFilePath + " doesn't exist!");
                return;
            }

            String trainFilePath = trainFilePathField.getText();

            file = new File(trainFilePath);

            if(!file.exists()){
                JOptionPane.showMessageDialog(null, testFilePath + " doesn't exist!");
                return;
            }

            KNN.getAlgorithm().setTestFilePath(testFilePath);
            KNN.getAlgorithm().setTrainFilePath(trainFilePath);

            KNN.getAlgorithm().loadData();

            getTrainingDataLabel().setText("Training Data: " + KNN.getAlgorithm().getTrainingData().size());
            getTestDataLabel().setText("Test Data: " + KNN.getAlgorithm().getTestData().size());
        });

        startUserTestButton.addActionListener( e -> {
            TrainData data = TrainData.createDataFromString(testInputData.getText());
            if(data == null) {
                JOptionPane.showMessageDialog(null, "Invalid test data");
                return;
            }

            String kString = userKTextField.getText();

            if(kString == null || kString.length() == 0) {
                JOptionPane.showMessageDialog(null, "Invalid k");
                return;
            }

            int k = -1;

            try{
                k = Integer.parseInt(kString);
            }
            catch (NumberFormatException exception){
                JOptionPane.showMessageDialog(null, "Invalid k");
                return;
            }

            KNN.getAlgorithm().setK(k);
            KNNResult result = KNN.getAlgorithm().countSpecificProblem(data);

            predictedLabel.setText("Predicted: " + result.getGroup());
            startUserTestButton.setSelected(false);
        });

        kSlider.addChangeListener(e -> {
            kSliderLabel.setText("Max K: " + kSlider.getValue());
        });

        startFileTestButton.addActionListener(e -> {
            List<KNNResult> multipleResults = new ArrayList<>();
            for(int i = 1; i <= kSlider.getValue(); i++){
                KNN.getAlgorithm().setK(i);
                KNNResult result = KNN.getAlgorithm().countProblem();
                multipleResults.add(result);
            }

            KNN.setMultipleResults(multipleResults);

            startFileTestButton.setSelected(false);
        });

        showGraphButton.addActionListener(e -> {
            if(KNN.getMultipleResults() == null) {
                JOptionPane.showMessageDialog(null, "First you must run tests!");
                return;
            }

            new GraphFrame(KNN.getMultipleResults());

            showGraphButton.setSelected(false);
        });
    }

    public JButton getStartUserTestButton() {
        return startUserTestButton;
    }

    public JTextField getTestInputData() {
        return testInputData;
    }

    public JLabel getUserTitleLabel() {
        return userTitleLabel;
    }

    public JLabel getTrainingDataLabel() {
        return trainingDataLabel;
    }

    public JLabel getTestDataLabel() {
        return testDataLabel;
    }

    public JSlider getkSlider() {
        return kSlider;
    }

    public JLabel getkSliderLabel() {
        return kSliderLabel;
    }

    public JLabel getPredictedLabel() {
        return predictedLabel;
    }

    public JTextField getUserKTextField() {
        return userKTextField;
    }

    public JLabel getUserKLabel() {
        return userKLabel;
    }

    public JLabel getUserDataLabel() {
        return userDataLabel;
    }

    public JTextField getTrainFilePathField() {
        return trainFilePathField;
    }

    public JLabel getTrainFilePathLabel() {
        return trainFilePathLabel;
    }

    public JLabel getTestFilePathLabel() {
        return testFilePathLabel;
    }

    public JTextField getTestFilePathField() {
        return testFilePathField;
    }

    public JToggleButton getLoadFilesBUtton() {
        return loadFilesButton;
    }

    public JToggleButton getStartFileTestButton() {
        return startFileTestButton;
    }

    public JToggleButton getShowGraphButton() {
        return showGraphButton;
    }
}
