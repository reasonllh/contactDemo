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

public class UpdateUserController implements Initializable {


    private static User current;  // 当前用户

    @FXML
    private TextField namefield;

    @FXML
    private TextField emailfield;


    @FXML
    private TextField phonefield;

    @FXML
    private ChoiceBox<String> groupbox;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField dhField;

    @FXML
    private DatePicker birthday;

    @FXML
    private TextField emailcode;

    @FXML
    private TextField remark;

    @FXML
    private TextField address;

    @FXML
    private TextField workUnit;

    @FXML
    private TextField mainPage;

    private String filePath;

    private DbUtil dbUtil = new DbUtil();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 初始化所有的栏目
        List<Group> groups = dbUtil.selectGroup();  // 获取所有分组
        groupbox.getItems().add("");
        groupbox.getItems().addAll(groups.stream().map(x -> x.getGroupName()).collect(Collectors.toList()));
        namefield.setText(current.getNickname());
        emailfield.setText(current.getEmail());
        phonefield.setText(current.getPhone());
        groupbox.setValue(current.getGroup());
        dhField.setText(current.getDh());
        emailcode.setText(current.getEmailcode());
        remark.setText(current.getRemark());
        address.setText(current.getAddress());
        workUnit.setText(current.getWorkUnit());
        filePath = current.getFilePath();
        birthday.getEditor().setText(current.getBirthday());
        mainPage.setText(current.getMainPage());
        imageView.setImage(new Image("file:" + current.getFilePath()));
    }

    @FXML
    public void saveUser(){
        // 按下修改按钮
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
        user.setId(current.getId());
        dbUtil.updateUser(user);
        AlertUtil.info("修改用户成功");
//        Main.back();
    }

    public static void setCurrent(User current) {
        // 放入当前用户
        UpdateUserController.current = current;
    }

    @FXML
    public void uploadFile(){
        // 按下照片 选择图片
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

    @FXML
    public void cancel(){
        // 关闭按钮
        Main.back();
    }
}
