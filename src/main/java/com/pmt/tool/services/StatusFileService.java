package com.pmt.tool.services;

import com.pmt.tool.dto.TStatusFileDto;

import java.util.Optional;

public interface StatusFileService {
    Optional<TStatusFileDto> insert_sf(TStatusFileDto status);
}
