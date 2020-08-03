package com.cloud.util;

import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

/**
 * 证书认证管理器（用于https请求）
 * @author zhuz
 */
public class MyX509TrustManager implements X509TrustManager {
    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
