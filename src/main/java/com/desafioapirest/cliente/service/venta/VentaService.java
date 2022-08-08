package com.desafioapirest.cliente.service.venta;

import com.desafioapirest.cliente.exception.ApiException;
import com.desafioapirest.cliente.model.Venta;

import java.util.List;

public interface VentaService {
   
    List<Venta> mostrarTodos();

    Venta mostrarPorId(int id) throws Exception;

    Venta ventaNueva(Venta nueva) throws Exception;

    String borrarVenta(int id);
}
