package com.oda.service.Impl.patient;

import com.oda.authorizeduser.AuthorizedUser;
import com.oda.dto.doctor.DoctorDto;
import com.oda.dto.patient.FeedbackDto;
import com.oda.model.patient.Feedback;
import com.oda.repo.patient.FeedbackRepo;
import com.oda.service.Impl.doctor.DoctorServiceImpl;
import com.oda.service.patient.FeedbackService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author kulPaudel
 * @project OnlineDoctorAppointMent
 * @Date 4/18/22
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepo feedbackRepo;
    private final DoctorServiceImpl doctorService;

    public FeedbackServiceImpl(FeedbackRepo feedbackRepo, DoctorServiceImpl doctorService) {
        this.feedbackRepo = feedbackRepo;
        this.doctorService = doctorService;
    }

    @Override
    public FeedbackDto save(FeedbackDto feedbackDto) throws ParseException, IOException {
        Feedback feedback = new Feedback();

        feedback.setId(feedbackDto.getId());
        feedback.setComment(feedbackDto.getComment());
        feedback.setPatient(AuthorizedUser.getPatient());
        feedback.setDoctor(feedbackDto.getDoctor());
        feedback.setRating(feedbackDto.getRating());
        feedback.setFeedbackDate(new Date());

        //findDoctor by id
      DoctorDto doctorDto = doctorService.findById(feedbackDto.getDoctorId());
        //calculate rating
        Double previousRating = doctorDto.getRating();
        Double currentRating = 0D;
        if (previousRating != null){
            //get current rating
            Double currentRatingByUser = feedbackDto.getRating();

            //get number of rater
            Integer feedbackNumber = doctorDto.getNumberOfFeedback();

            previousRating = previousRating * feedbackNumber;
            previousRating +=currentRatingByUser;
            //add new feedback
            feedbackNumber +=1;

            currentRating = previousRating /feedbackNumber;
            doctorDto.setRating(currentRating);
            doctorDto.setNumberOfFeedback(feedbackNumber);
        }else {
            doctorDto.setRating(2 + feedbackDto.getRating());
            doctorDto.setNumberOfFeedback(1);
        }
        //save feedback
        feedbackRepo.save(feedback);
        //save doctor
        doctorService.save(doctorDto);

        return FeedbackDto.builder().id(feedback.getId()).build();
    }

    @Override
    public FeedbackDto findById(Integer integer) {
        return null;
    }

    @Override
    public List<FeedbackDto> findALl() {
        return null;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    public List<Feedback> findFeedbackBYDoctorID(Integer doctorId){
        return feedbackRepo.findFeedbackByDoctorId(doctorId);
    }
}
