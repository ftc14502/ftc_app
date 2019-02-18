package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Autonomous(name = "DBug", group = "DogeCV")
public class DepotDebugger extends LinearOpMode {

    public void runOpMode() {

        AutoMecanumRobot robot = new AutoMecanumRobot(this);
        robot.closeClaw();
        robot.initializeMineralDetector();
        waitForStart();
        robot.comeDown();
        sleep(1000); // you can't
        robot.strafeLeft(1000);
        robot.independenceForward();
        robot.strafeRight(1250);


        robot.stop();
        sleep(300);


        Orientation angles = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        telemetry.addData("IMU first angle in LOOP", angles.firstAngle);
        telemetry.addData("IsAligned", robot.mineralDetector.getAligned()); // Is the bot aligned with the gold mineral?
        telemetry.addData("X Pos", robot.mineralDetector.getXPosition()); // Gold X position

        double xPojition = robot.mineralDetector.getXPosition();
        telemetry.addData("xPojition:", xPojition);


        if (xPojition <= 100) {

            telemetry.addData("Gold is on the left", "");
            telemetry.update();
            sleep(500);
            robot.turnLeftOnCenterAxis(35);
            robot.moveForward(42, 0.5);
            robot.turnRightOnCenterAxis(/*Tell me why*/70);
            robot.moveForward(21, 0.5); // inside depot


        } else if (xPojition >= 460) {
            telemetry.addData("Gold is on the right", "");
            telemetry.update();
            sleep(500);

            robot.turnRightOnCenterAxis(20);
            robot.moveForward(45, 0.8);
            robot.turnLeftOnCenterAxis(70);
            robot.moveForward(22, 0.8); // inside depot
            telemetry.addData("Gold is on the right", "");
        } else {
            telemetry.addData("Report: ", "Gold is straight!");
            telemetry.update();
            sleep(500);

            robot.turnLeftOnCenterAxis(5);

            robot.moveForward(48, 0.5);


            //you will go to crater from here

            telemetry.update();


        }

        robot.dumpMarker();


        telemetry.update();

        robot.mineralDetector.disable();
    }
}// STOPSHIP: 12/25/2018