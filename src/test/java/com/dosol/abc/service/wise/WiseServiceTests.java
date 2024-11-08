package com.dosol.abc.service.wise;

import com.dosol.abc.dto.board.BoardDTO;
import com.dosol.abc.dto.wise.WiseDTO;
import com.dosol.abc.service.wise.WiseService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Log4j2
public class WiseServiceTests {

    @Autowired
    private WiseService wiseService;


    @Test
    public void testRandomWise(){
        Long bno = 10L;
        WiseDTO randomWise = wiseService.getRandomWise();
        log.info( randomWise);
    }

    @Test
    public void testFindById2() {

        List<WiseDTO> allWises = wiseService.getAllWises();

        log.info(allWises);
    }

    }

