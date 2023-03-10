package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.SparkMaxAbsoluteEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

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

    public DriveSubsystem() {
        rightFront = new CANSparkMax(1, MotorType.kBrushless);
        leftFront = new CANSparkMax(4, MotorType.kBrushless);
        rightBack = new CANSparkMax(2, MotorType.kBrushless);
        leftBack = new CANSparkMax(3, MotorType.kBrushless);



        rightBack.follow(rightFront);
        leftBack.follow(leftFront);

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
        double maxDifV = SmartDashboard.getNumber("maxAccel", 0.03d);
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
    }

}