package com.api.my_finances.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BaseService<T, ID> {

    protected BaseService(JpaRepository<T, ID> repository, String serviceName) {
        this.repository = repository;
        this.serviceName = serviceName;
    }

    private final String serviceName;
    protected final JpaRepository<T, ID> repository;

    public abstract T save(T entity);

    public List<T> listAll(){
        try{
            return repository.findAll();
        } catch (Exception e){
            throw new RuntimeException("Não possivel carregar as informações de " + serviceName + " no momento");
        }
    }

    public String delete(ID id){
        if(!repository.existsById(id)){
            throw new IllegalArgumentException("ID não existe.");
        }
        repository.deleteById(id);
        return "Deletado com sucesso";
    }
}
