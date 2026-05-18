package co.com.polijic.ucation.postgresql.adapters;

import co.com.polijic.ucation.domain.common.MentorModel;
import co.com.polijic.ucation.domain.repositories.MentorRepositoryPort;
import co.com.polijic.ucation.domain.util.Mapper;
import co.com.polijic.ucation.postgresql.entities.MentorEntity;
import co.com.polijic.ucation.postgresql.repository.MentorRepository;
import org.springframework.stereotype.Service;

@Service
public class MentorBdAdapter implements MentorRepositoryPort {

    private final MentorRepository mentorRepository;

    public MentorBdAdapter(MentorRepository mentorRepository) {
        this.mentorRepository = mentorRepository;
    }

    @Override
    public MentorModel guardarMentor(MentorModel mentor) {
        MentorEntity mentorEntity = Mapper.map(mentor, MentorEntity.class);
        return Mapper.map(mentorRepository.save(mentorEntity), MentorModel.class);
    }
}
