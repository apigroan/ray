package com.blindskipper.ray.gui;

import com.blindskipper.ray.common.helper.UrlHelper;
import com.blindskipper.ray.gui.parsed.ParsedViewerPane;
import com.blindskipper.ray.gui.support.FileType;
import com.blindskipper.ray.gui.support.ImageHelper;
import com.blindskipper.ray.gui.support.OpenFileResult;
import com.blindskipper.ray.gui.support.OpenFileTask;
import com.blindskipper.ray.gui.support.RecentOpenFiles;
import com.blindskipper.ray.gui.tree.DirTreeView;
import com.blindskipper.ray.gui.tree.ZipTreeView;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author blindskipper
 * @version 1.0
 * @className com.blindskipper.ray.gui.RayApplication
 * @description ray application main class
 * @date 2021/1/15 5:34 PM
 **/
public class RayApplication extends Application {
    
    private static final String TITLE = "ray";
    
    
    private Stage stage;
    private BorderPane root;
    private RayMenuNavigationBar menuBar;
    
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        
        root = new BorderPane();
        root.setTop(createMenuBar());
        root.setCenter(createTabPane());
        
        Scene scene = new Scene(root, 960, 540);
        enableDragAndDrop(scene);
        
        stage.setScene(scene);
        stage.setTitle(TITLE);
        stage.getIcons().add(ImageHelper.loadImage("/main.png"));
        stage.getIcons().add(ImageHelper.loadImage("/main.png"));
        stage.show();
        
        String userDir = System.getProperty("user.dir");
        for (String arg : this.getParameters().getRaw()) {
            String path = arg;
            if (!arg.startsWith("/")) {
                path = userDir + "/" + arg;
            }
            openFile(new File(path));
        }
    }
    
    private TabPane createTabPane() {
        TabPane tp = new TabPane();
        tp.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) -> {
                    if (newTab != null) {
                        stage.setTitle(TITLE + " - " + newTab.getUserData());
                    }
                });
        return tp;
    }
    
    private Tab createFileTab(String url) {
        Tab tab = new Tab();
        tab.setText(UrlHelper.getFileName(url));
        tab.setUserData(url);
        tab.setContent(new BorderPane(new ProgressBar()));
        ((TabPane) root.getCenter()).getTabs().add(tab);
        return tab;
    }
    
    private Tab createDirTab(File dir) {
        Tab tab = new Tab();
        tab.setText(dir.getName() + "/");
        tab.setContent(new BorderPane(new ProgressBar()));
        ((TabPane) root.getCenter()).getTabs().add(tab);
        return tab;
    }
    
    private MenuBar createMenuBar() {
        menuBar = new RayMenuNavigationBar();
        menuBar.setOnOpenFile(this::onOpenFile);
        return menuBar;
    }
    
    private void enableDragAndDrop(Scene scene) {
        scene.setOnDragOver(event -> {
            Dragboard db = event.getDragboard();
            if (db.hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
            } else {
                event.consume();
            }
        });
        
        scene.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                success = true;
                for (File file : db.getFiles()) {
                    openFile(file);
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }
    
    private void onOpenFile(FileType ft, String url) {
        if (ft == FileType.FOLDER) {
            openDir(url);
        } else if (url == null) {
            showFileChooser(ft);
        } else {
            openFile(url);
        }
    }
    
    private void openDir(String url) {
        File dir = null;
        if (url != null) {
            try {
                dir = new File(new URL(url).toURI());
            } catch (MalformedURLException | URISyntaxException e) {
                e.printStackTrace(System.err);
            }
        } else {
            dir = RayFileChooser.showDirChooser(stage);
        }
        
        if (dir != null) {
            System.out.println(dir);
            try {
                DirTreeView treeView = DirTreeView.create(dir);
                treeView.setOpenFileHandler(this::openFile);
                
                Tab tab = createDirTab(dir);
                tab.setContent(treeView.getTreeView());
                
                RecentOpenFiles.INSTANCE.add(FileType.FOLDER, dir.toURI().toURL().toString());
                menuBar.updateRecentFiles();
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        }
    }
    
    private void showFileChooser(FileType ft) {
        File file = RayFileChooser.showFileChooser(stage, ft);
        if (file != null) {
            openFile(file);
        }
    }
    
    private void openFile(File file) {
        try {
            openFile(file.toURI().toURL().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace(System.err);
        }
    }
    
    private void openFile(String url) {
        Tab tab = createFileTab(url);
        OpenFileTask task = new OpenFileTask(url);
        
        task.setOnSucceeded((OpenFileResult ofr) -> {
            if (ofr.fileType.isZip()) {
                ZipTreeView treeView = new ZipTreeView(ofr.url, ofr.zipRootNode);
                treeView.setOpenFileHandler(this::openFile);
                tab.setContent(treeView.getTreeView());
            } else {
                ParsedViewerPane viewerPane = new ParsedViewerPane(ofr.fileRootNode, ofr.hexText);
                tab.setContent(viewerPane);
            }
            
            RecentOpenFiles.INSTANCE.add(ofr.fileType, url);
            menuBar.updateRecentFiles();
        });
        
        task.setOnFailed((Throwable err) -> {
            Text errMsg = new Text(err.toString());
            tab.setContent(errMsg);
        });
        
        task.startInNewThread();
    }
    
    public static void main(String[] args) {
        Application.launch(args);
    }
}
