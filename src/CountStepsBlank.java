import java.util.ArrayList;

import javax.swing.JFrame;
import org.math.plot.Plot2DPanel;

public class CountStepsBlank {

public static void countSteps(double[] times, double[][] censorData) {
		
		double[][] accelData = ArrayHelper.extractColumns(censorData, 1,3);
		double[][] gyroData = ArrayHelper.extractColumns(censorData, 7,3);
		double[][] linearAccelData = ArrayHelper.extractColumns(censorData, 10,3);
		double[] accelVectorArray = calculateMagnitudesFor(accelData);
		double[] gyroVectorArray = calculateMagnitudesFor(gyroData);
		double[] linearAccelVectorArray = calculateMagnitudesFor(linearAccelData);
		double SDAccel = calculateStandardDeviation(accelVectorArray, mean(accelVectorArray));
		double SDGyro = calculateStandardDeviation(gyroVectorArray, mean(gyroVectorArray));
		double SDlinearAccel = calculateStandardDeviation(linearAccelVectorArray, mean(linearAccelVectorArray));
		
		countSteps(accelVectorArray, SDAccel, 0.7, "Acceleration");
		countSteps(gyroVectorArray, SDGyro, 0.5, "Gyro");
		countSteps(linearAccelVectorArray, SDlinearAccel, 0.3, "Linear acceleration");
		System.out.println("");
		
	}

	public static void countSteps(double[] arr, double SD, double decimal, String sensorType) {
		int steps = 0;
		for (int i = 1; i < arr.length - 1; i++) {
			if (arr[i] > arr[i-1] && arr[i] > arr[i+1]) {
				if (arr[i] > mean(arr) + decimal*SD) steps++;
			}
		}
		System.out.print( sensorType + " steps: " + steps + "\t|\t");
	}
	
	public static double[] getAccurateTime(double[] times) {
		double[] newtimes = new double[times.length];
		for (int i = 0; i < times.length; i++) {
			newtimes[i] = times[i]- times[0];
		}
		return newtimes;
	}
	
	public static double mean(double[] arr) {
		double totalSum = 0.0;
		for (int i = 0; i < arr.length; i++) {
			totalSum += arr[i];
		}
		return totalSum/arr.length;
	}
	
	public static double calculateStandardDeviation(double[] arr, double mean) {
		double SumofSquaredDifferences = 0.0;
		double sum = arr.length - 1;
		for (int i = 0; i < arr.length; i++) {
			SumofSquaredDifferences += (arr[i] - mean)*(arr[i] - mean);
		}
		double standardDeviation = Math.sqrt(SumofSquaredDifferences/sum);
		return standardDeviation;
	}

	public static double[] calculateMagnitudesFor(double[][] censorData) {
		double[] vectorMagnitudeArray = new double[censorData.length];
		for (int i = 0; i < censorData.length; i++) {
			vectorMagnitudeArray[i] = calculateMagnitude(censorData[i][0], censorData[i][1], censorData[i][2]);
		}
		return vectorMagnitudeArray;
	}
	
	public static double[] convertTime(double[] arr) {
		for (int i = 0; i < arr.length; i++) {
			arr[i] = i;
		}
		return arr;
	}
	
	public static double calculateMagnitude(double x, double y, double z) {
		return Math.sqrt(x*x + y*y + z*z);
	}

	public static void displayJFrame(Plot2DPanel plot) {
		JFrame frame = new JFrame("Results");
		frame.setSize(800, 600);
		frame.setContentPane(plot);
		frame.setVisible(true);
	}

}