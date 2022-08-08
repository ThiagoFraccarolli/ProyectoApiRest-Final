package com.desafioapirest.cliente.service.clientes;

import com.desafioapirest.cliente.exception.ApiException;
import com.desafioapirest.cliente.model.Clientes;
import com.desafioapirest.cliente.dto.ClientesDTO;
import com.desafioapirest.cliente.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService{
    @Autowired
    ClientesRepository clientesRepository;
    List<Clientes> copiaDeCliente;

    int finalLista;
    int anio;
    int mes;
    int dia;

    Clientes elementocliente;
    ClientesDTO amostrar;
    Integer edad;
    Date fechanacimiento;
    String fechanacimientoString;

                                /*                     /
                                /       *GET*          /
                                /                     */


    @Override
    public List<Clientes> mostrarTodos() {
        return clientesRepository.findAll();
    }

    @Override
    public List<ClientesDTO> mostrarEdad() {
        List<ClientesDTO> listaParaMostrar = new ArrayList<>();
        copiaDeCliente=clientesRepository.findAll();
        finalLista=copiaDeCliente.size();
        for(int i=0; i<=finalLista-1;i++){
           elementocliente=copiaDeCliente.get(i);
           edad = calcularEdad(elementocliente);
           amostrar= new ClientesDTO(elementocliente.getIdcliente(),elementocliente.getDni(), elementocliente.getNombre(), elementocliente.getApellido(), edad);
           listaParaMostrar.add(amostrar);
        }
        return listaParaMostrar;
    }

    @Override
    public Clientes mostrarPorId(Integer idcliente) throws Exception {
        elementocliente= clientesRepository.findById(idcliente).orElse(null);
        if(elementocliente==null){throw new ApiException("Cliente no encontrado con ese ID");}
        return elementocliente;
    }

                                /*                     /
                                /         *POST*       /
                                /                     */

    public ClientesDTO clienteNuevo(Clientes cliente) throws Exception {
        boolean dniRepetido = buscarDniRepetido(cliente);
        if(dniRepetido){
            throw new ApiException("DNI ya existente");
        }else {
            int id = calcularID();
            cliente.setIdcliente(id);
            clientesRepository.save(cliente);
            edad = calcularEdad(cliente);
            amostrar = new ClientesDTO(id, cliente.getDni(), cliente.getNombre(), cliente.getApellido(), edad);
            return (amostrar);
        }
    }

    @Override
    public ClientesDTO actualizarCliente(Clientes cliente) throws Exception{
        copiaDeCliente = clientesRepository.findAll();
        finalLista = copiaDeCliente.size();
        if(cliente.getIdcliente()<=finalLista && cliente.getIdcliente()>0){
            clientesRepository.save(cliente);
            edad=calcularEdad(cliente);
            amostrar = new ClientesDTO(cliente.getIdcliente(), cliente.getDni(), cliente.getNombre(), cliente.getApellido(), edad);
          return amostrar;
        }
        throw new ApiException("El ID de Cliente no existe");
    }

                                /*                     /
                                /        *DELET*       /
                                /                     */

    @Override
    public String borrarCliente(int id) {
        copiaDeCliente= clientesRepository.findAll();
        String texto = "Cliente no encontrado";
        if(id<1){return texto;}
        for(int i=0;i<copiaDeCliente.size();i++){
            if(id==copiaDeCliente.get(i).getIdcliente()) {
                clientesRepository.deleteById(id);
                texto = "El Cliente ha sido eliminado";
                i=copiaDeCliente.size();
            }
        }
        return texto;
    }

                                /*                     /
                                /       *METODOS*      /
                                /                     */
    @Override
    public boolean buscarIDCliente(int idcliente) {
        copiaDeCliente=clientesRepository.findAll();
        for(int i=0;i<copiaDeCliente.size();i++){
            if(copiaDeCliente.get(i).getIdcliente()==idcliente) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getNombreApellido(int idcliente) {
        elementocliente = clientesRepository.findById(idcliente).orElse(null);
        return elementocliente.getNombre()+" "+elementocliente.getApellido();
    }


    private int calcularEdad (Clientes cliente){
        fechanacimiento=cliente.getFechanacimiento();
        fechanacimientoString=fechanacimiento.toString();
        anio=Integer.parseInt(fechanacimientoString.substring(0,4));
        mes= Integer.parseInt(fechanacimientoString.substring(5,7));
        dia= Integer.parseInt(fechanacimientoString.substring(8,10));
        edad=Period.between(LocalDate.of(anio,mes,dia),LocalDate.now()).getYears();
        return edad;
    }

    private boolean buscarDniRepetido(Clientes cliente){
        copiaDeCliente=clientesRepository.findAll();
        for(int i=0;i<copiaDeCliente.size();i++){
            elementocliente=copiaDeCliente.get(i);
            if(elementocliente.getDni().equals(cliente.getDni())) {
                return true;
            }
        }
        return false;
    }

    private int calcularID(){
        return clientesRepository.findAll().size()+1;   //Le sumo 1 a la lista para que no agarre siempre el mismo
    }

}
