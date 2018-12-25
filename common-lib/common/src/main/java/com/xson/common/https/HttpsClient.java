package com.xson.common.https;

import android.content.Context;

import com.data.volley.Request;
import com.data.volley.RequestQueue;
import com.data.volley.Response;
import com.data.volley.error.VolleyError;
import com.data.volley.request.StringRequest;
import com.data.volley.toolbox.HurlStack;
import com.data.volley.toolbox.Volley;
import com.xson.common.helper.BeanRequest;
import com.xson.common.widget.ProgressHUD;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by xkai on 2018/3/12.
 */

public class HttpsClient {

    private static RequestQueue requestQueue;
    private final WeakReference<Context> contextWeakReference;
    private ProgressHUD mProgressHUD;
    private static Context context;

    public HttpsClient(Context context) {
        this.context = context;
        this.contextWeakReference = new WeakReference<Context>(context);
//        debug = SysUtils.isDebug(context); //直接显示详细错误
    }

    public synchronized static HttpsClient newInstance(Context context) {
        if (requestQueue == null) {
            HTTPSTrustManager.allowAllSSL();
//            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
            //传入处理Https的HurlStack
            requestQueue = Volley.newRequestQueue(context.getApplicationContext(),new HurlStack(null, initSSLSocketFactory()));
        }
        return new HttpsClient(context);
    }

    public void request(String url, final ListBeanListener listener) {
        if (contextWeakReference.get() == null)
            return;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new BeanRequest.SuccessListener<String>() {
            @Override
            public void onResponse(String response) {
                if (listener != null) {
                    listener.onResponse(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (listener != null) {
                    listener.onErrorResponse(error);
                }
            }
        });
        requestQueue.add(stringRequest);
    }

    public interface ListBeanListener {
        void onResponse(String response);

        void onErrorResponse(VolleyError error);
    }

    //生成SSLSocketFactory
    private static SSLSocketFactory initSSLSocketFactory() {
        //生成证书:Certificate
        CertificateFactory cf = null;
        SSLSocketFactory factory = null;
        try {
            cf = CertificateFactory.getInstance("X.509");
            InputStream caInput = context.getAssets().open("root.crt");
            Certificate ca = null;
            try {
                ca = cf.generateCertificate(caInput);
            } finally {
                try {
                    caInput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //初始化公钥:keyStore
            String keyType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            //初始化TrustManagerFactory
            String algorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory managerFactory = TrustManagerFactory.getInstance(algorithm);
            managerFactory.init(keyStore);

            //初始化sslContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, managerFactory.getTrustManagers(), null);
            factory = sslContext.getSocketFactory();

        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        return factory;
    }

}
