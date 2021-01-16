package com.blindskipper.ray.gui.support;

import java.io.File;
import java.net.URL;
import java.util.function.Consumer;

import com.blindskipper.ray.common.FileAssembly;
import com.blindskipper.ray.common.helper.UrlHelper;
import com.blindskipper.ray.gui.parsed.HexText;
import com.blindskipper.ray.gui.tree.ZipTreeLoader;
import com.blindskipper.ray.gui.tree.ZipTreeNode;
import javafx.concurrent.Task;

public class OpenFileTask extends Task<OpenFileResult> {

    private final String url;

    public OpenFileTask(String url) {
        this.url = url;
    }

    @Override
    protected OpenFileResult call() throws Exception {
        System.out.println("loading " + url + "...");

        FileType fileType = FileTypeInferer.inferFileType(url);
        if (fileType.isZip()) {
            ZipTreeNode rootNode = ZipTreeLoader.load(new File(new URL(url).toURI()));
            return new OpenFileResult(url, fileType, rootNode);
        }

        byte[] data =UrlHelper.readData(url);
        if (fileType == FileType.UNKNOWN) {
            fileType = FileTypeInferer.inferFileType(data);
        }

        FileAssembly fc = fileType.parser.parse(data);
        fc.setName(UrlHelper.getFileName(url));
        HexText hex = new HexText(data);

        return new OpenFileResult(url, fileType, fc, hex);
    }

    public void setOnSucceeded(Consumer<OpenFileResult> callback) {
        super.setOnSucceeded(
                e -> callback.accept((OpenFileResult) e.getSource().getValue()));
    }

    public void setOnFailed(Consumer<Throwable> callback) {
        super.setOnFailed(event -> {
            Throwable err = event.getSource().getException();
            err.printStackTrace(System.err);

            callback.accept(err);
        });
    }

    public void startInNewThread() {
        new Thread(this).start();
    }

}
