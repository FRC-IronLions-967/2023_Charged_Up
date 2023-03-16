package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SPI;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.IO;
import frc.robot.Utils.Utils;

public class DriveSubsystem extends SubsystemBase {
    private CANSparkMax rightFront;
    private CANSparkMax leftFront;
    private CANSparkMax rightBack;
    private CANSparkMax leftBack;

    private double xAxisRate;
    private double yAxisRate;

    private IO io;

    private double v = 0.0;
    private double r = 0.0;
    private double l = 0.0;

    private final double MAX = 1.0;


    private boolean autoBalanceXMode;
    private boolean autoBalanceYMode;
    
    private AHRS ahrs;

    public DriveSubsystem() {
        rightFront = new CANSparkMax(1, MotorType.kBrushless);
        leftFront = new CANSparkMax(4, MotorType.kBrushless);
        rightBack = new CANSparkMax(2, MotorType.kBrushless);
        leftBack = new CANSparkMax(3, MotorType.kBrushless);

        rightBack.follow(rightFront);
        leftBack.follow(leftFront);
        rightFront.setInverted(true);
        rightBack.setInverted(true);
        leftFront.setInverted(false);
        leftBack.setInverted(false);

        io = IO.getInstance();

        ahrs = new AHRS(SPI.Port.kMXP);
    }
    public void move(double r, double l) {
        r = (r > MAX) ? MAX : r;
        r = (r < -(MAX)) ? -(MAX) : r;
        l = (l > MAX) ? MAX : l;
        l = (l < -(MAX)) ? -(MAX) : l;

        rightFront.set(r);
        leftFront.set(l);


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

    public void manualAutoBal() {
            System.out.println(true);
            xAxisRate            = 0.0;
            yAxisRate            = 0.0;
            double pitchAngleDegrees    = ahrs.getPitch();
            double rollAngleDegrees     = ahrs.getRoll();

            // if ( autoBalanceXMode ) {
                double pitchAngleRadians = pitchAngleDegrees * (Math.PI / 180.0);
                xAxisRate = Math.sin(pitchAngleRadians) * -1;
            // }
            // if ( autoBalanceYMode ) {
                double rollAngleRadians = rollAngleDegrees * (Math.PI / 180.0);
                yAxisRate = Math.sin(rollAngleRadians) * -1;
            // }
            

            move(-xAxisRate, -xAxisRate);
            
        
            //   System.out.println("Autobalancing is being ran");
        }


    @Override 
    public void periodic(){
        double driveScaling = 1.0;
        if (io.getDriverController().getRightTrigger() > 0.5 && io.getDriverController().getLeftTrigger() > 0.5) {
            driveScaling = 0.5;
        }
        double y = -driveScaling * io.getDriverController().getLeftStickY();
        double x = driveScaling * io.getDriverController().getRightStickX();
        arcadeDrive(x, y);






        System.out.println(xAxisRate + " :Xrate - " + yAxisRate +" :Yrate");
        
        System.out.println(true);
        xAxisRate            = 0.0;
        yAxisRate            = 0.0;
        double pitchAngleDegrees    = ahrs.getPitch();
        double rollAngleDegrees     = ahrs.getRoll();

        // if ( autoBalanceXMode ) {
            double pitchAngleRadians = pitchAngleDegrees * (Math.PI / 180.0);
            xAxisRate = Math.sin(pitchAngleRadians) * -1;
        // }
        // if ( autoBalanceYMode ) {
            double rollAngleRadians = rollAngleDegrees * (Math.PI / 180.0);
            yAxisRate = Math.sin(rollAngleRadians) * -1;
        // }
        
        
        move(-xAxisRate / 2, -xAxisRate / 2);
        
    

    }
}