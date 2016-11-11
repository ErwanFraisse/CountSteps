
import javax.swing.JFrame;
import org.math.plot.Plot2DPanel;

public class BasicPlotting {
	public static double[][] sampleData, sampleData2, sampleData3;
	public static String datafile = "data/walkingSampleData-out.csv";
	public static String datafile2 = "TestData/data/back pocket 32.csv";

	public static void main(String[] args) {
		// Create data set
		CSVData dataset = CSVData.createDataSet(datafile, 0);
		CSVData dataset2 = CSVData.createDataSet(datafile2, 0);

		// Get 2d array of all data
		sampleData = dataset.getAllData();
		sampleData2 = dataset2.getAllData();

		double[] time = ArrayHelper.extractColumn(sampleData2, 0);

		//OG = original
		double[][] OGaccel = ArrayHelper.extractColumns(sampleData2, new int[] { 1, 2, 3 });
		double[] OGmagAccel = CountStepsBlank.calculateMagnitudesFor(OGaccel);

		//naive, initial algorithm
		CountStepsBlank.countSteps(CountStepsBlank.getAccurateTime(time), sampleData2);

		double[] time2 = ArrayHelper.extractColumn(sampleData2, 0);

		//Iphone 6S did not have linear acceleration data so I commented it out
		double[][] NEWaccel = ArrayHelper.extractColumns(sampleData2, new int[] { 1, 2, 3 });
		double[] NEWmagAccel = CountStepsBlank.calculateMagnitudesFor(NEWaccel);
		double[][] NEWgyro = ArrayHelper.extractColumns(sampleData2, new int[] { 4, 5, 6 });
		double[] NEWmagGyro = CountStepsBlank.calculateMagnitudesFor(NEWgyro);
//		double[][] NEWlinAccel = ArrayHelper.extractColumns(sampleData2, new int[] { 7, 8, 9 });
//		double[] NEWmagLinAccel = CountStepsBlank.calculateMagnitudesFor(NEWlinAccel);

		//improved algorithm
		CountStepsBlank.refinedCountSteps(CountStepsBlank.getAccurateTime(time2), sampleData2);

		Plot2DPanel plot = new Plot2DPanel();
		
		// add a line plot to the PlotPanel
		plot.addLinePlot("Original Acceleration Data count steps", OGmagAccel);
		plot.addLinePlot("NEW accel Data magnitude", NEWmagAccel);
		plot.addLinePlot("NEW Gyro Data magnitude", NEWmagGyro);
//		plot.addLinePlot("NEW linear accel magnitude", NEWmagLinAccel);
		plot.addLinePlot("threshold NEW gyro", threshold(CountStepsBlank.mean(NEWmagGyro), NEWmagGyro, CountStepsBlank.calculateStandardDeviation(NEWmagGyro, CountStepsBlank.mean(NEWmagGyro)), 2));
		plot.addLinePlot("threshold NEW accel", threshold(CountStepsBlank.mean(NEWmagAccel), NEWmagAccel, CountStepsBlank.calculateStandardDeviation(NEWmagAccel, CountStepsBlank.mean(NEWmagAccel)), 1));
//		plot.addLinePlot("threshold NEW lin Accel", threshold(CountStepsBlank.mean(NEWmagLinAccel), NEWmagLinAccel, CountStepsBlank.calculateStandardDeviation(NEWmagLinAccel, CountStepsBlank.mean(NEWmagLinAccel)), 2));
		plot.addLinePlot("threshold OG accel", threshold(CountStepsBlank.mean(OGmagAccel), OGmagAccel, CountStepsBlank.calculateStandardDeviation(OGmagAccel, CountStepsBlank.mean(OGmagAccel)), 1));

		
		// put the PlotPanel in a JFrame, as a JPanel
		JFrame frame = new JFrame("Results");
		frame.setSize(800, 600);
		frame.setContentPane(plot);
		frame.setVisible(true);
	}
	
	/***
	 * calculates mean + x*SD threshold of a sensor
	 * 
	 * @param mean/// sensor's magnitude array mean
	 * @param vector/// sensor's magnitude array
	 * @param SD/// sensor's magnitude array standard deviation
	 * @param decimal/// sensors's decimal value to get desired standard deviations(x*SD)
	 * 
	 * @return a double array representing the threshold to draw on the graph
	 */
	public static double[] threshold(double mean, double[] vector, double SD, double decimal) {
		double[] meanArray = new double[vector.length];
		for (int j = 0; j < meanArray.length; j++) {
			meanArray[j] = mean + decimal*SD;
		}
		return meanArray;
	}

}
