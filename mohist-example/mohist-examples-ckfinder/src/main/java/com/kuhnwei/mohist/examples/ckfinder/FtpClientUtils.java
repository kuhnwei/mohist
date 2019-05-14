package com.kuhnwei.mohist.examples.ckfinder;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;

/**
 * @author Kuhn Wei
 * @date Created on 13:54 2017/11/8
 */
public class FtpClientUtils {

    /**
     * FTP客户端对象
     */
    private FTPClient ftpClient;

    /**
     * FTP服务器地址
     */
    private String server;

    /**
     * FTP端口号
     */
    private int port;

    /**
     * FTP用户名
     */
    private String username;

    /**
     * FTP密码
     */
    private String password;

    /**
     * 字符串："UTF-8"
     */
    private static final String UTF_8 = "UTF-8";

    /**
     * 带参构造函数
     * @param server FTP服务器地址
     * @param port FTP服务器端口号
     * @param username FTP用户名
     * @param password FTP密码
     */
    public FtpClientUtils(String server, int port, String username, String password) {
        this.server = server;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    /**
     * 连接FTP服务器
     * @return true:连接成功 ，false:连接失败
     */
    public boolean open() {
        if (this.ftpClient != null && this.ftpClient.isConnected()) {
            return true;
        }
        try {
            this.ftpClient = new FTPClient();
            this.ftpClient.connect(this.server, this.port);
            this.ftpClient.login(this.username, this.password);
            int reply = this.ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                this.close();
                System.exit(1);
            }
            return true;
        } catch (IOException e) {
            System.err.println("[FTP [" + this.server + "] 链接失败！！！]");
            this.close();
            return false;
        }
    }

    /**
     * 获取目录下所有的文件名称
     * @param pathName 目录地址
     * @return  FTPFile数组 或 null
     */
    public FTPFile [] listFTPFile(String pathName) {
        if (!this.ftpClient.isConnected()) {
            return null;
        }
        try {
            return this.ftpClient.listFiles(convertDefaultEncoding(pathName));
        } catch (IOException e) {
            System.err.println("[从 " + pathName + " 目录获取文件名称失败！！！]");
            return null;
        }
    }

    /**
     * 切换工作目录
     * @param pathName 目的目录地址
     * @return true:切换成功，false:切换失败
     */
    public boolean changeWorkingDirectory(String pathName) {
        if (!this.ftpClient.isConnected()) {
            return false;
        }
        try {
            // 将目录地址中的斜杠统一
            pathName = unifiedSlash(pathName);
            char slash = '/';
            if (pathName.indexOf(slash) == -1) {
                // 只有一层目录
                this.ftpClient.changeWorkingDirectory(convertDefaultEncoding(pathName));
            } else {
                // 多层目录
                String[] paths = pathName.split("/");
                for (int i = 0; i < paths.length; i ++) {
                    this.ftpClient.changeWorkingDirectory(convertDefaultEncoding(paths[i]));
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 创建目录，并设置工作目录为当前创建的目录
     * @param pathName 需要创建的目录路径
     * @return true:创建成功，false:创建失败
     */
    public boolean makeDirectory(String pathName) {
        if (!this.ftpClient.isConnected()) {
            return false;
        }
        try {
            pathName = unifiedSlash(pathName);
            char slash = '/';
            if (pathName.indexOf(slash) == -1) {
                // 只有一层目录
                this.ftpClient.makeDirectory(convertDefaultEncoding(pathName));
                this.changeWorkingDirectory(pathName);
            } else {
                // 多层目录
                String[] paths = pathName.split("/");
                for (int i = 0; i < paths.length; i++) {
                    this.ftpClient.makeDirectory(convertDefaultEncoding(paths[i]));
                    this.changeWorkingDirectory(paths[i]);
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 上传文件到FTP
     * @param inputStream 文件流
     * @param pathName 目的目录地址
     * @param fileName 文件名
     * @return
     */
    public boolean uploadFile(InputStream inputStream, String pathName, String fileName) {
        if (!this.ftpClient.isConnected()) {
            return false;
        }
        boolean succeed = false;
        try {
            this.makeDirectory(pathName);
            this.ftpClient.setBufferSize(100000);
            this.ftpClient.setControlEncoding(UTF_8);
            this.ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            succeed = this.ftpClient.storeFile(convertDefaultEncoding(fileName), inputStream);
        } catch (IOException e) {
            this.close();
            System.err.println("上传文件：[" + fileName + "], 到FTP： [" + pathName + "] 失败！！！");
            return false;
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return succeed;
    }

    /**
     * 从FTP服务器下载文件到本地
     * @param ftpFilePath FTP上的文件路径地址
     * @param localFilePath 保存到本地的路径地址
     * @return
     */
    public boolean downloadFile(String ftpFilePath, String localFilePath) {
        if (!this.ftpClient.isConnected()) {
            return false;
        }
        boolean succeed = false;
        this.ftpClient.enterLocalPassiveMode();
        try {
            ftpFilePath = unifiedSlash(ftpFilePath);
            File file = new File(localFilePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            this.ftpClient.retrieveFile(convertDefaultEncoding(ftpFilePath), fileOutputStream);
            fileOutputStream.close();
            succeed = true;
        } catch (IOException e) {
            System.err.println("下载文件：[" + ftpFilePath + "], 到本地： [" + localFilePath + "] 失败！！！");
        }
        return succeed;
    }

    /**
     * 下载FTP上的目录到本地（包括目录下的全部内容）
     * @param pathName FTP目录地址
     * @param localDirName 保存到本地的地址
     * @return true:下载成功，false:下载失败
     */
    public boolean downloadDirectory(String pathName, String localDirName) {
        if (!this.ftpClient.isConnected()) {
            return false;
        }
        // 获取FTP目录下的内容
        FTPFile[] files = this.listFTPFile(pathName);
        if (files != null || files.length > 0) {
            // 如果目录下有内容
            for (FTPFile file : files) {
                String ftpPathName = pathName + "/" + convertUtf8Encoding(file.getName());
                String localPathName = localDirName + "/" + convertUtf8Encoding(file.getName());
                if (file.isFile()) {
                    // 如果是文件，则直接下载到本地
                    this.downloadFile(ftpPathName, localPathName);
                } else {
                    // 如果为目录，则继续递归下载目录下的内容
                    this.downloadDirectory(ftpPathName, localPathName);
                }
            }
        }

        File directory = new File(localDirName);
        directory.mkdirs();
        return true;
    }

    /**
     * 删除FTP上的文件
     * @param filePath 文件路径
     * @return true:删除成功，false:删除失败
     */
    public boolean deleteFile(String filePath) {
        if (!this.ftpClient.isConnected()) {
            return false;
        }
        try {
            return this.ftpClient.deleteFile(convertDefaultEncoding(filePath));
        } catch (IOException e) {
            System.err.println("删除文件：[" + filePath + "] 失败！！！");
            return false;
        }
    }

    /**
     * 删除FTP上的目录
     * @param pathName 目录地址
     * @return true:删除成功，false:删除失败
     */
    public boolean deleteDirectory(String pathName) {
        if (!this.ftpClient.isConnected()) {
            return false;
        }
        try {
            // 获取目录下的文件及子目录列表
            FTPFile[] files = listFTPFile(pathName);
            if (files != null && files.length > 0) {
                // 如果目录下存在文件或子目录，则层层删除
                for (FTPFile file : files) {
                    //获取系统的默认编码，把文件名进行重新读取
                    String filePath = pathName + "/" + convertUtf8Encoding(file.getName());
                    if (file.isFile()) {
                        // 如果为文件则直接删除
                        this.deleteFile(filePath);
                    } else {
                        // 如果为目录则继续递归删除
                        this.deleteDirectory(filePath);
                    }
                }
            }
            // 最后删除目的目录
            return this.ftpClient.removeDirectory(convertDefaultEncoding(pathName));
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 重命名目录或文件
     * @param oldPath 原名
     * @param newPath 新名
     * @return true:成功，false：失败
     */
    public boolean rename(String oldPath, String newPath) {
        if (!this.ftpClient.isConnected()) {
            return false;
        }
        try {
            return this.ftpClient.rename(convertDefaultEncoding(oldPath), convertDefaultEncoding(newPath));
        } catch (IOException e) {
            System.out.println("重命名：oldPath = [" + oldPath + "], newPath = [" + newPath + "] 失败！！！");
            return false;
        }
    }

    /**
     * 关闭FTP的连接
     */
    public void close() {
        if (this.ftpClient != null && this.ftpClient.isConnected()) {
            try {
                this.ftpClient.disconnect();
            } catch (IOException e) {
                System.err.println("FTP [" + this.server + "] 连接关闭失败！！！");
            }
        }
    }

    /**
     * 将目录地址中的斜杠统一
     * @param pathName 目录路径
     * @return 返回处理后的目录地址
     */
    private String unifiedSlash(String pathName) {
        char[] chars = pathName.toCharArray();
        StringBuilder sbPathName = new StringBuilder(256);
        for (int i = 0; i < chars.length; i ++) {
            if ('\\' == chars[i]) {
                sbPathName.append("/");
            } else {
                sbPathName.append(chars[i]);
            }
        }
        return sbPathName.toString();
    }

    /**
     * 把地址编码转成FTP协议默认的编码格式 ISO-8859-1;
     * 因为FTP协议里面，规定文件名编码为iso-8859-1，所以目录名或文件名需要转码。
     * @param pathName 需要转码的字符串
     * @return 转码后的字符串
     */
    private String convertDefaultEncoding(String pathName) {
        try {
            return new String(pathName.getBytes(UTF_8), FTP.DEFAULT_CONTROL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return pathName;
        }
    }

    /**
     * 读取FTP服务器上的文件及目录名时，如果不解码转成系统本地的编码，则中文会出现乱码
     * @param str 需转码的字符串
     * @return 返回转码后的字符串
     */
    private String convertUtf8Encoding(String str) {
        try {
            return new String(str.getBytes(FTP.DEFAULT_CONTROL_ENCODING), UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return str;
        }
    }

    /**
     * 获取：FTPClient对象
     * @return FTPClient对象
     */
    public FTPClient getFtpClient() {
        return ftpClient;
    }

    /**
     * 设置：FTPClient对象
     * @param ftpClient FTPClient对象
     */
    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }
}
