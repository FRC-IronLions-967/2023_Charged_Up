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
    manipulatorController.whenButtonPressed("SELECT", new RunPnuematicsCommand(0));
    manipulatorController.whenButtonPressed("START", new RunPnuematicsCommand(1));
    // manipulatorController.whenButtonPressed("X", new TogglePnuematicsCommand(0));
    // manipulatorController.whenButtonReleased("X", new TogglePnuematicsCommand(2));
    // manipulatorController.whenButtonPressed("A", new TogglePnuematicsCommand(1));
    // manipulatorController.whenButtonReleased("A", new TogglePnuematicsCommand(2));
}
public XBoxController getDriverController(){
    return driverController;
}
public XBoxController getManipulatorController(){
    return manipulatorController;

}
}