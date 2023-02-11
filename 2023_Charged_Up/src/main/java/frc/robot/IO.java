package frc.robot;


import frc.robot.Utils.controls.XBoxController;
import frc.robot.commands.*;

public class IO { 
    private static IO instance; 
    private XBoxController driverController;
    private XBoxController manipulatorController;

    private IO() {
        driverController = new XBoxController(0);
        manipulatorController = new XBoxController(1);
    }
public static IO getInstance() {
    if(instance == null) instance = new IO();

    return instance;
}
public void teleopInt(){
    manipulatorController.whenButtonPressed("A", new RunSparkCommand(1.0));
    manipulatorController.whenButtonReleased("A", new RunSparkCommand(0.0));
    manipulatorController.whenButtonPressed("X", new RunSparkCommand(-1.0));
    manipulatorController.whenButtonReleased("X", new RunSparkCommand(0.0));

    manipulatorController.whenButtonPressed("LBUMP", new MoveArmCommand(true));
    manipulatorController.whenButtonPressed("RBUMP", new MoveArmCommand(false));
    manipulatorController.whenButtonPressed("B", new MoveClawCommand(true));
    manipulatorController.whenButtonReleased("B", new MoveClawCommand(false));
}
public XBoxController getDriverController(){
    return driverController;
}
public XBoxController getManipulatorController(){
    return manipulatorController;

}
}