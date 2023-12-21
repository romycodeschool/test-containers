using orders_service.OrderDetails.Dtos;
using orders_service.OrderDetails.model;

namespace orders_service.OrderDetails.Services.Interfaces;

public interface IOrderDetailCommandService
{
    Task<OrderDetail> CreateOrderDetail(CreateOrderDetailRequest orderDetailRequest);
    Task<OrderDetail> UpdateOrderDetail(int id, UpdateOrderDetailRequest orderDetailRequest);
    Task DeleteOrderDetail(int id);
}