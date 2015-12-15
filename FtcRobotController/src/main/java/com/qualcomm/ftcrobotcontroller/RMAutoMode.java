package com.qualcomm.ftcrobotcontroller;

import java.util.ArrayList;
import java.util.List;

public abstract class RMAutoMode extends RMOpMode {

    protected List<State> stateList =  new ArrayList<State>();
    protected State currentState;

    protected abstract void setStateList();

}
