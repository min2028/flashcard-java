package model;

import java.util.List;

public interface Compartable {

    boolean add(Object o);

    boolean remove(Object o);

    int size();
}