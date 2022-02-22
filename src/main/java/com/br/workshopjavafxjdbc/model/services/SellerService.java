package com.br.workshopjavafxjdbc.model.services;

import com.br.workshopjavafxjdbc.model.dao.DaoFactory;
import com.br.workshopjavafxjdbc.model.dao.SellerDao;
import com.br.workshopjavafxjdbc.model.entities.Seller;

import java.util.List;

public class SellerService {
    private SellerDao dao = DaoFactory.createSellerDao();

    public List<Seller> findAll() {
        return dao.findAll();
    }

    public void saveOrUpdate(Seller obj) {
        if (obj.getId() == null) {
            dao.insert(obj);
        }
        else {
            dao.update(obj);
        }
    }

    public void remove(Seller obj) {
        dao.deleteById(obj.getId());
    }
}
