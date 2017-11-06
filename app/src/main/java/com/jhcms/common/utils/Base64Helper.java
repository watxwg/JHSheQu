package com.jhcms.common.utils;

import android.util.Base64;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Base64Helper工具类
 */
public class Base64Helper {

    public static String encodeBase64File(String filePath) {
        try {
            File tmpFile = new File(filePath);
        BufferedInputStream in = new BufferedInputStream(
                new FileInputStream(tmpFile));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        byte[] buffer = new byte[in.available()];
        int length;
        while ((length = in.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    } catch (Exception e) {
        Log.e("tag", "读取视频出现异常");
    }
        return null;
    }



    /**
     * decoderBase64File:(将base64字符解码保存文件). <br/>
     *
     * @param base64Code 编码后的字串
     * @param savePath   文件保存路径
     * @throws Exception
     * @author guhaizhou@126.com
     * @since JDK 1.6
     */
    public static void decoderBase64File(String base64Code, String savePath) throws Exception {
        //byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
        byte[] buffer = Base64.decode(base64Code, Base64.DEFAULT);
        FileOutputStream out = new FileOutputStream(savePath);
        out.write(buffer);
        out.close();
    }
}
