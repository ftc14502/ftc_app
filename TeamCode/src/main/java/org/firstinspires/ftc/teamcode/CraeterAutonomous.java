package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;


public class CraeterAutonomous extends LinearOpMode {


//    private GoldAlignDetector mineralDetector;


    @Override

    public void runOpMode() {
        AutoMecanumRobot robot = new AutoMecanumRobot(this);
        waitForStart();
        // robot.comeDown();
        sleep(300);
        robot.strafeLeft(1000);
        robot.independenceForward();
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

//            boolean xyz = mineralDetector.getAligned();
            if (robot.mineralDetector.getAligned() == true) {
                telemetry.addData("Report: ", "We're Aligned. Move Forward!");
                telemetry.update();
                robot.moveForward(34, 0.5);

                //you will go to crater from here
                telemetry.addData("netAngle", robot.netAngle);
                telemetry.update();

                break;

            }
            if (xPojition <= 280) {
                robot.turnLeftOnCenterAxis(25);
                robot.pathTakenToPushMineral = Direction.LEFT;

            } //
            else if (xPojition >= 360) {
                robot.turnRightOnCenterAxis(30);
                robot.pathTakenToPushMineral = Direction.RIGHT;

            }
            telemetry.update();

        }
        robot.mineralDetector.disable();
    }


}
// STOPSHIP: 12/25/2018  adds

