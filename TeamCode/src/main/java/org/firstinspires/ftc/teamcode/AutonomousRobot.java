package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class AutonomousRobot {
    DcMotor leftWheel;
    DcMotor rightWheel;
    DcMotor dumpMotor;
    CRServo pushColour;
    ColorSensor colourSensor;
    private int mineralCounter;

    HardwareMap hardwareMap = null;
    Telemetry telemetry;


    public AutonomousRobot(HardwareMap hw, Telemetry telephone) {
        telemetry = telephone;
        hardwareMap = hw;
        mineralCounter = 0;

        leftWheel = hardwareMap.dcMotor.get("leftWheel");
        rightWheel = hardwareMap.dcMotor.get("rightWheel");
        dumpMotor = hardwareMap.dcMotor.get("dumpMotor");
        pushColour = hardwareMap.crservo.get("pushColour");
        colourSensor = hardwareMap.colorSensor.get("colourSensor");

    }

// 114.6 ish encoders per inch
// 13.5 ticks per degree for rotation
// 1170 ticks for a quarter rotation

    public void rightTurn(int degree) {
        //int tick = 1440/13*10; // amount o inches fur tickz
        double angle_d = 12 * (degree); //algorithim for da angle, sub degree with degree
        int angle = (int) angle_d;
        leftWheel.setDirection(DcMotor.Direction.REVERSE);
        rightWheel.setDirection(DcMotor.Direction.REVERSE);

        leftWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // using encoders
        rightWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        telemetry.addData("Status", "Waiting");
        telemetry.update();

        leftWheel.setTargetPosition(angle); // setting the target to the angle specified.
        rightWheel.setTargetPosition(angle);

        leftWheel.setPower(0.2);
        rightWheel.setPower(0.2); // turnning

        while (leftWheel.isBusy() && rightWheel.isBusy()) {
            telemetry.addData("leftMotor", leftWheel.getCurrentPosition() + "value" + leftWheel.isBusy());
            telemetry.addData("rightMotor", rightWheel.getCurrentPosition() + "value" + rightWheel.isBusy());
            telemetry.update();
        }
        leftWheel.setPower(0);
        rightWheel.setPower(0); // stopping

    }

    public void leftTurn(int degree) {
        double angle_d = 12 * (degree); //algorithim for da angle, sub degree with degree
        int angle = (int) angle_d;
        leftWheel.setDirection(DcMotor.Direction.FORWARD);
        rightWheel.setDirection(DcMotor.Direction.FORWARD);


        leftWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        telemetry.addData("Status", "Waiting");
        telemetry.update();

        telemetry.addData("Status", "running");
        telemetry.update();
        leftWheel.setTargetPosition(angle);
        rightWheel.setTargetPosition(angle);

        leftWheel.setPower(0.2);
        rightWheel.setPower(0.2);

        while (leftWheel.isBusy() && rightWheel.isBusy()) {
            telemetry.addData("leftMotor", leftWheel.getCurrentPosition() + "value" + leftWheel.isBusy());
            telemetry.addData("rightMotor", rightWheel.getCurrentPosition() + "value" + rightWheel.isBusy());
            telemetry.update();
        }
        leftWheel.setPower(0);
        rightWheel.setPower(0);

    }

    public void forward(int inches, float power) {
        int tick = 92 * (inches); // going inches
        leftWheel.setDirection(DcMotor.Direction.REVERSE);
        rightWheel.setDirection(DcMotor.Direction.FORWARD);


        leftWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // setting encoders
        rightWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        telemetry.addData("Status", "Running");
        telemetry.update();

        leftWheel.setTargetPosition(tick); // setting position to move x inches forward
        rightWheel.setTargetPosition(tick);

        leftWheel.setPower(power); //goin forward
        rightWheel.setPower(power);


        while (leftWheel.isBusy() && rightWheel.isBusy()) {
            telemetry.addData("leftMotor", leftWheel.getCurrentPosition() + "value" + leftWheel.isBusy());
            telemetry.addData("rightMotor", rightWheel.getCurrentPosition() + "value" + rightWheel.isBusy());
            telemetry.addData("tick", tick);
            telemetry.update();
        }
        leftWheel.setPower(0);
        rightWheel.setPower(0);

    }


    public void backward(int inches, float power) {
        int tick = 92 * (inches); // esta bien,



        leftWheel.setDirection(DcMotor.Direction.FORWARD); // igualmente principlas pero backward
        rightWheel.setDirection(DcMotor.Direction.REVERSE);


        leftWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        telemetry.addData("Status", "Running");
        telemetry.update();

        leftWheel.setTargetPosition(tick);
        rightWheel.setTargetPosition(tick);

        leftWheel.setPower(power);
        rightWheel.setPower(power);

        while (leftWheel.isBusy() && rightWheel.isBusy()) {
            telemetry.addData("leftMotor", leftWheel.getCurrentPosition() + "value" + leftWheel.isBusy());
            telemetry.addData("rightMotor", rightWheel.getCurrentPosition() + "value" + rightWheel.isBusy());
            telemetry.addData("tick", tick);
            telemetry.update();
        }
        leftWheel.setPower(0);
        rightWheel.setPower(0);


    }

    public void moveDumperUp() {
        dumpMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dumpMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dumpMotor.setDirection(DcMotor.Direction.FORWARD);
        dumpMotor.setTargetPosition(440);
        dumpMotor.setPower(0.5);
        while (dumpMotor.isBusy()) {
            telemetry.addData("Status", "Lifting");
            telemetry.addData("dumpMotor", dumpMotor.getCurrentPosition() + " is moving. WAIT OR ELSE" + dumpMotor.isBusy());
            telemetry.update();
        }
        dumpMotor.setPower(0);
    }

    public void moveDumperDown() {
        dumpMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dumpMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dumpMotor.setDirection(DcMotor.Direction.REVERSE);
        dumpMotor.setTargetPosition(300);
        dumpMotor.setPower(0.5);
        while (dumpMotor.isBusy()) {
            telemetry.addData("Status", "Lifting");
            telemetry.addData("dumpMotor", dumpMotor.getCurrentPosition() + " is moving. WAIT OR ELSE" + dumpMotor.isBusy());
            telemetry.update();
        }
        dumpMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        dumpMotor.setPower(0);
        sleep(300);
        //dumpMotor.setPower(0);
    }



    public void depotColourProgramme(int fullDistance) {
        pushColour.setDirection(CRServo.Direction.FORWARD);
        pushColour.setPower(1);
        sleep(500);
        pushColour.setPower(0);
        boolean goldFound = false;
        double tick_1 = (92 * (fullDistance)); //- rightWheel.getCurrentPosition();;
        int tickz = (int) tick_1;
        //int tick = 1440 / 13 * (fullDistance);
        float hsvValues[] = {0F, 0F, 0F};
        final float values[] = hsvValues;
        colourSensor.enableLed(true);
        // run infinitely

        leftWheel.setDirection(DcMotor.Direction.REVERSE);
        rightWheel.setDirection(DcMotor.Direction.FORWARD);


        leftWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // setting encoders
        rightWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftWheel.setTargetPosition(tickz); // setting position to move x inches forward
        rightWheel.setTargetPosition(tickz);

        leftWheel.setTargetPosition(tickz); // setting position to move x inches forward
        rightWheel.setTargetPosition(tickz);
        leftWheel.setPower(0.2);
        rightWheel.setPower(0.2);
        Color.RGBToHSV(colourSensor.red() * 8, colourSensor.green() * 8, colourSensor.blue() * 8, hsvValues);


        while (leftWheel.isBusy() && rightWheel.isBusy()) {
            telemetry.addData("leftMotor", leftWheel.getCurrentPosition() + "value" + leftWheel.isBusy());
            telemetry.addData("rightMotor", rightWheel.getCurrentPosition() + "value" + rightWheel.isBusy());
            telemetry.addData("tick", tickz);
            telemetry.addData("Hue", hsvValues);
            telemetry.update();
            Color.RGBToHSV(colourSensor.red() * 8, colourSensor.green() * 8, colourSensor.blue() * 8, hsvValues);


            if (hsvValues[0] >= 35 && hsvValues[0] <= 50) {

                goldFound = true;
                leftWheel.setPower(0);
                rightWheel.setPower(0);



                telemetry.addData("Hue", hsvValues[0]);
                telemetry.addData("gold found", goldFound);
                telemetry.update();
                pushColour.setDirection(CRServo.Direction.FORWARD);
                pushColour.setPower(1);
                sleep(2500);
                pushColour.setDirection(CRServo.Direction.REVERSE);
                pushColour.setPower(1);
                sleep(3000);
                pushColour.setPower(0);

                double tick_2 = (92 * (fullDistance)) - rightWheel.getCurrentPosition();;
                int tick = (int) tick_2;
                //((1440 / 13 * (16)) * (mineralCounter - 1));


                leftWheel.setDirection(DcMotor.Direction.REVERSE);
                rightWheel.setDirection(DcMotor.Direction.FORWARD);


                leftWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // setting encoders
                rightWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                leftWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rightWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                leftWheel.setTargetPosition(tick); // setting position to move x inches forward
                rightWheel.setTargetPosition(tick);

                leftWheel.setPower(0.5); //goin forward
                rightWheel.setPower(0.5);


                while (leftWheel.isBusy() && rightWheel.isBusy()) {
                    telemetry.addData("leftMotor", leftWheel.getCurrentPosition() + "value" + leftWheel.isBusy());
                    telemetry.addData("rightMotor", rightWheel.getCurrentPosition() + "value" + rightWheel.isBusy());
                    telemetry.addData("tick", tick);
                    telemetry.update();
                }
                leftWheel.setPower(0);
                rightWheel.setPower(0);
                sleep(1000);
                break;
            }
           /* if (rightWheel.getCurrentPosition() == 1440/13*(32)) {
                if (goldFound = false) {
                    break;
                }
            } */
            else {
                telemetry.addData("nothing has been", "found");
                telemetry.addData("Hue", hsvValues[0]);
                telemetry.update();
            }
        }
    }


    public void craterColourProgramme(int fullDistance) {
        pushColour.setDirection(CRServo.Direction.FORWARD);
        pushColour.setPower(1);
        sleep(500);
        pushColour.setPower(0);
        boolean goldFound;

        double tick_1 = 92* (fullDistance); //algorithim for da angle, sub degree with degree
        int tickz = (int) tick_1;
        float hsvValues[] = {0F, 0F, 0F};
        final float values[] = hsvValues;
        colourSensor.enableLed(true);
        // run infinitely

        telemetry.addData("Distance Moved", rightWheel.getCurrentPosition() * (13 / 1440));
        telemetry.update();
        //tick = 1440/13*(fullDistance );
        leftWheel.setDirection(DcMotor.Direction.FORWARD);
        rightWheel.setDirection(DcMotor.Direction.REVERSE);


        leftWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // setting encoders
        rightWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftWheel.setTargetPosition(tickz); // setting position to move x inches forward
        rightWheel.setTargetPosition(tickz);

        leftWheel.setPower(0.2); //goin forward
        rightWheel.setPower(0.2);

        Color.RGBToHSV(colourSensor.red() * 8, colourSensor.green() * 8, colourSensor.blue() * 8, hsvValues);
        telemetry.addData("going into while loop","");
        telemetry.update();
        while (leftWheel.isBusy() && rightWheel.isBusy()) {
            telemetry.addData("leftMotor", leftWheel.getCurrentPosition() + "value" + leftWheel.isBusy());
            telemetry.addData("rightMotor", rightWheel.getCurrentPosition() + "value" + rightWheel.isBusy());
            telemetry.addData("tick", tickz);
            telemetry.update();
            Color.RGBToHSV(colourSensor.red() * 8, colourSensor.green() * 8, colourSensor.blue() * 8, hsvValues);

            if (hsvValues[0] >= 35 && hsvValues[0] <= 50) {

                goldFound = true;
                leftWheel.setPower(0);
                rightWheel.setPower(0);


                telemetry.addData("Hue", hsvValues[0]);
                telemetry.addData("gold found", goldFound);
                telemetry.update();
                pushColour.setDirection(CRServo.Direction.FORWARD);
                pushColour.setPower(1);
                sleep(4000);
                pushColour.setDirection(CRServo.Direction.REVERSE);
                pushColour.setPower(1);
                sleep(4500);

                pushColour.setPower(0);

                double tick_2 = (92 * (fullDistance)) - rightWheel.getCurrentPosition();
                int tick = (int) tick_2;
                //tick = (1440 / 13.5 * (70)) - rightWheel.getCurrentPosition();//((1440 / 13 * (16)) * (mineralCounter - 1));


                leftWheel.setDirection(DcMotor.Direction.FORWARD);
                rightWheel.setDirection(DcMotor.Direction.REVERSE);


                leftWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // setting encoders
                rightWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                leftWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rightWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                leftWheel.setTargetPosition(tick); // setting position to move x inches forward
                rightWheel.setTargetPosition(tick);

                leftWheel.setPower(0.5); //goin forward
                rightWheel.setPower(0.5);


                while (leftWheel.isBusy() && rightWheel.isBusy()) {
                    telemetry.addData("leftMotor", leftWheel.getCurrentPosition() + "value" + leftWheel.isBusy());
                    telemetry.addData("rightMotor", rightWheel.getCurrentPosition() + "value" + rightWheel.isBusy());
                    telemetry.addData("tick", tick);
                    telemetry.update();
                }
                leftWheel.setPower(0);
                rightWheel.setPower(0);
                //sleep(1000);
                break;
            }
            else {
                telemetry.addData("nothing has been", "found");
                telemetry.addData("Hue", hsvValues[0]);
                telemetry.update();
                goldFound = false;
            }
        }
    }




    public void pushColour() {
        pushColour.setPower(1);
        pushColour.setDirection(CRServoImplEx.Direction.FORWARD);
        sleep(3000);
        pushColour.setDirection(CRServoImplEx.Direction.REVERSE);
        sleep(3000);
    }

    private final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}