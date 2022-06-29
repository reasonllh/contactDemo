package controller;

import entity.Group;
import entity.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import util.AlertUtil;
import util.DbUtil;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
* @ClassName AddUserController
* @Author reason-llh
* @Date 2022/4/10 9:54
* @Description 新增联系人操作类
* @Version 1.0.0
**/

public class AddUserController implements Initializable {


    @FXML
    private TextField namefield;  // 姓名

    @FXML
    private TextField emailfield;  // 邮箱

    @FXML
    private TextField phonefield;  // 手机号

    @FXML
    private ChoiceBox<String> groupbox;   // 分组选择器

    @FXML
    private ImageView imageView;  // 照片

    @FXML
    private TextField dhField;  // 电话

    @FXML
    private DatePicker birthday;  // 生日

    @FXML
    private TextField emailcode;  // 邮编

    @FXML
    private TextField remark;  // 备注

    @FXML
    private TextField address;  // 家庭住址

    @FXML
    private TextField workUnit;  // 工作单位

    @FXML
    private TextField mainPage;  // 个人主页

    private String filePath;  // 图片路径

    private DbUtil dbUtil = new DbUtil();  // 数据库操作类

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 初始化，获取所有分组并放入分组选择器中
        List<Group> groups = dbUtil.selectGroup();
        groupbox.getItems().add("");
        groupbox.getItems().addAll(groups.stream().map(x -> x.getGroupName()).collect(Collectors.toList()));
    }

    @FXML
    public void saveUser(){
        // 按下新增联系人页面的添加按钮
        String name = namefield.getText();
        if (name == null || name.trim().length() <= 0) {
            AlertUtil.error("请输入姓名");
            return;
        }

        String email = emailfield.getText();
        if (email == null || email.trim().length() <= 0) {
            AlertUtil.error("请输入邮箱");
            return;
        }

        String phone = phonefield.getText();
        if (phone == null || phone.trim().length() <= 0) {
            AlertUtil.error("请输入手机号");
            return;
        }

        String dhFieldText = dhField.getText();
        if (dhFieldText == null || dhFieldText.trim().length() <= 0) {
            AlertUtil.error("请输入电话");
            return;
        }

        String birthdayText = birthday.getEditor().getText();
        if (birthdayText == null || birthdayText.trim().length() <= 0) {
            AlertUtil.error("请选择生日");
            return;
        }

        String emailcodeText = emailcode.getText();
        if (emailcodeText == null || emailcodeText.trim().length() <= 0) {
            AlertUtil.error("请填写邮编");
            return;
        }

        String group = groupbox.getValue();
        User user = new User();
        user.setNickname(name);
        user.setPhone(phone);
        user.setEmail(email);
        user.setGroup(group);
        user.setDh(dhFieldText);
        user.setBirthday(birthdayText);
        user.setEmailcode(emailcodeText);
        user.setMainPage(mainPage.getText());
        user.setFilePath(filePath);
        user.setAddress(address.getText());
        user.setWorkUnit(workUnit.getText());
        user.setRemark(remark.getText());
        dbUtil.addUser(user);
        //Util.writeUser(name, phone, email, group);
        AlertUtil.info("添加用户成功");
    }

    @FXML
    public void cancel(){
        // 关闭按钮,返回主页面
        Main.back();
    }

    @FXML
    public void uploadFile(){
        // 点击选择照片
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("View Pictures");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("GIF", "*.gif"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        File file = fileChooser.showOpenDialog(null);
        if (file!=null){
            this.filePath = file.getAbsolutePath();
            imageView.setImage(new Image("file:" + file.getAbsolutePath()));
        }
    }
}
