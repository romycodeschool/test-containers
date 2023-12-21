using Microsoft.AspNetCore.Mvc;
using orders_service.Orders.Dtos;
using orders_service.Orders.Services.Interfaces;
using orders_service.Orders.Controllers.Interfaces;
using orders_service.Orders.model;
using orders_service.System.Constants;
using orders_service.System.Exceptions;

namespace orders_service.Orders.Controllers
{
    public class OrdersController : OrdersApiController
    {
        private IOrderCommandService _orderCommandService;
        private IOrderQueryService _orderQueryService;

        public OrdersController(IOrderCommandService orderCommandService, IOrderQueryService orderQueryService)
        {
            _orderCommandService = orderCommandService;
            _orderQueryService = orderQueryService;
        }
        
        public override async Task<ActionResult<IEnumerable<Order>>> GetOrders()
        {
            try
            {
                IEnumerable<Order> orders = await _orderQueryService.GetAllOrders();

                return Ok(orders);
            }
            catch (ItemsDoNotExist ex)
            {
                return BadRequest(ex.Message);
            }
        }
        
        public override async Task<ActionResult<Order>> GetOrderById(int id)
        {
            try
            {
                Order order = await _orderQueryService.GetOrderById(id);

                return Ok(order);
            }
            catch (ItemDoesNotExist ex)
            {
                return BadRequest(ex.Message);
            }
        }
        
        public override async Task<ActionResult<Order>> CreateOrder(CreateOrderRequest orderRequest)
        {
            Order order = await _orderCommandService.CreateOrder(orderRequest);

            return Ok(order);
        }
        
        public override async Task<ActionResult<Order>> UpdateOrder(int id, UpdateOrderRequest orderRequest)
        {
            try
            {
                Order order = await _orderCommandService.UpdateOrder(id, orderRequest);

                return Ok(order);
            }
            catch (ItemDoesNotExist ex)
            {
                return BadRequest(ex.Message);
            }
            catch (ItemNotModifiable ex)
            {
                return BadRequest(ex.Message);
            }
        }
        
        public override async Task<IActionResult> DeleteOrder(int id)
        {
            try
            {
                await _orderCommandService.DeleteOrder(id);

                return Ok(Constants.ORDER_DELETED);
            }
            catch (ItemDoesNotExist ex)
            {
                return BadRequest(ex.Message);
            }
        }
    }
}
