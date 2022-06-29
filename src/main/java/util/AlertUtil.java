package util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
* @ClassName AlertUtil
* @Author reason-llh
* @Date 2022/4/13 11:48
* @Description 弹窗工具类 -- 操作成功/操作失败/确认操作
* @Version 1.0.0
**/

public class AlertUtil {

    /**
    *
    * @param msg
    * @author reason-llh
    * @date 2022/4/13 11:55
    * @description 操作出错弹窗
    **/
    public static void error(String msg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("出错了");
        alert.setContentText(msg);
        alert.show();
    }

    /**
    *
    * @param msg
    * @author reason-llh
    * @date 2022/4/13 12:01
    * @description 操作成功弹窗
    **/
    public static void info(String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("操作成功");
        alert.setContentText(msg);
        alert.show();
    }

    /**
    *
    * @param msg
    * @return boolean
    * @author reason-llh
    * @date 2022/4/13 12:04
    * @description 确认操作弹窗  true->确认 false->取消
    **/
    public static boolean confirm(String msg){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("操作确认");
        alert.setContentText(msg);
        Optional<ButtonType> type = alert.showAndWait();
        if (type.isPresent()) {
            ButtonType buttonType = type.get();
            boolean ok_done = buttonType.getText().equals("确定");
            System.out.println(ok_done);
            return ok_done;
        }
        return false;
    }
}
