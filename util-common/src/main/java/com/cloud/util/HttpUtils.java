package com.cloud.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;


/**
 * @author zhuz
 * @date 2020/7/29
 */
public class HttpUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    public static final int TIME_OUT = 10;

    private HttpUtils() {
    }

    /**
     * post 请求 无参数
     *
     * @param url
     * @return {@link String}
     * @author zhuz
     * @date 2020/8/5
     */
    public static String post(String url) {
        return post(url, "");
    }

    /**
     * post 请求 有参数
     *
     * @param url
     * @param data
     * @return {@link String}
     * @author zhuz
     * @date 2020/8/5
     */
    public static String post(String url, String data) {
        return httpPost(url, data);
    }

    /**
     * post 请求
     *
     * @param url
     * @param data
     * @return {@link String}
     * @author zhuz
     * @date 2020/8/5
     */
    private static String httpPost(String url, String data) {
        try {
            HttpEntity entity = Request.Post(url)
                    .bodyString(data, ContentType.create("text/html", Consts.UTF_8))
                    .execute().returnResponse().getEntity();
            return entity != null ? EntityUtils.toString(entity) : null;
        } catch (Exception e) {
            logger.error("post请求异常：" + e + "  post url:" + url);
        }
        return null;
    }

    /************************************************  get请求  ************************************************/

    /**
     * get请求
     *
     * @param url
     * @return {@link String}
     * @author zhuz
     * @date 2020/8/5
     */
    public static String get(String url) {
        return httpGet(url);
    }

    /**
     * 发送get请求
     *
     * @param url
     * @return {@link String}
     * @author zhuz
     * @date 2020/8/5
     */
    private static String httpGet(String url) {
        try {
            HttpEntity entity = Request.Get(url).execute().returnResponse().getEntity();
            return entity != null ? EntityUtils.toString(entity) : null;
        } catch (Exception e) {
            logger.error("get请求异常，" + e + " get url:" + url);
        }
        return "";
    }

    /************************************************ 文件数据  ************************************************/

    /**
     * 上传文件
     *
     * @param url  URL
     * @param file 需要上传的文件
     * @return {@link String}
     * @author zhuz
     * @date 2020/8/5
     */
    public static String postFile(String url, File file) {
        return postFile(url, null, file);
    }

    /**
     * 上传文件
     *
     * @param url  URL
     * @param name 文件的post参数名称
     * @param file 上传的文件
     * @return {@link String}
     * @author zhuz
     * @date 2020/8/5
     */
    public static String postFile(String url, String name, File file) {
        try {
            HttpEntity reqEntity = MultipartEntityBuilder.create().addBinaryBody(name, file).build();
            Request request = Request.Post(url);
            request.body(reqEntity);
            HttpEntity resEntity = request.execute().returnResponse().getEntity();
            return resEntity != null ? EntityUtils.toString(resEntity) : null;
        } catch (Exception e) {
            logger.error("postFile请求异常，" + e + "  post url:" + url);
        }
        return null;
    }

    public static String postFile(String url, InputStream inputStream) {
        try {
            HttpEntity reqEntity = MultipartEntityBuilder.create().addBinaryBody("media", inputStream, ContentType.DEFAULT_BINARY, "ceshi.jpg").build();
            Request request = Request.Post(url);
            request.body(reqEntity);
            HttpEntity resEntity = request.execute().returnResponse().getEntity();
            return resEntity != null ? EntityUtils.toString(resEntity) : null;
        } catch (Exception e) {
            logger.error("postFile请求异常，" + e + "post url:" + url);
        }
        return null;
    }

    public static String postFile(String path, InputStream inputStream, String fileName, long fileSize) throws Exception {
        URL url = new URL(path);
        String result = null;
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        // 以Post方式提交表单，默认get方式
        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);
        // post方式不能使用缓存
        con.setUseCaches(false);
        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");
        // 设置边界
        String boundary = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        // 请求正文信息
        // 第一部分：
        StringBuilder sb = new StringBuilder();
        // 必须多两道线
        sb.append("--");
        sb.append(boundary);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"media\";filelength=\"" + fileSize + "\";filename=\"" + fileName + "\"\r\n");
        sb.append("Content-Type: application/json;charset=UTF-8\r\n\r\n");
        byte[] head = sb.toString().getBytes("utf-8");
        // 获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        // 输出表头
        out.write(head);
        // 文件正文部分
        // 把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(inputStream);
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();
        // 结尾部分  定义最后数据分隔线
        byte[] foot = ("\r\n--" + boundary + "--\r\n").getBytes("utf-8");
        out.write(foot);
        out.flush();
        out.close();
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        try {
            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
        } catch (IOException e) {
            logger.error("发送POST请求出现异常！" + e);
            throw new IOException("数据读取异常");
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return result;
    }

    /**
     * 文件的二进制流，客户端使用outputStream输出为文件
     *
     * @param url
     * @return {@link byte[]}
     * @author zhuz
     * @date 2020/8/5
     */
    public static byte[] getFile(String url) {
        try {
            Request request = Request.Get(url);
            HttpEntity resEntity = request.execute().returnResponse().getEntity();
            return EntityUtils.toByteArray(resEntity);
        } catch (Exception e) {
            logger.error("postFile请求异常，" + e + " post url:" + url);
        }
        return null;
    }

    /************************************************  传送数据  ************************************************/

    /**
     * 传送数据
     *
     * @param requestUrl
     * @param requestMethod
     * @param outputStr
     * @return {@link JSONObject}
     * @author zhuz
     * @date 2020/8/5
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] trustManagers = {new MyX509TrustManager()};
            //自动调用Security.getProviders()获取已经注册的提供者
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustManagers, new SecureRandom());
            // 从上述SSLContext对象中获取SSLSocketFactory对象
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setSSLSocketFactory(sslSocketFactory);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod(requestMethod);
            connection.setReadTimeout(TIME_OUT);
            connection.setConnectTimeout(5000);

            //当有数据需要提交时
            if (StringUtils.isNotEmpty(outputStr)) {
                connection.setDoOutput(true);
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            connection.connect();
            //将返回的输入流转为字符串
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            bufferedReader.close();
            connection.disconnect();
            jsonObject = JSONObject.parseObject(stringBuffer.toString());
        } catch (Exception e) {
            logger.error("httpsRequest请求异常：", e);
        }
        return jsonObject;
    }

}
