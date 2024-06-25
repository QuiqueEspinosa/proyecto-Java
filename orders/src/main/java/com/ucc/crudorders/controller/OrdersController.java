package com.ucc.crudorders.controller;

import com.ucc.crudorders.model.Orders;
import com.ucc.crudorders.service.OrdersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/ord")
@RequiredArgsConstructor
public class OrdersController {
    private final OrdersService ordersService; //inyeccion


    //METODO PARA OBTENER

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Orders> getOrders() {
        return this.ordersService.getOrders();
    }


    //METODO PARA CREAR

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> newOrders (@Valid @RequestBody Orders orders, BindingResult bindingResult){
        if(bindingResult.hasErrors())
        {
            //manejos de los errores
            List<String> error = bindingResult.getAllErrors().stream()//devuelve una lista de los errores y stream te lo comvierte en string
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)//mapea la lista y lo tranforma en mensaje
                    .collect(Collectors.toList()); //metodo para rencopilar la lista de error y muestra al cliente el error
            return  new ResponseEntity<>(error , HttpStatus.BAD_REQUEST);//retorna el mensaje
        }
        return  ordersService.newOrders(orders);// si no hay error crea el producto
    }

    //METODO PARA ACTUALIZAR
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateOrders(@PathVariable("id")Long id,@RequestBody Orders updateOrders){
        return ordersService.updateOrders(id,updateOrders);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteOrders(@PathVariable("id")Long id){
        return  ordersService.deleteOrders(id);
    }

}
