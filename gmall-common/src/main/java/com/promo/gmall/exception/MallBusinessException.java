package com.promo.gmall.exception;

/**
 * 系统业务异常
 *
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
public class MallBusinessException extends RuntimeException {

    private static final long serialVersionUID = 3163565997781377059L;

    private final CodeMsg codeMsg;

    public MallBusinessException(CodeMsg codeMsg) {
        super(codeMsg.getMessage());
        this.codeMsg = codeMsg;
    }


    public MallBusinessException(Throwable cause, CodeMsg codeMsg) {
        super(codeMsg.getMessage(), cause);
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }
}
