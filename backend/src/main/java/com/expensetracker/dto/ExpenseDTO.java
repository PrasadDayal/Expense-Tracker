package com.expensetracker.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDTO {
    private Long id;
    private String title;
    private BigDecimal amount; // Converted INR
    private BigDecimal originalAmount;
    private String originalCurrency;
    private BigDecimal convertedAmountINR;
    private String currency; // Legacy field, might be same as originalCurrency
    private UserDTO paidBy;
    private Long groupId;
    private LocalDate date;
    private List<SplitDTO> splits;
}
