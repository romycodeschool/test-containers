using Microsoft.AspNetCore.Mvc;
using orders_service.OrderDetails.Dtos;
using orders_service.OrderDetails.model;

namespace orders_service.OrderDetails.Controllers.Interfaces;

[ApiController]
[Route("api/v1/[controller]")]
public abstract class OrderDetailsApiController : ControllerBase
{
    [HttpGet("all")]
    [ProducesResponseType(statusCode:200,type:typeof(IEnumerable<OrderDetail>))]
    [ProducesResponseType(statusCode:400,type:typeof(String))]
    public abstract Task<ActionResult<IEnumerable<OrderDetail>>> GetAllOrderDetails();
    
    [HttpGet("order-detail/{id}")]
    [ProducesResponseType(statusCode:200,type:typeof(OrderDetail))]
    [ProducesResponseType(statusCode:400,type:typeof(String))]
    public abstract Task<ActionResult<OrderDetail>> GetOrderDetailById([FromRoute]int id);
    
    [HttpPost("create")]
    [ProducesResponseType(statusCode:200,type:typeof(OrderDetail))]
    [ProducesResponseType(statusCode:400,type:typeof(String))]
    public abstract Task<ActionResult<OrderDetail>> CreateOrderDetail([FromBody]CreateOrderDetailRequest orderDetailRequest);
    
    [HttpPut("update")]
    [ProducesResponseType(statusCode:200,type:typeof(OrderDetail))]
    [ProducesResponseType(statusCode:400,type:typeof(String))]
    public abstract Task<ActionResult<OrderDetail>> UpdateOrderDetail([FromQuery]int id, [FromBody]UpdateOrderDetailRequest orderDetailRequest);
    
    [HttpDelete("delete")]
    [ProducesResponseType(statusCode:200,type:typeof(String))]
    [ProducesResponseType(statusCode:400,type:typeof(String))]
    public abstract Task<ActionResult> DeleteOrderDetail([FromQuery]int id);
}