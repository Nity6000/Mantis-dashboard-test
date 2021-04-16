package frc.team4909.robot.subsystems.indexer.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.team4909.robot.Robot;
import frc.team4909.robot.subsystems.intake.commands.IntakeDeploy;
import frc.team4909.robot.subsystems.intake.commands.IntakeIn;;

public class SmartIndexerAndSorterUp extends ParallelCommandGroup {
    public SmartIndexerAndSorterUp() {
        super();
        addCommands(new SmartSorterOn(Robot.sorterSubsystem),
        new SmartIndexerUp(Robot.indexerSubsystem),
        new IntakeDeploy());

    }
}