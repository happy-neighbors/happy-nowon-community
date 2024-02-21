package com.project.community.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CopyObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileUploadUtil {
	
	private final S3Client s3Client;
	
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;
	
	@Value("${cloud.aws.s3.upload-temp}")
	private String temp;
	
	@Value("${cloud.aws.s3.upload-path}")
	private String upload;
	
	
	public Map<String, String> s3Upload(String bucketPath,MultipartFile file)  {
		String newFileName=newFileNameByNanotime(file.getOriginalFilename());
		//버킷 내부의 이미지 경로
		String bucketKey=bucketPath+newFileName;
		
		// 이미지에 대한 URL 생성 요청
		
		// MultipartFile을 InputStream으로 변환
        InputStream inputStream=null;
		try {
			inputStream = file.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}

        // 업로드할 객체 생성
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(bucketKey)
                .contentType(file.getContentType())
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();

        // S3 버킷에 이미지 파일 업로드
        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, file.getSize()));

        log.debug("Image uploaded successfully!");
        
        String url=s3Client.utilities().getUrl(builder->builder.bucket(bucket).key(bucketKey))
        			.toString().substring(6);
		Map<String, String> result=new HashMap<>();
		result.put("url", url);
		result.put("bucketKey", bucketKey);
		result.put("orgName", file.getOriginalFilename());
		result.put("newName", newFileName);
		return result;
	}
	
	
	public String s3TempToSrc(String sourceBucket, String destinationKey) {
		
		CopyObjectRequest copyObjectRequest=CopyObjectRequest.builder()
				.sourceBucket(bucket)
				.sourceKey(sourceBucket)
				.destinationBucket(bucket)
				.destinationKey(destinationKey)
				.acl(ObjectCannedACL.PUBLIC_READ)
				.build();
		
		s3Client.copyObject(copyObjectRequest);
		
		s3Client.deleteObject(builder->builder.bucket(bucket).key(sourceBucket));//temp 경로 이미지 삭제
		
		return s3Client.utilities().getUrl(builder->builder.bucket(bucket).key(destinationKey))
     			.toString().substring(6);//src 폴더의 url
	}

	/**
	 * 
	 * @param s3Client S3Client 객체
	 * @param bucket aws 버킷이름
	 * @param path 버킷 내부 경로
	 * @param img  MultipartFile 객체로 이미지정보
	 * @return
	 * @throws IOException 
	 */
	public Map<String, String> s3Upload(S3Client s3Client, String bucket, String path, MultipartFile imageFile)  {
		String newName=newFileNameByNanotime(imageFile.getOriginalFilename());
		//버킷 내부의 이미지 경로
		String bucketKey=path+newName;
		
		// 이미지에 대한 URL 생성 요청
		
		// MultipartFile을 InputStream으로 변환
        InputStream inputStream=null;
		try {
			inputStream = imageFile.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}

        // 업로드할 객체 생성
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(bucketKey)
                .contentType(imageFile.getContentType())
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();

        // S3 버킷에 이미지 파일 업로드
        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, imageFile.getSize()));

        log.debug("Image uploaded successfully!");
        
        String url=s3Client.utilities().getUrl(builder->builder.bucket(bucket).key(bucketKey))
        			.toString().substring(6);
		Map<String, String> result=new HashMap<>();
		result.put("url", url);
		result.put("bucketKey", bucketKey);
		result.put("orgName", imageFile.getOriginalFilename());
		result.put("newName", newName);
		return result;
	}
	
	public Map<String, String> s3TempUpload(MultipartFile file) {
		return s3Upload(temp, file);
	}
	
	public Map<String, String> s3SrcUpload(MultipartFile file) {
		return s3Upload(upload, file);
	}

	/**
	 * 
	 * @param newName temp에 저장된 파일이름 : 파일이 변경되었으면 newName, 아니면 orgName
	 * @return
	 */
	public String s3TempToSrc(String newName) {
		return s3TempToSrc(s3Client, bucket, temp+newName, upload+newName);
		
	}
	
	/**
	 * 
	 * @param s3Client 
	 * @param bucket 동일한 버킷내에서 이동하는경우 여서 1개만 설정함 sourceBucket, destinationBucket
	 * @param sourceBucket 원본파일 버킷키(temp경로)
	 * @param destinationKey 이동할 버킷키
	 * @return  destinationKey 리턴
	 */
	public String s3TempToSrc(S3Client s3Client, String bucket, String sourceBucket, String destinationKey) {
		CopyObjectRequest cor=CopyObjectRequest.builder()
				.sourceBucket(bucket)
				.sourceKey(sourceBucket)
				.destinationBucket(bucket)
				.destinationKey(destinationKey)
				.acl(ObjectCannedACL.PUBLIC_READ)
				.build();
				
		s3Client.copyObject(cor);
		
		s3Client.deleteObject(builder->builder.bucket(bucket).key(sourceBucket));//temp 경로 이미지 삭제
		
		//return s3Client.utilities().getUrl(builder->builder.bucket(bucket).key(destinationKey))
     	//		.toString().substring(6);//src 폴더의 url
		return destinationKey;
	}
	
	
	
	/**
	 * 
	 * @param path : static 하위 경로 예) "/images/upload/temp"
	 * @param tempImg : MultipartFile 객체
	 * @return
	 */
	public Map<String, String> classPathUpload(String path, MultipartFile tempImg) {
		ClassPathResource cpr=new ClassPathResource("static"+path);
		String newFileName=newFileName(tempImg.getOriginalFilename());
		try {
			File folder=cpr.getFile();
			if(!folder.exists())folder.createNewFile();
			
			tempImg.transferTo(new File(folder, newFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Map<String, String> result=new HashMap<>();
		result.put("url", path+newFileName);
		result.put("orgName", tempImg.getOriginalFilename());
		
		return result;
	}
	
	
	
	
	//파일이름 UUID를 이용하여 변경
	private static String newFileName(String orgName) {
		int idx=orgName.lastIndexOf(".");
		return UUID.randomUUID().toString() //새로운이름을 UUID로 생성
				+ orgName.substring(idx); //.확장자
	}
	
	//파일이름 nanoTime()을 이용하여 변경
	public static String newFileNameByNanotime(String orgName) {
		int idx=orgName.lastIndexOf(".");
		return orgName.substring(0, idx)+"-"+(System.nanoTime()/1000000)
				+ orgName.substring(idx); //.확장자
	}

	
	

}
