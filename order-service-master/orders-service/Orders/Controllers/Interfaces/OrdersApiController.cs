using Microsoft.AspNetCore.Mvc;
using orders_service.Orders.Dtos;
using orders_service.Orders.model;

namespace orders_service.Orders.Controllers.Interfaces
{
    [ApiController]
    [Route("api/v1/[controller]")]
    public abstract class OrdersApiController : ControllerBase
    {
        [HttpGet("all")]
        [ProducesResponseType(statusCode:200,type:typeof(IEnumerable<Order>))]
        [ProducesResponseType(statusCode:400,type:typeof(String))]
        public abstract Task<ActionResult<IEnumerable<Order>>> GetOrders();
        
        [HttpGet("order/{id}")]
        [ProducesResponseType(statusCode:200,type:typeof(Order))]
        [ProducesResponseType(statusCode:400,type:typeof(String))]
        public abstract Task<ActionResult<Order>> GetOrderById([FromRoute]int id);
        
        [HttpPost("create")]
        [ProducesResponseType(statusCode:200,type:typeof(Order))]
        [ProducesResponseType(statusCode:400,type:typeof(String))]
        public abstract Task<ActionResult<Order>> CreateOrder([FromBody]CreateOrderRequest orderRequest);
        
        [HttpPut("update")]
        [ProducesResponseType(statusCode:200,type:typeof(Order))]
        [ProducesResponseType(statusCode:400,type:typeof(String))]
        public abstract Task<ActionResult<Order>> UpdateOrder([FromQuery]int id, [FromBody]UpdateOrderRequest orderRequest);
        
        [HttpDelete("delete")]
        [ProducesResponseType(statusCode:200,type:typeof(String))]
        [ProducesResponseType(statusCode:400,type:typeof(String))]
        public abstract Task<IActionResult> DeleteOrder([FromQuery]int id);
    }
}
