package com.example.adplacementservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
    private Integer senderId;
    private Integer recipientId;
    private Integer chatId;
    private String content;
}
