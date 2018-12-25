package com.xson.common.https;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xkai on 2018/3/13.
 */

public class HttpsDome {

    public static String post(String httpsUrl, String params) {
        String result = null;
        SSLContext sslctxt;
        try {
            // 为请求通TLS协议，生成SSLContext对象
            // 初始化SSLContext
            sslctxt = SSLContext.getInstance("TLS");
            sslctxt.init(null, new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new java.security.SecureRandom());
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslctxt.getSocketFactory());
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return false;
                }
            });
            OkHttpClient client = new OkHttpClient();
            FormBody formBody = new FormBody.Builder()
                    .add("plat", params)
                    .build();
            Request request = new Request.Builder().url(httpsUrl).post(formBody).build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().toString();
            } else {
                throw new IOException("Unexpected code " + response);
            }
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (KeyManagementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }
}