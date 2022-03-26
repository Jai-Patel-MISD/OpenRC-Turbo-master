package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Minecraft")
//@Disabled
public class Minecraft extends OpMode {
    VibeBotHardware bot = new VibeBotHardware();
    private final ElapsedTime noteTimer = new ElapsedTime();
    NotesLibrary notes = new NotesLibrary();
    public static double BPM = 60;
    private final double quarterNote = 60/BPM;
    private final double halfNote = quarterNote * 2;
    private final double wholeNote = quarterNote * 4;
    double lastError = 0;
    double integral = 0;
    public static PIDCoefficients pidCoeffs = new PIDCoefficients(7,0.0000005,.00001);
    public PIDCoefficients pidGains = new PIDCoefficients(0,0,0);
    public double targetPos;
    private final ElapsedTime PIDTimer  = new ElapsedTime();
    private final Double[] notesOrder = {
            notes.b3, notes.a3, notes.e2, //1 and last notes tied
            notes.g2, //2
            notes.b3, notes.a3, notes.e2,// 3 last note tied
            notes.d2//4
    };
    private final Double[] noteTime = {
            halfNote, quarterNote, halfNote,//1 with tie
            halfNote+quarterNote,//2
            halfNote, quarterNote, halfNote,//3 tied
            halfNote + quarterNote//4
    };
    boolean firstIf = true;
    private enum State {
        MOVE_TO_NOTE,
        STRIKE,
        STOP
    }
    private State currentState;
    private int index = 1;

    @Override
    public void init(){
        bot.init(hardwareMap);
        targetPos = bot.notes.b3;
        newState(State.MOVE_TO_NOTE);
    }

    @Override
    public void init_loop(){
        pid(targetPos);
        telemetryBlock();
    }
    @Override
    public void start(){noteTimer.reset();}

    @Override
    public void loop(){
        pid(targetPos);
        telemetryBlock();
        if (firstIf){
            bot.mallet1.setPosition(.66);
            if(noteTimer.time()>.2){
                bot.mallet1.setPosition(.45);
                noteTimer.reset();
                firstIf = false;
            }
        } else {
            switch (currentState) {
                case MOVE_TO_NOTE:
                    if (notesOrder.length >= index + 1) {
                        targetPos = notesOrder[index];
                        newState(State.STRIKE);
                    } else {
                        newState(State.STOP);
                    }
                    break;
                case STRIKE:
                    if (noteTime[index] <= noteTimer.time()) {
                        if (noteTime[index] + .2 <= noteTimer.time()) {
                            bot.mallet1.setPosition(.45);
                            index += 1;
                            newState(State.MOVE_TO_NOTE);
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
    }
    @Override
    public void stop(){


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
