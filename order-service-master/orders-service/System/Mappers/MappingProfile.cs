using AutoMapper;
using orders_service.OrderDetails.Dtos;
using orders_service.Orders.Dtos;
using orders_service.Orders.model;
using orders_service.OrderDetails.model;

namespace orders_service.System.Mappers;

public class MappingProfile:Profile
{
    public MappingProfile()
    {
        // Mapping from DTO to Entity
        CreateMap<OrderDetailCreateDTO, OrderDetail>();
        CreateMap<CreateOrderRequest, Order>()
            .ForMember(dest => dest.OrderDetails, 
                act => act.MapFrom(src => src.OrderDetails));
        CreateMap<UpdateOrderRequest, Order>()
            .ForMember(dest => dest.OrderDetails, 
                act => act.MapFrom(src => src.OrderDetails));
        CreateMap<CreateOrderDetailRequest, OrderDetail>();
        CreateMap<UpdateOrderDetailRequest, OrderDetail>();
    }
}