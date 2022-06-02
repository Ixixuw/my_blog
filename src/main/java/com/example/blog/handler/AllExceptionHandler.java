package com.example.blog.handler;

import com.example.blog.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//�Լ���@Controllerע��ķ����������ش��� AOP��ʵ��
@ControllerAdvice
public class AllExceptionHandler {
    //�����쳣��������Exception.class���쳣
    @ExceptionHandler(Exception.class)
    @ResponseBody//����json����
    public Result doException(Exception ex){
        ex.printStackTrace();
        return Result.fail(-999,"ϵͳ�쳣");
    }
}
