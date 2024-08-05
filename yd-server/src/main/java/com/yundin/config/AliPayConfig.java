package com.yundin.config;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@Component
public class AliPayConfig {
    private String appId="9021000139674244";
    //应用私钥
    private String appPrivateKey="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDLCEa0RFlZsFyxRXGColYOQxlH8lPWA+H8guy/41asV/hunbA9V/SoZJamBmAWWFleC5TADPVZLhMPyl+RKmWDUgCi/uryJp2OjYOB3MIrZzskx1mO2Qy6WW8wYvrXhlP4IJIOjGgEQT4WYliCX15ZAmGxyzVRk0VMvycnIj8P2p7ulYLxseQlcsXPsO2ylxh/46jMe/xWFpSEyLnOMt4E/LRHY2s6DsjwVnyD8gjwnRffSwi6Q1jSWFglZlyUkLiNirUT2fuzsg1QF+vaLUrp6UB0aehJfpd6NC1omGDwxhQ0fKTV5ME+cpJVCP29+poI+VnU7MF3wykQp9pii2EZAgMBAAECggEBAKMGZzdyNkiFCziwaP+a9/yFtjQGCXksLvZB7nX5xmumS24Ooifvixz3f9uZ/OeUU1XneK5o+Ux9aYLhXbFDWLWET8yRYffEN/jx+ml46FRPlLbodynzcHvlOhmhMIeEbcIr7PtPlGnuNTtmBmnQyAIynl1TrOFunlOMnZSXtAk2dG9Vo/ckcBe/FVdHdV1dBi8zUVp2/k+1rU3E6HlzeUbj5abVHdgnW9GSACgEW8R08epXmLERW0EslJLjO/DNd+1RoKzF9qK3MRJmEx/5Xna601mvhsPnyEH8WDIwNJFaYTIuwhcAHnjGobfKW+xF4bSpuCtebqzPaxowsKgLsg0CgYEA8Z+XgurihtDzPUbue9WeW8a1/Uid2sawpvjJBQdjcE9Fc1IqO/oqi36TV8aDZpWJ+Y8QdmpJQPxPbWKROfVFOcD+iwbW8bx83QTNays5mWQcdPZliE4NFflsSiv8jczkt9J432t2L2yjDWCVE+fhwj+CQ8veeklnstBowsviw4cCgYEA1xzdcEcj3Cn/nPlsnLLTdyGjPVadG/8LkvUXiZ/HwNPx3zJDWAUksZxyZObRvuS+runBGjPKQsokJsBct8WRsNMge2rbASqz0zpNbcEXIGBPe3gCebg/ie4Ufh6TjQpnVagwiSCmxS/7l4vLzpmiK9fULzcRpO8nRxfzz2ptHl8CgYEAxncnFULntIMW+KM1qoPpokrTG2u1NYl0eoHpY/cQp6SFUFcSF9fBcHkUf8mwDugPv+vSQuHEr9tPRnLrBOdA6FJtAjcpAMCo80ZJkJidpDAF69Cjz1UFDKKmXzpIJRVCXPdFj4aOOiAKwict7H7QAiT2U8e2Q03eHpNvKWodJisCgYB9p0TKcyPFVVsUzLXfCq5vplx+VfvMAL04CKbJI2ZBgwyEPg4MemSmwyYnPpVClsez0KOVtK7vOLjJUhBW4LdrvMf6j76B/fmw5xuXgvUJWnjTPJTlmPS0kzexPBcgifU2mdpQH5yLDq4+JdDVS/To+1fchKRv3arp4lnuzNIUrQKBgDd0oYH932RD7TBz/RqFnmv8SP4CsBusaBSBrDlTjws30mAXoDLGhJAlqvA3JbYXrux7v7b2qVOZqCdspqhbKW/QqS+L1QrBQPIAYp/OlGUQ1rH9NxT4YE3BXzf+9RH8SvToV0iRQuPh7VexBBIMrCQ4/tfXoGhISxKqmMemWTHC\n";
    // 支付宝公钥
    private String alipayPublicKey=" MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgbbS03tKmg6C9eOnMaa33OU85w8YfhE77XoqouXRFSd09VCVHZ7ZqO2uT5A8tMB1OkMRugkuym0SDpv2KKdFqvBoz6gAWDkuoHe/0oCWezRF/hYOrszFqnd1PgV3RsokLljioHz4y70RuVte83++WJhMkViLCsG/UE35jDHLtQVTghI0J/CCmDbatHWeWvO43dYRPxsxR0w+amIvODlW46zy4gaveB3QPKibvP+siE/nDa7BUYyzFhGMm3G1pcprU7RHNlgo/EvWFSWXe6X6bQMoSYzl13yj+0OPuuMoa6p0u0a9Dii2TS0ZqT+1rSFiBNMLndileluOQ9GPwDPhIwIDAQAB";
    //支付宝异步通知路径,付款完毕后会异步调用本项目的方法,必须为公网地址
    private String notifyUrl="https://6249129c.r5.cpolar.top/alipay/notify";
    //这是沙箱接口路径,正式路径为https://openapi.alipay.com/gateway.do
    public String GATEWAY_URL ="https://openapi-sandbox.dl.alipaydev.com/gateway.do";
    public String FORMAT ="JSON";
    public String CHARSET ="utf-8";
    public String SIGN_TYPE ="RSA2";


    @PostConstruct
    public void init() {
        // 设置参数（全局只需设置一次）
        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = "openapi.alipaydev.com";
        config.signType = "RSA2";
        config.appId = this.appId;
        config.merchantPrivateKey = this.appPrivateKey;
        config.alipayPublicKey = this.alipayPublicKey;
        config.notifyUrl = this.notifyUrl;
        Factory.setOptions(config);
        System.out.println("=======支付宝SDK初始化成功=======");
    }
}
