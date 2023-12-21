using orders_service.OrderDetails.Dtos;
using orders_service.OrderDetails.model;
using orders_service.OrderDetails.Repository.Interfaces;
using orders_service.OrderDetails.Services.Interfaces;
using orders_service.Orders.Repository.Interfaces;
using orders_service.System.Constants;
using orders_service.System.Exceptions;

namespace orders_service.OrderDetails.Services;

public class OrderDetailCommandService : IOrderDetailCommandService
{
    private IOrderRepository _orderRepository;
    private IOrderDetailRepository _orderDetailRepository;

    public OrderDetailCommandService(IOrderDetailRepository orderDetailRepository, IOrderRepository orderRepository)
    {
        _orderDetailRepository = orderDetailRepository;
        _orderRepository = orderRepository;
    }
    
    public async Task<OrderDetail> CreateOrderDetail(CreateOrderDetailRequest orderDetailRequest)
    {
        if(await _orderRepository.GetByIdAsync(orderDetailRequest.OrderId) == null!)
        {
            throw new ItemDoesNotExist(Constants.ORDER_DOES_NOT_EXIST);
        }

        return await _orderDetailRepository.CreateAsync(orderDetailRequest);
    }

    public async Task<OrderDetail> UpdateOrderDetail(int id, UpdateOrderDetailRequest orderDetailRequest)
    {
        if (await _orderDetailRepository.GetByIdAsync(id) == null!)
        {
            throw new ItemDoesNotExist(Constants.ORDER_DETAIL_DOES_NOT_EXIST);
        }

        return await _orderDetailRepository.UpdateAsync(id, orderDetailRequest);
    }

    public async Task DeleteOrderDetail(int id)
    {
        if (await _orderDetailRepository.GetByIdAsync(id) == null!)
        {
            throw new ItemDoesNotExist(Constants.ORDER_DETAIL_DOES_NOT_EXIST);
        }

        await _orderDetailRepository.DeleteAsync(id);
    }
}