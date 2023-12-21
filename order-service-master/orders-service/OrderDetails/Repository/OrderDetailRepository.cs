using Microsoft.EntityFrameworkCore;
using AutoMapper;
using orders_service.Data;
using orders_service.OrderDetails.Dtos;
using orders_service.OrderDetails.model;
using orders_service.OrderDetails.Repository.Interfaces;

namespace orders_service.OrderDetails.Repository;

public class OrderDetailRepository : IOrderDetailRepository
{
    private AppDbContext _context;
    private IMapper _mapper;

    public OrderDetailRepository(AppDbContext context, IMapper mapper)
    {
        _context = context;
        _mapper = mapper;
    }
    
    public async Task<IEnumerable<OrderDetail>> GetAllAsync()
    {
        return await _context.OrderDetail
            .Include(od => od.Order)
            .ToListAsync();
    }

    public async Task<OrderDetail> GetByIdAsync(int id)
    {
        return (await _context.OrderDetail
            .Include(od => od.Order)
            .FirstOrDefaultAsync(od => od.Id == id))!;
    }

    public async Task<OrderDetail> CreateAsync(CreateOrderDetailRequest orderDetailRequest)
    {
        OrderDetail orderDetail = _mapper.Map<OrderDetail>(orderDetailRequest);
        _context.OrderDetail.Add(orderDetail);
        await _context.SaveChangesAsync();
        return orderDetail;
    }

    public async Task<OrderDetail> UpdateAsync(int id, UpdateOrderDetailRequest orderDetailRequest)
    {
        OrderDetail orderDetail = (await _context.OrderDetail.FindAsync(id))!;
        
        OrderDetail mapped = _mapper.Map<OrderDetail>(orderDetailRequest);
        orderDetail.ProductId = mapped.ProductId;
        orderDetail.Quantity = mapped.Quantity;
        orderDetail.Price = mapped.Price;
        _context.OrderDetail.Update(orderDetail);
        await _context.SaveChangesAsync();
        return orderDetail;
    }

    public async Task DeleteAsync(int id)
    {
        OrderDetail orderDetail = (await _context.OrderDetail.FindAsync(id))!;
        _context.OrderDetail.Remove(orderDetail);
        await _context.SaveChangesAsync();
    }
}