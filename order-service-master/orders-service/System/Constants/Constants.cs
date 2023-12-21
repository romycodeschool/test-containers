namespace orders_service.System.Constants;

public static class Constants
{
    #region ORDERS
    
    public const string NO_ORDERS_EXIST = "There are no orders.";
    public const string ORDER_DOES_NOT_EXIST = "This order does not exist.";
    public const string ORDER_ALREADY_PROCESSING_OR_COMPLETED =
        "Can't update an order that's already being processed, completed or cancelled!";
    public const string ORDER_DELETED = "Order successfully deleted!";
    
    #endregion
    
    #region ORDER_DETAILS

    public const string NO_ORDER_DETAILS_EXIST = "There are no order details.";
    public const string ORDER_DETAIL_DOES_NOT_EXIST = "This order detail does not exist.";
    public const string ORDER_DETAIL_DELETED = "Order detail successfully deleted!";

    #endregion
}