package component;

import com.pmt.tool.dto.FileUploadDto;
import com.pmt.tool.entity.FileUpload;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FileUploadConverter {

    public FileUploadDto entityToDto(@NotNull FileUpload fileUpload) {
        FileUploadDto fileUploadDto = new FileUploadDto();

        fileUploadDto.setIdFile(fileUpload.getIdFile());
        fileUploadDto.setData(fileUpload.getData());
        fileUploadDto.setNameFile(fileUpload.getNameFile());
        fileUploadDto.setPathFile(fileUpload.getPathFile());
        fileUploadDto.setSizeFile(fileUpload.getSizeFile());
        fileUploadDto.setTypeFile(fileUpload.getTypeFile());

        return fileUploadDto;
    }

    public List<FileUploadDto> entityToDto(@NotNull List<FileUpload> fileUploads) {

        return fileUploads.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public FileUpload dtoToEntity(@NotNull FileUploadDto fileUploadDto) {

        FileUpload fileUpload = new FileUpload();

        fileUpload.setIdFile(fileUploadDto.getIdFile());
        fileUpload.setData(fileUploadDto.getData());
        fileUpload.setNameFile(fileUploadDto.getNameFile());
        fileUpload.setPathFile(fileUploadDto.getPathFile());
        fileUpload.setSizeFile(fileUploadDto.getSizeFile());
        fileUpload.setTypeFile(fileUploadDto.getTypeFile());

        return fileUpload;
    }

    public List<FileUpload> dtoToEntity(@NotNull List<FileUploadDto> fileUploadsDto) {
        return fileUploadsDto.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }

}
