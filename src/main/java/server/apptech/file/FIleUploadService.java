package server.apptech.file;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import server.apptech.file.domain.File;
import server.apptech.file.domain.FileType;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FIleUploadService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final FileRepository fileRepository;

    public File saveFile(MultipartFile multipartFile) throws IOException {

        File file = uploadFile(multipartFile);

        Arrays.stream(FileType.values())
                .filter(fileType -> fileType.name().toLowerCase().equals(multipartFile.getContentType().replace("/", "_")))
                .findFirst()
                .ifPresent(file::assignFileType);

        return fileRepository.save(file);
    }
    private File uploadFile(MultipartFile multipartFile) throws IOException {

        String originalFilename = multipartFile.getOriginalFilename();
        String uuid = UUID.randomUUID().toString() + "-" + originalFilename;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, originalFilename, multipartFile.getInputStream(), metadata);
        String fileUrl = amazonS3.getUrl(bucket, originalFilename).toString();

        return File.builder()
                .url(fileUrl)
                .uuid(uuid)
                .build();
    }
}
