package vn.hoidanit.jobhunter.util.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import vn.hoidanit.jobhunter.domain.RestResponse;

@RestControllerAdvice // cái này có chức năng bắt lỗi toàn bộ hệ thống lun
public class GlobalExcaption {// Phạm vi sử dụng là ở claau bồ
    // cái này nó ăn hết lỗi trong terminal
    // ResponseEntity phải có gênrate
    @ExceptionHandler(value = idInvalidException.class)
    public ResponseEntity<RestResponse<Object>> handleIdException(idInvalidException idException) {
        RestResponse<Object> res = new RestResponse<Object>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError(idException.getMessage()); // đây là lỗi
        res.setMesage("idInvalidException");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }
}
