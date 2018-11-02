package ru.mgazizova.demodb.datasource;

import java.util.List;
import ru.mgazizova.demodb.model.Man;

/**
 * @author vtarasov
 * @since 04.11.18
 */
public interface ManFinder {
    Man load(int id);
    List<Man> list();
}
