package tech.weizhang.learn.netty.common;

import cn.hutool.json.JSONUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import tech.weizhang.utils.ZJsonUtil;

import java.nio.charset.Charset;

@Data
public abstract class Message<T extends MessageBody> {
    private MessageHeader messageHeader;
    private T messageBody;

    public T getMessageBody(){
        return messageBody;
    }

    public void encode(ByteBuf byteBuf){
        byteBuf.writeInt(messageHeader.getVersion());
        byteBuf.writeLong(messageHeader.getStreamId());
        byteBuf.writeInt(messageHeader.getOpCode());
        byteBuf.writeBytes(JSONUtil.toJsonStr(messageBody).getBytes());
    }

    public abstract Class<T> getMessageBodyDecodeClass(int opcode);

    public void decode(ByteBuf msg){
        int version = msg.readInt();
        long streamId = msg.readLong();
        int opCode = msg.readInt();

        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setOpCode(opCode);
        messageHeader.setStreamId(streamId);
        messageHeader.setVersion(version);
        this.messageHeader = messageHeader;

        Class<T> bodyClazz = getMessageBodyDecodeClass(opCode);
        T body = ZJsonUtil.fromJson(msg.toString(Charset.forName("UTF-8")),bodyClazz);
        this.messageBody = body;
    }
}
