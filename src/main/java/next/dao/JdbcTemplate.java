package next.dao;

import core.jdbc.ConnectionManager;
import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {

//    public void update(String sql, PreparedStatementSetter pss) throws DataAccessException {
//
//        try(Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt=con.prepareStatement(sql)) {
//
//            pss.setValues(pstmt);
//            pstmt.executeUpdate();
//
//        } catch(SQLException ex){
//            throw new DataAccessException(ex);
//        }
//
//    }

    public void update(String sql, Object... parameters) throws DataAccessException {

        try(Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt=con.prepareStatement(sql)) {

            for(int i=0; i<parameters.length; i++){
                pstmt.setObject(i+1, parameters[i]);
            }
            pstmt.executeUpdate();

        } catch(SQLException ex){
            throw new DataAccessException(ex);
        }

    }

    public <T> List<T> query(String sql, PreparedStatementSetter pss, RowMapper<T> rm) throws DataAccessException {

        List<T> list=null;

        try(Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt=con.prepareStatement(sql);) {

            pss.setValues(pstmt);
            ResultSet rs=pstmt.executeQuery(sql);

            while(rs.next()){
                list.add(rm.mapRow(rs));
            }

        }catch(SQLException ex){
            throw new DataAccessException(ex);
        }

        return list;
    }

    public <T> T queryForObject(String sql, PreparedStatementSetter pss, RowMapper<T> rm) {

        try(Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt=con.prepareStatement(sql)) {

            pss.setValues(pstmt);
            ResultSet rs=pstmt.executeQuery();

            T user = null;

            if (rs.next()) {
                user = rm.mapRow(rs);
            }

            return user;

        }catch(SQLException ex){
            throw new DataAccessException(ex);
        }

    }

}