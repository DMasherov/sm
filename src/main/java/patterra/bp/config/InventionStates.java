package patterra.bp.config;

public enum InventionStates {
    PROCESSING,
        ENTERING_APPLICATION,
        CLIENT_INFORMED,
        READY_TO_SUBMIT,

    FORMAL,
        FORMAL_IN_PROGRESS,
        OFFICE_REQUEST_ON_FORMAL,
        APPLICATION_WITHDRAWED_ON_FORMAL,
        FORMAL_COMPLETED,

    ESSENTIAL,
        ESSENTIAL_IN_PROGRESS,
        OFFICE_REQUEST_ON_ESSENTIAL,
        PATENT_DENIED,
        PATENT_GRANTED,
        APPLICATION_WITHDRAWED_ON_ESSENTIAL,
        READY_TO_RECEIVE,

    PATENT_READY,

    END,
    // HISTORY // TODO: persist state machine?
}
