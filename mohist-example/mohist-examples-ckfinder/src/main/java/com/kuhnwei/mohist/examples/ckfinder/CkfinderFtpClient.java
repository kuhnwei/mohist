package com.kuhnwei.mohist.examples.ckfinder;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

/**
 * @author Kuhn Wei
 * @date Created on 15:49 2017/11/8
 */
public class CkfinderFtpClient {

    private static final String UPLOAD_PATH = "";
    private static final String SERVER = "192.168.0.217";
    private static final int PORT = 21;
    private static final String USERNAME = "test";
    private static final String PASSWORD = "test";
    private static FtpClientUtils ftpClientUtils = new FtpClientUtils(SERVER, PORT, USERNAME, PASSWORD);

    private static final String FTP_PATH = "/home/test";

    public static void uploadFile(InputStream inputStream, String pathName, String fileName) {
        if (ftpClientUtils.open()) {
            ftpClientUtils.uploadFile(inputStream, pathName, fileName);
            ftpClientUtils.close();
        }
    }

    public static void deleteFile(String filePath) {
        if (ftpClientUtils.open()) {
            ftpClientUtils.deleteFile(FTP_PATH + filePath);
            ftpClientUtils.close();
        }
    }

    public static void createFolder(String pathName) {
        if (ftpClientUtils.open()) {
            ftpClientUtils.makeDirectory(pathName);
            ftpClientUtils.close();
        }
    }

    public static void deleteFolder(String pathName) {
        if (ftpClientUtils.open()) {
            ftpClientUtils.deleteDirectory(FTP_PATH + pathName);
            ftpClientUtils.close();
        }
    }

    public static void rename(String oldPath, String newPath) {
        if (ftpClientUtils.open()) {
            ftpClientUtils.rename(FTP_PATH + oldPath, FTP_PATH + newPath);
            ftpClientUtils.close();
        }
    }

    public static void listFiles(String pathName) {
        if (ftpClientUtils.open()) {
            ftpClientUtils.listFTPFile(pathName);
            ftpClientUtils.close();
        }
    }

    public static void download(HttpServletRequest request, String baseURL) {
        if (ftpClientUtils.open()) {
            String ftpPathName = FTP_PATH + baseURL;
            String localPathName = request.getSession().getServletContext().getRealPath("/userfiles");
            ftpClientUtils.downloadDirectory(ftpPathName, localPathName);
            ftpClientUtils.close();
        }
    }

}
