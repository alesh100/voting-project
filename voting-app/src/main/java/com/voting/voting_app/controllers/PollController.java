package com.voting.voting_app.controllers;

import com.voting.voting_app.model.Poll;
import com.voting.voting_app.request.Vote;
import com.voting.voting_app.services.PollService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/polls")
public class PollController {

    private PollService pollService;

    public PollController(PollService pollService) {
        this.pollService = pollService;
    }

    @PostMapping
    public Poll createPoll(@RequestBody Poll poll){
        return pollService.createPoll(poll);
    }

    @GetMapping
    public List<Poll> getAllPoll(){
        return pollService.getAllPoll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPoll(@PathVariable Long id) {
        return pollService.getPollById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/vote")
    public void vote(@RequestBody Vote vote){
         pollService.vote(vote.getPollId(), vote.getOptionIndex());
    }


}
