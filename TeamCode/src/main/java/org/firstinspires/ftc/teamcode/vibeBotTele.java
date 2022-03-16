package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name = "Vibe Bot TeleOp")
public class vibeBotTele extends LinearOpMode {

    VibeBotHardware bot = new VibeBotHardware();
    @Override
    public void runOpMode() throws InterruptedException {
        bot.init(hardwareMap);
        waitForStart();

        while(!isStopRequested()){
            if(gamepad1.right_bumper){
                bot.mallet1.setPosition(1);
            } else {
                bot.mallet1.setPosition(.6);
            }
            bot.cart1.setVelocity(1000 * (gamepad1.left_trigger- gamepad1.right_trigger));

        }
    }
}