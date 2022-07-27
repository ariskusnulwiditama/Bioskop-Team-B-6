package TeamB.Bioskop6.service;

import TeamB.Bioskop6.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttachmentService {
    Attachment saveAttachment(MultipartFile file) throws Exception;

    Attachment getAttachment(String fileId) throws Exception;

    List<Attachment> findAllAttachment();
}
