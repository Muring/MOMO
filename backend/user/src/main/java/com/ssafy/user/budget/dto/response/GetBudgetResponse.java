package com.ssafy.user.budget.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.user.budget.domain.Budget;
import java.sql.Date;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class GetBudgetResponse {

    private int budgetId;
    private String name;
    private long monthlyFee;
    private long currentFee;
    private long finalFee;
    private int monthlyDueDate;
    private LocalDate finalDueDate;
    private boolean status;

    @QueryProjection
    public GetBudgetResponse(int budgetId, String name, long monthlyFee, long currentFee,
        long finalFee,
        int monthlyDueDate, LocalDate finalDueDate, boolean status) {
        this.budgetId = budgetId;
        this.name = name;
        this.monthlyFee = monthlyFee;
        this.currentFee = currentFee;
        this.finalFee = finalFee;
        this.monthlyDueDate = monthlyDueDate;
        this.finalDueDate = finalDueDate;
        this.status = status;
    }

    public static GetBudgetResponse from(Budget budget) {
        return new GetBudgetResponse(
            budget.getBudgetId(),
            budget.getName(),
            (budget.getFinalMoney() - budget.getCurrentMoney()) / leftCollectionDate(
                budget.getMonthlyDueDate(), LocalDate.now(), budget.getDueDate()),
            budget.getCurrentMoney(),
            budget.getFinalMoney(),
            budget.getMonthlyDueDate(),
            budget.getDueDate(),
            true // status
        );
    }

    public static int leftCollectionDate(int day, LocalDate today, LocalDate lastDay) {
        int cnt = 0;

        LocalDate tempDate = today.withDayOfMonth(day);

        if (tempDate.isBefore(today)) {
            tempDate = tempDate.plusMonths(1);
        }

        while (!tempDate.isAfter(lastDay)) {
            if (tempDate.getDayOfMonth() == day) {
                cnt++;
            }
            tempDate = tempDate.plusMonths(1);
        }

        return cnt;
    }
}
