package io.zipcoder.persistenceapp.services;

import io.zipcoder.persistenceapp.entities.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository repository;

    public Department findById(Long id) {
        return repository.findOne(id);
    }

    public Department create(Department department) {
        return repository.save(department);
    }

    public Department updateName(Long id, String name) {
        Department department = findById(id);
        department.setName(name);
        return repository.save(department);
    }
}
