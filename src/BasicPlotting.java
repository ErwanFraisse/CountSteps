import java.util.Arrays;
import java.util.Random;
import javax.swing.JFrame;
import org.math.plot.Plot2DPanel;

public class BasicPlotting {
	public static double[][] sampleData, sampleData2, sampleData3;
	public static String datafile = "data/64StepsInPocketJogging-out.csv";
	public static String datafile2 = "data/64StepsInHandJogging-out.csv";
	public static String datafile3 = "data/walkingSampleData-out.csv";
	
	public static void main(String[] args) {
		// Create data set
		CSVData dataset = CSVData.createDataSet(datafile, 0);
		CSVData dataset2 = CSVData.createDataSet(datafile2, 0);
		CSVData dataset3 = CSVData.createDataSet(datafile3, 0);

		// Get 2d array of all data
		sampleData = dataset.getAllData();
		sampleData2 = dataset2.getAllData();
		sampleData3 = dataset3.getAllData();

		double[] time = ArrayHelper.extractColumn(sampleData, 0);		
		
//		double[][] accel = ArrayHelper.extractColumns(sampleData, new int[] { 1, 2, 3 });
//		double[] magAccel = CountStepsBlank.calculateMagnitudesFor(accel);
//		double[][] gyro = ArrayHelper.extractColumns(sampleData, new int[] { 7, 8, 9 });
//		double[] magGyro = CountStepsBlank.calculateMagnitudesFor(gyro);
		
//		System.out.println(CountStepsBlank.mean(magAccel));
//		System.out.println(CountStepsBlank.calculateStandardDeviation(magAccel, CountStepsBlank.mean(magAccel)));
		CountStepsBlank.countSteps(time, sampleData);
		
		double[] time2 = ArrayHelper.extractColumn(sampleData2, 0);		
		
//		double[][] accel2 = ArrayHelper.extractColumns(sampleData2, new int[] { 1, 2, 3 });
//		double[] magsAccel2 = CountStepsBlank.calculateMagnitudesFor(accel2);
//		double[][] gyro2 = ArrayHelper.extractColumns(sampleData2, new int[] { 7, 8, 9 });
//		double[] magGyro2 = CountStepsBlank.calculateMagnitudesFor(gyro2);
		
//		System.out.println(CountStepsBlank.mean(magsAccel2));
//		System.out.println(CountStepsBlank.calculateStandardDeviation(magsAccel2, CountStepsBlank.mean(magsAccel2)));
		CountStepsBlank.countSteps(time2, sampleData2);
		
		double[] time3 = ArrayHelper.extractColumn(sampleData3, 0);		
		
//		double[][] accel3 = ArrayHelper.extractColumns(sampleData3, new int[] { 1, 2, 3 });
//		double[] magsAccel3 = CountStepsBlank.calculateMagnitudesFor(accel3);
//		double[][] gyro3 = ArrayHelper.extractColumns(sampleData3, new int[] { 7, 8, 9 });
//		double[] magGyro3 = CountStepsBlank.calculateMagnitudesFor(gyro3);
		
//		System.out.println(CountStepsBlank.mean(magsAccel3));
//		System.out.println(CountStepsBlank.calculateStandardDeviation(magsAccel3, CountStepsBlank.mean(magsAccel3)));
		CountStepsBlank.countSteps(time3, sampleData3);
		
		
//		Plot2DPanel plot = new Plot2DPanel();
		
		// add a line plot to the PlotPanel
//		plot.addLinePlot("Acceleration Data magnitude", mags);
//		plot.addLinePlot("Gyro Data magnitude", mags2);
		
		// put the PlotPanel in a JFrame, as a JPanel
//		JFrame frame = new JFrame("Results");
//		frame.setSize(800, 600);
//		frame.setContentPane(plot);
//		frame.setVisible(true);
	}

}
