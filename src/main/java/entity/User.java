package entity;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
* @ClassName User
* @Author reason-llh
* @Date 2022/4/22 18:36
* @Description contact实体类User
* @Version 1.0.0
**/

public class User {

    private SimpleStringProperty id = new SimpleStringProperty();

    private SimpleBooleanProperty selected = new SimpleBooleanProperty();

    private SimpleStringProperty nickname = new SimpleStringProperty();

    private SimpleStringProperty phone = new SimpleStringProperty();

    private SimpleStringProperty email = new SimpleStringProperty();

    private SimpleStringProperty group = new SimpleStringProperty();

    private SimpleStringProperty filePath = new SimpleStringProperty();

    private SimpleStringProperty remark = new SimpleStringProperty();

    private SimpleStringProperty address = new SimpleStringProperty();

    private SimpleStringProperty workUnit = new SimpleStringProperty();

    private SimpleStringProperty mainPage = new SimpleStringProperty();

    private SimpleStringProperty emailcode = new SimpleStringProperty();

    private SimpleStringProperty birthday = new SimpleStringProperty();

    private SimpleStringProperty dh = new SimpleStringProperty();


    public SimpleStringProperty idProperty() {
        return id;
    }

    public String getFilePath() {
        return filePath.get();
    }

    public SimpleStringProperty filePathProperty() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath.set(filePath);
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getId() {
        return id.get();
    }

    public String getGroup() {
        return group.get();
    }

    public SimpleStringProperty groupProperty() {
        return group;
    }

    public void setGroup(String group) {
        this.group.set(group);
    }

    public String getNickname() {
        return nickname.get();
    }

    public SimpleStringProperty nicknameProperty() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname.set(nickname);
    }

    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getRemark() {
        return remark.get();
    }

    public SimpleStringProperty remarkProperty() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark.set(remark);
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getWorkUnit() {
        return workUnit.get();
    }

    public SimpleStringProperty workUnitProperty() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit.set(workUnit);
    }

    public String getMainPage() {
        return mainPage.get();
    }

    public SimpleStringProperty mainPageProperty() {
        return mainPage;
    }

    public void setMainPage(String mainPage) {
        this.mainPage.set(mainPage);
    }

    public String getEmailcode() {
        return emailcode.get();
    }

    public SimpleStringProperty emailcodeProperty() {
        return emailcode;
    }

    public void setEmailcode(String emailcode) {
        this.emailcode.set(emailcode);
    }

    public String getBirthday() {
        return birthday.get();
    }

    public SimpleStringProperty birthdayProperty() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday.set(birthday);
    }

    public String getDh() {
        return dh.get();
    }

    public SimpleStringProperty dhProperty() {
        return dh;
    }

    public void setDh(String dh) {
        this.dh.set(dh);
    }

    public boolean isSelected() {
        return selected.get();
    }

    public SimpleBooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }
}
