package ru.mgazizova.demodb.datasource;

import java.util.List;

import ru.mgazizova.demodb.model.Factory;

/**
 * @author vtarasov
 * @since 04.11.18
 */
public interface FactoryFinder {
    Factory load(int id) throws Exception;

    List<Factory> list() throws Exception;
}
