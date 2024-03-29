package com.example.pointofsalebyDulan.service.impl;

import com.example.pointofsalebyDulan.dto.QueryInterface.OrderDetailsInterface;
import com.example.pointofsalebyDulan.dto.paginated.PaginatedResponseOrderDetails;
import com.example.pointofsalebyDulan.dto.request.RequestOrderSaveDTO;
import com.example.pointofsalebyDulan.dto.response.ResponseOrderDetailsDTO;
import com.example.pointofsalebyDulan.entity.Order;
import com.example.pointofsalebyDulan.entity.OrderDetails;
import com.example.pointofsalebyDulan.repo.CustomerRepo;
import com.example.pointofsalebyDulan.repo.ItemRepo;
import com.example.pointofsalebyDulan.repo.OrderDetailsRepo;
import com.example.pointofsalebyDulan.repo.OrderRepo;
import com.example.pointofsalebyDulan.service.OrderService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderServiceIMPL implements OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderDetailsRepo orderDetailsRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private ItemRepo itemRepo;

    @Override
    @Transactional
    public String addOrder(RequestOrderSaveDTO requestOrderSaveDto) {
        Order order = new Order(
               customerRepo.getById(requestOrderSaveDto.getCustomer()),
               requestOrderSaveDto.getDate(),
               requestOrderSaveDto.getAmount()
        );
        orderRepo.save(order);

        if(orderRepo.existsById(order.getOrderId())){
            List<OrderDetails> orderDetails1 =  modelMapper.map(
                    requestOrderSaveDto.getOrderDetails(), new TypeToken<List<OrderDetails>>(){}.getType());

            for (int i = 0; i < orderDetails1.size(); i++) {
                orderDetails1.get(i).setOrders(order);
                orderDetails1.get(i).setItems(itemRepo.getById(requestOrderSaveDto.getOrderDetails().get(i).getItems()));
            }
            if(orderDetails1.size()>0){
                orderDetailsRepo.saveAll(orderDetails1);
            }
        }

        return "Done, Order saved";
    }

    @Override
    public PaginatedResponseOrderDetails getAllOrderDetails(boolean status, int page, int size) {

    List<OrderDetailsInterface> orderDetailsInterfaces = orderRepo.getAllOrders(status, PageRequest.of(page,size));

    List<ResponseOrderDetailsDTO> list = new ArrayList<>();

        for (OrderDetailsInterface o : orderDetailsInterfaces) {
            ResponseOrderDetailsDTO responseOrderDetailsDTO = new ResponseOrderDetailsDTO(
              o.getCustomerName(),
              o. getCustomerAddress(),
              o.getContactNumbers(),
              o.getDate(),
              o.getTotal()
            );
            list.add(responseOrderDetailsDTO);
        }

        PaginatedResponseOrderDetails paginatedResponseOrderDetails = new PaginatedResponseOrderDetails(list,orderRepo.getCount(status));

        return paginatedResponseOrderDetails;
    }
}
