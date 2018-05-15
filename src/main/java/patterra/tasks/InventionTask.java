package patterra.tasks;

import patterra.bp.config.InventionEvents;
import patterra.domain.Invention;

public class InventionTask extends Reminder {
    private InventionEvents event;
    private Invention invention;

    public InventionEvents getEvent() {
        return event;
    }

    public void setEvent(InventionEvents event) {
        this.event = event;
    }

    public Invention getInvention() {
        return invention;
    }

    public void setInvention(Invention invention) {
        this.invention = invention;
    }
}
