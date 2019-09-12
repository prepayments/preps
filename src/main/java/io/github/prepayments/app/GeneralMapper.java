package io.github.prepayments.app;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * Maps T1 to T2
 * @param <T1>
 * @param <T2>
 */
public interface GeneralMapper<T1, T2> {

    T2 toTypeT2(T1 typeT1);

    T1 toTypeT1(T2 typeT2);

    default List<T1> toTypeT1(List<T2> typeT2) {
        return typeT2.stream()
            .map(this::toTypeT1)
            .collect(ImmutableList.toImmutableList());
    }

    default List<T2> toTypeT2(List<T1> typeT1) {
        return typeT1.stream()
                     .map(this::toTypeT2)
                     .collect(ImmutableList.toImmutableList());
    }
}
