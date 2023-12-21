using Microsoft.EntityFrameworkCore;
using AutoMapper;
using orders_service.Data;
using orders_service.Orders.Dtos;
using orders_service.Orders.model;
using orders_service.Orders.Repository.Interfaces;

namespace orders_service.Orders.Repository;

public class OrderRepository : IOrderRepository
{
    private AppDbContext _context;
    private IMapper _mapper;

    public OrderRepository(AppDbContext context, IMapper mapper)
    {
        _context = context;
        _mapper = mapper;
    }
    
    public async Task<IEnumerable<Order>> GetAllAsync()
    {
        return await _context.Orders
            .Include(o => o.OrderDetails)
            .ToListAsync();
    }

    public async Task<Order> GetByIdAsync(int id)
    {
        return (await _context.Orders
            .Include(o => o.OrderDetails)
            .FirstOrDefaultAsync(o => o.Id == id))!;
    }

    public async Task<Order> CreateAsync(CreateOrderRequest orderRequest)
    {
        Order order = _mapper.Map<Order>(orderRequest);
        order.OrderStatus = OrderStatus.Pending;
        _context.Orders.Add(order);
        await _context.SaveChangesAsync();
        return order;
    }

    public async Task<Order> UpdateAsync(int id, UpdateOrderRequest orderRequest)
    {
        Order order = await _context.Orders.FindAsync(id);

        Order mapped = _mapper.Map<Order>(orderRequest);

        order.CustomerId = mapped.CustomerId;
        order.OrderStatus = mapped.OrderStatus;
        order.OrderDetails = mapped.OrderDetails;
        
        _context.Orders.Update(order);
        await _context.SaveChangesAsync();
        return order;
    }

    public async Task DeleteAsync(int id)
    {
        Order order = await _context.Orders.FindAsync(id);
        _context.Orders.Remove(order);
        await _context.SaveChangesAsync();
    }
}