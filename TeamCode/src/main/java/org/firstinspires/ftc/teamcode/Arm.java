package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Arm {
    public DcMotor armMotor;       // i.e. moving front and back
    public DcMotor dumpMotor;      // i.e. dumps to basket
    public DcMotor flyWheel;       // i.e. collecting minerals into robot

    private Telemetry telemetry;

    private Gamepad gamepad1 = null;

    private HardwareMap hardwareMap = null;

    private boolean isDumperUp;

    int counter;



    public Arm(HardwareMap hw, Telemetry telephone, Gamepad gpd) {
        gamepad1 = gpd;
        telemetry = telephone;
        hardwareMap = hw;
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        dumpMotor = hardwareMap.get(DcMotor.class, "dumpMotor");
        flyWheel = hardwareMap.get(DcMotor.class, "flyWheel");


        armMotor.setDirection(DcMotor.Direction.FORWARD);
        dumpMotor.setDirection(DcMotor.Direction.FORWARD);
        flyWheel.setPower(Servo.MIN_POSITION);
        dumpMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dumpMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        armMotor.setPower(0);
        dumpMotor.setPower(0);
        flyWheel.setPower(0);

        isDumperUp = true;
        counter = 0;

    }

    public void moveDumper() { // this prevents confusion
        if (isDumperUp) {
            moveDumperDown();
        } else {
            moveDumperUp();
            isDumperUp = true;
        } // the dumping mechanism must start being up and recoiled for the robot to work properly
    }


    public void moveDumperUp() {
        telemetry.addData("Status", " calling method moveDumper");
        telemetry.update();
        while (!isDumperUp) {
            telemetry.addData("status", "inside moveDumperUp function");
            telemetry.update();
            dumpMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            dumpMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            dumpMotor.setDirection(DcMotor.Direction.FORWARD);
            dumpMotor.setTargetPosition(480);
            dumpMotor.setPower(0.3);
            while (dumpMotor.isBusy()) {
                telemetry.addData("Status", "Lifting");
                telemetry.addData("dumpMotor", "current position: " + dumpMotor.getCurrentPosition() + "Waiting:" + dumpMotor.isBusy());
                telemetry.update();
            }
            isDumperUp = true;
        }
            telemetry.addData("Status", "Lifted");
            telemetry.update();


    }


    public void moveDumperDown() {
        while (isDumperUp) {
            telemetry.addData("status", "inside moveDumperDown function");
            telemetry.update();
            dumpMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            dumpMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            dumpMotor.setDirection(DcMotor.Direction.REVERSE);
            dumpMotor.setTargetPosition(250);
            dumpMotor.setPower(0.3);
            while (dumpMotor.isBusy()) {
                telemetry.addData("Status", "Lifting");
                telemetry.addData("dumpMotor", "currentPosition: " + dumpMotor.getCurrentPosition() + "Waiting:" + dumpMotor.isBusy());
                telemetry.update();
            }

            sleep(1000);

            dumpMotor.setPower(0);
            isDumperUp = false;
            //cor++; IS USELESS
        }
            telemetry.addData("Status:", "Dumper is already down");
            telemetry.update();

//        if (counter == 2) {
//            isDumperUp = false;
//            counter = 0;
//        }
    }
    public void independentBumperUp(){
        telemetry.addData("Status:", "calling method independentBumper");
        telemetry.update();

        telemetry.addData("X:", gamepad1.x);
        telemetry.update();
        dumpMotor.setDirection(DcMotor.Direction.FORWARD);
        dumpMotor.setPower(0.3);

    }
    public void independentBumperDown(){
        telemetry.addData("Status:", "Calling method independentBumperDown");
        telemetry.update();

        telemetry.addData("Y:", gamepad1.y);
        telemetry.addData("Status:", "inside the function independentBumperDown");
        telemetry.update();
        dumpMotor.setDirection(DcMotor.Direction.REVERSE);
        dumpMotor.setPower(0.3);
    }

    public void pushMineral() {
        if (gamepad1.dpad_left) {
            telemetry.addData("status", "inside pushMineral function");
            telemetry.update();
            flyWheel.setDirection(DcMotor.Direction.FORWARD);
            flyWheel.setPower(1);
            telemetry.addData("Status", "Pushing Out The Mineral");
            telemetry.update();
        } else {
            flyWheel.setPower(0);
        }

    }

    public void takeMineral() {
        if (gamepad1.dpad_right) {
            telemetry.addData("status", "inside takeMineral function");
            telemetry.update();
            flyWheel.setDirection(DcMotor.Direction.REVERSE);
            flyWheel.setPower(1);
            telemetry.addData("Status", "Take Mineral");
            telemetry.update();
        } else {
            flyWheel.setPower(0);
        }

    }

    public void forwardArmMotor() {
        telemetry.addData("status", " inside forwardArmMotor function");
        telemetry.update();

        armMotor.setDirection(DcMotor.Direction.REVERSE);
        telemetry.addData("Condition", "Arm Extending");
        telemetry.update();
        armMotor.setDirection(DcMotor.Direction.REVERSE);
        armMotor.setPower(1);
    }


    public void backwardArmMotor() {
        telemetry.addData("status", "inside backwardArmMotor function");
        telemetry.update();
        armMotor.setDirection(DcMotor.Direction.FORWARD);
        armMotor.setPower(1);
        telemetry.addData("Condition", "Arm Backing up");
        telemetry.update();
        armMotor.setDirection(DcMotor.Direction.FORWARD);
        armMotor.setTargetPosition(200);
        armMotor.setPower(0.5);
    }

    public final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
