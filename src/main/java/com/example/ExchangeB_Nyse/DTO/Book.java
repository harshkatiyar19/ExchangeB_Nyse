package com.example.ExchangeB_Nyse.DTO;

import java.time.LocalDateTime;
import java.util.Date;

public record Book(
        int orderId,
        Symbol symbol,
        Side side,
        float price,
        int qty,
        int filledQty,
        int remainingQty,
        OrderType orderType,
        OrderStatus orderStatus,
        OrderValidity orderValidity,
        Date timeStamp,
        String exchange
        //int userId,

) {}
