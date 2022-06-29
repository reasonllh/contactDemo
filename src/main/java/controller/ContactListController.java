package controller;

import entity.Group;
import entity.User;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import util.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
* @ClassName ContactListController
* @Author reason-llh
* @Date 2022/4/3 20:15
* @Description 联系人列表操作类
* @Version 1.0.0
**/

public class ContactListController implements Initializable {

    @FXML
    private TextField searchField;  // 搜索框

    @FXML
    private TableView<User> userTab;  // 用户表格

    @FXML
    private TableView<Group> groupTab;  // 分组表格

    @FXML
    private ChoiceBox<String> groupBox;   // 分组选择器

    private DbUtil dbUtil = new DbUtil();  // 数据库操作

    private String groupVal; // 当前所选分组的值

    private ObservableList<User> dataList;  // 存放当前表的User对象

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchField.setPromptText("请输入关键字");
        // 先让分组选择栏初始化
        List<Group> groups = dbUtil.selectGroup();
        groupBox.setValue("");
        groupBox.getItems().addAll(groups.stream().map(x -> x.getGroupName()).collect(Collectors.toList()));
        searchField.textProperty().addListener(new ChangeListener<String>() {
               @Override
               public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                   showTab();
               }
           });
        showTab();
        showGroup();
    }

    /**
    *
    * @param
    * @return void
    * @author reason-llh
    * @date 2022/4/4 21:49
    * @description 获取分组表格 & 设置监听点击分组表格事件，即按所选分组重新展示用户列表
    **/
    private void showGroup() {
        groupTab.getItems().clear();
        groupTab.getColumns().clear();

        List<Group> groupList = dbUtil.selectGroup();

        TableColumn<Group, String> groupName = new TableColumn<>("分组名称");
        groupName.setPrefWidth(129);
        groupName.setCellValueFactory(
                new PropertyValueFactory<Group, String>("groupName")
        );

        ObservableList<Group> datalist = FXCollections.observableList(groupList);
        groupTab.setItems(datalist);
        groupTab.getColumns().addAll(groupName);
        // 点击分组表格某一行后 触发事件->更换groupVal
        groupTab.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Group>() {
            @Override
            public void changed(ObservableValue<? extends Group> observable, Group oldValue, Group newValue) {
                if (newValue != null) {
                    groupVal = newValue.getGroupName();
                }
                showTab();
            }
        });
    }

    /**
    *
    * @param
    * @return void
    * @author reason-llh
    * @date 2022/4/4 10:29
    * @description 获取用户表格 模糊搜索获取用户表格
    **/
    private void showTab() {
        userTab.getItems().clear();
        userTab.getColumns().clear();
        userTab.setEditable(true);  // 用于表格可勾选

        List<User> userList = dbUtil.selectUser();  // 从数据库中拿到用户表
        String searchVal = searchField.getText();   // 拿到搜索框的值
        // 如果搜索框有值,将模糊查询姓名、手机、分组、拼音
        if (searchVal != null && searchVal.trim().length() > 0) {
            userList = userList.stream().filter(x -> x.getNickname().contains(searchVal)
                    || x.getPhone().contains(searchVal)
                    || (x.getGroup() != null && x.getGroup().contains(searchVal))
                    || PinyinUtil.getFirstSpell(x.getNickname()).contains(PinyinUtil.getFirstSpell(searchVal))
                    || PinyinUtil.getFullSpell(x.getNickname()).contains(PinyinUtil.getFullSpell(searchVal))).collect(Collectors.toList());
        }
        if (groupVal != null) {
            userList = userList.stream().filter(x -> groupVal.equals(x.getGroup())).collect(Collectors.toList());
        }

        TableColumn<User, Boolean> selectedContacts = new TableColumn<>("选择");
        selectedContacts.setPrefWidth(50);
        selectedContacts.setCellValueFactory(
                new PropertyValueFactory<User, Boolean>("selected")
        );

        // 表格多行选定,改变selected状态
        selectedContacts.setCellFactory(
                CellFactory.tableCheckBoxColumn(new Callback<Integer, ObservableValue<Boolean>>() {
                    @Override
                    public ObservableValue<Boolean> call(Integer index) {
                        final User g = (User) userTab.getItems().get(index);
                        ObservableValue<Boolean> ret =
                                new SimpleBooleanProperty(g, "selected", g.isSelected());
                        ret.addListener(new ChangeListener<Boolean>() {
                            @Override
                            public void changed(
                                    ObservableValue<? extends Boolean> observable,
                                    Boolean oldValue, Boolean newValue) {
                                g.setSelected(newValue);
                            }
                        });
                        return ret;
                    }
                }));


        TableColumn<User, String> nickname = new TableColumn<>("联系人");
        nickname.setPrefWidth(100);
        nickname.setCellValueFactory(
                new PropertyValueFactory<User, String>("nickname")
        );

        TableColumn<User, String> phone = new TableColumn<>("手机");
        phone.setPrefWidth(150);
        phone.setCellValueFactory(
                new PropertyValueFactory<User, String>("phone")
        );

        TableColumn<User, String> email = new TableColumn<>("邮箱");
        email.setPrefWidth(150);
        email.setCellValueFactory(
                new PropertyValueFactory<User, String>("email")
        );

        TableColumn<User, String> group = new TableColumn<>("分组");
        group.setPrefWidth(150);
        group.setCellValueFactory(
                new PropertyValueFactory<User, String>("group")
        );

        TableColumn<User, String> remark = new TableColumn<>("备注");
        remark.setPrefWidth(170);
        remark.setCellValueFactory(
                new PropertyValueFactory<User, String>("remark")
        );

//        TableColumn<User, String> birthday = new TableColumn<>("生日");
//        birthday.setPrefWidth(100);
//        birthday.setCellValueFactory(
//                new PropertyValueFactory<User, String>("birthday")
//        );
//
//        TableColumn<User, String> emailcode = new TableColumn<>("邮编");
//        emailcode.setPrefWidth(100);
//        emailcode.setCellValueFactory(
//                new PropertyValueFactory<User, String>("emailcode")
//        );

        ObservableList<User> datalist = FXCollections.observableList(userList);
        dataList = datalist;  // 把最新的用户表存起来
        userTab.setItems(datalist);
        userTab.getColumns().addAll(selectedContacts,nickname, phone, email, group, remark);
    }

    /**
    *
    * @param
    * @return java.lang.Boolean
    * @author reason-llh
    * @date 2022/5/9 12:21
    * @description 判断表格中是否没有联系人 & 判断是否没有联系人被选中
    **/
    public Boolean selectedJudge(){
        // 判断表格是否有联系人
        int size = dataList.size();
        if (size <= 0) {
            AlertUtil.error("没有联系人");
            return true;
        }
        Boolean flag = true;  // 假设没有联系人被选中
        for (int i = size - 1; i >= 0; i--) {
            User u = dataList.get(i);
            if (u.isSelected()) {
                flag = false;
                break;
            }
        }
        if(flag){
            // 没有联系人被选中
            AlertUtil.error("请选择联系人");
        }
        return flag;
    }

    @FXML
    public void addUser() throws IOException {
        // 新增联系人
        Parent root = FXMLLoader.load(getClass().getResource("../view/adduser.fxml"));
        Stage stage = Main.getStage();
        stage.setTitle("添加联系人");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void deleteUser() {
        // 删除联系人
        /**
        User selectedItem = userTab.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            AlertUtil.error("请选择一条记录");
            return;
        }
         */
        // 没有联系人被选中
        if(selectedJudge()){
            return;
        }
        if (AlertUtil.confirm("确定要删除用户?")) {
            int size = dataList.size();
            for (int i = size - 1; i >= 0; i--) {
                User u = dataList.get(i);
                if (u.isSelected()) {
                    dbUtil.deleteUser(Integer.parseInt(u.getId()));
                }
            }
            showTab();
        }
    }


    @FXML
    public void toUpdate() {
        // 修改联系人
        User selectedItem = userTab.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            AlertUtil.error("请选择一条记录");
            return;
        }
        Parent root = null;
        try {
            UpdateUserController.setCurrent(selectedItem);
            root = FXMLLoader.load(Main.class.getResource("../view/updateuser.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = Main.getStage();
        stage.setTitle("修改联系人");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void search() {
        showTab();
    }

    @FXML
    public void addGroup() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/addgroup.fxml"));
        Stage stage = Main.getStage();
        stage.setTitle("新建分组");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void deleteGroup() {
        Group selectedItem = groupTab.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            AlertUtil.error("请选择待删除的分组记录");
            return;
        }
        if (AlertUtil.confirm("确定要删除分组?")) {
            System.out.println("==============");
            dbUtil.deleteGroup(Integer.parseInt(selectedItem.getId()));
            List<User> userList = dbUtil.selectUser();
            for (User user : userList) {
                if (user.getGroup()!= null && user.getGroup().equals(selectedItem.getGroupName())) {
                    user.setGroup("");
                    dbUtil.updateUser(user);
                }
            }
            showGroup();
            showAllUser();
        }
    }

    @FXML
    public void toUpdateGroup() {
        Group selectedItem = groupTab.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            AlertUtil.error("请选择待修改的分组记录");
            return;
        }
        Parent root = null;
        try {
            UpdateGroupController.setOldname(selectedItem.getGroupName());
            root = FXMLLoader.load(Main.class.getResource("../view/updategroup.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = Main.getStage();
        stage.setTitle("修改分组");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void moveGroup(){
        // 点击移动分组
        String value = groupBox.getValue();  // 获取当前选择的分组的值
        if(value.equals("")){  // 没有选择分组
            AlertUtil.error("请选择分组");
            return;
        }
        // 没有联系人被选中
        if(selectedJudge()){
            return;
        }
        if (AlertUtil.confirm("确定要将所选联系人移动到该分组?\n\n")) {
            int size = dataList.size();
            for (int i = size - 1; i >= 0; i--) {
                User u = dataList.get(i);
                if (u.isSelected()) {
                    u.setGroup(value);
                    dbUtil.updateUser(u);
                }
            }
            showTab();
        }
    }

    @FXML
    public void moveUserFromGroup(){
        // 没有联系人被选中
        if(selectedJudge()){
            return;
        }
        if (AlertUtil.confirm("确定要将所选联系人从所在分组中移除?\n\n")) {
            int size = dataList.size();
            for (int i = size - 1; i >= 0; i--) {
                User u = dataList.get(i);
                if (u.isSelected()) {
                    u.setGroup("");
                    dbUtil.updateUser(u);
                }
            }
            showTab();
        }
    }

    @FXML
    public void exportUser() {
        // 导出 csv
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv")
        );
        File file = chooser.showSaveDialog(null);
        if (file != null) {
            List<User> userList = dbUtil.selectUser();
            String searchVal = searchField.getText();
            // 如果搜索框有值，筛选user列表
            if (searchVal != null && searchVal.trim().length() > 0) {
                userList = userList.stream().filter(x -> x.getNickname().contains(searchVal)
                        || x.getPhone().contains(searchVal)
                        || x.getGroup().contains(searchVal)
                        || PinyinUtil.getFirstSpell(x.getNickname()).contains(PinyinUtil.getFirstSpell(searchVal))).collect(Collectors.toList());
            }
            // 如果当前已选择分组，按分组筛选user列表
            if (groupVal != null) {
                userList = userList.stream().filter(x -> x.getGroup().equals(groupVal)).collect(Collectors.toList());
            }
            List<String[]> contentList = new ArrayList<>();
            for (User user : userList) {
                String[] content = {user.getNickname(), user.getEmail(), user.getPhone(), user.getGroup(),
                        user.getFilePath(), user.getRemark(), user.getAddress(), user.getWorkUnit(),
                        user.getMainPage(), user.getEmailcode(), user.getBirthday(), user.getDh()};
                contentList.add(content);
            }
            try {
                CsvUtil.write(file.getAbsolutePath(), contentList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void importUser() {
        // 导入csv
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv")
        );
        File file = chooser.showOpenDialog(null);
        if (file != null) {
            try {
                // csv导入工具使用
                List<User> userList = CsvUtil.read(file.getAbsolutePath(), true);
                for (User user : userList) {
                    dbUtil.addUser(user);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        showTab();
    }

    @FXML
    public void importVcf() {
        // 导入vcf
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("VCF", "*.vcf")
        );
        File file = chooser.showOpenDialog(null);
        if (file != null) {
            List<User> userList = VcfUtil.read(file.getAbsolutePath());
            for (User user : userList) {
                dbUtil.addUser(user);
            }
        }
        showTab();
    }

    @FXML
    public void showAllUser(){
        //全部联系人按钮，分组值置为空，搜索框值清除，再重新展示用户表格
        groupVal = null;
        searchField.clear();
        showTab();
    }

    @FXML
    public void showNoGroupUser(){
        groupVal = "";
        showTab();
    }

    @FXML
    public void exportVcf() {
        // 导出为vcf
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("VCF", "*.vcf")
        );
        File file = chooser.showSaveDialog(null);
        if (file != null) {
            List<User> userList = dbUtil.selectUser();
            String searchVal = searchField.getText();
            if (searchVal != null && searchVal.trim().length() > 0) {
                userList = userList.stream().filter(x -> x.getNickname().contains(searchVal)
                        || x.getPhone().contains(searchVal)
                        || x.getGroup().contains(searchVal)
                        || PinyinUtil.getFirstSpell(x.getNickname()).contains(PinyinUtil.getFirstSpell(searchVal))).collect(Collectors.toList());
            }
            if (groupVal != null) {
                userList = userList.stream().filter(x -> x.getGroup().equals(groupVal)).collect(Collectors.toList());
            }
            VcfUtil.write(userList, file.getAbsolutePath());

        }
    }
}
