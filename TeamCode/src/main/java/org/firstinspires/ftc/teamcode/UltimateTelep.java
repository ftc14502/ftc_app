package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;


// You get to use the quality wannnabe PS4 game controller... wait what?


public class UltimateTelep extends LinearOpMode {

    private DcMotor leftWheel;
    private DcMotor rightWheel;
    public ColorSensor colorSensor;

    @Override
    public void runOpMode() {
        // Get configuration
        leftWheel = hardwareMap.get(DcMotor.class, "leftWheel");
        rightWheel = hardwareMap.get(DcMotor.class, "rightWheel");
        colorSensor = hardwareMap.get(ColorSensor.class, "colourSensor");

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        colorSensor.enableLed(false);
        Arm arm = new Arm (hardwareMap, telemetry, gamepad1);
        Elevator elevator = new Elevator (hardwareMap, telemetry, gamepad2);

        leftWheel.setDirection(DcMotor.Direction.REVERSE);
        rightWheel.setDirection(DcMotor.Direction.REVERSE);

        leftWheel.setPower(0);
        rightWheel.setPower(0);


        telemetry.addData("Status", "Waiting");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            leftWheel.setPower(-gamepad1.left_stick_y);
            rightWheel.setPower(gamepad1.left_stick_y);
            leftWheel.setPower(gamepad1.right_stick_x);
            rightWheel.setPower(gamepad1.right_stick_x);
            telemetry.addData("Left Power: ", gamepad1.left_stick_y);
            telemetry.addData("Right Power: ", gamepad1.right_stick_y);
            arm.dumpMotor.setDirection(DcMotor.Direction.FORWARD);
            arm.dumpMotor.setPower(gamepad1.left_trigger);



            telemetry.update();


            if (gamepad1==null) {
                telemetry.addData("Status", "gamepad 1 is null");
                telemetry.update();
                continue;
            }

            if (gamepad2==null) {
                telemetry.addData("Status", "bus 2 is null");
                telemetry.update();
                continue;
            }

            if (gamepad1.dpad_left) {
                arm.pushMineral();
            }else {
                arm.flyWheel.setPower(0);

            }

            if (gamepad1.dpad_right) {
                arm.takeMineral();
            }else {
                arm.flyWheel.setPower(0);
            }

            if (gamepad1.dpad_up) {
                arm.forwardArmMotor();
            }else {
                arm.armMotor.setPower(0);
            }
            if (gamepad1.dpad_down) {
                arm.backwardArmMotor();
            }else{
                arm.armMotor.setPower(0);
            }

            if (gamepad1.left_bumper) {
                arm.moveDumper();
            }

            if (gamepad1.right_bumper) {
                arm.moveDumper();
            }

            // Gamepad2 controls
            if (gamepad2.right_bumper && !gamepad2.left_bumper) {
                elevator.elevatorUp();
            } else {
                elevator.elevatorMotor.setPower(0.0);
            }


            if (gamepad2.left_bumper && !gamepad2.right_bumper) {
                elevator.elevatorDown();
            } else {
                elevator.elevatorMotor.setPower(0.0);
            }

            if (gamepad2.left_bumper && gamepad2.right_bumper) {
                elevator.elevatorDownWithoutLimit();
            } else {
                elevator.elevatorMotor.setPower(0.0);
            }

            if (gamepad2.dpad_down) {
                elevator.dumpStart();
            } else if (gamepad2.dpad_up) {
                elevator.dumpEnd();
            }

        }
    }

} // end of the class UltimateTeleOp.java
