package com.travel.travel_on.model.service;

import com.travel.travel_on.entity.FAQ;
import com.travel.travel_on.entity.Notice;
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
    NoticeRepository noticeRepository;

    @Autowired
    FAQRepository faqRepository;

    @Override
    public Page<Notice> findPage(Pageable pageable) {
        return noticeRepository.findAll(pageable);
    }

    @Override
    public boolean write(Notice notice) {
        if (notice == null){
            return false;
        }else {
            noticeRepository.save(notice);
            return true;
        }
    }

    @Override
    public Notice selectOne(Integer id) {
        Optional<Notice> result = noticeRepository.findById(id);
        if(result.isPresent()){
            Notice notice = result.get();
            return notice;
        }
        return null;
    }

    @Override
    public boolean update(Notice notice) {
        Optional<Notice> result = noticeRepository.findById(notice.getNoticeId());
        if (result.isPresent()){
            noticeRepository.save(notice);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        Optional<Notice> result = noticeRepository.findById(id);

        if(!result.isPresent()){
            return  false;
        }else{
            result.ifPresent(notice -> {
                noticeRepository.delete(notice);
            });
            return true;
        }
    }

    @Override
    public Page<FAQ> faqPage(Pageable pageable) {
        return faqRepository.findAll(pageable);
    }

    @Override
    public Page<FAQ> search(String keyword, Pageable pageable) {
        return faqRepository.findByQuestionContaining(keyword, pageable);
    }

}
