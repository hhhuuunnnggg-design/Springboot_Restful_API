package vn.hoidanit.jobhunter.domain;

// import com.mysql.cj.x.protobuf.MysqlxDatatypes.Object;
// import com.mysql.cj.x.protobuf.MysqlxDatatypes.Scalar.String;

public class RestResponse<T> {
    private int statusCode;
    private String error;
    private Object mesage;
    private T data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getMesage() {
        return mesage;
    }

    public void setMesage(Object mesage) {
        this.mesage = mesage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
