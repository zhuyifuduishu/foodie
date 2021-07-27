package com.whu.exception;

import com.whu.utils.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class CustomExceptionHandler {

    //上传文件超过500K，捕获异常:MaxUploadSizeExceededException
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Result handlerMaxUploadFile(MaxUploadSizeExceededException ex) {
        return Result.errorMsg("文件上传的大小不能超过500K，请压缩图片或者降低图片质量！");
    }

}
