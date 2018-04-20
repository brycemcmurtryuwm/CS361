package com.haxorz.lab12;

public class StateTransition
{
    public final DoorState CurrentState;
    public final GarageCmd Command;

    public StateTransition(DoorState currentState, GarageCmd command)
    {
        CurrentState = currentState;
        Command = command;
    }



    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof StateTransition))
            return false;

        StateTransition other = (StateTransition)obj ;
        return this.CurrentState == other.CurrentState && this.Command == other.Command;
    }

}
