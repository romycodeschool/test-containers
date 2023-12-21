using Microsoft.EntityFrameworkCore;
using orders_service.OrderDetails.model;
using orders_service.Orders.model;

namespace orders_service.Data;

public class AppDbContext : DbContext
{
    public AppDbContext(DbContextOptions<AppDbContext> options) : base(options)
    {
    }

    public virtual DbSet<Order> Orders { get; set; }
    public DbSet<OrderDetail> OrderDetail { get; set; }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<OrderDetail>()
            .HasOne(p => p.Order)
            .WithMany(b => b.OrderDetails)
            .HasForeignKey(p => p.OrderId);
    }
}
