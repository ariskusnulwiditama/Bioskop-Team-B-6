package TeamB.Bioskop6.controller;

import TeamB.Bioskop6.dto.AttachmentResponse;
import TeamB.Bioskop6.entity.Attachment;
import TeamB.Bioskop6.service.AttachmentServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Tag(name = "8. Attachment Controller")
@SecurityRequirement(name = "bearer-key")
public class AttachmentController {
    private final AttachmentServiceImpl attachmentService;

    @PostMapping("/dashboard/upload")
    public AttachmentResponse uploadFile(@RequestParam("file") MultipartFile file) throws Exception {

        Attachment attachment = null;
        String downloadUrl = "";
        attachment=this.attachmentService.saveAttachment(file);
        downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(attachment.getId()).toUriString();

        return new AttachmentResponse(attachment.getFileName(), downloadUrl, file.getContentType(), file.getSize());

    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) throws Exception {

        Attachment attachment = null;
        attachment = this.attachmentService.getAttachment(fileId);

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + attachment.getFileName() + "\"").body(new ByteArrayResource(attachment.getData()));

    }

    @GetMapping("/showFile/{fileId}")
    public ResponseEntity<byte[]> showFile(@PathVariable String fileId) throws Exception{
        Attachment attachment = this.attachmentService.getAttachment(fileId);
        byte[] fileBytes = attachment.getData();

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(attachment.getFileType())).body(fileBytes);

    }

}