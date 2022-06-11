/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UploadImgServer;

/**
 *
 * @author rss
 */
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
 
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
 
/**
 * A utility class that provides functionality for uploading files to a FTP
 * server.
 *
 * @author www.codejava.net
 *
 */
public class FTPUtility {
 
    private String host;
    private int port;
    private String username;
    private String password;
 
    private FTPClient ftpClient = new FTPClient();
    private int replyCode;
 
    private OutputStream outputStream;
     
    public FTPUtility(String host, int port, String user, String pass) {
        this.host = host;
        this.port = port;
        this.username = user;
        this.password = pass;
    }
 
    /**
     * Connect and login to the server.
     *
     * @throws FTPException
     */
    public void connect() throws Exception {
        try {
            ftpClient.connect(host, port);
            replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                throw new Exception("FTP serve refused connection.");
            }
 
            boolean logged = ftpClient.login(username, password);
            if (!logged) {
                // failed to login
                ftpClient.disconnect();
                throw new Exception("Could not login to the server.");
            }
 
            ftpClient.enterLocalPassiveMode();
 
        } catch (IOException ex) {
            throw new Exception("I/O error: " + ex.getMessage());
        }
    }
 
    /**
     * Start uploading a file to the server
     * @param uploadFile the file to be uploaded
     * @param destDir destination directory on the server
     * where the file is stored
     * @throws FTPException if client-server communication error occurred
     */
    public void uploadFile(File uploadFile, String destDir) throws Exception {
        try {
            boolean success = ftpClient.changeWorkingDirectory(destDir);
            if (!success) {
                throw new Exception("Could not change working directory to "
                        + destDir + ". The directory may not exist.");
            }
             
            success = ftpClient.setFileType(FTP.BINARY_FILE_TYPE);         
            if (!success) {
                throw new Exception("Could not set binary file type.");
            }
             
            outputStream = ftpClient.storeFileStream(uploadFile.getName());
             
        } catch (IOException ex) {
            throw new Exception("Error uploading file: " + ex.getMessage());
        }
    }
 
    /**
     * Write an array of bytes to the output stream.
     */
    public void writeFileBytes(byte[] bytes, int offset, int length)
            throws IOException {
        outputStream.write(bytes, offset, length);
    }
     
    /**
     * Complete the upload operation.
     */
    public void finish() throws IOException {
        outputStream.close();
        ftpClient.completePendingCommand();
    }
     
    /**
     * Log out and disconnect from the server
     */
    public void disconnect() throws Exception {
        if (ftpClient.isConnected()) {
            try {
                if (!ftpClient.logout()) {
                    throw new Exception("Could not log out from the server");
                }
                ftpClient.disconnect();
            } catch (IOException ex) {
                throw new Exception("Error disconnect from the server: "
                        + ex.getMessage());
            }
        }
    }
}