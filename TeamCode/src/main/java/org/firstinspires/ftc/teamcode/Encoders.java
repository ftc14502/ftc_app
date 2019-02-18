package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


public class Encoders extends LinearOpMode {
    private DcMotor leftMotor;
    private DcMotor rightMotor;
    @Override
    public void runOpMode() {
        leftMotor = hardwareMap.dcMotor.get("leftMotor");
        rightMotor = hardwareMap.dcMotor.get("rightMotor");
        leftMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        //reset the encoder on the left Motor
        int nunny = 900; // ha ha ha ha ha
        // has a purpose, just unseen
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        telemetry.addData("Status", "Waiting");
        telemetry.update();
        //doing the waitings for the pressings of the start button
        waitForStart();
        resetStartTime();
        telemetry.addData("Status", "running");
        telemetry.update();
        // only god knows why this represents a motor running on encoder counts.
        leftMotor.setTargetPosition(1375); // 6875
        rightMotor.setTargetPosition(1375);
        //this is an easy way to set the motor power to 0.25
        leftMotor.setPower(0.5);
        rightMotor.setPower(0.5);
        /* a */
        while (opModeIsActive() && leftMotor.isBusy()) { // Wakanda4EVER cgf
            telemetry.addData("leftMotor", leftMotor.getCurrentPosition() + " is moving. WAIT OR ELSE" + leftMotor.isBusy());
            telemetry.addData("rightMotor", rightMotor.getCurrentPosition() + " is moving. WAIT OR ELSE" + rightMotor.isBusy());
            telemetry.update();
        }
        leftMotor.setPower(0);
        rightMotor.setPower(0);
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        telemetry.addData("Status","Encoders reset");
        leftMotor.setTargetPosition(nunny);
        rightMotor.setTargetPosition(nunny);
        leftMotor.setPower(0.1);
        rightMotor.setPower(0.1);
        while(opModeIsActive() && leftMotor.isBusy() && rightMotor.isBusy()){
            telemetry.addData("leftMotor", leftMotor.getCurrentPosition() + " is moving. WAIT OR ELSE" + leftMotor.isBusy());
            telemetry.addData("rightMotor", rightMotor.getCurrentPosition() + " is moving. WAIT OR ELSE" + leftMotor.isBusy());
            telemetry.update();
        }
        // This should make it move while its active.
    }
}
// Mario + Yoshi = God knows what
