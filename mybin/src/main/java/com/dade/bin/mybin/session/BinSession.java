package com.dade.bin.mybin.session;

import java.io.Closeable;
import java.util.List;

public interface BinSession extends Closeable {

    <T> T cleanOne();

    <E> List<E> cleanList();

    @Override
    void close();

}
