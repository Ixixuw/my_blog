package com.example.blog.utils;

import com.alibaba.fastjson.JSON;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


@Component
public class QiniuUtils {
    public static  final String url = "http://rbrt7y84k.hb-bkt.clouddn.com/";
    @Value("F3MngXTWXKgIlU2h8NXgfMzHL_ZcTyIz1j3jpo7k")
    private  String accessKey;
    @Value("ia2iMUtN3MKudjhgkb4zoqXwsb6YI1PVjhdd20mG")
    private  String accessSecretKey;

    public  boolean upload(MultipartFile file, String fileName){

        //����һ����ָ�� Region �����������
        Configuration cfg = new Configuration(Region.huabei());
        //...���������ο���ע��
        UploadManager uploadManager = new UploadManager(cfg);
        //...�����ϴ�ƾ֤��Ȼ��׼���ϴ�
        String bucket = "blogix";
        //Ĭ�ϲ�ָ��key������£����ļ����ݵ�hashֵ��Ϊ�ļ���
        try {
            byte[] uploadBytes = file.getBytes();
            Auth auth = Auth.create(accessKey, accessSecretKey);
            String upToken = auth.uploadToken(bucket);
            Response response = uploadManager.put(uploadBytes, fileName, upToken);
            //�����ϴ��ɹ��Ľ��
            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
