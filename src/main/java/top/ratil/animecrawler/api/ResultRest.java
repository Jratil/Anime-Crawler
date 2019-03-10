package top.ratil.animecrawler.api;

public class ResultRest {

    public static Result success() {
        return new Result().setCode(ResultCode.SUCCESS)
                .setMsg("success");
    }

    public static Result success(Object data) {
        return new Result().setCode(ResultCode.SUCCESS)
                .setMsg("success")
                .setData(data);
    }

    public static Result fail(String msg) {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMsg(msg);
    }

    public static Result fail(int code, String msg) {
        return new Result()
                .setCode(code)
                .setMsg(msg);
    }
}
