using orders_service.Orders.Repository.Interfaces;
using orders_service.Orders.Services.Interfaces;
using orders_service.Orders.model;
using orders_service.System.Constants;
using orders_service.System.Exceptions;

namespace orders_service.Orders.Services;

public class OrderQueryService : IOrderQueryService
{
    public IOrderRepository _orderRepository;

    public OrderQueryService(IOrderRepository orderRepository)
    {
        _orderRepository = orderRepository;
    }
    
    public async Task<IEnumerable<Order>> GetAllOrders()
    {
        IEnumerable<Order> orders = await _orderRepository.GetAllAsync();

        if (orders.Count() == 0)
        {
            throw new ItemsDoNotExist(Constants.NO_ORDERS_EXIST);
        }

        return orders;
    }

    public async Task<Order> GetOrderById(int id)
    {
        Order order = await _orderRepository.GetByIdAsync(id);

        if (order == null)
        {
            throw new ItemDoesNotExist(Constants.ORDER_DOES_NOT_EXIST);
        }

        return order;
    }
}