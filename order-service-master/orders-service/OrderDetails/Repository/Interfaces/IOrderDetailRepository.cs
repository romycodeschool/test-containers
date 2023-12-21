using orders_service.OrderDetails.Dtos;
using orders_service.OrderDetails.model;

namespace orders_service.OrderDetails.Repository.Interfaces;

public interface IOrderDetailRepository
{
    Task<IEnumerable<OrderDetail>> GetAllAsync();
    Task<OrderDetail> GetByIdAsync(int id);
    Task<OrderDetail> CreateAsync(CreateOrderDetailRequest orderDetailRequest);
    Task<OrderDetail> UpdateAsync(int id, UpdateOrderDetailRequest orderDetailRequest);
    Task DeleteAsync(int id);
}