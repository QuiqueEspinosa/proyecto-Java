package com.ucc.crudorders.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity //esta notacion sirve para que marque la clase como una actividad en la base de datos
@Table(name = "Orders")//le vamos a deci el nombre para que se guarde en la base de datos
@Getter
@Setter
@AllArgsConstructor //genera un contructor que nos inicializa los campos automaticamente
@NoArgsConstructor //genera un contructor pero para los sin orgumentos
@Builder //metodo estatico para la creacion de las intancias

public class Orders {
    @Id //marcamos la llave primaria de la base de datos
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //se genera automaticamente
    private Long id;

    @NotBlank(message = "el campo numero orden no debe estar vacio")
    private String ord;
    @NotBlank(message = "el campo Sku no debe estar vacio")
    private String sku;
    @NotBlank(message = "el campo cantidad no debe estar vacio")
    private String cant;

}
