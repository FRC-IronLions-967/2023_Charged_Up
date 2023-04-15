package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.REVPhysicsSim;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.SparkMaxAbsoluteEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.IO;
import frc.robot.Utils.Utils;

public class DriveSubsystem extends SubsystemBase {
    private CANSparkMax rightFront;
    private CANSparkMax leftFront;
    private CANSparkMax rightBack;
    private CANSparkMax leftBack;

    private IO io;

    private double v = 0.0;
    private double r = 0.0;
    private double l = 0.0;

    public double go;

    private final double MAX = 1.0;

    private String idleMode = "Coast";

    private SparkMaxPIDController rightFrontController;
    private SparkMaxPIDController leftFrontController;
  
    private double maxRPM = 5700;

    public boolean driveBackFinished;
    public boolean driveTimeout;

    private double xAxisRate;
    private boolean navxUpdate;
    private AHRS ahrs;
    private boolean runAutoBal = false;
    private boolean driveToStation = false;


    public DriveSubsystem() {
        rightFront = new CANSparkMax(1, MotorType.kBrushless);
        leftFront = new CANSparkMax(4, MotorType.kBrushless);
        rightBack = new CANSparkMax(2, MotorType.kBrushless);
        leftBack = new CANSparkMax(3, MotorType.kBrushless);



        rightBack.follow(rightFront);
        leftBack.follow(leftFront);
        REVPhysicsSim.getInstance().addSparkMax(leftFront, DCMotor.getNEO(1));
        REVPhysicsSim.getInstance().addSparkMax(rightFront, DCMotor.getNEO(1));

        rightFrontController = rightFront.getPIDController();
        rightFrontController.setP(3e-4);  //needs tuning
        rightFrontController.setI(0);
        rightFrontController.setD(0);
        rightFrontController.setFF(0.000015);
        rightFrontController.setReference(0, ControlType.kVelocity);
        rightFrontController.setOutputRange(-1, 1);
        rightFront.setClosedLoopRampRate(0.35);

        leftFrontController = leftFront.getPIDController();
        leftFrontController.setP(3e-4);  //needs tuning
        leftFrontController.setI(0);
        leftFrontController.setD(0);
        leftFrontController.setFF(0.000015);
        leftFrontController.setReference(0, ControlType.kVelocity);
        leftFrontController.setOutputRange(-1, 1);
        leftFront.setClosedLoopRampRate(0.35);

        rightFront.setInverted(true);
        rightBack.setInverted(true);
        leftFront.setInverted(false);
        leftBack.setInverted(false);

        io = IO.getInstance();
        driveBackFinished = false;
        driveTimeout = false;
        brakeMotors();

        navxUpdate = true;
        ahrs = new AHRS(SPI.Port.kMXP);
    }
    public void move(double r, double l) {
        r = (r > MAX) ? MAX : r;
        r = (r < -(MAX)) ? -(MAX) : r;
        l = (l > MAX) ? MAX : l;
        l = (l < -(MAX)) ? -(MAX) : l;

        // rightFrontController.setReference(r * maxRPM, ControlType.kVelocity);
        // leftFrontController.setReference(l * maxRPM, ControlType.kVelocity);
        rightFront.set(r);
        leftFront.set(l);

        go = r + l;

    }
    public void arcadeDrive(double x, double y){
        x = Utils.deadband(x, 0.0);
        y = Utils.deadband(y, 0.0);
        
        double difV = y - v;
        SmartDashboard.putNumber("difV", difV);
        double maxDifV = 0.1; //SmartDashboard.getNumber("maxAccel", 0.1d);
        if (difV > 0){ 
            v += (difV > maxDifV) ? maxDifV : difV;
        } else {
            v -= (Math.abs(difV) > maxDifV) ? maxDifV : Math.abs(difV);
        }
        double s = (Math.abs(v) < 0.05)
            ? SmartDashboard.getNumber("scale", 0.5d) * x * SmartDashboard.getNumber("zeroTurn", 0.3d)
            : SmartDashboard.getNumber("scale", 0.5d) * x * Math.abs(v);

            double l = v + s;
            double r = v - s;

            move(r, l);
    }
    
    public void brakeMotors(){
        rightBack.setIdleMode(IdleMode.kBrake);
        leftBack.setIdleMode(IdleMode.kBrake);
        rightFront.setIdleMode(IdleMode.kBrake);
        leftFront.setIdleMode(IdleMode.kBrake);

        idleMode = "BRAKE";
    }

    public void coastMotors(){
        rightBack.setIdleMode(IdleMode.kCoast);
        leftBack.setIdleMode(IdleMode.kCoast);
        rightFront.setIdleMode(IdleMode.kCoast);
        leftFront.setIdleMode(IdleMode.kCoast);

        idleMode = "COAST";
    }

    public double checkAngle(){
        // xAxisRate            = 0.0;

        // double pitchAngleDegrees    = ahrs.getPitch();
        // double pitchAngleRadians = pitchAngleDegrees * (Math.PI / 180.0);
        // xAxisRate = Math.sin(pitchAngleRadians) * -1;

        // // System.out.println(xAxisRate + " xAxisRate");
        return Math.abs(xAxisRate);
    }

    private void updatePitch(){
        if (navxUpdate) {
            double pitchAngleDegrees    = ahrs.getPitch();
            double pitchAngleRadians = pitchAngleDegrees * (Math.PI / 180.0);
            xAxisRate = Math.sin(pitchAngleRadians) * -1;
        }
        navxUpdate = !navxUpdate;
        
    }

    public void autoBal(){
        double absXAxisRate = 0.0;
        absXAxisRate = Math.abs(xAxisRate);
        if(absXAxisRate > 0.18){
            move(-xAxisRate / 2, -xAxisRate / 2);
        } else if (absXAxisRate <= 0.18 && absXAxisRate >= 0.1){
            move(-xAxisRate / 3, -xAxisRate / 3);
        } else if (absXAxisRate < 0.1 && absXAxisRate >= 0.025){
            move(-xAxisRate / 5, -xAxisRate / 5);
        } else if(absXAxisRate < 0.025){
            move(0.0,0.0);
        }
            // driveToStation = false;
            // runAutoBal = true;
    }

    public void teleopReset(){
        runAutoBal = false;
        driveToStation = false;
    }

    public void isDriving(){
        driveToStation = true;
    }

    @Override 
    public void periodic(){
        double driveScaling = 1.0;
        if (io.getDriverController().getRightTrigger() > 0.0 && io.getDriverController().getLeftTrigger() > 0.0) {
            driveScaling = 0.5;
        }
        double y = -driveScaling * io.getDriverController().getLeftStickY();
        double x = driveScaling * io.getDriverController().getRightStickX();
        arcadeDrive(x, y);  
    
        SmartDashboard.putString("Brakes/Coast", idleMode);
        updatePitch();

        // checkAngle();

        // System.out.println("Running during auto");
        // if(runAutoBal){
        //     xAxisRate            = 0.0;
        //     double absXAxisRate = 0.0;

        //     double pitchAngleDegrees    = ahrs.getPitch();
        //     double pitchAngleRadians = pitchAngleDegrees * (Math.PI / 180.0);
        //     xAxisRate = Math.sin(pitchAngleRadians) * -1;

        //     System.out.println(xAxisRate);
        //     absXAxisRate = Math.abs(xAxisRate);
        //     if(absXAxisRate > 0.20){
        //         move(-xAxisRate / 1.5, -xAxisRate / 1.5);
        //     } else if (absXAxisRate <= 0.20 && absXAxisRate >= 0.1){
        //         move(-xAxisRate / 3, -xAxisRate / 3);
        //     } else if (absXAxisRate < 0.1 && absXAxisRate >= 0.025){
        //         move(-xAxisRate / 5, -xAxisRate / 5);
        //     } else if(absXAxisRate < 0.025){
        //         move(0.0,0.0);
        //     }
        //     // System.out.println(true);
    
        // }

        // if(driveToStation){
        //     move(-0.35, -0.35);
        // }
    }
    @Override
    public void simulationPeriodic() {
        REVPhysicsSim.getInstance().run();
    }

}