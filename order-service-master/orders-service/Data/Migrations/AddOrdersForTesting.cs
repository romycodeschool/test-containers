using FluentMigrator;

namespace orders_service.Data.Migrations;

[Migration(101112023)] // index.dd.mm.yyyy
public class AddOrdersForTesting : Migration
{
    public override void Up()
    {
        Execute.Script(@"./Data/Scripts/data.sql");
    }

    public override void Down()
    {
        throw new NotImplementedException();
    }
}