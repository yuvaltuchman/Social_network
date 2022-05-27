package bgu.spl.net.srv;

import bgu.spl.net.Messages.Message;
import bgu.spl.net.api.*;

import java.util.function.Supplier;

public class BguServer<T> extends BaseServer<T>{
    public BguServer(int port, Supplier<BidiMessagingProtocol<T>> protocolFactory, Supplier<MessageEncoderDecoder<T>> encdecFactory) {
        super(port, protocolFactory, encdecFactory);
    }

    @Override
    protected void execute(BlockingConnectionHandler<T> handler) {
        new Thread(handler).start();
    }

    public static void main(String[] args) {
        Supplier<BidiMessagingProtocol<Message>> protocolfactory = new Supplier<BidiMessagingProtocol<Message>>() {
            @Override
            public BguMessageProtocol get(){
                BguMessageProtocol b = new BguMessageProtocol();
                //b.start(currentClientId,connections);
                return b;
            }
        };
        Supplier<BguEncoderDecoder> encdecfactory = new Supplier<BguEncoderDecoder>() {
            @Override
            public BguEncoderDecoder get() {
                return new BguEncoderDecoder();
            }
        };
        new BguServer(7777, protocolfactory,encdecfactory).serve();
    }
}
