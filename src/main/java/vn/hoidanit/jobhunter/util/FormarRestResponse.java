package vn.hoidanit.jobhunter.util;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import jakarta.servlet.http.HttpServletResponse;
import vn.hoidanit.jobhunter.domain.RestResponse;
import vn.hoidanit.jobhunter.util.annotation.ApiMessage;

@ControllerAdvice
public class FormarRestResponse implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {

        return true;
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response) {
        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
        int status = servletResponse.getStatus();

        // error
        RestResponse<Object> res = new RestResponse<Object>();
        res.setStatusCode(status);

        // nếu body là 1 chuỗi String thì không cần formatcode
        if (body instanceof String) {
            return body;
        }
        if (status >= 400) {
            return body; // body ở đây chính là lỗi
        } else {
            // case susses
            res.setData(body);
            ApiMessage message = returnType.getMethodAnnotation(ApiMessage.class);
            res.setMesage(message != null ? message.value() : "CALL API SUCCESS");
        }
        return res;
    }


}
