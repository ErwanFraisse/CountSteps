import java.util.ArrayList;

import javax.swing.JFrame;
import org.math.plot.Plot2DPanel;

public class CountStepsBlank {

public static int countSteps(double[] times, double[][] censorData) {
		
		double[][] accelData = ArrayHelper.extractColumns(censorData, 1,3);
		double[][] gyroData = ArrayHelper.extractColumns(censorData, 4,3);
		double[] accelVectorArray = calculateMagnitudesFor(accelData);
		double[] gyroVectorArray = calculateMagnitudesFor(gyroData);
		double SDAccel = calculateStandardDeviation(accelVectorArray, mean(accelVectorArray));
		double SDGyro = calculateStandardDeviation(gyroVectorArray, mean(gyroVectorArray));
		
		int stepsAccel = 0;
		for (int i = 1; i < accelVectorArray.length - 1; i++) {
			if (accelVectorArray[i] > accelVectorArray[i-1] && accelVectorArray[i] > accelVectorArray[i+1]) {
				if (accelVectorArray[i] > mean(accelVectorArray) + SDAccel) stepsAccel++;
			}
		}
		System.out.println("Calculated steps for acceleration data: " + stepsAccel);
		
		int stepsGyro = 0;
		for (int i = 1; i < gyroVectorArray.length - 1; i++) {
			if (gyroVectorArray[i] > gyroVectorArray[i-1] && gyroVectorArray[i] > gyroVectorArray[i+1]) {
				if (gyroVectorArray[i] > mean(gyroVectorArray) + 0.85*SDGyro) stepsGyro++;		
			}
		}
		System.out.println("Calculated steps for gyro data: " + stepsGyro);
		return stepsGyro;
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