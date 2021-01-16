package com.blindskipper.ray.gui.support;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class RecentOpenFiles {

    public static final RecentOpenFiles INSTANCE = new RecentOpenFiles();


    private final LinkedList<RecentOpenFile> list = new LinkedList<>();
    private boolean listChanged = false;

    private RecentOpenFiles() {
        loadFromTmp();
        Runtime.getRuntime()
                .addShutdownHook(new Thread(this::saveToTmp));
    }

    public File getLastOpenFile(FileType ft) {
        for (RecentOpenFile rf : list) {
            if (rf.type == ft && rf.url.startsWith("file")) {
                try {
                    return new File(new URL(rf.url).toURI());
                } catch (MalformedURLException | URISyntaxException e) {
                    e.printStackTrace(System.err);
                }
            }
        }

        return null;
    }

    public List<RecentOpenFile> getAll() {
        return list;
    }

    public void add(FileType fileType, String fileURL) {
        add(new RecentOpenFile(fileType, fileURL));
    }

    private void add(RecentOpenFile rf) {
        listChanged = true;
        list.remove(rf);
        list.addFirst(rf);
        if (list.size() > 20) {
            list.removeLast();
        }
    }

    private void saveToTmp() {
        if (!list.isEmpty() && listChanged) {
            byte[] bytes = list.stream()
                    .map(RecentOpenFile::toString)
                    .collect(Collectors.joining("\n"))
                    .getBytes(StandardCharsets.UTF_8);

            Path tmp = Paths.get(System.getProperty("java.io.tmpdir"), "ray.tmp");
            System.out.println("saving " + tmp + " ...");
            try {
                Files.write(tmp, bytes);
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
        }
    }

    private void loadFromTmp() {
        Path tmp = Paths.get(System.getProperty("java.io.tmpdir"), "ray.tmp");
        if (Files.exists(tmp)) {
            System.out.println("loading " + tmp + " ...");
            try {
                List<String> rfs = Files.readAllLines(tmp, StandardCharsets.UTF_8);
                for (String rf : rfs) {
                    if (rf.contains("#=>")) {
                        list.addLast(new RecentOpenFile(rf));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
        }
    }

}
