package com.desafioapirest.cliente.controller;

import com.desafioapirest.cliente.model.Productos;
import com.desafioapirest.cliente.service.productos.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("productos")
public class ProductoController {

    @Autowired
    ProductoService productoService;

    /* ******************
     *****getmap*********
     ****************** */

    @GetMapping("")
    public List<Productos> mostrarTodos(){
        return productoService.mostrarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> mostrarPorId( @PathVariable int id) throws Exception{
        Productos producto= productoService.mostrarPorId(id);
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }


    /* ******************
     *****postmap********
     ****************** */


    @PostMapping("/crear")
    public ResponseEntity<Object> productoNuevo(@RequestBody Productos producto) throws Exception {
        producto= productoService.productoNuevo(producto);
        return new ResponseEntity<>(producto,HttpStatus.OK);
    }

    @PostMapping("/actualizar")
    public ResponseEntity<Object> actualizarProducto(@RequestBody Productos producto) throws Exception {
        producto= productoService.actualizarProducto(producto);
        return new ResponseEntity<>(producto,HttpStatus.OK);
    }

    /* ******************
     *****Deletmap********
     ****************** */

    @DeleteMapping("/borrar/{id}")
    public String borrarProducto(@PathVariable int id){
        String texto = productoService.borrarProducto(id);
        return texto;
    }

}
