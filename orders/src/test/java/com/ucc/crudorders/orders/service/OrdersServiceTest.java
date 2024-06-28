package com.ucc.crudorders.orders.service;

import com.ucc.crudorders.model.Orders;
import com.ucc.crudorders.repositories.OrdersRepository;
import com.ucc.crudorders.service.OrdersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
 class OrdersServiceTest {
    @MockBean  //objeto simulado en las clases
    private OrdersRepository ordersRepository;

    @Autowired   //inyecta en el repocitorio (inyecta dependensias)
    private OrdersService ordersService;

    @BeforeEach // esta anotacion sirve para que el metodo sea ejecutado antes de cada test
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
   void getOrders() { //aqui creamos un objeto order donde inicializamos los campos una simulacion
        Orders ord = new Orders(1L, "1000", "SKU001", "150");
        List<Orders> orders = Collections.singletonList(ord); //creamos una lista que contenga solos ese producto

        when(ordersRepository.findAll()).thenReturn(orders);//configura el mock para cuando se llame el metodo final  donde va retornar una vista de productos

        List<Orders> result = ordersService.getOrders(); //llamamos al metodo get product del service donde nos retorna la vista
        assertEquals(1, result.size()); //esto compara lo que nosotros esperamos con la respuesta
        assertEquals("SKU001", result.get(0).getSku());// y aca compara si el id es el mismo id con el id que le pasamos en la simulacion
    }
    @Test
  void testDeleteOrders() { //vamos a testear que el producto se elimine bien
        Long ordersId = 1L; //declaramos una variable tipó long

        // Ejecutar el Método a Probar
        ResponseEntity<Object> response = ordersService.deleteOrders(ordersId);//llama el metodo delete product con el id que le proporsionamos y deberia devolvernos una respuesta

        // Verificar el Comportamiento
        verify(ordersRepository, times(1)).deleteById(ordersId); //metodo de mockito que verifica que el metodo del repositorio fue llamado una sola vez
        assertEquals(HttpStatus.OK, response.getStatusCode()); //compara 2 resultados es un metodo de junit verifica si 2 valores son iguales  y los compara
        assertEquals("Orders delete successfully", response.getBody());
    }
}
