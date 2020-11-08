package tech.weizhang.learn.netty.common;

public class ResponseMessage extends Message<OperationResult>{
    @Override
    public Class<OperationResult> getMessageBodyDecodeClass(int opcode) {
        return null;
    }
}
