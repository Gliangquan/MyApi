package com.liangquan.consumer;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.liangquan.model.RpcRequest;
import com.liangquan.model.RpcResponse;
import com.liangquan.model.Users;
import com.liangquan.serializer.JdkSerializer;
import com.liangquan.serializer.Serializer;
import com.liangquan.service.UsersService;
import java.io.IOException;

/**
 * 静态代理
 */
public class UserServiceProxy implements UsersService {

    public Users getUsers(Users user) {
        // 指定序列化器
        Serializer serializer = new JdkSerializer();

        // 发请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(UsersService.class.getName())
                .methodName("getUsers")
                .parameterTypes(new Class[]{Users.class})
                .args(new Object[]{user})
                .build();
        try {
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            byte[] result;
            try (HttpResponse httpResponse = HttpRequest.post("http://localhost:8091")
                    .body(bodyBytes)
                    .execute()) {
                result = httpResponse.bodyBytes();
            }
            RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
            return (Users) rpcResponse.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
