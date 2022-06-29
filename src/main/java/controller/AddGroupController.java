package controller;

import entity.Group;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import util.AlertUtil;
import util.DbUtil;

import java.util.List;

/**
* @ClassName AddGroupController
* @Author reason-llh
* @Date 2022/4/14 10:43
* @Description 新增分组操作类
* @Version 1.0.0
**/

public class AddGroupController {

    @FXML
    private TextField groupfield;  // 分组名

    private DbUtil dbUtil = new DbUtil();   // 数据库操作类

    @FXML
    public void saveGroup(){
        // 添加组名
        // 拿到当前输入组名
        String text = groupfield.getText();
        if (text == null || text.trim().length() <= 0) {
            AlertUtil.error("组名不能为空");
            return;
        }

        List<Group> groups = dbUtil.selectGroup();
        for (Group group : groups) {
            if (group.getGroupName().equals(text)) {
                AlertUtil.error("分组已存在");
                return;
            }
        }
        dbUtil.addGroup(text);
        AlertUtil.info("创建分组成功");
    }

    @FXML
    public void cancel(){
        // 返回
        Main.back();
    }
}
