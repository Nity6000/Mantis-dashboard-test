package frc.team4909.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {
  private final double slope = -56.4;
  private final double yintercept = 105.95;
  private final double lightOn = 3.0;
  private final double lightFlash= 2.0;
  private final double lightOff = 1.0;
  private final double blink = 2.0;
  private final NetworkTable frontCamFeed;
  private NetworkTable rearCamFeed;
  private NetworkTable activeCamFeed;

  private double tv;
  private double tx;
  private double ty;
  private double ta;
  private double ts;
  private double tl;
  //Camera Dep
  private final double targetHeight = 29;
  private final double cameraHeight = 5;
  private double cameraAngle = 0;
  //Target Dependent
  private double angleToTarget;
  private double distanceToTarget;
  private final boolean porthacth = false;
  private boolean usingFrontCam = false;

  private double frontLight;
  private double backLight;
  private String camName;
  private double pipeline = 0;

  /**
   * Gets the X offset in degrees.
   *
   * @param targetHeight is used for the distance formulas.
   * @param cameraHeight is used for the distance formulas.
   * @param cameraAngle  is used for the distance formulas
   */
  public Vision() {
    super();
    this.frontCamFeed = NetworkTableInstance.getDefault().getTable("limelight");
    this.setFrontCamFeed();
    this.updateTableVariables();
  }

  
  
  double ledState = 1;

  @Override
  public void periodic() {
    frontCamFeed.getEntry("ledMode").setNumber(ledState);
    frontCamFeed.getEntry("pipeline").setNumber(pipeline);
  }

  /**
   * Updates network frontCamFeed variables.
   */
  private void updateTableVariables() {
    tv = activeCamFeed.getEntry("tv").getDouble(0.0);
    tx = activeCamFeed.getEntry("tx").getDouble(0.0);
    ty = activeCamFeed.getEntry("ty").getDouble(0.0);
    ta = activeCamFeed.getEntry("ta").getDouble(0.0);
    ts = activeCamFeed.getEntry("ts").getDouble(0.0);
    tl = activeCamFeed.getEntry("tl").getDouble(0.0);
  }

  public void setLights(final double ledState){
    this.ledState = ledState;
  }

  public void setPipeline(final double pipeline){
    this.pipeline = pipeline;
  }
  /**
   * Gets the X offset in degrees.
   *
   * @return The X offset in degrees.
   */
  public double getXOffset() {
    return tx;
  }

  /**
   * Gets the Y offset in radians.
   *
   * @return The Y offset in radians.
   */
  public double getYOffset() {
    return ty;
  }

//   public double getYOffsetWithMin() { 
//     if (Math.toRadians(ty) < -RobotMap.CAMERA_ANGLE){
//       return -RobotMap.CAMERA_ANGLE;
//     }
//     else {
//       return Math.toRadians(ty);
//     }
//   }

  /**
   * Gets the target area.
   *
   * @return The target area.
   */
  public double getTargetArea() {
    return ta;
  }

  /**
   * Gets the target skew.
   *
   * @return Target Skew.
   */
  public double getTargetSkew() {
    return ts;
  }

  /**
   * Gets the X limelight's latency.
   *
   * @return Latency
   */
  public double getLatency() {
    return tl;
  }

  /**
   * Returns 1 if a target is visible, returns 0 if there's no target visible.
   *
   * @return 1 or 0
   */
  public double isTargetVisibleDouble() {
    return tv;
  }

  /**
   * Gets the distance of the target using the formula from the limelight's documentation.
   *
   * @return The distance to the target using a formula.
   */
  public double calculateDistanceFromCameraHeight(final double targetHeight, final double cameraHeight, final double cameraAngle) {
    final double methodDistance = (targetHeight - cameraHeight) / Math.tan(Math.toRadians(cameraAngle + getYOffset()));
    return methodDistance;
  }

 // public double calculatevelocity(double distance)
 // {
  //  distance=this.calculateDistanceArea();
  //  yspeed=distance/Robot.
 // }

//   public double calculateDistanceFromCameraHeightWithMin(double targetHeight, double cameraHeight, double cameraAngle) {
//     if (porthacth) {
//       // this.targetHeight = RobotMap.ROCKET_PORT_HEIGHT;
//     }
//     double methodDistance = (targetHeight - cameraHeight) / Math.tan(cameraAngle + getYOffsetWithMin());
//     return methodDistance;
//   }

  /**
   * Gets the distance to the target based of off its area using a slope-intercept formula.
   *
   * @return The distance based off of the area of the target.
   */
  public double calculateDistanceArea() {
    return slope * ta + yintercept;
  }



  /**
   * Sets the camera feed to stream from the front facing camera.
   */
  public void setFrontCamFeed() {
    this.activeCamFeed = this.frontCamFeed;
    this.usingFrontCam = true;
    this.frontLight = this.lightOn;
    this.backLight = this.lightOff;
    this.cameraAngle = 0;
  }



  public void flash() {
    this.frontLight = this.lightFlash;
    this.backLight = this.lightFlash;
  }

  public boolean isUsingFrontCam() {
    return this.usingFrontCam;
  }

  public boolean targetAcquired(){
    if(this.tv == 1){
        return true;
    }
    else{
        return false;
    }
  }

  /**
   * Updates the values of the Vision class.
   */
  public void updateVisionDashboard() {
    updateTableVariables();
    SmartDashboard.putNumber("X Offset: ", getXOffset());
    SmartDashboard.putNumber("Y Offset: ", getYOffset());
    SmartDashboard.putNumber("Distance to target(Formula): ",
            calculateDistanceFromCameraHeight(this.targetHeight,
                    this.cameraHeight,
                    this.cameraAngle));
    SmartDashboard.putNumber("Distance to target(Area): ", calculateDistanceArea());
    SmartDashboard.putNumber("Target Area: ", getTargetArea());
    SmartDashboard.putNumber("Target Skew: ", getTargetSkew());
    SmartDashboard.putNumber("LimeLight Latency: ", getLatency());
    SmartDashboard.putNumber("Target Found (double): ", isTargetVisibleDouble());

    SmartDashboard.putBoolean("Front Camera: ", this.usingFrontCam);

    SmartDashboard.putBoolean("Is Target Acquired?", this.targetAcquired());
  }

}