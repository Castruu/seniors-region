package org.castruu.regions.repository;


import com.mongodb.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface Repository<T, K> {

    List<T> findAll();

    Optional<T> find(@NonNull K id);

    T create(@NonNull T entity);

    T update(@NonNull T entity);

    void delete(@NonNull T entity);

}
