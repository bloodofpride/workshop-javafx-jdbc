package com.br.workshopjavafxjdbc.model.services;

import com.br.workshopjavafxjdbc.model.dao.DaoFactory;
import com.br.workshopjavafxjdbc.model.dao.DepartmentDao;
import com.br.workshopjavafxjdbc.model.entities.Department;

import java.util.List;

public class DepartmentService {
    private DepartmentDao dao = DaoFactory.createDepartmentDao();

    public List<Department> findAll(){
        return dao.findAll();
    }

    public void saveOrUpdate(Department department){
        if (department.getId() == null){
            dao.insert(department);
        }
        else{
            dao.update(department);
        }
    }
}
