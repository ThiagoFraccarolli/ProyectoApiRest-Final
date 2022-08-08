package com.desafioapirest.cliente.service.productos;

import com.desafioapirest.cliente.exception.ApiException;
import com.desafioapirest.cliente.model.Productos;
import com.desafioapirest.cliente.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService{

    @Autowired
    ProductoRepository productoRepository;

    List<Productos> productoscopia;
    Productos productoAMostrar = new Productos();
    String descripcionOriginal;

                                /*                     /
                                /       *GET*          /
                                /                     */

    @Override
    public List<Productos> mostrarTodos() {
        return productoRepository.findAll();
    }


    @Override
    public Productos mostrarPorId(int id) throws Exception {
        productoAMostrar= productoRepository.findById(id).orElse(null);
        if(productoAMostrar==null){throw new ApiException("Comprobante no encontrado con ese ID");}
        return productoAMostrar;
    }


                                /*                     /
                                /         *POST*       /
                                /                     */


    @Override
    public Productos productoNuevo(Productos producto) throws Exception {
        boolean codigoRepetido = buscarCodigoRepetido(producto);
        if(codigoRepetido){
            throw new ApiException("Este codigo pertenece a otro producto");
        }else {
            int id = calcularID();
            producto.setIdproducto(id);
            productoRepository.save(producto);
            return (producto);
        }
    }

    @Override
    public Productos actualizarProducto(Productos producto) throws Exception {
        int idfinal = productoRepository.findAll().size();
        if(producto.getIdproducto()<=idfinal && producto.getIdproducto()>0){
            producto.setCodigo(producto.getCodigo().toUpperCase());
            productoRepository.save(producto);
            return producto;
        }
        throw new ApiException("El ID de Producto no existe");
    }

                                /*                     /
                                /        *DELET*       /
                                /                     */

    @Override
    public String borrarProducto(int id) {
        String texto = "Producto no encontrado con ese ID";
        if(id<1){return texto;}
        if(buscarIdProducto(id)){
            productoAMostrar=productoRepository.findById(id).orElse(null);
            texto = "El producto: ("+productoAMostrar.getDescripcion()+") se ha eliminado";
            productoRepository.deleteById(id);
        }
        return texto;
    }

                                /*                     /
                                /       *METODOS*      /
                                /                     */

    public boolean buscarIdProducto(int id){
        productoscopia=productoRepository.findAll();
        for(int i=0;i<productoscopia.size();i++){
            if(productoscopia.get(i).getIdproducto()==id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String VerifCantidad(Integer idproducto, int cantidad) throws Exception{
        productoAMostrar = productoRepository.findById(idproducto).orElse(null);
        if(productoAMostrar.getStock()>=cantidad){
            return String.valueOf(cantidad*productoAMostrar.getPrecio());
        }
        return "Stock Insuficiente para la compra. El stock del producto :"+productoAMostrar.getDescripcion()+" es de : "+productoAMostrar.getStock();
    }

    @Override
    public String getDescripcion(Integer idproducto) {
        productoAMostrar = productoRepository.findById(idproducto).orElse(null);
        return productoAMostrar.getDescripcion();
    }

    @Override
    public void modifStock(Integer idproducto, int cantidad) {
        productoAMostrar = productoRepository.findById(idproducto).orElse(null);
        int nuevoStock= productoAMostrar.getStock()-cantidad;
        productoAMostrar.setStock(nuevoStock);
        productoRepository.save(productoAMostrar);
        return;
    }

    @Override
    public void devolucionStock(Integer idproducto, int cantidad) {
        productoAMostrar = productoRepository.findById(idproducto).orElse(null);
        int stockEntabla= productoAMostrar.getStock();
        productoAMostrar.setStock(stockEntabla+cantidad);
        productoRepository.save(productoAMostrar);
        return;
    }


    private boolean buscarCodigoRepetido(Productos producto){
        productoscopia=productoRepository.findAll();
        for(int i=0;i<productoscopia.size();i++){
            if(productoscopia.get(i).getCodigo().equalsIgnoreCase(producto.getCodigo())) {
            return true;
            }
        }
        return false;
    }

    private int calcularID(){
        return productoRepository.findAll().size()+1;
    }
}
