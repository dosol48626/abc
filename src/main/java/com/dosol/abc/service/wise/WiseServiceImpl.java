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
                        .writer(wise.getWriter())
                        .randomNumber(random.nextInt(100) + 1)  // 1~100 사이의 랜덤 숫자 생성
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public WiseDTO getRandomWise() {


        // 랜덤으로 하나의 Wise 객체 선택
        Random random = new Random();
        Long idrandom=random.nextLong(100);
        Wise randomWise = wiseRepository.findById(idrandom).orElseThrow();

        // WiseDTO로 변환하여 반환
        return WiseDTO.builder()
                .wiseId(randomWise.getWiseId())
                .content(randomWise.getContent())
                .writer(randomWise.getWriter())
                .build();
    }

}