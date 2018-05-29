package patterra.bp.invention.config.sm;

public enum Events {
    // ввод заявки
    ENTER_INVENTION,
    SEND_CONFIRMATION,
    PREPARE_TO_SUBMIT,

    // общие события для формальной и ЭС
    SUBMIT_APPLICATION,
    REGISTER_OFFICE_REQUEST,
    REPLY_TO_OFFICE_REQUEST,
    EXTEND_REQUEST,
    WITHDRAW,
    RESTORE,

    // формальная экспертиза
    FORMAL_COMPLETE,

    // экспертиза по существу
    REGISTER_GRANTING,
    REGISTER_DENY,
    SUBMIT_APPEAL,
    PATENT_RECEIVE,


    // поддержание патента
    RECEIVE_PATENT,
    SEND_CERTIFICATE,
    PAY_ANNUAL_FEE,
    PROLONG_PATENT,

    // прекращение делопроизводства
    STOP
}
