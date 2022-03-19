package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name = "Mary TeleOp")
//@Disabled
public class FirstSongTeleOp extends OpMode {
    VibeBotHardware bot = new VibeBotHardware();
    private final ElapsedTime noteTime = new ElapsedTime();
    public static double BPM = 120;
    private final double wholeNote = BPM/15;
    private final double halfNote = BPM/30;
    private final double quarterNote = BPM/60;
    private final double eighthNote = BPM/120;
    private final double sixteenthNote = BPM/240;
    private static final boolean isOn = true;
    double lastError = 0;
    double integral = 0;
    public static PIDCoefficients pidCoeffs = new PIDCoefficients(3.5,0.0000001,.00001);
    public PIDCoefficients pidGains = new PIDCoefficients(0,0,0);
    public double targetPos = 0;
    private final ElapsedTime PIDTimer  = new ElapsedTime();

    @Override
    public void init(){
        bot.init(hardwareMap);
        targetPos = bot.notes.e2;
    }
    @Override
    public void start(){
        playNote(quarterNote, bot.notes.e2);
        playNote(quarterNote, bot.notes.d2);
        playNote(quarterNote, bot.notes.c2);
        playNote(quarterNote, bot.notes.d2);
        playNote(quarterNote, bot.notes.e2);
        playNote(quarterNote, bot.notes.e2);
        playNote(quarterNote, bot.notes.e2);
        playNote(halfNote, bot.notes.d2);
        playNote(quarterNote, bot.notes.d2);
        playNote(quarterNote, bot.notes.d2);
        playNote(halfNote, bot.notes.e2);
        playNote(quarterNote, bot.notes.g2);
        playNote(quarterNote, bot.notes.g2);
        playNote(halfNote, bot.notes.e2);
        playNote(quarterNote, bot.notes.d2);
        playNote(quarterNote, bot.notes.c2);
        playNote(quarterNote, bot.notes.d2);
        playNote(quarterNote, bot.notes.e2);
        playNote(quarterNote, bot.notes.e2);
        playNote(quarterNote, bot.notes.e2);
        playNote(quarterNote, bot.notes.e2);
        playNote(quarterNote, bot.notes.d2);
        playNote(quarterNote, bot.notes.d2);
        playNote(quarterNote, bot.notes.e2);
        playNote(quarterNote, bot.notes.d2);
        playNote(quarterNote, bot.notes.c2);
        playNote(wholeNote, bot.notes.c2);
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
        telemetry.addData("servo position", bot.mallet1.getPosition());
        telemetry.addData("cart1 pos", bot.cart1.getCurrentPosition());
        telemetry.addData("cart1 target pos", targetPos);
        telemetry.addData("error", targetPos - bot.cart1.getCurrentPosition());
        telemetry.update();
    }
    @Override
    public void stop(){
        bot.cart1.setVelocity(0);
    }
    public void playNote(double time, double newPos){
        noteTime.reset();
        noteTime.startTime();
        while(time >= noteTime.time()){
            telemetry.addData("time", noteTime.time());
            telemetry.update();
        }
        noteTime.reset();
        bot.drop();
        targetPos = newPos;
    }
}
