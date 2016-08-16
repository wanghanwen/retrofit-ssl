package com.ecomotto.secure.cer;

public class CertificateConfig {
    public static final String KEY_STORE_TYPE_BKS = "bks";
    public static final String KEY_STORE_TYPE_P12 = "PKCS12";
    public static final String keyStoreFileName = "client.p12";
    public static final String keyStorePassword = "123456";
    static final String trustStoreFileName = "client.truststore";
    public static final String trustStorePassword = "123456";
    public static final String alias = "server";
}
