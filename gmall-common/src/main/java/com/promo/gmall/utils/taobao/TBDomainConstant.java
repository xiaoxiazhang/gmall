package com.promo.gmall.utils.taobao;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.x.x
 */
public abstract class TBDomainConstant {

    /**
     * 州,地区查询URL
     */
    public static final String STATE_QUERY_URL = "https://h5api.m.taobao.com/h5/mtop.cainiao.address.ua.global.division.list/1.0/?jsv=2.5.1&appKey={appKey}&t={timestamp}&sign={sign}&api=mtop.cainiao.address.ua.global.division.list&v=1.0&dataType=jsonp&type=jsonp&callback=mtopjsonp8&data=%7B%22sn%22%3A%22suibianchuan%22%2C%22pid%22%3A%22{pid}%22%7D";


    public static final String STATE_QUERY_APP_KEY = "12574478";

    /**
     * 地区对应的邮政编码查询
     */
    public static final String STATE_CODE_QUERY_URL = "https://h5api.m.taobao.com/h5/mtop.cainiao.address.ua.global.division.search.byname/1.0/?jsv=2.5.1&appKey={appKey}&t={timestamp}&sign={sign}&api=mtop.cainiao.address.ua.global.division.search.byname&v=1.0&timeout=20000&dataType=jsonp&type=jsonp&callback=mtopjsonp107&data=%7B%22language%22%3A%22en%22%2C%22maxRegion%22%3A100%2C%22maxPc%22%3A100%2C%22returnPostcode%22%3Atrue%2C%22iso%22%3A%22US%22%2C%22searchName%22%3A%22{searchName}%22%7D";


    public static final String STATE_CODE_QUERY_APP_KEY = "27769795";

    /**
     * 用户cookie【自己到网页里面去截取】
     */
    public static final String COOKIE = "cookie2=139411ec5a6c2999ed348da11ba44d7d; t=9b6b02b5fe8fd928c0914b87de6d6d00; _tb_token_=753676367bee7; _samesite_flag_=true; cna=6jayFVlTNgsCAXAK82iyIPpE; lgc=8\\u5FC38\\u4EE5\\u9501; dnk=8\\u5FC38\\u4EE5\\u9501; tracknick=8\\u5FC38\\u4EE5\\u9501; mt=ci=32_1; thw=cn; xlly_s=1; sgcookie=E100wM++S9AX09KpOLHh2uhPQRS1+xPxA9gL7boHJjKQE6ykNl+/JWyb5CF9T+Rc9cgAuoWil5p5rAsqGZXyrZ0Otg==; unb=781294163; uc3=id2=VAmrURQTg7LD&nk2=WymDxb122YA=&vt3=F8dCuAov7muLd0MRlic=&lg2=WqG3DMC9VAQiUQ==; csg=79e03935; cookie17=VAmrURQTg7LD; skt=d4126439393b723c; existShop=MTYxNTQ0NjgxNA==; uc4=nk4=0@WQpghlAwfeBgrS8jwNqNTintrA==&id4=0@VhCQTaTuhFYS1wpq/Jyl4vcmPIA=; _cc_=URm48syIZQ==; _l_g_=Ug==; sg=3e; _nk_=8\\u5FC38\\u4EE5\\u9501; cookie1=Bxua8OrmG6fJobxjRzb7y4rB91JKHrjQf3nCz0kcr90=; _m_h5_tk=b6cbdf531653ed300e024b4d2009589d_1615455095633; _m_h5_tk_enc=dd45f0b79cb1534d0eac4bafcae2c0d0; uc1=cookie14=Uoe1hx32Er0SlQ==&cookie16=W5iHLLyFPlMGbLDwA+dvAGZqLg==&cookie15=V32FPkk/w0dUvg==&existShop=false&pas=0&cookie21=W5iHLLyFe3xm&cart_m=0; tfstk=cudPB39xn6xbdhCIB_1UADkFEPpRZg9Wd-7GEKvlMy7zNB6lixqdo_wd0MjtiTf..; l=eBSqyOhmjUTqmDp3BO5Zlurza77TZIOfcsPzaNbMiInca69diFaJvNCQ11SDrdtjgtfbJetrSFrMgRUW5V43Wx1Ny6RNuEFuGypw-; isg=BNraftL7bpzD0eIxwYN5pqrMK4b8C17llk6aOORSz205V3KRzJmk9P3hJyNLh9Z9";


    /**
     * token
     */
    public static final String TOKEN = "b6cbdf531653ed300e024b4d2009589d";


    /**
     * 美国对应的ID
     */
    public static final String US_ID = "228";


}
