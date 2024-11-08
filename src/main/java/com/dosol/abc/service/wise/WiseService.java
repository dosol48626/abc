package com.dosol.abc.service.wise;

import com.dosol.abc.domain.wise.Wise;
import com.dosol.abc.dto.wise.WiseDTO;

import java.util.List;

public interface WiseService {

    // 모든 Wise를 조회하여 WiseDTO 리스트로 반환
    List<WiseDTO> getAllWises();

    // Wise ID로 Wise를 조회하여 WiseDTO로 반환
    public WiseDTO getRandomWise();

    /*WiseDTO getWiseById(Long id);*/
}
