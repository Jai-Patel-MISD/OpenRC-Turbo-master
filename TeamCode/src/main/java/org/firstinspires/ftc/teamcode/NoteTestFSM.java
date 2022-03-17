package org.firstinspires.ftc.teamcode;

import android.annotation.SuppressLint;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;


import java.util.Arrays;
import java.util.List;

@TeleOp(name = "Note Test")
//@Disabled
public class NoteTestFSM extends OpMode {
    VibeBotHardware bot = new VibeBotHardware();
    private final ElapsedTime noteTimer = new ElapsedTime();
    NotesLibrary notes = new NotesLibrary();
    public static double BPM = 60;
    private final double quarterNote = BPM/60;
    double lastError = 0;
    double integral = 0;
    public static PIDCoefficients pidCoeffs = new PIDCoefficients(3.5,0.0000005,.00001);
    public PIDCoefficients pidGains = new PIDCoefficients(0,0,0);
    public double targetPos;
    private final ElapsedTime PIDTimer  = new ElapsedTime();
    private final List<Double> notesOrder = Arrays.asList(notes.e2, notes.d2, notes.c2);
    private final List<Double> noteTime = Arrays.asList(quarterNote, quarterNote, quarterNote);
    private enum State {
        MOVE_TO_NOTE,
        STRIKE,
        STOP
    }
    private State currentState;

    @Override
    public void init(){
        bot.init(hardwareMap);
        targetPos = bot.notes.e2;
        newState(State.MOVE_TO_NOTE);
    }

    @Override
    public void init_loop(){
        pid(targetPos);
        telemetryBlock();
    }
    @Override
    public void start(){noteTimer.reset();}
    @SuppressLint("DefaultLocale")
    @Override
    public void loop(){
        pid(targetPos);
        telemetryBlock();

        switch (currentState){
            case MOVE_TO_NOTE:
                if(!notesOrder.isEmpty()){
                    targetPos = notesOrder.get(0);
                    notesOrder.remove(0);
                    newState(State.STRIKE);
                } else {
                    newState(State.STOP);
                }
                break;
            case STRIKE:
                if(noteTime.get(0) <= noteTimer.time()){
                    if (noteTime.get(0) + noteTime.get(0)/2 <= noteTimer.time()){
                        bot.mallet1.setPosition(.66);
                        noteTime.remove(0);
                        noteTimer.reset();
                        newState(State.MOVE_TO_NOTE);
                    } else {
                        bot.mallet1.setPosition(1);
                    }
                }
                break;
            case STOP:
                bot.cart1.setVelocity(0);
                break;
        }
    }
    @Override
    public void stop(){
        bot.cart1.setVelocity(0);
    }
    public void newState(State newState){
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