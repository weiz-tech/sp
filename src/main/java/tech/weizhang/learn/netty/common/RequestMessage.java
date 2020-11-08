package tech.weizhang.learn.netty.common;

public class RequestMessage extends Message<Operation>{

    @Override
    public Class<Operation> getMessageBodyDecodeClass(int opcode) {
        return null;
    }
}
