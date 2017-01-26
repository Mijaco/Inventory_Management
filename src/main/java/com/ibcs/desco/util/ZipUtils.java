package com.ibcs.desco.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Zip utility class for to compress and uncompress
 *
 * @author Mohammed Hossain Doula
 * @since 04/16/2016
 */
public class ZipUtils {
    /**
     * list of files in a directory. use for populate image list for recursive
     */
    private static List<String> filesListInDir = new ArrayList<String>();

    /**
     * This method zips a directory
     *
     * @param dir        - directory file path
     * @param zipDirName - zip name - it can be with full classified path
     */
    public static void compress(File dir, String zipDirName) {
        populateFilesList(dir);
        try {
            FileOutputStream fos = new FileOutputStream(zipDirName);
            compress(filesListInDir, dir, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method zips a directory
     *
     * @param dir          - directory file path
     * @param outputStream - output stream where file save
     */
    public static void compress(File dir, OutputStream outputStream) {
        populateFilesList(dir);
        try {
            compress(filesListInDir, dir, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method zips files from list array
     *
     * @param baseDir    - base directory path
     * @param zipDirName - zip name - it can be with full classified path
     * @fileList -  file list array
     */
    public static void compress(List<String> fileList, File baseDir, String zipDirName) {
        try {
            FileOutputStream fos = new FileOutputStream(zipDirName);
            compress(fileList, baseDir, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method zips files from list array
     *
     * @param baseDir      - base directory path
     * @param outputStream - output stream where file save
     * @fileList -  file list array
     */
    public static void compress(List<String> fileList, File baseDir, OutputStream outputStream) {
        try {
            ZipOutputStream zos = new ZipOutputStream(outputStream);
            for (String filePath : fileList) {
                ZipEntry ze = new ZipEntry(filePath.substring(baseDir.getAbsolutePath().length() + 1, filePath.length()));
                zos.putNextEntry(ze);
                FileInputStream fis = new FileInputStream(filePath);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
                fis.close();
            }
            zos.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method populates all the files in a directory to a List
     *
     * @param dir - directory path
     * @throws IOException
     */
    private static void populateFilesList(File dir) {
        try {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    filesListInDir.add(file.getAbsolutePath());
                } else {
                    populateFilesList(file);
                }
            }
        } catch (Exception IEX) {
            IEX.printStackTrace();
        }
    }

    /**
     * Extract the src file
     *
     * @param srcFile
     * @throws IOException
     */
    public static void extract(File srcFile) {
        extract(srcFile, true);
    }

    /**
     * extract zip files from directory path name
     *
     * @param filename - file path name
     */
    public static void extract(String filename) {
        File srcFile = new File(filename);
        extract(srcFile);
    }

    /**
     * extract zip files from directory path name
     *
     * @param srcFile                - source file object
     * @param isExtractSameDirectory - Extract same directory name or individually
     */
    public static void extract(File srcFile, boolean isExtractSameDirectory) {
        String zipPath = "";
        String fileAbsolutePath = srcFile.getAbsolutePath();
        if (isExtractSameDirectory) {
            zipPath = srcFile.getAbsolutePath().substring(0, srcFile.getAbsolutePath().length() - 4);
        } else {
            zipPath = fileAbsolutePath.substring(0, fileAbsolutePath.lastIndexOf(File.separator));
        }
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(srcFile);
            Enumeration<? extends ZipEntry> e = zipFile.entries();
            while (e.hasMoreElements()) {
                ZipEntry entry = e.nextElement();
                String fileName = entry.getName();
                if (fileName != null && fileName.contains("\\")) {
                    fileName = fileName.replace("\\", "/");
                }
                File destinationPath = new File(zipPath, fileName);
                destinationPath.getParentFile().mkdirs();
                if (entry.isDirectory()) {
                    continue;
                }
                BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
                int b;
                byte buffer[] = new byte[1024];
                FileOutputStream fos = new FileOutputStream(destinationPath);
                BufferedOutputStream bos = new BufferedOutputStream(fos, 1024);
                while ((b = bis.read(buffer, 0, 1024)) != -1) {
                    bos.write(buffer, 0, b);
                }
                bos.close();
                bis.close();
            }
        } catch (IOException ioe) {
            System.out.println("Error opening zip file" + ioe);
        } finally {
            try {
                if (zipFile != null) {
                    zipFile.close();
                }
            } catch (IOException ioe) {
                System.out.println("Error while closing zip file" + ioe);
            }
        }
    }
}