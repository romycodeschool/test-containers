using orders_service.Orders.Dtos;
using orders_service.Orders.Repository.Interfaces;
using orders_service.Orders.Services.Interfaces;
using orders_service.Orders.model;
using orders_service.System.Constants;
using orders_service.System.Exceptions;

namespace orders_service.Orders.Services;

public class OrderCommandService : IOrderCommandService
{
    public IOrderRepository _orderRepository;

    public OrderCommandService(IOrderRepository orderRepository)
    {
        _orderRepository = orderRepository;
    }
    
    public async Task<Order> CreateOrder(CreateOrderRequest orderRequest)
    {
        return await _orderRepository.CreateAsync(orderRequest);
    }

    public async Task<Order> UpdateOrder(int id, UpdateOrderRequest orderRequest)
    {
        Order order = await _orderRepository.GetByIdAsync(id);

        if (order == null)
        {
            throw new ItemDoesNotExist(Constants.ORDER_DOES_NOT_EXIST);
        }
        
        if (order.OrderStatus > 0)
        {
            throw new ItemNotModifiable(Constants.ORDER_ALREADY_PROCESSING_OR_COMPLETED);
        }

        order = await _orderRepository.UpdateAsync(id, orderRequest);
        return order;
    }

    public async Task DeleteOrder(int id)
    {
        Order order = await _orderRepository.GetByIdAsync(id);

        if (order == null)
        {
            throw new ItemDoesNotExist(Constants.ORDER_DOES_NOT_EXIST);
        }

        await _orderRepository.DeleteAsync(id);
    }
}