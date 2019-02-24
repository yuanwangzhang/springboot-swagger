package com.lance.common.exception;

/**
 * 该类是所有异常处理类的基类
 *
 * @author lance
 */
public class BaseException extends RuntimeException
{
    /**
     * 具体的异常类
     */
    private Throwable cause = null;

    /**
     * 父类的构造方法
     */
    public BaseException()
    {
        super();
    }

    /**
     * 外部传入的中文错误信息
     *
     * @param msg 外部传入的中文错误信息
     */
    public BaseException(String msg)
    {
        super(msg);
    }

    /**
     * 外部传入中文错误信息和异常类信息
     *
     * @param msg 外部传入的中文错误信息
     * @param e   外部传入的异常类信息
     */
    public BaseException(String msg, Throwable e)
    {
        super(msg, e);
        this.cause = e;
    }

    public BaseException(Throwable cause)
    {
        super(cause);
    }

    /**
     * 输出异常的StackTrace
     */
    @Override
    public void printStackTrace()
    {
        super.printStackTrace();
        Throwable cause = getCause();
        if (cause != null)
        {
            cause.printStackTrace();
        }
    }

    @Override
    public Throwable getCause()
    {
        return cause;
    }

    @Override
    public String getMessage()
    {
        String message = super.getMessage();
        Throwable cause = getCause();
        if (cause != null)
        {
            return message + "; nested exception is " + cause;
        }
        return message;
    }
}
