package com.haxorz.lab12;

import java.util.HashMap;

public class StateMachine {

    HashMap<StateTransition, DoorState> transitions;
    public DoorState CurrentState;

    public StateMachine()
    {
        CurrentState = DoorState.CLOSED;
        transitions = new HashMap<StateTransition, DoorState>()
        {
            { new StateTransition(DoorState.CLOSED, GarageCmd.ClickStart), DoorState.OPENING },
            { new StateTransition(DoorState.OPENING, GarageCmd.ClickStop), DoorState.STOPPED_WAS_OPENING },
            { new StateTransition(DoorState.OPENING, GarageCmd.LimitStop), DoorState.OPEN },
            { new StateTransition(DoorState.STOPPED_WAS_OPENING, GarageCmd.ClickStart), DoorState.CLOSING },
            { new StateTransition(DoorState.STOPPED_WAS_CLOSING, GarageCmd.ClickStart), DoorState.OPENING },
            { new StateTransition(DoorState.CLOSING, GarageCmd.ClickStop), DoorState.STOPPED_WAS_CLOSING },
            { new StateTransition(DoorState.CLOSING, GarageCmd.LimitStop), DoorState.CLOSED },
            { new StateTransition(DoorState.OPEN, GarageCmd.LimitStop), DoorState.OPEN },
                { new StateTransition(DoorState.OPEN, GarageCmd.ClickStart), DoorState.CLOSING }
        };
    }

}

