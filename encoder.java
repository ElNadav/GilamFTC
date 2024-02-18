import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "encoder")
public class encoder extends OpMode {
    DcMotor motor;
    double ticks = 288;
    double newTarget;
    int currentState = 0;
    boolean joystickControl = false;

    @Override
    public void init() {
        motor = hardwareMap.get(DcMotor.class, "motor");
        telemetry.addData("Hardware", "Initialized");
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop() {
        if (gamepad1.x) { // Sets Control to either Joystick or A/B/Y Buttons
            joystickControl = !joystickControl;
            if (joystickControl) {
                motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }
            else {
                motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
        }
        telemetry.addData("Joystick Control", joystickControl + "\n");
        if (joystickControl) {
            float direction = gamepad1.left_stick_x;
            telemetry.addData("Left Stick", direction);
            telemetry.update();
            motor.setPower(direction);
        }
        else {
            telemetry.addData("Instructions", "\nPress A to perform a Half turn. \nPress B to stop motor and reset position. \nPress Y to go back to last position. \n");
            switch (currentState) {
                case 0:
                    telemetry.addData("Motor state", "\nB was pressed, motor stopped and reset position");
                    break;
                case 1:
                    telemetry.addData("Motor state", "\nA was pressed, motor performing half turn");
                    break;
                case 2:
                    telemetry.addData("Motor state", "\nY was pressed, going back to last position");
                    break;
            }
            telemetry.addData("\nMotor position", "\n" + (int) ((motor.getCurrentPosition() / ticks) * 100) + "%");
            if (gamepad1.a) { // Perform a Half Turn
                runEncoder(2, 1, 0.3);
            }
            if (gamepad1.b) { // Stop Motor
                runEncoder(0, 0, 0);
            }
            if (gamepad1.y) { // Reset position to last known position
                runEncoder(0, 2, 0.2);
            }
        }
    }
    public void runEncoder(int turn, int currentState, double power) {
        this.currentState = currentState;
        if (currentState != 2) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
        if (turn != 0) {
            newTarget = ticks/turn; //creates a half spin of the motor
        }
        else {
            newTarget = 0;
        }
        motor.setTargetPosition((int)newTarget);
        motor.setPower(power);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}
