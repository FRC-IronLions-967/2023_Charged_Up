package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxLimitSwitch;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxLimitSwitch.Type;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.IO;
import frc.robot.commands.LeadScrewAdjustCommand;
import frc.robot.commands.LeadScrewInitializeCommand;
import frc.robot.commands.LeadScrewStopCommand;

public class LeadScrewSubsystem extends SubsystemBase {

    private IO io;
    
    private CANSparkMax leadScrew;
    private SparkMaxLimitSwitch screwForwardLimit;
    private SparkMaxLimitSwitch screwReverseLimit;
    private SparkMaxPIDController leadScrewController;

    private Boolean leadScrewInitialized;
    private boolean leadScrewLimitSwitchKillEnabled;
    private double leadScrewTargetPosition;
    private static LeadScrewStates state;

    private double leadScrewManualDeadband = 0.2;
    private double leadScrewMinPosition = 0.0;
    private double leadScrewMaxPosition = 17.0;

    private double mmPerInch = 25.4;
    private double leadScrewPitch = 8; //2mm pitch, 4 start, 8mm per revolution
    private double leadScrewRevPerInch = mmPerInch / leadScrewPitch; //3.175 revolution for 1 inch approx.
    private double leadScrewGearboxRatio = 9.0; //modify as hardware changes, current ratio is 9:1

    public LeadScrewSubsystem() {
        leadScrew = new CANSparkMax(5, MotorType.kBrushless);
        leadScrew.setInverted(true);
        leadScrew.clearFaults();
        System.out.println("Lead Screw Started");

        screwForwardLimit = leadScrew.getForwardLimitSwitch(Type.kNormallyOpen);
        screwReverseLimit = leadScrew.getReverseLimitSwitch(Type.kNormallyOpen);
        leadScrewLimitSwitchKillEnabled = false;
        screwForwardLimit.enableLimitSwitch(false);
        screwReverseLimit.enableLimitSwitch(false);

        leadScrew.getEncoder().setPosition(0.0);
        leadScrew.getEncoder().setPositionConversionFactor(1 / (leadScrewGearboxRatio * leadScrewRevPerInch));
        leadScrew.setClosedLoopRampRate(0.5);

        leadScrewController = leadScrew.getPIDController();
        leadScrewController.setP(4);  //needs tuning
        leadScrewController.setI(0);
        leadScrewController.setD(0);
        leadScrewController.setReference(0, ControlType.kPosition);

        leadScrewInitialized = false;
        state = LeadScrewStates.UNINITIALIZED;

        io = IO.getInstance();
    }

    /**
     * Home lead screw, must be called before use
     */
    public void homeLeadScrew() {
        leadScrewController.setReference(-0.5, ControlType.kDutyCycle);
        state = LeadScrewStates.INITIALIZING;
    }
    /**
     * Prepare lead screw to accept commands
     */
    public void initializeLeadScrew() {
        if (leadScrewInitialized) return;
        leadScrewController.setReference(0.0, ControlType.kDutyCycle);
        leadScrew.getEncoder().setPosition(0.0);
        leadScrewController.setReference(1, ControlType.kPosition);
        leadScrewTargetPosition = 1;

        leadScrewLimitSwitchKillEnabled = true;
        screwForwardLimit.enableLimitSwitch(true);
        screwReverseLimit.enableLimitSwitch(true);

        leadScrewInitialized = true;
        state = LeadScrewStates.AUTO;
        System.out.println("Lead Screw Initialized");
    }
    
    /**
     * adjust lead scrwe without PID position control
     * @param speed duty cycle value -1 to 1
     */
    public void runMotor(double speed) {
        if (state != LeadScrewStates.UNINITIALIZED && state != LeadScrewStates.INITIALIZING) {
            state = LeadScrewStates.MANUAL;
            leadScrewController.setReference(speed, ControlType.kDutyCycle);
        }
    }

    /**
     * sets how far the lead screw should be extended
     * @param position in inches
     */
    public void setLeadScrewPosition(double position) {
        if (state != LeadScrewStates.UNINITIALIZED && state != LeadScrewStates.INITIALIZING) {

            // guard against out of range positions
            position = (position < leadScrewMinPosition) ? leadScrewMinPosition : position;
            position = (position > leadScrewMaxPosition) ? leadScrewMaxPosition : position;

            state = LeadScrewStates.AUTO;
            System.out.println("Commanded Position: " + position);
            System.out.println("Current Position: " + leadScrew.getEncoder().getPosition());
            leadScrewController.setReference(position, ControlType.kPosition);
            leadScrewTargetPosition = position;
        }
    }

    /**
     * interrupts both AUTO and MANUAL control, turns on positional control at the current position
     */
    public void stopLeadScrew() {
        if (state != LeadScrewStates.UNINITIALIZED && state != LeadScrewStates.INITIALIZING) {
            double currentPosition = leadScrew.getEncoder().getPosition();
            leadScrewController.setReference(currentPosition, ControlType.kPosition);
            leadScrewTargetPosition = currentPosition;
        }
    }

    /**
     * compares AUTO mode commanded position to current position, always returns true in MANUAL mode
     * @return true if lead screw is at the commanded position
     */
    public boolean isLeadScrewFinished() {
        boolean isFinished = true;
        if (state == LeadScrewStates.AUTO) {
            double currentPos = leadScrew.getEncoder().getPosition();
            if (Math.abs(leadScrewTargetPosition - currentPos) > 0.2) {  //current target is 0.2", may need adjustment
                isFinished = false;
            }
        }

        return isFinished;
    }

    /**
     * get the active state in the lead screw state machine
     * @return the active state
     */
    public LeadScrewStates getState() {
        return state;
    }

    /**
     * fallback method if setPositionConversionFactor does not work as expected.  Should be called locally before setting reference positions
     * @param commandedPosition position in inches
     * @return position in revolutions
     */
    private double convertLeadScrewPosition(double commandedPosition) {
        return commandedPosition * leadScrewGearboxRatio * leadScrewRevPerInch;
    }

    /**
     * check status of limit switches to ensure they are correctly enabled
     */
    private void checkLimitSwitchState() {
        // limit switches should always disable the controller in MANUAL mode
        if (state == LeadScrewStates.MANUAL && !leadScrewLimitSwitchKillEnabled) {
            leadScrewLimitSwitchKillEnabled = true;
            screwForwardLimit.enableLimitSwitch(true);
            screwReverseLimit.enableLimitSwitch(true);
        } else if (state == LeadScrewStates.AUTO) {
            if (screwForwardLimit.isPressed()) {
                screwForwardLimit.enableLimitSwitch(false);
                leadScrewLimitSwitchKillEnabled = false;
                leadScrew.clearFaults();
                leadScrewController.setReference(-0.5, ControlType.kDutyCycle);
            } else if (screwReverseLimit.isPressed()) {
                screwReverseLimit.enableLimitSwitch(false);
                leadScrewLimitSwitchKillEnabled = false;
                leadScrew.clearFaults();
                leadScrewController.setReference(0.5, ControlType.kDutyCycle);
            }

            if(!screwForwardLimit.isPressed() && !screwReverseLimit.isPressed() && !leadScrewLimitSwitchKillEnabled) {
                leadScrewLimitSwitchKillEnabled = true;
                screwForwardLimit.enableLimitSwitch(true);
                screwReverseLimit.enableLimitSwitch(true);
                stopLeadScrew();
            }
        }
    }

    /**
     * implements lead screw state machine, called by commandScheduler
     */
    @Override
    public void periodic() {
        switch(state) {
            case UNINITIALIZED:
                if (!leadScrewInitialized) {
                    CommandScheduler.getInstance().schedule(new LeadScrewInitializeCommand());
                } else {
                    state = LeadScrewStates.MANUAL;
                }
                break;
            case INITIALIZING: 
                if (screwReverseLimit.isPressed()){
                    System.out.println("pressed");
                    initializeLeadScrew();
                }
                break;
            case MANUAL:
                if (io.getManipulatorController().getLeftTrigger() > leadScrewManualDeadband ||
                        io.getManipulatorController().getRightTrigger() > leadScrewManualDeadband) {
                    CommandScheduler.getInstance().schedule(new LeadScrewAdjustCommand(
                        io.getManipulatorController().getLeftTrigger() - io.getManipulatorController().getRightTrigger()));
                } else {
                    CommandScheduler.getInstance().schedule(new LeadScrewStopCommand());
                    state = LeadScrewStates.AUTO;
                }
                checkLimitSwitchState();
                break;
            case AUTO:
                if (io.getManipulatorController().getLeftTrigger() > leadScrewManualDeadband ||
                        io.getManipulatorController().getRightTrigger() > leadScrewManualDeadband) {
                    CommandScheduler.getInstance().schedule(new LeadScrewStopCommand());
                    state = LeadScrewStates.MANUAL;
                }
                checkLimitSwitchState();

                //SmartDashboard.putNumber("Lead Screw Actual Position", leadScrew.getEncoder().getPosition());
                //SmartDashboard.putNumber("Lead Screw Commanded Position", leadScrewTargetPosition);
                //leadScrewController.setP(SmartDashboard.getNumber("Lead Screw P value", 0));
                //leadScrewController.setI(SmartDashboard.getNumber("Lead Screw I value", 0));
                //leadScrewController.setD(SmartDashboard.getNumber("Lead Screw D value", 0));

                break;
        }
    }
}
