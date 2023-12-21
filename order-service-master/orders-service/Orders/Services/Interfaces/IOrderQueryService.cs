using orders_service.Orders.Dtos;
using orders_service.Orders.model;

namespace orders_service.Orders.Services.Interfaces;

public interface IOrderQueryService
{
    Task<IEnumerable<Order>> GetAllOrders();
    Task<Order> GetOrderById(int id);
}