 package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
public class DepotAutonomous extends LinearOpMode {

    public void runOpMode() {

        AutoMecanumRobot robot = new AutoMecanumRobot(this);
        robot.closeClaw();
        waitForStart();
        robot.comeDown();
        sleep(300); // you can't
        robot.strafeLeft(1000);
        robot.independenceForward();
        robot.strafeRight(1250);


        robot.stop();
        sleep(300);


        while (opModeIsActive()) {
            Orientation angles = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            telemetry.addData("IMU first angle in LOOP", angles.firstAngle);
            telemetry.addData("IsAligned", robot.mineralDetector.getAligned()); // Is the bot aligned with the gold mineral?
            telemetry.addData("X Pos", robot.mineralDetector.getXPosition()); // Gold X position
            double xPojition = robot.mineralDetector.getXPosition();
            telemetry.addData("xPojition:", xPojition);
            telemetry.update();
            sleep(5000);
//            boolean xyz = mineralDetector.getAligned();
            if (robot.mineralDetector.getAligned() == true || robot.mineralFound==true) {
                telemetry.addData("Report: ", "We're Aligned. Move Forward!");
                telemetry.update();
                robot.moveForward(25, 1);
                if (robot.pathTakenToPushMineral == Direction.RIGHT) {
                    robot.moveForward(38, 0.5); // moving more for right so that when turning to depot it does not hit anything
                } else {
                    robot.moveForward(34, 0.5);
                }
                robot.goToDepot();
                //you will go to crater from here
                telemetry.addData("netAngle", robot.netAngle);
                telemetry.update();
                break;
            }
            if (xPojition <= 260) {
                telemetry.addData("Gold is on the left", "");
                robot.turnLeftOnCenterAxis(40);
                if (robot.mineralFound==false){
                    robot.turnRightOnCenterAxis(80); //||\\
                    if (robot.mineralFound==false){
                        break;
                    }
                }


                robot.pathTakenToPushMineral = Direction.LEFT;
            }
            else if (xPojition >= 360 ) {
                robot.turnRightOnCenterAxis(50);
                robot.pathTakenToPushMineral = Direction.RIGHT;
                telemetry.addData("Gold is on the right", "");

                sleep(5000);
            }




            telemetry.update();
        }
        robot.mineralDetector.disable();
    }
}// STOPSHIP: 12/25/2018