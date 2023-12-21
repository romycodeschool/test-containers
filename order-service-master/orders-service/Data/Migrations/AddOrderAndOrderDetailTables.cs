using FluentMigrator;

namespace orders_service.Data.Migrations;
[Migration(203)]
public class AddOrderAndOrderDetailTables: Migration
{
    public override void Up()
    {
        Create.Table("Orders")
            .WithColumn("Id").AsInt32().PrimaryKey().Identity() // Assuming you're using a string as an ID
            .WithColumn("CustomerId").AsString(128).NotNullable() // Adjusted assuming 'CustomerId' is a string, change the type as needed
            .WithColumn("OrderStatus").AsInt32().NotNullable()
            .WithColumn("CreatedAt").AsDateTime().NotNullable();

        // Here we are creating the 'OrderDetails' table
        Create.Table("OrderDetail")
            .WithColumn("Id").AsInt32().PrimaryKey().Identity() // Adjusted to be a string to match your entity
            .WithColumn("ProductId").AsString(128)
            .NotNullable() // Adjusted assuming 'ProductId' is a string, change the type as needed
            .WithColumn("OrderId").AsInt32().NotNullable()
            .WithColumn("Quantity").AsInt32().NotNullable()
            .WithColumn("Price").AsDecimal(18, 2).NotNullable();
        
        
        Create.Index("IX_OrderDetails_OrderId")
            .OnTable("OrderDetail")
            .OnColumn("OrderId").Ascending()
            .WithOptions().NonClustered();
        
        
        Create.ForeignKey("FK_OrderDetails_Orders") // Correct naming convention for the foreign key
            .FromTable("OrderDetail")
            .ForeignColumn("OrderId")
            .ToTable("Orders")
            .PrimaryColumn("Id");
        
    }

    public override void Down()
    {
        Delete.ForeignKey("FK_Orders_OrdersDetails").OnTable("OrderDetails"); // remove the foreign key first
        Delete.Index("IX_OrderDetails_OrderId").OnTable("OrderDetails"); // then remove the index
        Delete.Table("OrderDetails");
        Delete.Table("Orders");
    }
}

