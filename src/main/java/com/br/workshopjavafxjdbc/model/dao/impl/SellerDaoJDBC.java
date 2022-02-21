package com.br.workshopjavafxjdbc.model.dao.impl;

import com.br.workshopjavafxjdbc.db.DB;
import com.br.workshopjavafxjdbc.db.DbException;
import com.br.workshopjavafxjdbc.model.dao.SellerDao;
import com.br.workshopjavafxjdbc.model.entities.Department;
import com.br.workshopjavafxjdbc.model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {
    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "INSERT INTO seller " +
                            "(name,email,birthdate,basesalary,departmentid) " +
                            "VALUES " +
                            "(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1,seller.getName());
            st.setString(2,seller.getEmail());
            st.setDate(3, new Date(seller.getBirthDate().getTime()));
            st.setDouble(4,seller.getBaseSalary());
            st.setInt(5,seller.getDepartment().getId());

            int rowsAffected = st.executeUpdate();

            if(rowsAffected > 0){
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()){
                    int id = rs.getInt(1);
                    seller.setId(id);
                }
                DB.closeResultSet(rs);
            }
            else{
                throw new DbException("Unexpected error! No rows affected!");
            }
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Seller seller) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "UPDATE seller " +
                        "SET name = ?, email = ?, birthdate = ?, basesalary = ?, departmentid = ? " +
                        "WHERE id = ?");
            st.setString(1, seller.getName());
            st.setString(2, seller.getEmail());
            st.setDate(3, new Date(seller.getBirthDate().getTime()));
            st.setDouble(4, seller.getBaseSalary());
            st.setInt(5, seller.getDepartment().getId());
            st.setInt(6, seller.getId());

            st.executeUpdate();
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "DELETE FROM seller "+
                    "WHERE Id = ?");
            st.setInt(1, id);

            int rowAffected = st.executeUpdate();
            if(rowAffected == 0){
                throw new DbException("there is no seller with the id = "+id);
            }

        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT s.*, d.name as depName " +
                            "FROM seller s INNER JOIN department d " +
                            "ON s.departmentid = d.id " +
                            "WHERE s.id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Department dep = instantiateDepartment(rs);
                Seller seller = instantiateSeller(rs, dep);
                return seller;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT s.*, d.name as depName " +
                            "FROM seller s INNER JOIN department d " +
                            "ON s.departmentid = d.id " +
                            "ORDER BY s.name");
            rs = st.executeQuery();
            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();
            while (rs.next()){
                Department dep = map.get(rs.getInt("departmentid"));

                if(dep == null){
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("departmentid"), dep);
                }
                Seller seller = instantiateSeller(rs, dep);
                sellers.add(seller);
            }
            return sellers;
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT  s.*, d.Name as DepName " +
                        "FROM seller s INNER JOIN department d " +
                        "ON s.departmentid = d.id " +
                        "WHERE s.departmentid = ? " +
                        "ORDER BY s.name");
            st.setInt(1,department.getId());
            rs = st.executeQuery();
            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();
            while (rs.next()) {
                Department dep = map.get(rs.getInt("departmentid"));

                if(dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("departmentid"), dep);
                }

                Seller seller = instantiateSeller(rs, dep);
                sellers.add(seller);
            }
            return sellers;
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
        Seller obj = new Seller();
        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));
        obj.setEmail(rs.getString("Email"));
        obj.setBaseSalary(rs.getDouble("BaseSalary"));
        obj.setBirthDate(rs.getDate("BirthDate"));
        obj.setDepartment(dep);
        return obj;
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }
}

