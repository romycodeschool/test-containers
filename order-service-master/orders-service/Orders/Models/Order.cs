using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using orders_service.OrderDetails.model;

namespace orders_service.Orders.model;

public class Order
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public int Id { get; set; }

    [Required]
    public string CustomerId { get; set; }

    [Required]
    public OrderStatus OrderStatus { get; set; }

    [Required]
    public DateTime CreatedAt { get; set; }

    public virtual List<OrderDetail> OrderDetails { get; set; }
}