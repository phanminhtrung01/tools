package com.pmt.tool.services.serviceIml;

import com.pmt.tool.component.Converter;
import com.pmt.tool.dto.TStatusFileDto;
import com.pmt.tool.entity.TStatusFile;
import com.pmt.tool.repositories.StatusFileRepository;
import com.pmt.tool.services.StatusFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatusFileServiceIml implements StatusFileService {
    private final StatusFileRepository statusFileRepository;
    private final Converter<TStatusFile, TStatusFileDto> statusFileConverter;

    @Override
    public Optional<TStatusFileDto> insert_sf(TStatusFileDto status) {

        TStatusFile statusFile = statusFileConverter.dtoToEntity(status, TStatusFile.class);
        statusFile.setDateCompleted(new Date());

        statusFileRepository.save(statusFile);

        return Optional
                .of(statusFileConverter.entityToDto(statusFile, TStatusFileDto.class));
    }
}
