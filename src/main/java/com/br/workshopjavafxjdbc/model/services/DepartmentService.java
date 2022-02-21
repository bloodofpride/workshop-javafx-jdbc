package com.br.workshopjavafxjdbc.model.services;

import com.br.workshopjavafxjdbc.model.entities.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentService {
    public List<Department> findAll(){
        List<Department> list = new ArrayList<>();
        list.add(new Department(1, "Books"));
        list.add(new Department(2, "Computer"));
        list.add(new Department(1, "Electronics"));
        return list;
    }
}
