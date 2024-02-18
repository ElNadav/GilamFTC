import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp(name = "servo")
public class servo extends OpMode {

    public Servo armServo;
    public CRServo pixelServo;
    @Override
    public void init() {
        armServo = hardwareMap.get(Servo.class, "armServo");
        pixelServo = hardwareMap.get(CRServo.class, "pixelServo");
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            armServo.setPosition(1);
            telemetry.addData("Button Press","A");
        }
        if (gamepad1.b) {
            armServo.setPosition(-1);
            pixelServo.setDirection(DcMotorSimple.Direction.REVERSE);
            pixelServo.setPower(0.3);
            telemetry.addData("Button Press","B");
            telemetry.update();
        }
        if (gamepad1.y) {
            telemetry.addData("Button Press", "Y");
            armServo.setPosition(0.5);
            telemetry.update();
        }

        // CRServo Programming:
        /* Nice trick: If you use the SRS programmer to change servo state to S instead of C,
           releasing the bumpers will result in the servo returning to its middle state (0.5) that was set when programmed.
           you can set the 0.5 position by pressing PROGRAM on the SRS Programmer.
        */

        if (gamepad1.right_bumper) {
            telemetry.addData("Bumper","Right");
            pixelServo.setDirection(DcMotorSimple.Direction.FORWARD);
            pixelServo.setPower(0.7);
            telemetry.update();
        }
        else if (gamepad1.left_bumper) {
            telemetry.addData("Bumper","Left");
            pixelServo.setDirection(DcMotorSimple.Direction.REVERSE);
            pixelServo.setPower(0.7);
            telemetry.update();
        }
        else {
            pixelServo.setPower(0);
        }

    }
}
