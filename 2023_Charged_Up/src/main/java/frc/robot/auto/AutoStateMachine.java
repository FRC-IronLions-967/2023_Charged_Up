package frc.robot.auto;

public enum AutoStateMachine {
    IDLE,
    INITIALIZING,
    LEAD_SCREW_OUT,
    FINISH_ARM,
    SETTLE_ROBOT,
    PLACE_GAME_PIECE,
    DRIVE,
    RETRACT_ARM,
}
