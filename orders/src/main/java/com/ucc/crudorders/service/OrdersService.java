package com.ucc.crudorders.service;

import com.ucc.crudorders.model.Orders;
import com.ucc.crudorders.repositories.OrdersRepository;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service //mira estamos haciendo un service ja
@RequiredArgsConstructor //para no inicializar las variabnles


public class OrdersService {

    private final OrdersRepository ordersRepository; //inyeccion de dependencia.nos permite usa los de la interface

    //METODO PARA OBTENER
    public List<Orders> getOrders() {
        return ordersRepository.findAll();
    }


    //METODO PARA CREAR
    public ResponseEntity<Object>  newOrders(Orders orders) {
        ordersRepository.save(orders);
        return new ResponseEntity<>(
                HttpStatus.CREATED
        );
    }


    //Metodo para actualizar un producto
    public ResponseEntity<Object> updateOrders(Long id,Orders updatedOrders){ //definimos el update que acepte el id del product y updateproduct que devuelve el objeto

        Optional<Orders> existingProductOptional = ordersRepository.findById(id); //utilizamos este jpa para buscar el producto que coinsida con el producto de la base de datos

        if(existingProductOptional.isPresent()){ //si existe un product y esta presente(isPresent)

            Orders existingProduct = existingProductOptional.get();
            existingProduct.setOrd(updatedOrders.getOrd());
            existingProduct.setSku(updatedOrders.getSku());
            existingProduct.setCant(updatedOrders.getCant());


            ordersRepository.save(existingProduct);
            return new ResponseEntity<>("Pedido updated successfully", HttpStatus.OK);//mensaje de okey

        }else {
            return new ResponseEntity<>("Pedido not found" , HttpStatus.NOT_FOUND); //mensaje de error 404
        }

    }



    //Metodo para borrar product
    public ResponseEntity<Object> deleteOrders (Long id){
        ordersRepository.deleteById(id);
        return new ResponseEntity<>("Orders delete successfully", HttpStatus.OK);
    }
}
