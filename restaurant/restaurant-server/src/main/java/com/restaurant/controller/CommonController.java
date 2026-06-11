package com.restaurant.controller;

import com.restaurant.common.Result;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * File Upload and Download
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    // You can configure this in application.yml
    // For now, hardcode or use a temporary path
    private String basePath = System.getProperty("user.dir") + "/images/";

    /**
     * File Download
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        try {
            // Input Stream: Read file content
            File file = new File(basePath + name);
            
            // Fallback: If not found in basePath, look in src/main/resources/static/images
            // This is useful for initial seed data in development environment
            if (!file.exists()) {
                String projectPath = System.getProperty("user.dir");
                // Adjust path depending on where you run it (root or module)
                File resourceFile = new File(projectPath + "/src/main/resources/static/images/" + name);
                if (resourceFile.exists()) {
                    file = resourceFile;
                } else {
                    // Try without /images prefix
                    resourceFile = new File(projectPath + "/src/main/resources/static/" + name);
                    if (resourceFile.exists()) {
                        file = resourceFile;
                    }
                }
            }

            if (!file.exists()) {
                return; 
            }
            
            FileInputStream fileInputStream = new FileInputStream(file);

            // Output Stream: Write back to browser/client
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }

            // Close resources
            outputStream.close();
            fileInputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
