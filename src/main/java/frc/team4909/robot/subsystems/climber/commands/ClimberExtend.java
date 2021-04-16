package frc.team4909.robot.subsystems.climber.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import frc.team4909.robot.Robot;
import frc.team4909.robot.RobotConstants;
import frc.team4909.robot.operator.controllers.BionicF310;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClimberExtend extends CommandBase {

    double pos, time;
    double delaySec = 0.025;

    public ClimberExtend(){
        super();
        addRequirements(Robot.climberSubsystem);
    }

    @Override
    public void initialize() {
        time = Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
        //Robot.leds.setRed();
        Robot.climberSubsystem.setClimberSpeed(0.4);
        Robot.cameraSubsystem.climb = true;
        Robot.climberSubsystem.setRatchetAngle(0.4);
        if(Timer.getFPGATimestamp() - time > delaySec){
           Robot.climberSubsystem.setClimberSpeed(-1/*-0.3*/);
        }
        // Robot.climberSubsystem.setClimberPosition(RobotConstants.climberTop);
    }

    @Override
    public void end(boolean interrupted) {
        Robot.climberSubsystem.setRatchetAngle(0.1);
        Robot.climberSubsystem.setClimberSpeed(0);
    }
}