import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name = "testing")
public class testing extends OpMode {

    DcMotor leftMotor;

    @Override
    public void init() {
        leftMotor = hardwareMap.get(DcMotor.class, "leftMotor");
        telemetry.addData("ftc", "First");
        telemetry.addData("initialization" , "success");

    }

    @Override
    public void loop() {
        float direction = gamepad1.left_stick_x;
        telemetry.addData("Left Stick", direction);
        telemetry.update();
        leftMotor.setPower(direction);
    }
}
