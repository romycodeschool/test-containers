using System.ComponentModel.DataAnnotations;

namespace orders_service.Orders.Dtos;

public class CreateOrderRequest
{
    [Required]
    public string CustomerId { get; set; }

    [Required] 
    public DateTime CreatedAt { get; set; }
    
    public List<OrderDetailCreateDTO> OrderDetails { get; set; }
}