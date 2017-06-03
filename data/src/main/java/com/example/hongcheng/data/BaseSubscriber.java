package com.example.hongcheng.data;

import android.content.Context;

import com.example.hongcheng.common.util.LoggerUtils;
import com.example.hongcheng.common.util.NetUtils;
import com.example.hongcheng.data.response.BaseResponse;

import rx.Subscriber;

/**
 * Created by hongcheng on 17/1/22.
 */
public abstract class BaseSubscriber<T extends BaseResponse> extends Subscriber<T> {

    private final Context context;

    public BaseSubscriber(Context context) {
        this.context = context;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!NetUtils.isConnected(context)){
            LoggerUtils.error("BaseSubscriber", "retrofit network err onStar");
            onCompleted();
        }
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if(e instanceof ActionException)
        {
            onError((ActionException)e);
        }
        else
        {
            LoggerUtils.error(BaseSubscriber.class.getName(), e.getMessage());
            onError(new ActionException(ExceptionHandler.ERROR.UNKNOWN, "unknow error"));
        }
    }

    @Override
    public void onNext(T t) {
        onBaseNext(t);
    }

    public abstract void onBaseNext(T t);
    public abstract void onError(ActionException e);
}
