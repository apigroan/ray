package com.blindskipper.ray.gui;

import com.blindskipper.ray.gui.support.FileType;
import com.blindskipper.ray.gui.support.ImageHelper;
import com.blindskipper.ray.gui.support.RecentOpenFile;
import com.blindskipper.ray.gui.support.RecentOpenFiles;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;

import java.util.function.BiConsumer;

public final class RayMenuNavigationBar extends MenuBar {

    private BiConsumer<FileType, String> onOpenFile;

    public RayMenuNavigationBar() {
        createFileMenu();
        createHelpMenu();
    }

    private void createFileMenu() {
        Menu fileMenu = new Menu("_File");
        fileMenu.getItems().add(createOpenMenu());
        fileMenu.getItems().add(createRecentMenu());
        fileMenu.setMnemonicParsing(true);
        getMenus().add(fileMenu);
    }

    private Menu createOpenMenu() {
        ImageView folderIcon = ImageHelper.createImageView("/folder.png");
        Menu openMenu = new Menu("_Open", folderIcon);
        openMenu.getItems().add(createOpenFolderItem(folderIcon));
        openMenu.getItems().add(createOpenMenuItem(FileType.JAVA_JAR));
        openMenu.getItems().add(createOpenMenuItem(FileType.JAVA_JMOD));
        openMenu.getItems().add(createOpenMenuItem(FileType.JAVA_CLASS));
        openMenu.setMnemonicParsing(true);
        return openMenu;
    }

    private MenuItem createOpenFolderItem(ImageView icon) {
        MenuItem item = new MenuItem("Folder ... ", icon);
        item.setOnAction(e -> onOpenFile.accept(FileType.FOLDER, null));
        return item;
    }

    private MenuItem createOpenMenuItem(FileType ft) {
        String text = ft.filter.getDescription() + " ...";
        ImageView icon = new ImageView(ft.icon);
        MenuItem item = new MenuItem(text, icon);
        item.setOnAction(e -> onOpenFile.accept(ft, null));
        return item;
    }

    private Menu createRecentMenu() {
        Menu recentMenu = new Menu("Open _Recent", ImageHelper.createImageView("/recent.png"));
        for (RecentOpenFile rf : RecentOpenFiles.INSTANCE.getAll()) {
            ImageView icon = new ImageView(rf.type.icon);
            MenuItem menuItem = new MenuItem(rf.url.toString(), icon);
            menuItem.setOnAction(e -> onOpenFile.accept(rf.type, rf.url));
            recentMenu.getItems().add(menuItem);
        }
        recentMenu.setMnemonicParsing(true);
        return recentMenu;
    }
    
    private void createHelpMenu() {
        MenuItem aboutMenuItem = new MenuItem("_About");
        aboutMenuItem.setOnAction(e -> AboutDialog.showDialog());
        aboutMenuItem.setMnemonicParsing(true);

        Menu helpMenu = new Menu("_Help");
        helpMenu.getItems().add(aboutMenuItem);
        helpMenu.setMnemonicParsing(true);

        getMenus().add(helpMenu);
    }

    public void setOnOpenFile(BiConsumer<FileType, String> onOpenFile) {
        this.onOpenFile = onOpenFile;
    }

    public void updateRecentFiles() {
        Menu fileMenu = getMenus().get(0);
        fileMenu.getItems().set(1, createRecentMenu());
    }

}
