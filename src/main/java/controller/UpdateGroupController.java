package controller;

import entity.Group;
import entity.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import util.AlertUtil;
import util.DbUtil;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
* @ClassName UpdateGroupController
* @Author reason-llh
* @Date 2022/4/15 19:48
* @Description 修改分组操作类
* @Version 1.0.0
**/

public class UpdateGroupController implements Initializable {

    @FXML
    private TextField groupfield;  // 分组名输入框

    private static String oldname;  // 原分组名

    private DbUtil dbUtil = new DbUtil();  // 数据库操作类

    public static void setOldname(String oldname) {
        // 放置原分组名
        UpdateGroupController.oldname = oldname;
    }

    @FXML
    public void saveGroup(){
        // 修改分组名
        String text = groupfield.getText();
        if (text == null || text.trim().length() <= 0) {
            AlertUtil.error("组名不能为空");
            return;
        }

        List<Group> groups = dbUtil.selectGroup();
        groups = groups.stream().filter(x -> ! x.getGroupName().equals(oldname)).collect(Collectors.toList());
        for (Group group : groups) {
            if (group.getGroupName().equals(text)) {
                AlertUtil.error("分组已存在");
                return;
            }
        }
        dbUtil.updateGroup(oldname, text);

        // 更新旧分组成员放置新分组中
        List<User> users = dbUtil.selectUser();
        for (User user : users) {
            // 如果用户所在分组与旧分组匹配，那么将放置新分组，并且更新数据库
            if (user.getGroup() != null && user.getGroup().endsWith(oldname)) {
                user.setGroup(text);
                dbUtil.updateUser(user);
            }
        }

        AlertUtil.info("更新分组成功");
//        Main.back();
    }

    @FXML
    public void cancel(){
        // 返回主界面
        Main.back();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 将旧分组名默认放入输入框中
        groupfield.setText(oldname);
    }
}
