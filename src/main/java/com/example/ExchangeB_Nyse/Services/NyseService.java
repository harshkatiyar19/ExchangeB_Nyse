package com.example.ExchangeB_Nyse.Services;


import com.example.ExchangeB_Nyse.DTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class NyseService {

    @Autowired
    private SimpMessagingTemplate template;

    private final Random random = new Random();

    @Scheduled(fixedRate = 2000) // every 2 seconds
    public void sendRandomNumber() {
        float min = 10.5f;
        float max = 20.5f;
        int year=2025,month=6,date=24,hours=15,minutes=30;

        float price = min + random.nextFloat() * (max - min);
        int qty = random.nextInt(10,101);
        int filledQty = random.nextInt(0,qty+1);
        int remainingQty=qty-filledQty;

        int orderStatusCode =0;

        LocalDateTime specificTime = LocalDateTime.of(year,month,date,hours,minutes);
        LocalDateTime timestamp = LocalDateTime.now();

        int orderValidityCode = random.nextInt(1,6);


        if(filledQty==0){
            orderStatusCode=1;//,7;
        } else if (filledQty>0&&filledQty<qty) {
            if(timestamp.isAfter(specificTime)&&orderValidityCode==1){
                orderStatusCode=4;
            }else{
                orderStatusCode=2;
            }

        }else {
            orderStatusCode=3;
        }

        Side side = Side.fromCode(random.nextInt(1,3));
        OrderType orderType= OrderType.fromCode(random.nextInt(1,9));
        OrderStatus orderStatus = OrderStatus.fromCode(orderStatusCode) ;
        OrderValidity orderValidity = OrderValidity.fromCode(orderValidityCode);
        int id = random.nextInt();
        Book book = new Book(id,"APPL",side,price,qty,filledQty,remainingQty,orderType,orderStatus,orderValidity,timestamp);


        template.convertAndSend("/topic/book", book);
//        System.out.println("Sending book: " + book);

    }
}
