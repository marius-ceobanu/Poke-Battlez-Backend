package com.example.pokebattlez.controller.service;

import com.example.pokebattlez.controller.repository.AccountRepository;
import com.example.pokebattlez.controller.repository.PokemonRepository;
import com.example.pokebattlez.controller.repository.TeamRepository;
import com.example.pokebattlez.model.OnlineUsers;
import com.example.pokebattlez.model.entity.Pokemon;
import com.example.pokebattlez.model.entity.Team;
import com.example.pokebattlez.model.request.PokemonRequest;
import com.example.pokebattlez.model.request.TeamRequest;
import com.example.pokebattlez.model.request.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TeamService {

    private final OnlineUsers onlineUsers;
    private final AccountRepository accountRepository;
    private final PokemonRepository pokemonRepository;
    private final TeamRepository teamRepository;

    private void update(Pokemon pokemon, PokemonRequest pokemonRequest) {
        pokemon.updateFields(pokemonRequest);
        pokemonRepository.save(pokemon);
    }

    private void create(Long userId, PokemonRequest pokemonRequest) {
        Team team = teamRepository
                .findById(pokemonRequest.getTeamId())
                .orElse(Team.builder()
                        .trainer(accountRepository.findById(userId).orElse(null))
                        .pokemon(new ArrayList<>())
                        .build()
                );

        team.addPokemon(Pokemon.from(pokemonRequest));
        teamRepository.save(team);
    }

    public void updatePokemon(Long userId, PokemonRequest pokemonRequest) {
        pokemonRepository.findById(pokemonRequest.getId()).ifPresentOrElse(
                pokemon -> update(pokemon, pokemonRequest),
                () -> create(userId, pokemonRequest)
        );
    }

    public TeamRequest getTeam(Long id) {
        return teamRepository.findTeamByTrainer_Id(id)
                .map(team -> TeamRequest.builder()
                        .teamId(team.getId())
                        .pokemon(team.getPokemon().stream().map(Pokemon::generateRequest).collect(Collectors.toList()))
                        .build()
                )
                .orElse(null);
    }

    public TeamRequest getTeamByConnId(String conId) {
        return onlineUsers.getUser(conId).map(User::getId).map(this::getTeam).orElse(null);
    }
}
