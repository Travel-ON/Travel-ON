package com.travel.travel_on.model.service;

import com.travel.travel_on.dto.FAQ;
import com.travel.travel_on.dto.Notice;
import com.travel.travel_on.model.repo.FAQRepository;
import com.travel.travel_on.model.repo.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    NoticeRepository nRepo;

    @Autowired
    FAQRepository fRepo;

//    @Override
//    public List<Notice> select() {
//        List<Notice> result = nRepo.findAll();
//        if (!result.isEmpty()){
//            return result;
//        }
//        return null;
//    }

    @Override
    public Page<Notice> findPage(Pageable pageable) {
        return nRepo.findAll(pageable);
    }

    @Override
    public int write(Notice notice) {
        Optional<Notice> result = nRepo.findById(notice.getNoticeId());
        if (result.isPresent()){
            return 1;
        }else {
            nRepo.save(notice);
            return 0;
        }
    }

    @Override
    public Notice selectOne(Integer id) {
        Optional<Notice> result = nRepo.findById(id);
        if(result.isPresent()){
            Notice notice = result.get();
            return notice;
        }
        return null;
    }

    @Override
    public int update(Notice notice) {
        Optional<Notice> result = nRepo.findById(notice.getNoticeId());
        if (result.isPresent()){
            nRepo.save(notice);
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public int delete(Integer id) {
        Optional<Notice> result = nRepo.findById(id);
        result.ifPresent(notice -> {
            nRepo.delete(notice);
        });
        return 1;
    }

//    @Override
//    public List<FAQ> selectFAQ() {
//        List<FAQ> result = fRepo.findAll();
//        if (!result.isEmpty()){
//            return result;
//        }
//        return null;
//    }

    @Override
    public Page<FAQ> search(String keyword, Pageable pageable) {
        return fRepo.findByQuestionContaining(keyword, pageable);
    }


}
