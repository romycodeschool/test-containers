using orders_service.Orders.Dtos;
using orders_service.Orders.model;

namespace orders_service.Orders.Services.Interfaces;

public interface IOrderCommandService
{
    Task<Order> CreateOrder(CreateOrderRequest orderRequest);
    Task<Order> UpdateOrder(int id, UpdateOrderRequest orderRequest);
    Task DeleteOrder(int id);
}