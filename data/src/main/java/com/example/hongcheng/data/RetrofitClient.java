package com.example.hongcheng.data;

import com.example.hongcheng.data.response.BaseResponse;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by hongcheng on 17/1/22.
 */
public class RetrofitClient {

    private static class SingletonHolder {
        private static RetrofitClient INSTANCE = new RetrofitClient();
    }

    public static RetrofitClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private RetrofitClient() {

    }

    public <T> Subscription map(Observable<BaseResponse<T>> observable, BaseSubscriber<BaseResponse<T>> subscriber)
    {
        return observable.compose(schedulersTransformer())
                .compose(transformer())
                .subscribe(subscriber);
    }

    private Observable.Transformer schedulersTransformer() {
        return new Observable.Transformer() {


            @Override
            public Object call(Object observable) {
                return ((Observable)  observable).subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }

           /* @Override
            public Observable call(Observable observable) {
                return observable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }*/
        };
    }

    public <T> Observable.Transformer<BaseResponse<T>, T> transformer() {

        return new Observable.Transformer() {

            @Override
            public Object call(Object observable) {
                return ((Observable) observable).onErrorResumeNext(new ResponseErrorFunc<T>());
            }
        };
    }

    private static class ResponseErrorFunc<T> implements Func1<Throwable, Observable<T>> {
        @Override public Observable<T> call(Throwable t) {
            return Observable.error(ExceptionHandler.handleException(t));
        }
    }
}
