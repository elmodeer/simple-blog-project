package com.elmodeer.blog.aws;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.elmodeer.blog.controllers.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

public class AWSUtility {


    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final Regions clientRegion = Regions.EU_CENTRAL_1;

    // TODO!! create a property reader
    private static final String bucketName = "simple-blog-s3-dev";
    private static final AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                                              .withCredentials(new ProfileCredentialsProvider())
                                              .withRegion(clientRegion)
                                              .build();
    /**
     * Get s signed url to upload a file to S3
     *
     * @param fileName
     * @return signed url to upload the file
     */
    public static String generatePresignedPutUrl(String fileName) {
        try {
            // Set the pre-signed URL to expire after 10 mins.
            java.util.Date expiration = new java.util.Date();
            long expTimeMillis = expiration.getTime();
            expTimeMillis += 1000 * 60 * 10;
            expiration.setTime(expTimeMillis);

            // Generate the pre-signed URL
            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName)
                    .withMethod(HttpMethod.PUT)
                    .withExpiration(expiration);
            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
            logger.info("pre-signed URL for PUT operation has been generated.");
            return url.toString();
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.error("URL could not be generated");
        return null;
    }


    public static String generatePresignedGetUrl(String fileName) {
        String objectKey = fileName;
        try {
        // Set the presigned URL to expire after 10 mins.
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 10;
        expiration.setTime(expTimeMillis);

        // Generate the presigned URL.
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, objectKey)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);
        URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
        logger.info("pre-signed URL for GET operation has been generated.");
        return url.toString();
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
        return null;
    }
}
