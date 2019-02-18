package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
@Autonomous(name = "CraterA nomus Simple", group = "DogeCV")
public class CraterAutonomousSimplified extends LinearOpMode {

    public void runOpMode() {

        AutoMecanumRobot robot = new AutoMecanumRobot(this);
        robot.closeClaw();
        robot.initializeMineralDetector();
        waitForStart();
        robot.comeDown();
        sleep(500); // you can't
        robot.strafeLeft(1000);
        robot.independenceForward();
        robot.strafeRight(1250);
        robot.stop();
        sleep(500);

        Orientation angles = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        telemetry.addData("IMU first angle in LOOP", angles.firstAngle);
        telemetry.addData("IsAligned", robot.mineralDetector.getAligned()); // Is the bot aligned with the gold mineral?
        telemetry.addData("X Pos", robot.mineralDetector.getXPosition()); // Gold X position
        double xPojition = robot.mineralDetector.getXPosition();
        telemetry.addData("xPojition:", xPojition);

        sleep(200);

        if (xPojition <= 100) {

            telemetry.addData
                    ("Gold is on the left", "");
            telemetry.update();
            robot.turnLeftOnCenterAxis
                    (40);
            robot.moveForward
                    (36, 0.8);
            robot.moveForward
                    (-20, -0.8);
            sleep (200);
            robot.turnLeftOnCenterAxis
                    (50);                     // || \\
            robot.moveForward
                    (46, 0.8); // inside depot
            robot.turnLeftOnCenterAxis
                    (40);
            robot.strafeRight(500);
            robot.moveForward
                    (40, 0.7);



        }
        else if (xPojition >= 460 ) {
            telemetry.addData("Gold is on the right", "");
            telemetry.update();
            robot.turnRightOnCenterAxis(20);
            robot.moveForward(33, 0.7);
            robot.moveForward(-23, -0.7);
            robot.turnLeftOnCenterAxis(110);
            robot.moveForward(60, 0.7); // inside depot
            robot.turnLeftOnCenterAxis(36);
            //robot.strafeRight(600);
            robot.moveForward(40, 0.7);
            telemetry.addData("Gold is on the right", "");
        }
        else {
            telemetry.addData("Report: ", "Gold is straight!");
            telemetry.update();


            robot.turnLeftOnCenterAxis(10);

            robot.moveForward(31, 0.5);
            robot.moveForward(-24 , -.5);
            robot.turnLeftOnCenterAxis(65);
            robot.moveForward
                    (53, 0.8);
            robot.turnLeftOnCenterAxis
                    (49);
            robot.strafeRight(200);
            robot.moveForward(44,0.8);

            //you will go to crater from here

            telemetry.update();


        }

        robot.dumpMarker();
        robot.turnLeftOnCenterAxis(10);
        robot.moveForward(-62,-0.5);



        telemetry.update();

        robot.mineralDetector.disable();
    }
}// STOPSHIP: 12/25/2018