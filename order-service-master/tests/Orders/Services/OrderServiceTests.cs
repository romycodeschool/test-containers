/*using AutoMapper;
using Microsoft.EntityFrameworkCore;
using Moq;
using orders_service.Data;
using orders_service.Orders.Dtos;
using orders_service.Orders.Services;
using orders_service.OrdersDetails.model;


namespace tests.Orders.Services;

public class OrderServiceTests
{
    private readonly Mock<AppDbContext> _dbContextMock;
    private readonly Mock<IMapper> _mapperMock;
    private readonly OrderService _orderService;
    private readonly List<Order> _orders;

    public OrderServiceTests()
    {
        // Initialize mocks
        _dbContextMock = new Mock<AppDbContext>(new DbContextOptions<AppDbContext>());
        _mapperMock = new Mock<IMapper>();

        // Setup fake data
        _orders = new List<Order>
        {
            new Order { Id = 1, CustomerId = "C1", OrderStatus = OrderStatus.Completed, CreatedAt = DateTime.UtcNow },
            new Order { Id = 2, CustomerId = "C2", OrderStatus = OrderStatus.Pending, CreatedAt = DateTime.UtcNow }
        };

        var ordersQueryable = _orders.AsQueryable();

        var mockSet = new Mock<DbSet<Order>>();
        mockSet.As<IQueryable<Order>>().Setup(m => m.Provider).Returns(ordersQueryable.Provider);
        mockSet.As<IQueryable<Order>>().Setup(m => m.Expression).Returns(ordersQueryable.Expression);
        mockSet.As<IQueryable<Order>>().Setup(m => m.ElementType).Returns(ordersQueryable.ElementType);
        mockSet.As<IQueryable<Order>>().Setup(m => m.GetEnumerator()).Returns(ordersQueryable.GetEnumerator());

        _dbContextMock.Setup(db => db.Orders).Returns(mockSet.Object);

        // Initialize the service with the mocks
        _orderService = new OrderService(_dbContextMock.Object, _mapperMock.Object);
    }

    [Fact]
    public async Task GetAllAsync_ReturnsAllOrders()
    {
        var orders = await _orderService.GetAllAsync();
        Assert.Equal(_orders.Count, orders.Count()); // Assuming you set up 2 test orders
    }

    [Fact]
    public async Task GetByIdAsync_ValidId_ReturnsOrder()
    {
        var order = await _orderService.GetByIdAsync(1);
        Assert.NotNull(order);
        Assert.Equal(1, order.Id);
    }

    [Fact]
    public async Task GetByIdAsync_InvalidId_ReturnsNull()
    {
        var order = await _orderService.GetByIdAsync(99);
        Assert.Null(order);
    }

    [Fact]
    public async Task CreateAsync_CreatesOrder()
    {
        // Arrange
        var createOrderRequest = new CreateOrderRequest { /* Populate fields #1# };
        var newOrder = new Order { /* Populate fields #1# };
        _mapperMock.Setup(m => m.Map<Order>(It.IsAny<CreateOrderRequest>())).Returns(newOrder);

        // Act
        var order = await _orderService.CreateAsync(createOrderRequest);

        // Assert
        _mapperMock.Verify(m => m.Map<Order>(It.IsAny<CreateOrderRequest>()), Times.Once);
        Assert.NotNull(order);
        _dbContextMock.Verify(db => db.Orders.Add(It.IsAny<Order>()), Times.Once);
        _dbContextMock.Verify(db => db.SaveChangesAsync(default), Times.Once);
    }

    [Fact]
    public async Task UpdateAsync_ValidId_UpdatesAndReturnsOrder()
    {
        var updatedOrder = new Order { Id = 1, CustomerId = "C1-Updated", OrderStatus = OrderStatus.Cancelled, CreatedAt = DateTime.UtcNow };
        
        var order = await _orderService.UpdateAsync(1, updatedOrder);

        Assert.NotNull(order);
        Assert.Equal("C1-Updated", order.CustomerId);
        Assert.Equal(OrderStatus.Cancelled, order.OrderStatus);
        _dbContextMock.Verify(db => db.SaveChangesAsync(default), Times.Once);
    }

    [Fact]  
    public async Task UpdateAsync_InvalidId_ReturnsNull()
    {
        var nonExistingOrder = new Order { Id = 99, CustomerId = "C99", OrderStatus = OrderStatus.Cancelled, CreatedAt = DateTime.UtcNow };

        var order = await _orderService.UpdateAsync(99, nonExistingOrder);

        Assert.Null(order);
    }

    [Fact]
    public async Task DeleteAsync_ValidId_DeletesAndReturnsTrue()
    {
        var result = await _orderService.DeleteAsync(1);

        Assert.True(result);
        _dbContextMock.Verify(db => db.Orders.Remove(It.IsAny<Order>()), Times.Once);
        _dbContextMock.Verify(db => db.SaveChangesAsync(default), Times.Once);
    }

    [Fact]
    public async Task DeleteAsync_InvalidId_ReturnsFalse()
    {
        var result = await _orderService.DeleteAsync(99);

        Assert.False(result);
    }
}*/