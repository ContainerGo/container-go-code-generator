package vn.containergo.domain.enumeration;

/**
 * The ContainerState enumeration.
 */
public enum ContainerState {
    NEW,
    BIDDING,
    WAITING_FOR_OFFERS,
    OFFER_CHOSEN,
    SHIPMENT_PLAN_SENT,
    SHIPMENT_IN_PROGRESS,
    SHIPMENT_SUCCESS,
    SHIPMENT_FAIL,
    SHIPMENT_POD_APPROVED,
}
