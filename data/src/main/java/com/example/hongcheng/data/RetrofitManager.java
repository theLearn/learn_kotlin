package com.example.hongcheng.data;

import android.content.Context;

import com.example.hongcheng.common.util.NetUtils;
import com.example.hongcheng.common.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hongcheng on 16/3/30.
 */
public class RetrofitManager {
    public static Map<String, Object> mRetrofitMap = new HashMap<String, Object>();

    public static <T> T createRetrofit(Context context, Class<T> t){

        T mRetrofit = (T)mRetrofitMap.get(t.getName());

        if(mRetrofit == null){
            //设置缓存
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor);
            //缓存路径
            File httpCache = context.getCacheDir();
            if(httpCache != null){
                httpCache = new File(httpCache, "HttpResponseCache");
                okHttpBuilder.cache(new Cache(httpCache, HttpConstants.HTTP_RESPONSE_DISK_CACHE_MAX_SIZE));
            }
            //设置缓存策略
            okHttpBuilder.addNetworkInterceptor(new CacheInterceptors(context));
            okHttpBuilder.addInterceptor(new CacheInterceptors(context));

            //设置超时时间
            okHttpBuilder.connectTimeout(HttpConstants.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
            okHttpBuilder.readTimeout(HttpConstants.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);

            //okhttp使用https 设置SSL证书
//            okHttpBuilder.sslSocketFactory(NetUtils.createSSLSocketFactory(context, 0));
//            okHttpBuilder.hostnameVerifier(new HostnameVerifier() {
//                @Override
//                public boolean verify(String hostname, SSLSession session) {
//                    if(HttpConstants.HOST_NAME == hostname){
//                        return true;
//                    }
//                    return false;
//                }
//            });

            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(HttpConstants.BASE_URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                    .client(okHttpBuilder.build())
                                    .build();
            mRetrofit = retrofit.create(t);
            mRetrofitMap.put(t.getName(), mRetrofit);
        }

        return mRetrofit;
    }

    private static class CacheInterceptors implements Interceptor {

        private Context context;

        public CacheInterceptors(Context context) {
            this.context = context;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if(!NetUtils.isConnected(context)){
                request = request.newBuilder()
                                .cacheControl(CacheControl.FORCE_CACHE)
                                .build();
            }
            Response response = chain.proceed(request);
            if(NetUtils.isConnected(context)){
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", StringUtils.isEmpty(cacheControl) ? HttpConstants.CACHE_CONTROL_ERROR : cacheControl)
                        .build();
            }else{
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", HttpConstants.CACHE_CONTROL_CACHE)
                        .build();
            }
        }
    }
}
