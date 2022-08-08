package com.desafioapirest.cliente.controller;

import com.desafioapirest.cliente.exception.ApiException;
import com.desafioapirest.cliente.model.Comprobante;
import com.desafioapirest.cliente.service.comprobantes.ComprobanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("comprobantes")
public class ComprobanteController {
    @Autowired
    ComprobanteService comprobanteService;

    /* *******************
     *****getmap**********
     ****************** */

    @GetMapping("")
    public List<Comprobante> mostrarTodos(){
        return comprobanteService.mostrarTodos();
    }


    @GetMapping("{id}")
    public ResponseEntity<Object> mostrarPorId( @PathVariable int id) throws Exception{
        Comprobante comprobante= comprobanteService.mostrarPorId(id);
        if(comprobante==null){
            throw new ApiException("Comprobante no encontrado");
        }
        return new ResponseEntity<>(comprobante, HttpStatus.OK);
    }



}
