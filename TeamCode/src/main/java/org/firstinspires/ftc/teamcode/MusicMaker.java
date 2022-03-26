package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

public class MusicMaker {
    VibeBotHardware bot = new VibeBotHardware();
    private final ElapsedTime noteTimer = new ElapsedTime();
    NotesLibrary notes = new NotesLibrary();
    double lastError = 0;
    double integral = 0;
    public static PIDCoefficients pidCoeffs = new PIDCoefficients(7,0.0000005,.00001);
    public PIDCoefficients pidGains = new PIDCoefficients(0,0,0);
    public double targetPos;
    private final ElapsedTime PIDTimer  = new ElapsedTime();
    public Double[] notesOrder;
    public Double[] noteTime;
    private enum State {
        MOVE_TO_NOTE,
        STRIKE,
        STOP
    }
    private MusicMaker.State currentState;
    private int index = 0;

    public void init(){
        bot.init(hardwareMap);
        targetPos = notesOrder[0];
        newState(MusicMaker.State.MOVE_TO_NOTE);
    }

    public void init_loop(){
        pid(targetPos);
        telemetryBlock();
    }

    public void start(){noteTimer.reset();}

    public void loop(){
        pid(targetPos);
        telemetryBlock();


        switch (currentState){
            case MOVE_TO_NOTE:
                if(notesOrder.length >= index + 1){
                    targetPos = notesOrder[index];
                    newState(MusicMaker.State.STRIKE);
                } else {
                    newState(MusicMaker.State.STOP);
                }
                break;
            case STRIKE:
                if(noteTime[index] <= noteTimer.time()){
                    if (noteTime[index] + .2 <= noteTimer.time()){
                        bot.mallet1.setPosition(.45);
                        index += 1;
                        newState(MusicMaker.State.MOVE_TO_NOTE);
                        noteTimer.reset();
                    } else {
                        bot.mallet1.setPosition(notes.lower);
                    }
                } else {
                    break;
                }
                break;
            case STOP:
                bot.cart1.setVelocity(0);
                break;
        }
    }

    public void newState(MusicMaker.State newState){
        currentState = newState;
    }
    public void pid(double targetPos){
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
    }
    public void telemetryBlock(){
        telemetry.addData("servo position: ", bot.mallet1.getPosition());
        telemetry.addData("cart1 pos: ", bot.cart1.getCurrentPosition());
        telemetry.addData("cart1 target pos: ", targetPos);
        telemetry.addData("error: ", targetPos - bot.cart1.getCurrentPosition());
        telemetry.addData("Note Time", noteTimer.time());
        telemetry.addData("0", currentState.toString());
        telemetry.update();
    }
}
