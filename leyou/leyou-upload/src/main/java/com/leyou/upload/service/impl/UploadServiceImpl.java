package com.leyou.upload.service.impl;

import com.leyou.common.enums.ExceptionEnums;
import com.leyou.common.exception.LeyouException;
import com.leyou.upload.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class UploadServiceImpl implements UploadService {

    // 支持的文件类型
    private static final List<String> ALLOW_IMAGE_TYPE = Arrays.asList("image/png", "image/jpeg", "image/bmp");
//    文件的存储地址
    private static final String IMAGE_DIR = "E:\\software\\nginx-1.12.2\\html\\images";
//    图片的访问路径
    private static final String IMAGE_URL = "http://image.leyou.com/images/";

    /**
     * 上传图片
     * @param file
     * @return
     */
    @Override
    public String upload(MultipartFile file) {

        //1.获得file的类型
        String contentType = file.getContentType();
        //判断类型是否包含在suffixes中
        if (!ALLOW_IMAGE_TYPE.contains(contentType)){
        //不包含 抛异常
            throw new LeyouException(ExceptionEnums.INVALID_FILE_TYPE);
        }

        //2.文件内容的校验
        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image==null){
                throw new  RuntimeException();
            }
        } catch (Exception e) {
            throw new LeyouException(ExceptionEnums.INVALID_FILE_TYPE,e);
        }


//       文件的存储地址
//       获得原始文件的名称
        String originalFilename = file.getOriginalFilename();
//       分割最后一个 . 获得文件的后缀名
        String extension = StringUtils.substringAfterLast(originalFilename, ".");
//       随机生成文件名
        String uuid = UUID.randomUUID().toString();
//       拼接成为新的文件名
        originalFilename = uuid + "." + extension;
//       获得文件的路径
        File imagePath = new File(IMAGE_DIR, originalFilename);

//        保存文件
//        把文件转移到该路径下
        try {
            file.transferTo(imagePath);
        } catch (IOException e) {
//            日志
            log.error("文件上传失败！原因：{}", e.getMessage(), e);
//            抛出文件上穿异常
            throw new LeyouException(ExceptionEnums.FILE_UPLOAD_ERROR);
        }

//     返回图片的访问路径
        return IMAGE_URL+originalFilename;
    }

    /*@Autowired
    private OSS ossClient;

    @Autowired
    private OSSProperties  prop;

    public Map<String, Object> getSignature() {

        try {
            long expireTime = prop.getExpireTime();
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, prop.getMaxFileSize());
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, prop.getDir());

            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            Map<String, Object> respMap = new LinkedHashMap<>();
            respMap.put("accessId", prop.getAccessKeyId());
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", prop.getDir());
            respMap.put("host", prop.getHost());
            respMap.put("expire", expireEndTime);
            return respMap;
        }catch (Exception e){
            throw new LyException(ExceptionEnum.UPDATE_OPERATION_FAIL);
        }

    }*/
}
