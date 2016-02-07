package com.qualcomm.ftcrobotcontroller.control;

import com.qualcomm.robotcore.hardware.Gamepad;

public class Control {

    private ControllerInput currentInput;
    private ControllerInput prevInput;

    public Control(Gamepad g1, Gamepad g2){
        currentInput =  new ControllerInput(g1, g2);
        prevInput = currentInput;
    }

    public void update(Gamepad g1, Gamepad g2){
        prevInput =  currentInput;
        currentInput =  new ControllerInput(g1, g2);
    }

    public boolean button(Controller c, Button b){
        return currentInput.button(c, b);
    }

    public boolean buttonPressed(Controller c, Button b){
        boolean prev = prevInput.button(c, b);
        boolean curr = currentInput.button(c, b);
        return curr && !prev;
    }

    public boolean buttonHeld(Controller c, Button b){
        boolean prev = prevInput.button(c, b);
        boolean curr = currentInput.button(c, b);
        return curr && prev;
    }

    public boolean buttonReleased(Controller c, Button b){
        boolean prev = prevInput.button(c, b);
        boolean curr = currentInput.button(c, b);
        return !curr && prev;
    }

    public double joystickValue(Controller c, Joystick j, Axis a){
        return currentInput.joystickValue(c, j, a);
    }

    public double triggerValue(Controller c, Trigger t){
        return currentInput.triggerValue(c, t);
    }

}

class ControllerInput{

    private Gamepad game1;
    private Gamepad game2;

    public ControllerInput(Gamepad g1, Gamepad g2){
        game1 =  g1;
        game2 =  g2;
    }

    public double joystickValue(Controller c, Joystick j, Axis a){
        Gamepad g = getController(c);
        switch(j){
            case J_LEFT:
                switch(a){
                    case A_X:
                        return g.left_stick_x;
                    case A_Y:
                        return g.left_stick_y;
                }
            case J_RIGHT:
                switch(a){
                    case A_X:
                        return g.right_stick_x;
                    case A_Y:
                        return g.right_stick_y;
                }
        }
        return -1;
    }

    public double triggerValue(Controller c, Trigger t){
        Gamepad g = getController(c);
        switch (t){
            case T_LEFT:
                return g.left_trigger;
            case T_RIGHT:
                return g.right_trigger;
        }
        return -1;
    }

    public boolean button(Controller c, Button b){
        Gamepad g = getController(c);
        switch(b){
            case BUTTON_A:
                return g.a;
            case BUTTON_B:
                return g.b;
            case BUTTON_X:
                return g.x;
            case BUTTON_Y:
                return g.y;
            case BUTTON_LB:
                return g.left_bumper;
            case BUTTON_RB:
                return g.right_bumper;
            case BUTTON_BACK:
                return g.back;
            case BUTTON_START:
                return g.start;
            case BUTTON_JOYL:
                return g.left_stick_button;
            case BUTTON_JOYR:
                return g.right_stick_button;
        }
        return false;
    }

    private Gamepad getController(Controller c){
        switch (c){
            case C_ONE:
                return game1;
            case C_TWO:
                return game2;
        }
        return null; //Technically very bad practice...
    }

}