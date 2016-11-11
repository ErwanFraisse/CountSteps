
public class CountStepsBlank {

	public static final double OG_ACCEL_DATA_SD_DECIMAL = 1.0;
	public static final double NEW_ACCEL_DATA_SD_DECIMAL = 1.3;
	public static final double NEW_GYRO_DATA_SD_DECIMAL = 1.1;
	public static final double NEW_LINEAR_ACCEL_DATA_SD_DECIMAL = 1.3;
	
	/***
	 * refined algorithm to count steps
	 * @param times/// time in milliseconds (50 Hz)
	 * @param censorData/// the sensor data array
	 * 
	 * @return prints 3 integer values, each number of steps for acceleration, gyro, and linear acceleration sensor data.
	 */
	public static void refinedCountSteps(double[] times, double[][] censorData) {

		double[][] accelData = ArrayHelper.extractColumns(censorData, 10, 3);
		double[][] gyroData = ArrayHelper.extractColumns(censorData, 4, 3);
		double[][] linearAccelData = ArrayHelper.extractColumns(censorData, 7, 3);
		double[] accelVectorArray = calculateMagnitudesFor(accelData);
		double[] gyroVectorArray = calculateMagnitudesFor(gyroData);
		double[] linearAccelVectorArray = calculateMagnitudesFor(linearAccelData);
		double SDAccel = calculateStandardDeviation(accelVectorArray, mean(accelVectorArray));
		double SDGyro = calculateStandardDeviation(gyroVectorArray, mean(gyroVectorArray));
		double SDlinearAccel = calculateStandardDeviation(linearAccelVectorArray, mean(linearAccelVectorArray));

		countSteps(accelVectorArray, SDAccel, NEW_ACCEL_DATA_SD_DECIMAL, "NEW Acceleration");	
		countSteps(gyroVectorArray, SDGyro, NEW_GYRO_DATA_SD_DECIMAL, "NEW Gyro");
		countSteps(linearAccelVectorArray, SDlinearAccel, NEW_LINEAR_ACCEL_DATA_SD_DECIMAL, "NEW Linear acceleration");
		System.out.println("");
	}
	
	/***
	 * Naive count steps algorithm
	 * @param times/// time in milliseconds (50hz)
	 * @param censorData/// the sensor data array
	 * 
	 * @return prints 1 integer value, the number of steps for the acceleration sensor data
	 */ 
	public static void countSteps(double[] times, double[][] censorData) {

		double[][] accelData = ArrayHelper.extractColumns(censorData, 1, 3);
		double[] accelVectorArray = calculateMagnitudesFor(accelData);
		double SDAccel = calculateStandardDeviation(accelVectorArray, mean(accelVectorArray));

		countSteps(accelVectorArray, SDAccel, OG_ACCEL_DATA_SD_DECIMAL, "OG Acceleration");
		System.out.println("");

	}
	
	/***
	 * counts steps
	 * @param arr/// magnitude sensor array
	 * @param SD/// standard deviation of arr
	 * @param decimal/// number of standard deviations wanted (decimal*SD)
	 * @param sensorType/// type of sensor data used (accel, gyro, etc)
	 * 
	 * @return prints the number of steps for the sensor value used
	 */
	public static void countSteps(double[] arr, double SD, double decimal, String sensorType) {
		int steps = 0;
		for (int i = 1; i < arr.length - 1; i++) {
			if (arr[i] > arr[i - 1] && arr[i] > arr[i + 1]) {
				if (arr[i] > mean(arr) + decimal * SD)
					steps++;
			}
		}
		System.out.print(sensorType + " steps: " + steps + "\t|\t");
	}
	
	/***
	 * gets accurate time if time column of sensor data is in unix.
	 * @param times/// initial time column
	 * 
	 * @return time array going from 0-whatever value necessary
	 */
	public static double[] getAccurateTime(double[] times) {
		double[] newtimes = new double[times.length];
		for (int i = 0; i < times.length; i++) {
			newtimes[i] = times[i] - times[0];
		}
		return newtimes;
	}
	
	/***
	 * gets mean of a sensor's magnitude array
	 * @param arr/// a sensor's magnitude array
	 * 
	 * @return double value representing mean of a sensor's magnitude array values
	 */
	public static double mean(double[] arr) {
		double totalSum = 0.0;
		for (int i = 0; i < arr.length; i++) {
			totalSum += arr[i];
		}
		return totalSum / arr.length;
	}
	
	/***
	 * calculates standard deviation of a sensor's magnitude array
	 * @param arr/// a sensor's magnitude array
	 * @param mean/// a sensor's magnitude array average
	 * 
	 * @return double representing standard deviation value of a sensor's magnitude array
	 */
	public static double calculateStandardDeviation(double[] arr, double mean) {
		double SumofSquaredDifferences = 0.0;
		double sum = arr.length - 1;
		for (int i = 0; i < arr.length; i++) {
			SumofSquaredDifferences += (arr[i] - mean) * (arr[i] - mean);
		}
		double standardDeviation = Math.sqrt(SumofSquaredDifferences / sum);
		return standardDeviation;
	}
	
	/***
	 * creates an array containing each sensor's x, y, and z axis vector magnitudes
	 * @param censorData/// a sensor's x,y and z axis readings
	 * 
	 * @return an array with magnitudes for a given sensor
	 */
	public static double[] calculateMagnitudesFor(double[][] censorData) {
		double[] vectorMagnitudeArray = new double[censorData.length];
		for (int i = 0; i < censorData.length; i++) {
			vectorMagnitudeArray[i] = calculateMagnitude(censorData[i][0], censorData[i][1], censorData[i][2]);
		}
		return vectorMagnitudeArray;
	}
	
	/***
	 * calculates the magnitude for a x, y, and z-axis reading of a sensor
	 * 
	 * @param x/// a sensor's x-axis readings
	 * @param y/// a sensor's y-axis readings
	 * @param z/// a sensor's z-axis readings
	 * 
	 * @return double representing the x, y, and z-axis magnitude.
	 */
	public static double calculateMagnitude(double x, double y, double z) {
		return Math.sqrt(x * x + y * y + z * z);
	}
}