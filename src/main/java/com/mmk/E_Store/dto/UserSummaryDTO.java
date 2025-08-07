package com.mmk.E_Store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSummaryDTO {
    //private Long id;
    private String name;
    // Removed: address, emailAddress, password (sensitive/private info)
}