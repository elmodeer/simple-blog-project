package com.elmodeer.blog.aws;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.elmodeer.blog.controllers.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.net.HttpURLConnection;
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
     * @param mpFile
     * @return signed url to upload the file
     */
    public static boolean generatePresignedUrlAndUploadObject(MultipartFile mpFile) {
        String objectKey = mpFile.getOriginalFilename();
        try {
            // Set the pre-signed URL to expire after 10 mins.
            java.util.Date expiration = new java.util.Date();
            long expTimeMillis = expiration.getTime();
            expTimeMillis += 1000 * 60 * 10;
            expiration.setTime(expTimeMillis);

            // Generate the pre-signed URL.
            logger.info("Generating pre-signed URL.");
            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, objectKey)
                    .withMethod(HttpMethod.PUT)
                    .withExpiration(expiration);
            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

            // Create the connection and use it to upload the new object using the pre-signed URL.
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "image/jpeg");
            OutputStream out = connection.getOutputStream();
            // another possible solution
            // out.write(Files.readAllBytes(file.toPath()));
            out.write(mpFile.getBytes());
            out.close();

            // Check the HTTP response code. To complete the upload and make the object available,
            // you must interact with the connection object in some way.
            connection.getResponseCode();
            logger.info("HTTP response code: " + connection.getResponseCode());

            // Check to make sure that the object was uploaded successfully.
            S3Object object = s3Client.getObject(bucketName, objectKey);
            logger.info("Object " + object.getKey() + " created in bucket " + object.getBucketName());
            return true;

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
        return false;
    }

    public static String getPresignedURL(String fileName) {
        String objectKey = fileName;
        try {
        // Set the presigned URL to expire after 10 mins.
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 10;
        expiration.setTime(expTimeMillis);

        // Generate the presigned URL.
        logger.info("Generating pre-signed URL.");
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, objectKey)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);
        URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
        logger.info("Pre-Signed URL: " + url.toString());
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

//    might be useful
//    private static File convertMultiPartFileToFile(MultipartFile multipartFile) {
//        logger.info("Initiating converting");
//        final File file = new File(multipartFile.getOriginalFilename());////        logger.info("Converting finished");
//        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
//            outputStream.write(multipartFile.getBytes());
//        } catch (final IOException ex) {
//            logger.error("Error converting the multi-part file to file= ", ex.getMessage());
//        }
//        return file;
//    }
//
//    private static void uploadFileToS3Bucket(final String bucketName, final File file) {
//        final String uniqueFileName = LocalDateTime.now() + "_" + file.getName();
//        logger.info("Uploading file with name= " + uniqueFileName);
//        final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uniqueFileName, file);
//        s3Client.putObject(putObjectRequest);
//    }
}
