package com.br.workshopjavafxjdbc.model.dao;


import com.br.workshopjavafxjdbc.db.DB;
import com.br.workshopjavafxjdbc.model.dao.impl.DepartmentDaoJDBC;
import com.br.workshopjavafxjdbc.model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
    public static SellerDao createSellerDao(){
        return new SellerDaoJDBC(DB.getConnection());
    }
    public static DepartmentDao createDepartmentDao(){
        return new DepartmentDaoJDBC(DB.getConnection());
    }

}
