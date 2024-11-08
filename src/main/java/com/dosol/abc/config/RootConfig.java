package com.dosol.abc.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RootConfig {

    @Bean
    public ModelMapper getMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper;
    }
}

//package com.dosol.abc.config;
//
//import com.dosol.abc.domain.board.Reply;
//import com.dosol.abc.domain.board.Board;
//import com.dosol.abc.domain.user.User;
//import com.dosol.abc.dto.board.ReplyDTO;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.TypeMap;
//import org.modelmapper.convention.MatchingStrategies;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RootConfig {
//
//    @Bean
//    public ModelMapper getMapper() {
//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.getConfiguration()
//                .setFieldMatchingEnabled(true)
//                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
//                .setMatchingStrategy(MatchingStrategies.LOOSE);
//
//        // ReplyDTO와 Reply 간의 명확한 매핑 규칙 추가
//        TypeMap<ReplyDTO, Reply> typeMap = modelMapper.createTypeMap(ReplyDTO.class, Reply.class);
//        typeMap.addMappings(mapper -> {
//            mapper.map(ReplyDTO::getReplyId, Reply::setReplyId);
//            // Board와 User의 매핑을 명확히 지정
//            mapper.<Long>map(ReplyDTO::getBoardId, (dest, v) -> {
//                Board board = new Board();
//                board.setBoardId(v);
//                dest.setBoard(board);
//            });
//            mapper.<Long>map(ReplyDTO::getUserId, (dest, v) -> {
//                User user = new User();
//                user.setUserId(v);
//                dest.setUser(user);
//            });
//        });
//
//        return modelMapper;
//    }
//}
