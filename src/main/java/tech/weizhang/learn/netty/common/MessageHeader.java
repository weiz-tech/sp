package tech.weizhang.learn.netty.common;

import lombok.Data;
import org.apache.spark.sql.sources.In;

@Data
public class MessageHeader {
    private int version=1;
    private long streamId;
    private int opCode;
}
