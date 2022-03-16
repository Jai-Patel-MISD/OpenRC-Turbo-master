package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Note Test")
//@Disabled
public class PlayNoteTest extends OpMode {
    VibeBotHardware bot = new VibeBotHardware();
    private final ElapsedTime noteTime = new ElapsedTime();
    public static double BPM = 120;
    private final double wholeNote = BPM/15;
    private final double halfNote = BPM/30;
    private final double quarterNote = BPM/60;
    private final double eighthNote = BPM/120;
    private final double sixteenthNote = BPM/240;
    double lastError = 0;
    double integral = 0;
    public static PIDCoefficients pidCoeffs = new PIDCoefficients(3.5,0.0000001,.00001);
    public PIDCoefficients pidGains = new PIDCoefficients(0,0,0);
    public double targetPos;
    private final ElapsedTime PIDTimer  = new ElapsedTime();

    @Override
    public void init(){
        bot.init(hardwareMap);
    }
    @Override
    public void start(){

        targetPos = bot.notes.d2;
        noteTime.reset();
        noteTime.startTime();
        while (noteTime.time() < quarterNote){
            telemetry.addData("Note Time: ", noteTime.time());
            telemetry.update();
        }
        bot.drop();
        noteTime.reset();
        noteTime.startTime();
        targetPos = bot.notes.e2;
        while (noteTime.time() < quarterNote){
            telemetry.addData("Note Time: ", noteTime.time());
            telemetry.update();
        }
        bot.drop();
    }
    @Override
    public void loop(){
        PIDTimer.reset();
        double currentPos = bot.cart1.getCurrentPosition();
        double error = targetPos - currentPos;
        double deltaError = error - lastError;
        double derivative = deltaError / PIDTimer.time();

        integral += error * PIDTimer.time();

        pidGains.p = error * pidCoeffs.p;
        pidGains.i = integral * pidCoeffs.i;
        pidGains.d = derivative * pidCoeffs.d;

        bot.cart1.setVelocity(pidGains.p + pidGains.i + pidGains.d);
        lastError = error;

        telemetry.addData("servo position: ", bot.mallet1.getPosition());
        telemetry.addData("cart1 pos: ", bot.cart1.getCurrentPosition());
        telemetry.addData("cart1 target pos: ", targetPos);
        telemetry.addData("error: ", targetPos - bot.cart1.getCurrentPosition());
        telemetry.update();
    }
    @Override
    public void stop(){
        bot.cart1.setVelocity(0);
    }
    public void playNoteModified(double newPos, double time){
        bot.drop();
        targetPos = newPos;
        while(time >= noteTime.time()){
            telemetry.addData("Note Time: ", noteTime.time());
            telemetry.update();
        }
        noteTime.reset();
    }
}