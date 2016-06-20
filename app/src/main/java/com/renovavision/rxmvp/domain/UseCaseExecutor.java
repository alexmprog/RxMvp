package com.renovavision.rxmvp.domain;

import rx.Observable;
import rx.Scheduler;

public class UseCaseExecutor {

    public static final String SUBSCRIBE_SCHEDULER = "Subscribe_Executor";
    public static final String OBSERVE_SCHEDULER = "Observe_Executor";

    private Scheduler mSubscribeScheduler;

    private Scheduler mObserveScheduler;

    public UseCaseExecutor(Scheduler subscribeScheduler, Scheduler observeScheduler) {
        this.mSubscribeScheduler = subscribeScheduler;
        this.mObserveScheduler = observeScheduler;
    }

    public <T> Observable.Transformer<T, T> applySchedulers() {
        return tObservable -> tObservable.subscribeOn(mSubscribeScheduler)
                .observeOn(mObserveScheduler);
    }
}
