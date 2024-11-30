package com.voting.voting_app.services;

import com.voting.voting_app.model.OptionVote;
import com.voting.voting_app.model.Poll;
import com.voting.voting_app.repositories.PollRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class PollService {

    private PollRepository pollRepository;

    public PollService(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    public Poll createPoll(Poll poll) {
        return pollRepository.save(poll);
    }

    public List<Poll> getAllPoll() {
        return pollRepository.findAll();
    }


    public Optional<Object> getPollById(Long id) {
        return Optional.of(pollRepository.findById(id));
    }

    public void vote(Long pollId, int optionIndex) {
        //get poll from db
        Poll poll = pollRepository.findById(pollId).orElseThrow(()
                -> new RuntimeException("Poll not found"));
        //get all options
        List<OptionVote> options = poll.getOptions();
        // if index for vote is not valid, throw error
        if(optionIndex < 0 || optionIndex >+ options.size()){
            throw new IllegalArgumentException("invalid option index");
        }
        //get selected options
        OptionVote selectedOption = options.get(optionIndex);
        // increment vote for selected option
        selectedOption.setVoteOption(selectedOption.getVoteOption() + 1);
        // save incremented option into db
        pollRepository.save(poll);
    }
}
