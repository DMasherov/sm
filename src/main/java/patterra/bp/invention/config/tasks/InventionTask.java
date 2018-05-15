package patterra.bp.invention.config.tasks;

import patterra.bp.invention.config.sm.Events;
import patterra.bp.tasks.Task;
import patterra.domain.Invention;

public class InventionTask extends Task {
    private Events event;
    private Invention invention;

    public Events getEvent() {
        return event;
    }

    public void setEvent(Events event) {
        this.event = event;
    }

    public Invention getInvention() {
        return invention;
    }

    public void setInvention(Invention invention) {
        this.invention = invention;
    }
}
