package com.ucc.crudservice.controller;


import com.ucc.crudservice.model.Product;
import com.ucc.crudservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping ("api/products")
@RequiredArgsConstructor

//todos  @ son anotaciones

public class ProductController {
    private  final ProductService productService; //inyectamos el service


    //metodos para obtener los productos
    @Operation(summary = "Este edpoint se encarga de obtener todos los productos")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)

    //luego llamamos a la funcion que define que queremos obtener
    public List<Product> getProducts(){

        return this.productService.getProducts();
    }

    //metodos para agregar un producto
    @Operation(summary = "Este edpoint se encarga de crear un nuevo producto.")
    @ApiResponses(value = { //array de objeto donde cada api response tiene una respuesta diferente
            @ApiResponse(responseCode = "200", description = "Producto creado con exito",
                    content = {
                            @Content(mediaType = "application/json", //esto indica que va ser de un formato json
                                    schema = @Schema(implementation = Product.class))// es schema va ser de la clase product
                    }),
            @ApiResponse(responseCode = "500", description = "error de parametros", content = @Content),
            @ApiResponse(responseCode = "400", description = "error de la respuesta", content = @Content)
    })
    @PostMapping //metodo post
    @ResponseStatus(HttpStatus.CREATED)//responde el estado del metodo es una anotacion 200=ok 404 etc
    //llamamos la funcion para crear un nuevo producto

    public ResponseEntity<Object> newProduct (@Valid @RequestBody Product product, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            //manejos de los errores
            List<String> error = bindingResult.getAllErrors().stream()//devuelve una lista de los errores y stream te lo comvierte en string
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)//mapea la lista y lo tranforma en mensaje
                    .collect(Collectors.toList()); //metodo para rencopilar la lista de error y muestra al cliente el error
            return  new ResponseEntity<>(error , HttpStatus.BAD_REQUEST);//retorna el mensaje
        }
        return  productService.newProduct(product);// si no hay error crea el producto

    }




    //metodo put update
    @Operation(summary = "Este edpoint se encarga de actualizar/editar un nuevo producto.")
    @ApiResponses(value = { //array de objeto donde cada api response tiene una respuesta diferente
            @ApiResponse(responseCode = "200", description = "Producto Actualizado con exito",
                    content = {
                            @Content(mediaType = "application/json", //esto indica que va ser de un formato json
                                    schema = @Schema(implementation = Product.class))// es schema va ser de la clase product
                    }),
            @ApiResponse(responseCode = "500", description = "error de parametros", content = @Content),
            @ApiResponse(responseCode = "400", description = "error de la respuesta", content = @Content)
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateProduct(@PathVariable("id")Long id,@RequestBody Product updateProduct){
        return productService.updateProduct(id,updateProduct);
    }





    //Delete metodo
    @Operation(summary = "Este edpoint se encarga de Eliminar un nuevo producto.")
    @ApiResponses(value = { //array de objeto donde cada api response tiene una respuesta diferente
            @ApiResponse(responseCode = "200", description = "Producto eliminado con exito",
                    content = {
                            @Content(mediaType = "application/json", //esto indica que va ser de un formato json
                                    schema = @Schema(implementation = Product.class))// es schema va ser de la clase product
                    }),
            @ApiResponse(responseCode = "500", description = "error de parametros", content = @Content),

    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteProduct(@PathVariable("id")Long id){
        return  productService.deleteProduct(id);
    }
}



