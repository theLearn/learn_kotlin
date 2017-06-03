package com.example.hongcheng.data.request;

import com.example.hongcheng.data.HttpConstants;
import com.example.hongcheng.data.response.BaseResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by hongcheng on 17/5/7.
 */

public interface FileOperateRetrofit
{
	@Streaming
	@GET(HttpConstants.FILE_OPERATE)
	Observable<ResponseBody> download(@QueryMap Map<String, String> request);
	
	@Streaming
	@GET
	Observable<ResponseBody> downloadByUrl(@Url String fileUrl);
	
	@Multipart
	@POST(HttpConstants.FILE_OPERATE)
	Observable<BaseResponse<Boolean>> upload(@Part("description") RequestBody description, @Part MultipartBody.Part file);
	
}
