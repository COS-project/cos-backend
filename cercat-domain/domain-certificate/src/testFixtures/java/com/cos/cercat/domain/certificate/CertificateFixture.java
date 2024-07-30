package com.cos.cercat.domain.certificate;

public class CertificateFixture {

  public static Certificate createCertificate() {
    return new Certificate(
        1L,
        "testCertificate"
    );
  }

}
