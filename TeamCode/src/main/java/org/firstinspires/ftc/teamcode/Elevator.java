package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Elevator{
    public DcMotor elevatorMotor;
    public Servo elevatorServo;
    private double elevatorPosition=0;
    private int elevatorStartPosition=0;
    private Telemetry telemetry;
    private Gamepad gamepad2;



    public Elevator(HardwareMap hw, Telemetry telephone, Gamepad gpd) {
        elevatorMotor = hw.get(DcMotor.class, "elevatorMotor");
        elevatorServo = hw.get(Servo.class, "elevatorServo");
        telemetry = telephone;
        gamepad2 = gpd;

        elevatorMotor.setPower(0.0);
        elevatorMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevatorMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    public void elevatorUp() {
        telemetry.addData("Status", elevatorMotor.getCurrentPosition());
        telemetry.addData("Status", "Right Bumper Pressed");
        telemetry.update();
        elevatorMotor.setDirection(DcMotor.Direction.FORWARD);
        elevatorMotor.setPower(1.0);

    }

    public void elevatorDown() {
        //if (gamepad2.left_bumper) {
        elevatorMotor.setDirection(DcMotor.Direction.REVERSE);
        if ((elevatorMotor.getCurrentPosition() > elevatorStartPosition)) {
                telemetry.addData("Status", "Left Bumper Pressed");
                telemetry.update();
                elevatorMotor.setPower(1.0);

        }
    }

    public void elevatorDownWithoutLimit() {
        //if (gamepad2.left_bumper) {
            telemetry.addData("Status", elevatorMotor.getCurrentPosition());
            telemetry.update();
            elevatorMotor.setDirection(DcMotor.Direction.REVERSE);
            elevatorMotor.setPower(1.0);
        //} else {
        //    elevatorMotor.setPower(0.0);
        //}
    }

    public void dumpStart() {
        if (gamepad2.dpad_down) {
            elevatorServo.setDirection(Servo.Direction.FORWARD);
            elevatorServo.setPosition(elevatorPosition);              // 0.5 = 90 degrees 0 = 0 degrees 1 = 180 degrees
            telemetry.addData("dpad_down", gamepad2.dpad_down);
            telemetry.update();
        }
    }

    public void dumpEnd() {
        if (gamepad2.dpad_up) {
            elevatorServo.setDirection(Servo.Direction.FORWARD);
            elevatorServo.setPosition(1);
            telemetry.addData("dpad_up", gamepad2.dpad_up);
            telemetry.update();
        }
    }
}
