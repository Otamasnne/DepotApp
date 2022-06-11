package domainapp.modules.simple.service;

import domainapp.modules.simple.repository.IRepository;

import javax.inject.Inject;

public class Service implements IService{

    @Inject
    IRepository repository;

    @Override
    public void cargar() {

    }

    @Override
    public void consultar(Class<?> clase) {
        repository.retrieve(clase).stream().forEach(System.out::println);
    }
}
