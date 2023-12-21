using orders_service.Orders.Dtos;
using orders_service.Orders.model;

namespace orders_service.Orders.Repository.Interfaces;

public interface IOrderRepository
{
    Task<IEnumerable<Order>> GetAllAsync();
    Task<Order> GetByIdAsync(int id);
    Task<Order> CreateAsync(CreateOrderRequest orderRequest);
    Task<Order> UpdateAsync(int id, UpdateOrderRequest orderRequest);
    Task DeleteAsync(int id);
}