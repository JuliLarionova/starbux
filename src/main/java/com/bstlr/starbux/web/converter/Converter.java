package com.bstlr.starbux.web.converter;

import java.util.List;
import java.util.stream.Collectors;

public interface Converter<K, V> {
   // K convert(V v);

/*    default List<K> convert(List<V> v) {
        return v.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }*/
}
