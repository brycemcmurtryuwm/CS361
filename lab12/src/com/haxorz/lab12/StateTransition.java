package com.haxorz.lab12;

public class StateTransition
{
    public final DoorState CurrentState;
    public final DoorCmd Command;

    public StateTransition(DoorState currentState, DoorCmd command)
    {
        CurrentState = currentState;
        Command = command;
    }


    @Override
    public int hashCode() {
        return 17 + 7 * CurrentState.hashCode() + 31 * Command.hashCode();
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
