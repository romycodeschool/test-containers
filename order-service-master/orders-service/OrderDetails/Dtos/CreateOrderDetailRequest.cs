using System.ComponentModel.DataAnnotations;

namespace orders_service.OrderDetails.Dtos;

public class CreateOrderDetailRequest
{
    [Required]
    public int ProductId { get; set; }

    [Required]
    public int OrderId { get; set; }
    
    [Required]
    public int Quantity { get; set; }

    [Required]
    public decimal Price { get; set; }
}