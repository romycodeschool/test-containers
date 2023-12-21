using Microsoft.AspNetCore.Mvc;
using orders_service.OrderDetails.Controllers.Interfaces;
using orders_service.OrderDetails.Dtos;
using orders_service.OrderDetails.model;
using orders_service.OrderDetails.Services.Interfaces;
using orders_service.System.Constants;
using orders_service.System.Exceptions;

namespace orders_service.OrderDetails.Controllers;

public class OrderDetailsController : OrderDetailsApiController
{
    public IOrderDetailCommandService _orderDetailCommandService;
    public IOrderDetailQueryService _orderDetailQueryService;

    public OrderDetailsController(IOrderDetailCommandService orderDetailCommandService, IOrderDetailQueryService orderDetailQueryService)
    {
        _orderDetailCommandService = orderDetailCommandService;
        _orderDetailQueryService = orderDetailQueryService;
    }
    
    public override async Task<ActionResult<IEnumerable<OrderDetail>>> GetAllOrderDetails()
    {
        try
        {
            IEnumerable<OrderDetail> orderDetails = await _orderDetailQueryService.GetAllOrderDetails();

            return Ok(orderDetails);
        }
        catch (ItemsDoNotExist ex)
        {
            return BadRequest(ex.Message);
        }
    }

    public override async Task<ActionResult<OrderDetail>> GetOrderDetailById(int id)
    {
        try
        {
            OrderDetail orderDetail = await _orderDetailQueryService.GetOrderDetailById(id);

            return Ok(orderDetail);
        }
        catch (ItemDoesNotExist ex)
        {
            return BadRequest(ex.Message);
        }
    }

    public override async Task<ActionResult<OrderDetail>> CreateOrderDetail(CreateOrderDetailRequest orderDetailRequest)
    {
        try
        {
            OrderDetail orderDetail = await _orderDetailCommandService.CreateOrderDetail(orderDetailRequest);

            return Ok(orderDetail);
        }
        catch (ItemDoesNotExist ex)
        {
            return BadRequest(ex.Message);
        }
    }

    public override async Task<ActionResult<OrderDetail>> UpdateOrderDetail(int id, UpdateOrderDetailRequest orderDetailRequest)
    {
        try
        {
            OrderDetail orderDetail = await _orderDetailCommandService.UpdateOrderDetail(id, orderDetailRequest);

            return Ok(orderDetail);
        }
        catch (ItemDoesNotExist ex)
        {
            return BadRequest(ex.Message);
        }
    }

    public override async Task<ActionResult> DeleteOrderDetail(int id)
    {
        try
        {
            await _orderDetailCommandService.DeleteOrderDetail(id);

            return Ok(Constants.ORDER_DETAIL_DELETED);
        }
        catch (ItemDoesNotExist ex)
        {
            return BadRequest(ex.Message);
        }
    }
}