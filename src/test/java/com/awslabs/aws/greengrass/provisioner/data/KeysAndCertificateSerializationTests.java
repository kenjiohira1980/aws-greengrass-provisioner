package com.awslabs.aws.greengrass.provisioner.data;

import com.awslabs.general.helpers.implementations.BasicJsonHelper;
import com.awslabs.general.helpers.interfaces.JsonHelper;
import com.awslabs.iot.data.ImmutableCertificateArn;
import com.awslabs.iot.data.ImmutableCertificateId;
import com.awslabs.iot.data.ImmutableCertificatePem;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import software.amazon.awssdk.services.iot.model.KeyPair;

import static org.hamcrest.CoreMatchers.is;

public class KeysAndCertificateSerializationTests {
    private JsonHelper jsonHelper;
    private KeysAndCertificate keysAndCertificate;
    private KeyPair keyPair;

    @Before
    public void setup() {
        jsonHelper = new BasicJsonHelper();
        keyPair = KeyPair.builder()
                .privateKey("privateKey123")
                .publicKey("publicKey123")
                .build();
        keysAndCertificate = com.awslabs.aws.greengrass.provisioner.data.ImmutableKeysAndCertificate.builder()
                .certificateArn(ImmutableCertificateArn.builder().arn("certificateArn123").build())
                .certificateId(ImmutableCertificateId.builder().id("certificateId123").build())
                .certificatePem(ImmutableCertificatePem.builder().pem("certificatePem123").build())
                .keyPair(keyPair)
                .build();
    }

    @Test
    public void shouldSerializeWithoutExceptions() {
        jsonHelper.toJson(keysAndCertificate);
    }

    @Test
    public void shouldDeserializeWithoutExceptions() {
        jsonHelper.fromJson(KeysAndCertificate.class, jsonHelper.toJson(keysAndCertificate).getBytes());
    }

    @Test
    public void shouldDeserializeAndCreateIdenticalObject() {
        KeysAndCertificate testKeysAndCertificate = jsonHelper.fromJson(KeysAndCertificate.class, jsonHelper.toJson(keysAndCertificate).getBytes());

        MatcherAssert.assertThat(testKeysAndCertificate, is(keysAndCertificate));
    }
}
