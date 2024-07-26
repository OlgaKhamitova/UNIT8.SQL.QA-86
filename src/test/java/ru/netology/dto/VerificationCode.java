package ru.netology.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Data
@AllArgsConstructor

public class VerificationCode {
    String code;
}
