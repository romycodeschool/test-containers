using orders_service.OrderDetails.model;
using orders_service.OrderDetails.Repository.Interfaces;
using orders_service.OrderDetails.Services.Interfaces;
using orders_service.System.Constants;
using orders_service.System.Exceptions;

namespace orders_service.OrderDetails.Services;

public class OrderDetailQueryService : IOrderDetailQueryService
{
    private IOrderDetailRepository _orderDetailRepository;

    public OrderDetailQueryService(IOrderDetailRepository orderDetailRepository)
    {
        _orderDetailRepository = orderDetailRepository;
    }
    
    public async Task<IEnumerable<OrderDetail>> GetAllOrderDetails()
    {
        IEnumerable<OrderDetail> orderDetails = await _orderDetailRepository.GetAllAsync();

        if (orderDetails.Count() == 0)
        {
            throw new ItemsDoNotExist(Constants.NO_ORDER_DETAILS_EXIST);
        }

        return orderDetails;
    }

    public async Task<OrderDetail> GetOrderDetailById(int id)
    {
        OrderDetail orderDetail = await _orderDetailRepository.GetByIdAsync(id);

        if (orderDetail == null)
        {
            throw new ItemDoesNotExist(Constants.ORDER_DETAIL_DOES_NOT_EXIST);
        }

        return orderDetail;
    }
}