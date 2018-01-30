package org.usfirst.frc.team5472.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Limelight {
	
	/**
	 * All of these const variables are keys (and values in some cases) for the 'limelight' NetworkTable. 
	 * The 'getter' keys are used for receiving values from the table.
	 * The 'setter' key-value pairs are used for setting values on the table.
	 */
	
	//Setters
	private static final String LED_CONTROL = "ledMode";
	private static final int LED_ON = 0;
	private static final int LED_OFF = 1;
	
	private static final String CAMERA_MODE = "camMode";
	private static final int MODE_VISION_PROCESSING = 0;
	private static final int MODE_DRIVER_CAMERA = 1;
	
	private static final String ACTIVE_PIPELINE = "pipeline";
	private static final int SWITCH_DETECTION = 0;
	private static final int BOX_DETECTION = 1;
	
	//Getters
	private static final String TARGET_EXISTS = "tv";
	private static final String VERTICAL_ANGLE = "ty";
	private static final String HORIZONTAL_ANGLE = "tx";
	private static final String TARGET_AREA = "ta";
	private static final String TARGET_SKEWNESS = "ts";
	private static final String INVERSE_FRAMERATE = "tl"; //Time taken by the Limelight processor to process the frame

	private boolean limeLightConnected = false;
	
	private NetworkTable limeLightTable;
	
	private void checkConnection() {
		System.out.println("Testing Connection");
		limeLightTable = NetworkTableInstance.getDefault().getTable("limelight");
		if(limeLightTable.getKeys().size() < 20) {
			//Usually it has 25 keys, it will have 0 if the Limelight hasn't been connected
			//If a key has been set before checking this, the size will not be zero
			System.out.println("Limelight camera not connected, connect the Limelight camera and restart robot code.");
			limeLightConnected = false;
		} else {
			System.out.println("LimeLight connected.");
			limeLightConnected = true;
		}
	}
	
	public Limelight() {
		checkConnection();
	}
	
	public boolean isConnected() {
		return limeLightConnected;
	}
	
	public void setVisionProcessing(boolean enabled) {
		int asAnInt = enabled ? MODE_VISION_PROCESSING : MODE_DRIVER_CAMERA;
		limeLightTable.getEntry(CAMERA_MODE).setNumber(asAnInt);
	}
	
	public boolean getVisionProcessingEnabled() {
		int asAnInt = limeLightTable.getEntry(CAMERA_MODE).getNumber(MODE_VISION_PROCESSING).intValue();
		return asAnInt == MODE_VISION_PROCESSING ? true : false;
	}
	
	public void setLed(boolean enabled) {
		int asAnInt = enabled ? LED_ON : LED_OFF;
		limeLightTable.getEntry(LED_CONTROL).setNumber(asAnInt);
	}
	
	public boolean getLed() {
		int asAnInt = limeLightTable.getEntry(LED_CONTROL).getNumber(LED_ON).intValue();
		return asAnInt == LED_ON ? true : false;
	}
	
	public void useSwitchDetectionPipeline() {
		limeLightTable.getEntry(ACTIVE_PIPELINE).setNumber(SWITCH_DETECTION);
	}
	
	public void useBoxDetectionPipeline() {
		limeLightTable.getEntry(ACTIVE_PIPELINE).setNumber(BOX_DETECTION);
	}
	
	public int getActivePipeline() {
		return limeLightTable.getEntry(ACTIVE_PIPELINE).getNumber(SWITCH_DETECTION).intValue();
	}
	
	
	public double getHorizontalAngle() {
		return limeLightTable.getEntry(HORIZONTAL_ANGLE).getDouble(360.0);
	}
	
	public double getVerticalAngle() {
		return limeLightTable.getEntry(VERTICAL_ANGLE).getDouble(360.0);
	}
	
	public double getTargetArea() {
		return limeLightTable.getEntry(TARGET_AREA).getDouble(0.0);
	}
	
	public double getTargetSkewness() {
		return limeLightTable.getEntry(TARGET_SKEWNESS).getDouble(360.0);
	}
	
	public double getPipelineLatency() {
		return limeLightTable.getEntry(INVERSE_FRAMERATE).getDouble(0.0);
	}
	
	public boolean targetExists() {
		int fromTable = limeLightTable.getEntry(TARGET_EXISTS).getNumber(0).intValue(); //1 if true
		return fromTable == 1 ? true : false;
	}
	
	public void reportDebugInformation() {
		SmartDashboard.putString("Light On?", getLed() ? "yes" : "no");
		SmartDashboard.putString("Vision Processing Enabled?", getVisionProcessingEnabled() ? "yes" : "no");
		SmartDashboard.putString("Which Pipeline is in Use?", getActivePipeline() == SWITCH_DETECTION ? "Switch" : "Box");
	}
}