package com.renovavision.rxmvp.espresso.base;

import android.support.test.espresso.ViewAssertion;
import android.support.v7.widget.RecyclerView;

import org.hamcrest.Matcher;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertThat;


public final class EspressoTools {

    public static ViewAssertion hasItemsCount(final int count) {
        return (view, e) -> {
            if (!(view instanceof RecyclerView)) {
                throw e;
            }
            RecyclerView rv = (RecyclerView) view;
            if (rv.getAdapter() == null && count == 0) {
                return;
            }
            assertEquals(rv.getAdapter().getItemCount(), count);
        };
    }


    public static ViewAssertion hasHolderItemAtPosition(final int index,
                                                        final Matcher<RecyclerView.ViewHolder> viewHolderMatcher) {
        return (view, e) -> {
            if (!(view instanceof RecyclerView)) {
                throw e;
            }
            RecyclerView rv = (RecyclerView) view;
            assertThat(rv.findViewHolderForAdapterPosition(index), viewHolderMatcher);
        };
    }
}