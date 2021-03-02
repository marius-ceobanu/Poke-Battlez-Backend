package com.example.pokebattlez.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountConfirmation {
    private boolean state;
    private long id;
    private String username;
}
