package util;

import entity.Group;
import entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
* @ClassName DbUtil
* @Author reason-llh
* @Date 2022/4/23 11:47
* @Description 数据库连接与操作工具类
* @Version 1.0.0
**/

public class DbUtil {

    private String url = "jdbc:mysql://localhost:3306/contact?useSSL=false&useUnicode=true&characterEncoding=utf8";
    private String username = "root";
    private String password = "12345";

//    private String url = "jdbc:mysql://120.25.144.136:3306/contact?useSSL=false&useUnicode=true&characterEncoding=utf8";
//    private String username = "root";
//    private String password = "343053689Llhzzy";


    private Connection connection;

    public DbUtil(){
        try {
            // 连接mysql数据库contact
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
    *
    * @param user
    * @author reason-llh
    * @date 2022/4/23 12:49
    * @description 新增联系人
    **/
    public void addUser(User user){
        try {
            PreparedStatement statement = connection.prepareStatement("insert into `user` (`name`, `email`, `phone`, `group`, " +
                    "`filePath`, `remark`, `address`, `workUnit`, `mainPage`, `emailcode`, `birthday`, `dh`)" +
                    " values (?,?,?,?,?,?,?,?,?,?,?,?)");
            statement.setString(1, user.getNickname());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getGroup());
            statement.setString(5, user.getFilePath());
            statement.setString(6, user.getRemark());
            statement.setString(7, user.getAddress());
            statement.setString(8, user.getWorkUnit());
            statement.setString(9, user.getMainPage());
            statement.setString(10, user.getEmailcode());
            statement.setString(11, user.getBirthday());
            statement.setString(12, user.getDh());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
    *
    * @param user
    * @author reason-llh
    * @date 2022/4/23 16:00
    * @description 更新联系人,根据联系人id更新用户信息
    **/
    public void updateUser(User user){
        String sql = "update `user` set `name` = ?, "
                + "`email` = ?,"
                + "`phone` = ?,"
                + "`group` = ?,"
                + "`filePath` = ?,"
                + "`remark` = ?,"
                + "`address` = ?,"
                + "`workUnit` = ?,"
                + "`mainPage` = ?,"
                + "`emailcode` = ?,"
                + "`birthday` = ?,"
                + "`dh` = ? where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getNickname());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getGroup());
            statement.setString(5, user.getFilePath());
            statement.setString(6, user.getRemark());
            statement.setString(7, user.getAddress());
            statement.setString(8, user.getWorkUnit());
            statement.setString(9, user.getMainPage());
            statement.setString(10, user.getEmailcode());
            statement.setString(11, user.getBirthday());
            statement.setString(12, user.getDh());
            statement.setInt(13, Integer.parseInt(user.getId()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
    *
    * @param
    * @return java.util.List<entity.User>
    * @author reason-llh
    * @date 2022/4/23 16:19
    * @description 从数据库contact的user表中获取联系人列表List<entity.User>并返回
    **/
    public List<User> selectUser(){
        List<User> userList = new ArrayList<>();
        try {
            PreparedStatement prepareStatement = connection.prepareStatement("select * from `user`");
            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id") + "");
                user.setNickname(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPhone(resultSet.getString("phone"));
                user.setGroup(resultSet.getString("group"));
                user.setFilePath(resultSet.getString("filePath"));
                user.setRemark(resultSet.getString("remark"));
                user.setAddress(resultSet.getString("address"));
                user.setWorkUnit(resultSet.getString("workUnit"));
                user.setMainPage(resultSet.getString("mainPage"));
                user.setEmailcode(resultSet.getString("emailcode"));
                user.setBirthday(resultSet.getString("birthday"));
                user.setDh(resultSet.getString("dh"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    /**
    *
    * @param id
    * @author reason-llh
    * @date 2022/4/23 17:02
    * @description 根据id删除用户
    **/
    public void deleteUser(int id){
        try {
            PreparedStatement prepareStatement = connection.prepareStatement("delete  from `user` where id = " + id);
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
    *
    * @param group
    * @author reason-llh
    * @date 2022/4/23 12:31
    * @description 新增分组
    **/
    public void addGroup(String group){
        try {
            PreparedStatement statement = connection.prepareStatement("insert into `group` (`groupname`)" +
                    " values (?)");
            statement.setString(1, group);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
    *
    * @param old
    * @param newText
    * @author reason-llh
    * @date 2022/4/23 12:38
    * @description 修改分组名称
    **/
    public void updateGroup(String old, String newText){
        try {
            PreparedStatement statement = connection.prepareStatement("update  `group` set `groupname` = ? where groupname = ?" );
            statement.setString(1, newText);
            statement.setString(2, old);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
    *
    * @param
    * @return java.util.List<entity.Group>
    * @author reason-llh
    * @date 2022/4/23 12:41
    * @description 获取分组列表
    **/
    public List<Group> selectGroup(){
        List<Group> groupList = new ArrayList<>();
        try {
            PreparedStatement prepareStatement = connection.prepareStatement("select * from `group`");
            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                Group group = new Group();
                group.setId(resultSet.getInt("id") + "");
                group.setGroupName(resultSet.getString("groupname"));
                groupList.add(group);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupList;
    }

    /**
    *
    * @param id
    * @author reason-llh
    * @date 2022/4/23 12:51
    * @description 根据id删除分组
    **/
    public void deleteGroup(int id){
        try {
            PreparedStatement prepareStatement = connection.prepareStatement("delete  from `group` where id = " + id);
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
