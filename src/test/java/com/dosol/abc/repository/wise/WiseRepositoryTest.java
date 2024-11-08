package com.dosol.abc.repository.wise;


import com.dosol.abc.domain.wise.Wise;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.stream.IntStream;


@SpringBootTest
@Log4j2
public class WiseRepositoryTest {
    @Autowired
    private WiseRepository wiseRepository;

    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Wise wise = Wise.builder()
                    .content("Content" + i)
                    .build();

            Wise result = wiseRepository.save(wise);
            log.info("WiseId" + result.getWiseId());
        });
    }
}

