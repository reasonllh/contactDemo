package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
* @ClassName controller.Main
* @Author reason-llh
* @Date 2022/4/3 12:57
* @Description 主类, 程序入口类
* @Version 1.0.0
**/

public class Main extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../view/contactlist.fxml"));
        primaryStage.setTitle("个人通讯录");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    /**
    *
    * @param
    * @return void
    * @author reason-llh
    * @date 2022/4/6 20:10
    * @description 用户返回到主界面
    **/
    public static void back(){
        Parent root = null;
        try {
            root = FXMLLoader.load(Main.class.getResource("../view/contactlist.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("个人通讯录");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
