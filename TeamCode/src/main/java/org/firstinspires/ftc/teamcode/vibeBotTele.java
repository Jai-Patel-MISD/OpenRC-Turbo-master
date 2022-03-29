package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.util.Range.clip;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Vibe Bot TeleOp")
public class vibeBotTele extends LinearOpMode {

    VibeBotHardware bot = new VibeBotHardware();
    @Override
    public void runOpMode() throws InterruptedException {
        bot.init(hardwareMap);
        waitForStart();

        while(!isStopRequested()){
            bot.mallet1.setPosition(clip(.45-.25*gamepad1.right_stick_y,.48,.7));
            bot.cart1.setVelocity(1000 * (gamepad1.left_trigger- gamepad1.right_trigger));
            telemetry.addData("pos", bot.cart1.getCurrentPosition());
            telemetry.addData("servo pos", bot.mallet1.getPosition());
            telemetry.update();
        }
    }
}