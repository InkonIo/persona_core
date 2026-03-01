package kz.persona.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

@Configuration
public class AmazonConfig {

    @Value("${amazon.s3.access-key-id}")
    private String accessKeyId;

    @Value("${amazon.s3.secret-access-key}")
    private String secretAccessKey;

    @Bean
    public S3Client s3Client() {
        Region region = Region.US_EAST_1;
        StaticCredentialsProvider staticCredentialsProvider =
                StaticCredentialsProvider.create(AwsBasicCredentials.create(
                        accessKeyId,
                        secretAccessKey
                ));
        return S3Client.builder()
                .endpointOverride(URI.create("https://s3.msk.3hcloud.com"))
                .region(region)
                .credentialsProvider(staticCredentialsProvider)
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true)
                        .build())
                .build();
    }

}
