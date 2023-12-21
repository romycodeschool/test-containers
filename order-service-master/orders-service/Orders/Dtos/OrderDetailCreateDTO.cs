using System.ComponentModel.DataAnnotations;

namespace orders_service.Orders.Dtos;

public class OrderDetailCreateDTO
{
    [Required]
    public string ProductId { get; set; }
    
    [Required]
    public int Quantity { get; set; }
    
    [Required]
    public decimal Price { get; set; }
}