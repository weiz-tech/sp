package tech.weizhang.utils;

import java.util.concurrent.atomic.AtomicLong;

public final class IdUtil {
    private static final AtomicLong id = new AtomicLong();
    private IdUtil(){}
    private static long nextId(){
        return id.incrementAndGet();
    }
}
