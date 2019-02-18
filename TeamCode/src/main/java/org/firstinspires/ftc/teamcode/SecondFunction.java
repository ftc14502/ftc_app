package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
// the opposite of deporting things... We're importing merch

// Second Prg", group="Linear Opmode")
public class SecondFunction extends LinearOpMode{
    private DcMotor leftMotor;
    private DcMotor rightMotor;
    private DcMotor towerMotor;
    private Servo leftClaw;
    private Servo rightClaw; // nunny
// defining all the necessary stuff
    public void runOpMode() {
        // goal is to turn robot left
        // stop the left motor, whole robot turns left


        leftMotor = hardwareMap.get(DcMotor.class,  "leftMotor");
        rightMotor = hardwareMap.get(DcMotor.class, "rightMotor");
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the thingamabob to start or else we will lose the competition
        waitForStart();
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
// um... setting the direction of the motor, motors need guidance,
        // HELP PREVENT MOTOR ABUSE
        leftMotor.setPower(0.5);
        rightMotor.setPower(0.5);
        sleep(3000);
        leftMotor.setPower(0);
        rightMotor.setPower(0);
        telemetry.addData("Status", "Robot Stopped");
        telemetry.update();
// it moves forward for like three thousand milliseconds, or 3 seconds for you un-mathy noobs
        telemetry.addData("Status", "Robot Starting to do the turnings");
        telemetry.update();
        leftMotor.setPower(0);
        rightMotor.setPower(0.5);
        sleep(1500);
        rightMotor.setPower(0);
        telemetry.addData("Status", "Stopped");
        telemetry.update();
//it is turning
        telemetry.addData("Status", "Going forward again");
        telemetry.update();
        leftMotor.setPower(0.5);
        rightMotor.setPower(0.5);
        sleep(2000);
        leftMotor.setPower(0);
        rightMotor.setPower(0);
        telemetry.addData("Status", "Stopped");
        telemetry.update();
// turned and stop
        telemetry.addData("Status", "Starting to do the movings of the tower arm");
        telemetry.update();
        towerMotor = hardwareMap.get(DcMotor.class, "towerMotor");
        towerMotor.setDirection(DcMotor.Direction.REVERSE);
        towerMotor.setPower(0.25);
        sleep(1025);
        towerMotor.setPower(0);
        telemetry.addData("Status", "Completed");
        telemetry.update();

        telemetry.addData("Status", "Los Claws moving initiated and running");
        telemetry.update();
        leftClaw = hardwareMap.get(Servo.class, "leftClaw");
        rightClaw = hardwareMap.get(Servo.class,"rightClaw");
        leftClaw.setDirection(Servo.Direction.FORWARD);
        rightClaw.setDirection(Servo.Direction.FORWARD);
        leftClaw.setPosition(0.5);
        rightClaw.setPosition(0.5);
        sleep(500);
        telemetry.addData("Status", "Chillaxing in the el comedor");
        telemetry.update();

        telemetry.addData("Status", "Starting to do the backings of the tower arm");
        telemetry.update();
        leftMotor.setPower(-0.25);
        rightMotor.setPower(-0.25);
        sleep(2000);
        leftMotor.setPower(0);
        rightMotor.setPower(0);
        telemetry.addData("Status", "Backing up");
        telemetry.update();
//         moving the tower motor and making sure it stops
//         When the robot stops, then the thingy will show that the program has served its purpose


//        leftClaw.setDirection(DcMotor.Direction.FORWARD);
//        leftClaw = hardwareMap.get(DcMotor.class, "leftClaw");
//        leftClaw.setPower(0.15);
//        sleep(500);
//        leftClaw.setPower(0);
//
//        rightClaw.setDirection(DcMotor.Direction.FORWARD);
//        rightClaw = hardwareMap.get(DcMotor.class, "rightClaw");
//        rightClaw.setPower(0.15);
//        sleep(500);
//        rightClaw.setPower(0);

        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            telemetry.update();

            // The last lines enable the whole program to stop
        }
    }
}




