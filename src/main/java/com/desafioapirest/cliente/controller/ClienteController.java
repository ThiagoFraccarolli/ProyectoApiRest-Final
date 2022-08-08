package com.desafioapirest.cliente.controller;

import com.desafioapirest.cliente.model.Clientes;
import com.desafioapirest.cliente.dto.ClientesDTO;
import com.desafioapirest.cliente.service.clientes.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("clientes")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    /* ******************
     *****getmap*********
     ****************** */

    @GetMapping("")
    public List<Clientes> mostrarTodos(){
        return clienteService.mostrarTodos();
    }

    @GetMapping("/xedad")
    public List<ClientesDTO> mostrarEdad(){
        return clienteService.mostrarEdad();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> mostrarPorId( @PathVariable int id) throws Exception {
        Clientes cliente = clienteService.mostrarPorId(id);
            return new ResponseEntity<>(cliente,HttpStatus.OK);

    }


    /* *******************
     *****postmap*********
     ******************* */


    @PostMapping("/crear")
    public ResponseEntity<Object> clienteNuevo(@RequestBody Clientes cliente) throws Exception  {
        ClientesDTO nuevoCliente= clienteService.clienteNuevo(cliente);
        return new ResponseEntity<>(nuevoCliente,HttpStatus.OK);
    }

    @PostMapping("/actualizar")
    public ResponseEntity<Object> actualizarCliente(@RequestBody Clientes cliente) throws Exception {
        ClientesDTO nuevoCliente = clienteService.actualizarCliente(cliente);
        return new ResponseEntity<>(nuevoCliente,HttpStatus.OK);
    }

    /* *******************
     *****Deletmap********
     ****************** */

    @DeleteMapping("/borrar/{id}")
    public String borrarCliente(@PathVariable int id){
        String texto =clienteService.borrarCliente(id);
        return texto;
    }
}
