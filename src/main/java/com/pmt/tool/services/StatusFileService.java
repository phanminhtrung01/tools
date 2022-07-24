package com.pmt.tool.services;

import com.pmt.tool.component.Converter;
import com.pmt.tool.dto.TStatusFileDto;
import com.pmt.tool.entity.TStatusFile;
import com.pmt.tool.repositories.StatusFileRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class StatusFileService {

    private final StatusFileRepository statusFileRepository;
    private final Converter<TStatusFile, TStatusFileDto> statusFileConverter;

    public StatusFileService(
            StatusFileRepository statusFileRepository,
            Converter<TStatusFile, TStatusFileDto> statusFileConverter) {
        this.statusFileRepository = statusFileRepository;
        this.statusFileConverter = statusFileConverter;
    }

    public Optional<TStatusFileDto> insert_sf(TStatusFileDto status) {

        TStatusFile statusFile = statusFileConverter.dtoToEntity(status, TStatusFile.class);
        statusFile.setDateCompleted(new Date());

        statusFileRepository.save(statusFile);

        return Optional
                .of(statusFileConverter.entityToDto(statusFile, TStatusFileDto.class));
    }

}
