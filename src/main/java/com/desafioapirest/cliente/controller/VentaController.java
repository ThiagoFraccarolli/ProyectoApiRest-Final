package com.desafioapirest.cliente.controller;

import com.desafioapirest.cliente.model.Venta;
import com.desafioapirest.cliente.service.venta.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ventas")
public class VentaController {
    @Autowired
    VentaService ventaService;

    /* *******************
     *****gettmap*********
     ****************** */
    @GetMapping("")
    public List<Venta> mostrarTodos(){
        return ventaService.mostrarTodos();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> mostrarPorId(@PathVariable int id) throws Exception{
        Venta venta = ventaService.mostrarPorId(id);
        return new ResponseEntity<>(venta, HttpStatus.OK);
    }

    /* ******************
     *****postmap********
     ****************** */

    @PostMapping("/nueva")
    public ResponseEntity<Object> ventaNueva(@RequestBody Venta nueva)throws Exception{
        Venta ventaOk= ventaService.ventaNueva(nueva);
        return new ResponseEntity<>(ventaOk, HttpStatus.CREATED);
    }

    /* ******************
    *****Deletmap********
    ****************** */

    @DeleteMapping("/borrar/{id}")
    public String borrarVenta(@PathVariable int id){
        String texto = ventaService.borrarVenta(id);
        return texto;
    }
}
