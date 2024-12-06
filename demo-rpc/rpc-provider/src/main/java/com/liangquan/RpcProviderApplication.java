package com.liangquan;

import com.liangquan.registry.LocalRegistry;
import com.liangquan.server.VertxHttpServer;
import com.liangquan.service.UsersService;
import com.liangquan.server.HttpServer;

/**
 * Hello world!
 *
 */
public class RpcProviderApplication {
    public static void main( String[] args ) {

        // 1.注册服务
        LocalRegistry.register(UsersService.class.getName(), UsersService.class);

        // 2.启动web服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8091);
    }
}
