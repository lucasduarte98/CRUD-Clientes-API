package net.lucasduarte.springboot.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.lucasduarte.springboot.exception.ResourceNotFoundException;
import net.lucasduarte.springboot.model.Cliente;
import net.lucasduarte.springboot.repository.ClienteRepository;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "http://localhost:4200")
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	//Busca todos Clientes
	
	@GetMapping("/clientes")
	public List<Cliente> getAll(){
		return clienteRepository.findAll();
	}
	
	//Criar Clientes rest api
	@PostMapping("/clientes")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente create(@Valid @RequestBody Cliente cliente) {
		cliente.setAtivo(true);
		return clienteRepository.save(cliente);
		}
	
	//Buscar Cliente por Id
	
	@GetMapping("/clientes/{id}")
	public ResponseEntity<Cliente> getById(@PathVariable Long id) {
		Cliente cliente = clienteRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Cliente não encontrado:" + id));
		return ResponseEntity.ok(cliente);
		
	}
	
	// Alterando Cliente rest api
	
	@PutMapping("/clientes/{id}")
	public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody Cliente clienteDetails){
		Cliente cliente = clienteRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Cliente não encontrado:" + id));
		
		cliente.setName(clienteDetails.getName());
		cliente.setCpfCnpj(clienteDetails.getCpfCnpj());
		cliente.setEndereco(clienteDetails.getEndereco());
		cliente.setNumero(clienteDetails.getNumero());
		cliente.setBairro(clienteDetails.getBairro());
		cliente.setCep(clienteDetails.getCep());
		cliente.setCidade(clienteDetails.getCidade());
		cliente.setUf(clienteDetails.getUf());
		cliente.setTelefone(clienteDetails.getTelefone());
		cliente.setEmail(clienteDetails.getEmail());
		
		Cliente updateCliente = clienteRepository.save(cliente);
		return ResponseEntity.ok(updateCliente);
		
	}
	
	//Atualizando Status Ativo/Inativo
	@PatchMapping("/clientes/{id}")
	public Cliente updateStatus(@PathVariable Long id) {
		Cliente cliente = clienteRepository.getOne(id);
		cliente.updateStatus();
		return clienteRepository.save(cliente);
		} 
}

