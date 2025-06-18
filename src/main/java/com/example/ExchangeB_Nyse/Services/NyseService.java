package com.example.ExchangeB_Nyse.Services;


import com.example.ExchangeB_Nyse.DTO.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;



@Service
public class NyseService {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private TaskScheduler taskScheduler;

    private final Random random = new Random();

    @PostConstruct
    public void startRandomSchedule() {
        scheduleNextRun();
    }

    private void scheduleNextRun() {
        long delay = 1000 + random.nextInt(5000); // 1s to 6s delay
        taskScheduler.schedule(this::sendRandomNumber, Instant.now().plusMillis(delay));
    }

    private float roundToTwoDecimals(float value) {
        return (float) (Math.round(value * 100.0) / 100.0);
    }

    public void sendRandomNumber() {
        Symbol symbol=Symbol.fromCode(random.nextInt(1,8));
        float basePrice;
        float minFluctuation;
        float maxFluctuation;
        int minQty;
        int maxQty;

        switch (symbol) {
            case APPL -> {
                basePrice = 180.0f;
                minFluctuation = -3.0f;
                maxFluctuation = 5.0f;
                minQty = 10;
                maxQty = 200;
            }
            case TSLA -> {
                basePrice = 190.0f;
                minFluctuation = -8.0f;
                maxFluctuation = 10.0f;
                minQty = 5;
                maxQty = 100;
            }
            case GOOGL -> {
                basePrice = 135.0f;
                minFluctuation = -2.0f;
                maxFluctuation = 4.0f;
                minQty = 15;
                maxQty = 250;
            }
            case MSFT -> {
                basePrice = 340.0f;
                minFluctuation = -4.0f;
                maxFluctuation = 6.0f;
                minQty = 10;
                maxQty = 150;
            }
            case AMZN -> {
                basePrice = 125.0f;
                minFluctuation = -3.0f;
                maxFluctuation = 5.0f;
                minQty = 20;
                maxQty = 300;
            }
            case NVDA -> {
                basePrice = 1100.0f;
                minFluctuation = -20.0f;
                maxFluctuation = 30.0f;
                minQty = 2;
                maxQty = 50;
            }
            case META -> {
                basePrice = 460.0f;
                minFluctuation = -10.0f;
                maxFluctuation = 12.0f;
                minQty = 8;
                maxQty = 120;
            }
            default -> {
                basePrice = 100.0f;
                minFluctuation = -5.0f;
                maxFluctuation = 5.0f;
                minQty = 10;
                maxQty = 100;
            }
        }

        float price = roundToTwoDecimals(basePrice + minFluctuation + random.nextFloat() * (maxFluctuation - minFluctuation));
        int qty = random.nextInt(minQty, maxQty + 1);

        int year=2025,month=6,date=24,hours=15,minutes=30;
        int filledQty = random.nextInt(0,qty+1);

        int remainingQty=qty-filledQty;

        int orderStatusCode =0;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month); // or 5
        calendar.set(Calendar.DAY_OF_MONTH, date);
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 0);
        Date specificTime = calendar.getTime();
        Date timestamp = new Date();

        int orderValidityCode = random.nextInt(1,6);


        if(filledQty==0){
            orderStatusCode=1;//,7;
        } else if (filledQty>0&&filledQty<qty) {
            if(timestamp.after(specificTime)&&orderValidityCode==1){
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


        int id = random.nextInt(0,10000);
        Book book = new Book(id,symbol,side,price,qty,filledQty,remainingQty,orderType,orderStatus,orderValidity,timestamp,"NASDQ");


        template.convertAndSend("/topic/book", book);
//        System.out.println("Sending book: " + book);
        scheduleNextRun();
    }
}
