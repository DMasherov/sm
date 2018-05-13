package patterra.bp.statemachineconfig;

/*
+--------------------------------------------+
|                    MAIN                    |
+--------------------------------------------+
|                                            |
|  PREPARE -> FORMAL -> ESSENTIAL -> PATENT  |
|                                            |
+--------------------------------------------+
 */
public enum InventionStates {
    MAIN,

    PREPARE,
        ENTERING_APPLICATION,
        CLIENT_IS_INFORMED,
        READY_TO_SUBMIT,

    FORMAL,
        FORMAL_IN_PROGRESS,
        OFFICE_REQUEST_ON_FORMAL,
        APPLICATION_WITHDRAWED_ON_FORMAL,
        FORMAL_IS_COMPLETE,

    ESSENTIAL,
        ESSENTIAL_IN_PROGRESS,
        OFFICE_REQUEST_ON_ESSENTIAL,
        PATENT_DENIED,
        PATENT_GRANTED,
        APPLICATION_WITHDRAWED_ON_ESSENTIAL,
        READY_TO_RECEIVE,

    PATENT_IS_RECEIVED,

    // HISTORY // TODO: persist state machine?
}
