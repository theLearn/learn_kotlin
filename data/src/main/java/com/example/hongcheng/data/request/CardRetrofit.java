package com.example.hongcheng.data.request;

import com.example.hongcheng.data.HttpConstants;
import com.example.hongcheng.data.response.BaseResponse;
import com.example.hongcheng.data.response.models.Card;
import com.example.hongcheng.data.response.models.CardRecommend;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hongcheng on 16/9/11.
 */
public interface CardRetrofit {

    @GET(HttpConstants.GET_CARDS_URL)
    Observable<BaseResponse<List<Card>>> listCards();
    
    @GET(HttpConstants.GET_CARDS_DETAIL)
    Observable<BaseResponse<List<CardRecommend>>> getCardDetail(@Query("type") String type);
    
    @POST(HttpConstants.GET_CARDS_DETAIL)
    Observable<BaseResponse<Boolean>> insertCardDetail(@Body BaseRequest<CardRecommend> request);
    
    @HTTP(method = "DELETE", path = HttpConstants.GET_CARDS_DETAIL, hasBody = true)
    Observable<BaseResponse<Boolean>> delCardDetail(@Body BaseRequest<CardRecommend> request);
    
    @PUT(HttpConstants.GET_CARDS_DETAIL)
    Observable<BaseResponse<Boolean>> updateCardDetail(@Body BaseRequest<CardRecommend> request);
}
