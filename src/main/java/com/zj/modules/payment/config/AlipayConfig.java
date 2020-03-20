package com.zj.modules.payment.config;

import java.io.FileWriter;
import java.io.IOException;

public class AlipayConfig {

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016101100661839";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC48fsXkSvI7G8uRHzzRHyW6O59yqCFt7Cb8Y82xCnchdFNhFIG6AdpirmSiI04CkVfewyZ6Ln43KCD3tou4/XDaJ0bUgOtmj7vDROSoHD1I87p2G9eISUSNQq1dFvQH2/H1xnOUfaFaW9HhbJohqU5YMyqbE0WVH/2433GSQG2RZAw8atESLcwKtYDxhCQGDMeS8k8g2SKvK4NE8VHjX+CuDAUmqP+grUSEQ15X0N8koWWBMYo+jds06vbkBLtYcgABkKAQd1J0sRMNpt7eg+0tr8/v9PQ/BAIcGkOL0ZqbYVLyMNEcmjS7w7eQF5VH/cm1u21oFB2dgoC1b5tuglVAgMBAAECggEASYTHz8KBqUlzmOzYzst2y3ak95RI41718cfAqoCoM2VuomXrRZuO9sGSq1Fk8dYDZcVAaR4+b1Mk68b27JTp6Vf4vBeKJsSeJ+EG2s60mH43jnXfL4f1eJtlOmSa6szUXph/RakHWlljRwc6uJptq7VzJnwO2MWWaMpeASicaZvnxAxGBM7IeFVRKqyGYd17og+wyTiq55g0BI2O3dmnJIviO8drdyCkAoEyvq79ju1O7p5CScihFN71Y2VofR+1fMJRGvfJ9dNFUuxb7KFcGnd3vwG9aFcX5wdAiz1EBlTcGXlv1j4ex10evshWLkTDiuah0PZBc2LNJ7yK7OfBAQKBgQDvOgD433dwxMnNBZ4egp5bcLB2PpAHTAMbm4rG8GmKIZosJUiW9q6EMj5J0KPwfGJWkijTDIBPWtB/Tq64tkEXuNOuiB7EQts/sajXAFzUgtdwYrWTes1Gk9AUb5w4nnkQ6FMv3/0rRQ6MxFpeyWKlqQ1LP8//GwbZHvIvVwoFEQKBgQDF6aeHL5bakoywecvNRy2xKanCxPFRr70I54QqIhJ/NDM2moVuZ+RGilIkjLCDP+oVMRa0BlF4x8NRoc2LFk4j1UprHiMgwS/F8rNa+VAqI0Eyk/6w95uLVMv247Im9N0fTrDMCRdyP7zWQZtEnsYk9gDaPUp/F91mUkhkWUjwBQKBgQCjlgd6J92WOItCbpf8hxdgsgOJKCj3RGxPoaJZfaa99VJm46vqx475CR58/XZNidD5IANYppDLMu+mTpi96KtEXHgsPhrD3G/u8z7gnvfbvgkyad1+lxfuLj+46cVrFjr1a9kOwN1vjE5xxgeCD7YsUBiuH1nNOZ8KM9Yqtxs78QKBgQCqmeBEgvxJy1wlX8gTWtEDT4O7liLjJFcSDuf5ncdOCYRABHESm9HGEMQAJ5qceQLiY59LbcrbD2/JtW1GAOM4tkphDeh/+qegvbZnrFOzDxLLc5FvoPFe6KitWNegByF5NE7ogsnIPTMdig4615a8E6bmeUD3T24VHsayYdiitQKBgCNCADDq5Z5I1/kI25BOhU9ttXWkWxiZMAoYlromcRdUCqlPMOIpopchK5AdN8NCQmRexuztp1rqD3Iq275q+/hB+StxgzerAMNP+qxPkFARLcYhG0+KrziAIg0B0Jw7tEcacb1IZcoJh07R91Ap7Es9N8UmiY/XyQQn/Jbtt9OV";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuPH7F5EryOxvLkR880R8lujufcqghbewm/GPNsQp3IXRTYRSBugHaYq5koiNOApFX3sMmei5+Nygg97aLuP1w2idG1IDrZo+7w0TkqBw9SPO6dhvXiElEjUKtXRb0B9vx9cZzlH2hWlvR4WyaIalOWDMqmxNFlR/9uN9xkkBtkWQMPGrREi3MCrWA8YQkBgzHkvJPINkiryuDRPFR41/grgwFJqj/oK1EhENeV9DfJKFlgTGKPo3bNOr25AS7WHIAAZCgEHdSdLETDabe3oPtLa/P7/T0PwQCHBpDi9Gam2FS8jDRHJo0u8O3kBeVR/3JtbttaBQdnYKAtW+bboJVQIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://21742pa841.51mypc.cn:46064/lz/om/payment/alipay/syncNotify";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://192.168.1.66/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
//	public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
