package com.common.network;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.common.utils.LogUtil;
import com.common.utils.StringUtil;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.IdentityHashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;

/**
 * Created by jacktian on 15/8/19.
 */
public class HttpUtil {

    public static final String USER_AGENT = "WJK Android Client v2.0";
    public static final String MULTIPART_DATA_NAME = "file";
    public static final String TAG = HttpUtil.class.getSimpleName();

    static OkHttpClient client;


    /**
     * get方式
     * @param url
     * @return
     */
    public static String httpGet(Context context, String url) {
        if (url != null){
            Request request = new Request.Builder()
//                    .addHeader("User-Agent", USER_AGENT)
                    .url(url)
                    .build();
            Response response = null;
            try {
                response = getCustomClient().newCall(request).execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                }


            } catch (IOException e) {
                LogUtil.e(TAG, e.toString());
            }
        }

        return "";


    }
    /**
     * get方式
     * @param url
     * @return
     */
    public static String httpGet(String url,String lastModified,Context context) {
        if (url != null && url.contains("?")){
            Request request = new Request.Builder()
                    .addHeader("User-Agent", USER_AGENT)
//                    .addHeader("If-Modified-Since", lastModified)
                    .url(url)
                    .build();
            Response response;
            try {
                response = getCustomClient().newCall(request).execute();
                if (response.isSuccessful()) {
                    LastModified.saveLastModified(context, url.hashCode() + "", response.header("Last-Modified"));
                    return response.body().string();
                }


            } catch (IOException e) {
                LogUtil.e(TAG, e.toString());
            }
        }

        return "";


    }

    /**
     * post方式
     * @param url
     * @param postParameters
     * @return
     */
    public static String httpPost(Context context, String url, IdentityHashMap<String, String> postParameters) {
        try {
            FormEncodingBuilder builder = new FormEncodingBuilder();
            if  (postParameters == null) {
                postParameters = new IdentityHashMap<>();
            }
            if (url != null){
                Set<String> set = postParameters.keySet();
                for (String key : set) {
                    builder.add(key, postParameters.get(key));
                }
                RequestBody formBody = builder.build();
                Request request = new Request.Builder()
                        .addHeader("User-Agent", USER_AGENT)
                        .url(url)
                        .post(formBody)
                        .build();

                Response response = getCustomClient().newCall(request).execute();

                if (response.isSuccessful()) {
                    return response.body().string();
                }
            }

        } catch (Exception e) {
            LogUtil.e(TAG, "request error " + e.toString());
        } finally {
        }
        return "";
    }

	/**
	 * post方式传输文件
	 * @param context
	 * @param url
	 * @param postParameters
	 * @param file
	 * @return
	 */
	public static String httpPost(Context context, String url,IdentityHashMap<String, String> postParameters, String streamName, File file){
		try {
			if (file.exists() && file.isFile() && url != null){
				FileNameMap fileNameMap = URLConnection.getFileNameMap();
				String type = fileNameMap.getContentTypeFor(file.getAbsolutePath());
				Set<String> set = postParameters.keySet();
				MultipartBuilder multipartBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);
				for (String key : set) {
					multipartBuilder.addFormDataPart(key, postParameters.get(key));
				}
                if (StringUtil.isEmpty(streamName)){
                    streamName = MULTIPART_DATA_NAME;
                }
				multipartBuilder.addFormDataPart(streamName, file.getName(), RequestBody.create(MediaType.parse(type), file));
				RequestBody requestBody = multipartBuilder.build();
				Request request = new Request.Builder()
						.addHeader("User-Agent", USER_AGENT)
						.url(url)
						.post(requestBody)
						.build();
				Response response = getCustomClient().newCall(request).execute();
				if (response.isSuccessful()) {
					return response.body().string();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

    /**
     * put方式
     * @param url
     * @param postParameters
     * @return
     */
    public static String httpPut(Context context, String url, IdentityHashMap<String, String> postParameters) {
        try {
            FormEncodingBuilder builder = new FormEncodingBuilder();
            if  (postParameters == null) {
                postParameters = new IdentityHashMap<String, String>();
            }
            if (url != null && url.contains("?")){
//                Uri uri = Uri.parse(url);
//                String uriScheme = uri.getScheme();
//                String uriAuthority = uri.getAuthority();
//                String uriPath = uri.getPath();
//                for (String key : uri.getQueryParameterNames()){
//
//                    postParameters.put(key, uri.getQueryParameter(key));
//                }
//                url = uriScheme + "://" + uriAuthority;
//                postParameters = securityCheckParams(context, postParameters, uriPath);
                Set<String> set = postParameters.keySet();
                for (String key : set) {
                    builder.add(key, Uri.encode(postParameters.get(key), "UTF-8"));
                }
                RequestBody formBody = builder.build();
                Request request = new Request.Builder()
                        .addHeader("User-Agent", USER_AGENT)
                        .url(url)
                        .put(formBody)
                        .build();

                Response response = getCustomClient().newCall(request).execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                }
            }
        } catch (Exception e) {
            LogUtil.e("HttpUtil", e.toString());
        } finally {
        }
        return "";

    }

    /**
     * delete方式
     * @param url
     * @return
     */
    public static String httpDelete(Context context, String url, IdentityHashMap<String, String> postParameters) {
        try {
            FormEncodingBuilder builder = new FormEncodingBuilder();
            if  (postParameters == null) {
                postParameters = new IdentityHashMap<String, String>();
            }
            if (url != null && url.contains("?")){
//                Uri uri = Uri.parse(url);
//                String uriScheme = uri.getScheme();
//                String uriAuthority = uri.getAuthority();
//                String uriPath = uri.getPath();
//                for (String key : uri.getQueryParameterNames()){
//
//                    postParameters.put(key, uri.getQueryParameter(key));
//                }
//                url = uriScheme + "://" + uriAuthority;
//                postParameters = securityCheckParams(context, postParameters, uriPath);
                Set<String> set = postParameters.keySet();
                for (String key : set) {
                    builder.add(key, Uri.encode(postParameters.get(key), "UTF-8"));
                }
                RequestBody formBody = builder.build();
                Request request = new Request.Builder()
                        .addHeader("User-Agent", USER_AGENT)
                        .url(url)
                        .method("DELETE", formBody)
                        .build();

                Response response = getCustomClient().newCall(request).execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                }
            }
        } catch (Exception e) {
            LogUtil.e("HttpUtil", e.toString());
        } finally {
        }
        return "";

    }






    /**
     *初始化网络请求Client对象
     */
    private static OkHttpClient getCustomClient() {

        if (client == null) {
            synchronized (HttpUtil.class) {
                if (client == null) {
                    client = new OkHttpClient();

                    if (LogUtil.isDebug) {
                        client.networkInterceptors().add(new StethoInterceptor());
                    }
//                    setCertificateForString();
//                    client.setCertificatePinner(new CertificatePinner.Builder().add(Config.DOMAIN_NAME, "sha256/" + Config.HTTPS_SHA256).build());
                    client.setConnectTimeout(10, TimeUnit.SECONDS);
                    client.setWriteTimeout(30, TimeUnit.SECONDS);
                    client.setReadTimeout(30, TimeUnit.SECONDS);
                    client.setHostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            Log.e(TAG, "hostname : " + hostname + " session : " + session.getPeerHost() + " " + session.getProtocol()
                                    + " : " + session.getCipherSuite()
//                                    + " : " + session.getValueNames()[0]
                                    + (hostname.equals(session.getPeerHost())));
                            try {
                                for (Certificate certificate : session.getPeerCertificates()){
                                    try {
                                        Log.e(TAG, "public key : " + certificate.getPublicKey()
                                                              + " type : " + certificate.getType()
                                                              + " SubjectAlternativeName : " + ((X509Certificate)certificate).getSubjectAlternativeNames()
                                                              + " OID : " + ((X509Certificate)certificate).getSigAlgOID());
                                    } catch (CertificateParsingException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } catch (SSLPeerUnverifiedException e) {
                                e.printStackTrace();
                            }

                            return true;
                        }
                    });
                }
            }

        }

        return client;
    }


}
