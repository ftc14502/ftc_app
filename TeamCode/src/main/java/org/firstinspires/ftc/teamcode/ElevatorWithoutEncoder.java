package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


// Last Updated 11/19/2018
public class ElevatorWithoutEncoder extends LinearOpMode {
    DcMotor elevatorMotor;
    Servo elevatorServo;
    double elevatorPosition = 0;

    @Override                                            //counter clockwise up clockwise down
    public void runOpMode() {
        elevatorMotor = hardwareMap.get(DcMotor.class, "elevatorMotor");
        elevatorServo = hardwareMap.get(Servo.class, "elevatorServo");
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        elevatorMotor.setPower(0.0);
        elevatorMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        while (opModeIsActive()) {
            while (gamepad2.right_bumper) {
                //telemetry.addData("Status", "Right Bumper Pressed");
                telemetry.addData("Status", elevatorMotor.getCurrentPosition());
                telemetry.update();
                elevatorMotor.setDirection(DcMotor.Direction.REVERSE);
                elevatorMotor.setPower(1.0);
            }
            elevatorMotor.setPower(0.0);
            while (gamepad2.left_bumper) {
                //telemetry.addData("Status", "Left Bumper Pressed");
                telemetry.addData("Status", elevatorMotor.getCurrentPosition());
                telemetry.update();
                elevatorMotor.setDirection(DcMotor.Direction.FORWARD);
                elevatorMotor.setPower(0.5);
            }
            elevatorMotor.setPower(0.0);
            if (gamepad2.dpad_down) {
                elevatorServo.setDirection(Servo.Direction.FORWARD);
                elevatorServo.setPosition(elevatorPosition);              // 0.5 = 90 degrees 0 = 0 degrees 1 = 180 degrees
            } else if (gamepad2.dpad_up) {
                elevatorServo.setDirection(Servo.Direction.FORWARD);
                elevatorServo.setPosition(1);
            }
            telemetry.addData("dpad_up", gamepad2.dpad_up);
            telemetry.addData("dpad_down", gamepad2.dpad_down);
            telemetry.update();
        }
    }
}

