package com.lsxy.framework.rpc.mina.server;

import com.lsxy.framework.rpc.api.RPCHandler;
import com.lsxy.framework.rpc.api.AbstractSession;
import org.apache.mina.core.session.IoSession;

import java.net.InetSocketAddress;

/**
 * Created by tandy on 16/7/30.
 */
public class MinaServerSession extends AbstractSession {
    private IoSession ioSession;


    public MinaServerSession(IoSession ioSession, RPCHandler handler){
        super(handler);
        this.ioSession = ioSession;
        this.setId((String) ioSession.getAttribute("sessionid"));
    }

    @Override
    public void concreteWrite(Object object) {
        ioSession.write(object);
    }


    @Override
    public boolean isValid() {
        return this.ioSession != null && this.ioSession.isConnected() && this.ioSession.isActive();
    }

    @Override
    public void close(boolean b) {
        this.ioSession.closeNow();
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return (InetSocketAddress) ioSession.getRemoteAddress();
    }

}
