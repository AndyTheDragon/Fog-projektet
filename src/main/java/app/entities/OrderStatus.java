package app.entities;

public enum OrderStatus
{
    UNASSIGNED,
    ASSIGNED,
    CALCULATING,
    OFFER_SENT,
    OFFER_ACCEPTED,
    OFFER_REJECTED,
    PAYMENT_REQUESTED,
    PAID,
    COMPLETED,
    ERROR
}
