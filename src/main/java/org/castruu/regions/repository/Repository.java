package org.castruu.regions.repository;


import com.mongodb.lang.NonNull;

import java.util.List;

public interface Repository<T, K> {

    List<T> findAll();

    T find(@NonNull K id);

    T create(@NonNull T entity);

    T update(@NonNull T entity);

    void delete(@NonNull T entity);

}
