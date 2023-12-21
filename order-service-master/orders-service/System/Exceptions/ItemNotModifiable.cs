namespace orders_service.System.Exceptions;

public class ItemNotModifiable : Exception
{
    public ItemNotModifiable(string? message) : base(message)
    {
    }
}