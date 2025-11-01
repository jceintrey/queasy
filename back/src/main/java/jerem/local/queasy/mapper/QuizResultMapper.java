package jerem.local.queasy.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import jerem.local.queasy.dto.QuizResultDTO;
import jerem.local.queasy.model.QuizResult;

@Component
public class QuizResultMapper implements Mapper<QuizResult, QuizResultDTO> {

    private final ModelMapper modelMapper;

    public QuizResultMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

    }

    @Override
    public QuizResultDTO toDto(QuizResult entity) {
        return this.modelMapper.map(entity, QuizResultDTO.class);
    }

    @Override
    public QuizResult toEntity(QuizResultDTO dto) {

        QuizResult quizResult = this.modelMapper.map(dto, QuizResult.class);
        return quizResult;
    }

}
