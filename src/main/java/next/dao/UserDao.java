package next.dao;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.User;

public class UserDao {

    public void insert(User user) throws DataAccessException {

        PreparedStatementSetter pss=new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());

            }
        };

        JdbcTemplate jdbc=new JdbcTemplate();
        String sql="INSERT INTO USERS(userId,password,name,email) VALUES (?,?,?,?)";
        jdbc.update(sql, pss);
    }

    public void update(User user) throws DataAccessException {

        PreparedStatementSetter pss=new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getPassword());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getUserId());
            }
        };

        JdbcTemplate updateJdbcTemplate = new JdbcTemplate();
        String sql = "UPDATE USERS SET password=?, name=?, email=? WHERE userId=?";
        updateJdbcTemplate.update(sql, pss);
    }

    public List<User> findAll() throws DataAccessException {

        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        String sql="SELECT * FROM USERS";

        PreparedStatementSetter pss=new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
            }
        };

        RowMapper<User> rm=new RowMapper() {
            @Override
            public User mapRow(ResultSet rs) throws SQLException {

                return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                            rs.getString("email"));

            }
        };

        return jdbcTemplate.query(sql, pss, rm);
    }

    public User findByUserId(String userId) throws DataAccessException {

        PreparedStatementSetter pss=new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, userId);
            }
        };

        RowMapper<User> rm=new RowMapper() {
            @Override
            public User mapRow(ResultSet rs) throws SQLException {

                return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
            }
        };

        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        String sql= "SELECT * FROM USERS WHERE userId=?";

        return jdbcTemplate.queryForObject(sql, pss, rm);
    }
}
