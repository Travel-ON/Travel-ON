package com.travel.travel_on.model.service;

import com.travel.travel_on.dto.QNA;
import com.travel.travel_on.model.repo.QNARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QNAServiceImpl implements QNAService{

    @Autowired
    QNARepository qRepo;

    @Override
    public Page<QNA> selectQNA(String keyword, Pageable pageable) {
        return qRepo.findByTitleContaining(keyword, pageable);
    }

    @Override
    public QNA selectOne(Integer id) {
        Optional<QNA> result = qRepo.findById(id);
        if(result.isPresent()){
            QNA qna = result.get();
            return qna;
        }
        return null;
    }

    @Override
    public int write(QNA qna) {
        Optional<QNA> result = qRepo.findById(qna.getQnaId());
        if (result.isPresent()){
            return 1;
        }else {
            qRepo.save(qna);
            return 0;
        }
    }

    @Override
    public int update(QNA qna) {
        Optional<QNA> result = qRepo.findById(qna.getQnaId());
        if (result.isPresent()){
            qRepo.save(qna);
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public int delete(Integer id) {
        Optional<QNA> result = qRepo.findById(id);
        result.ifPresent(qna -> {
            qRepo.delete(qna);
        });
        return 1;
    }

}
