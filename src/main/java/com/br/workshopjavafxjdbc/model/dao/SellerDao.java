package com.br.workshopjavafxjdbc.model.dao;

import com.br.workshopjavafxjdbc.model.entities.Department;
import com.br.workshopjavafxjdbc.model.entities.Seller;
import java.util.List;

public interface SellerDao {
    void insert(Seller seller);
    void update(Seller seller);
    void deleteById(Integer id);
    Seller findById(Integer id);
    List<Seller> findAll();
    List<Seller> findByDepartment(Department department);
}
