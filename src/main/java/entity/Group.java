package entity;

import javafx.beans.property.SimpleStringProperty;

/**
* @ClassName Group
* @Author reason-llh
* @Date 2022/4/22 18:54
* @Description contact实体类Group
* @Version 1.0.0
**/

public class Group {

    private SimpleStringProperty id = new SimpleStringProperty();

    private SimpleStringProperty groupName = new SimpleStringProperty();

    public String getGroupName() {
        return groupName.get();
    }

    public SimpleStringProperty groupNameProperty() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName.set(groupName);
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }
}
