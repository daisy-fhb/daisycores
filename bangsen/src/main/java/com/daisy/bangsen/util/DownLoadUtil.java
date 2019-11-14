package com.daisy.bangsen.util;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class DownLoadUtil {
    /**
     * 下载 TargetPath下的文件
     * @param fileName 文件名
     * @return 返回结果 成功或者文件不存在
     */
    public static ResponseEntity downloadFile(String fileName, String TargetPath) {
        try {
            // 获取文件名称，中文可能被URL编码
            fileName = URLDecoder.decode(fileName, "UTF-8");
            // 获取本地文件系统中的文件资源
            FileSystemResource resource = new FileSystemResource(TargetPath + fileName);

            // 解析文件的 mime 类型
            String mediaTypeStr = URLConnection.getFileNameMap().getContentTypeFor(fileName);
            // 无法判断MIME类型时，作为流类型
            mediaTypeStr = (mediaTypeStr == null) ? MediaType.APPLICATION_OCTET_STREAM_VALUE : mediaTypeStr;
            // 实例化MIME
            MediaType mediaType = MediaType.parseMediaType(mediaTypeStr);


            /*
             * 构造响应的头
             */
            HttpHeaders headers = new HttpHeaders();
            // 下载之后需要在请求头中放置文件名，该文件名按照ISO_8859_1编码。
            String filenames = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            headers.setContentDispositionFormData("attachment", filenames);
            headers.setContentType(mediaType);
            /*
             * 返还资源
             */
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(resource.getInputStream().available())
                    .body(resource);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
