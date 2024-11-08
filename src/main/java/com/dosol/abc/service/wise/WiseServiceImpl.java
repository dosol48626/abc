package com.dosol.abc.service.wise;

import com.dosol.abc.domain.wise.Wise;
import com.dosol.abc.dto.wise.WiseDTO;
import com.dosol.abc.repository.wise.WiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Service
public class WiseServiceImpl implements WiseService {
    private final WiseRepository wiseRepository;

    @Autowired
    public WiseServiceImpl(WiseRepository wiseRepository) {
        this.wiseRepository = wiseRepository;
    }

    @Override
    public List<WiseDTO> getAllWises() {
        List<Wise> wises = wiseRepository.findAll();
        Random random = new Random();
        return wises.stream()
                .map(wise -> WiseDTO.builder()
                        .wiseId(wise.getWiseId())
                        .content(wise.getContent())
                        .randomNumber(random.nextInt(100) + 1)  // 1~100 사이의 랜덤 숫자 생성
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public WiseDTO getRandomWise() {
        // 모든 Wise 객체를 리스트로 가져옴
        List<Wise> wises = wiseRepository.findAll();

        // 만약 Wise가 없으면 예외 처리 (빈 리스트 방지)
        if (wises.isEmpty()) {
            throw new RuntimeException("No Wise quotes available");
        }

        // 랜덤으로 하나의 Wise 객체 선택
        Random random = new Random();
        Wise randomWise = wises.get(random.nextInt(wises.size())); // 랜덤 인덱스로 선택

        // WiseDTO로 변환하여 반환
        return WiseDTO.builder()
                .wiseId(randomWise.getWiseId())
                .content(randomWise.getContent())
                .randomNumber(random.nextInt(100) + 1) // 랜덤 숫자 생성 (디스플레이용)
                .build();
    }

}