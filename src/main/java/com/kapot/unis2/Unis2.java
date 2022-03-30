package com.kapot.unis2;

import com.kapot.unis2.ui.controller.CipherSceneController;
import com.kapot.unis2.ui.util.ThreadManager;
import com.kapot.unis2.util.UnisProps;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.URL;

public class Unis2 extends Application {

    /*
    TODO: file writing dont resets old file
     */

    public static final String CRYPTOFILE_SUFFIX = ".uniscrypt";

    private Stage stage;
    private UnisProps props;
    private ThreadManager threadManager;

    public static void main(String... args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.stage = primaryStage;
        this.props = new UnisProps(getResourceAsStream("props.properties")); //TODO: close stream?
        this.threadManager = new ThreadManager();

        URL fxmlUrl = getResource("assets/scenes/cipherScene.fxml");
        URL stylesheetUrl = getResource("assets/scenes/stylesheet/general.css");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(fxmlUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(stylesheetUrl.toExternalForm());

//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//
//        primaryStage.setHeight(screenSize.getHeight() / 1.2);
//        primaryStage.setWidth(screenSize.getWidth() / 2);

        primaryStage.setHeight(800.0);
        primaryStage.setWidth(800.0);

        primaryStage.setScene(scene);
        ((CipherSceneController) loader.getController()).init(this);
        primaryStage.setTitle("UnisCryptor2 v. " + props.VERSION + " | b/lkpt");

        primaryStage.getIcons().add(new Image(getResource("assets/icon/icon_16.png").toString()));
        primaryStage.getIcons().add(new Image(getResource("assets/icon/icon_32.png").toString()));
        primaryStage.getIcons().add(new Image(getResource("assets/icon/icon_64.png").toString()));
        primaryStage.getIcons().add(new Image(getResource("assets/icon/icon_256.png").toString()));

        primaryStage.show();

    }

    @Override
    public void stop() {
        threadManager.shutdown();
    }

    public static URL getResource(String name) {
        return Unis2.class.getClassLoader().getResource(name);
    }
    public static InputStream getResourceAsStream(String name) {
        return Unis2.class.getClassLoader().getResourceAsStream(name);
    }

    public Stage getStage() {
        return stage;
    }
    public UnisProps getProps() {
        return props;
    }
    public ThreadManager getThreadManager() {
        return threadManager;
    }

}
