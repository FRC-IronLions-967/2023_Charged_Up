package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class IO { 
    private static IO instance; 
    private XboxController driverController;
    private XboxController manipulatorController;

    private IO() {
        driverController = new XboxController(0);
        manipulatorController = new XboxController(0);
    }
public static IO getInstance() {
    if(instance == null) instance = new IO();

    return instance;
}
public void teleopInt(){

}
public XboxController getDriverController(){
    return driverController;
}
public XboxController getManipulatorController(){
    return manipulatorController;

}
}