package com.ecomotto.secure.cer;

import java.io.InputStream;


public interface CertificateEncryption {
    InputStream decode(InputStream in);
}
