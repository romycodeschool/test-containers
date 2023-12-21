using orders_service.OrderDetails.model;

namespace orders_service.OrderDetails.Services.Interfaces;

public interface IOrderDetailQueryService
{
    Task<IEnumerable<OrderDetail>> GetAllOrderDetails();
    Task<OrderDetail> GetOrderDetailById(int id);
}