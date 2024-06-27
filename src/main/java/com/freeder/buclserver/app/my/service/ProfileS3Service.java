package com.freeder.buclserver.app.my.service;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.UUID;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.freeder.buclserver.global.exception.s3.FileDeleteException;
import com.freeder.buclserver.global.exception.s3.FileExtensionNotSupportException;
import com.freeder.buclserver.global.exception.s3.FileUploadException;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Utilities;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@RequiredArgsConstructor
@Service
public class ProfileS3Service {

	private final S3Client s3Client;

	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	@Value("${cloud.aws.region.static}")
	private String region;

	private static final String S3_BUCKET_DIR_PATH = "assets/images/profiles/";

	private String S3_BUCKET_BASE_URL;

	@PostConstruct
	protected void init() {
		S3_BUCKET_BASE_URL = "https://" + bucketName + ".s3." + region + ".amazonaws.com/";
	}

	public String uploadFile(MultipartFile file) {
		String fileName = createFileName(file.getOriginalFilename());
		String s3Key = S3_BUCKET_DIR_PATH + fileName;

		PutObjectRequest putObjectRequest = PutObjectRequest.builder()
			.bucket(bucketName)
			.key(s3Key)
			.contentType(file.getContentType())
			.build();

		try (InputStream inputStream = file.getInputStream()) {
			RequestBody requestBody = RequestBody.fromInputStream(inputStream, file.getSize());
			s3Client.putObject(putObjectRequest, requestBody);
		} catch (Exception ex) {
			throw new FileUploadException();
		}

		return getUploadFileUrl(s3Key);
	}

	public void deleteFile(String fileUrl) {
		String s3Key = fileUrl.substring(S3_BUCKET_BASE_URL.length());

		DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
			.bucket(bucketName)
			.key(s3Key)
			.build();

		try {
			s3Client.deleteObject(deleteObjectRequest);
		} catch (Exception ex) {
			throw new FileDeleteException();
		}
	}

	private String createFileName(String fileName) {
		checkFileExtension(fileName);
		return UUID.randomUUID().toString().concat(fileName);
	}

	private void checkFileExtension(String fileName) {
		List<String> extensions = List.of(".jpg", ".jpeg", ".png", ".bmp");
		String fileExtension = fileName.substring(fileName.lastIndexOf("."));

		if (!extensions.contains(fileExtension)) {
			throw new FileExtensionNotSupportException();
		}
	}

	private String getUploadFileUrl(String key) {
		S3Utilities s3Utilities = s3Client.utilities();

		GetUrlRequest request = GetUrlRequest.builder()
			.bucket(bucketName)
			.key(key)
			.build();

		URL url = s3Utilities.getUrl(request);

		return url.toString();
	}
}
