package com.ciclo3.reto3.service;

import com.ciclo3.reto3.entities.Score;
import com.ciclo3.reto3.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScoreService {

    @Autowired
    public ScoreRepository scoreRepository;

    public List<Score> getAll(){return scoreRepository.getAll();}

    public Optional<Score> getScore(int id){return scoreRepository.getScore(id);}


    public Score save(Score score){
        if(score.getIdScore()==null){
            return scoreRepository.save(score);
        }else{
            Optional<Score> e= scoreRepository.getScore(score.getIdScore());
            if (e.isPresent()){
                return scoreRepository.save(score);
            }else {
                return score;
            }
        }
    }

    public Score update(Score score){
        if(score.getIdScore()!=null){
            Optional<Score> e = scoreRepository.getScore(score.getIdScore());
            if (!e.isPresent()){
                if(score.getMessageText()!=null){
                    e.get().setMessageText(score.getMessageText());
                }
                if(score.getStars()!=null){
                    e.get().setStars(score.getStars());
                }
                scoreRepository.save(e.get());
                return e.get();
            }else{
                return score;
            }
        }else {
            return score;
        }
    }

    public boolean delete(int id){
        boolean flag=false;
        Optional<Score> e= scoreRepository.getScore(id);
        if(e.isPresent()){
            scoreRepository.delete(e.get());
            flag=true;
        }
        return flag;
    }

}

