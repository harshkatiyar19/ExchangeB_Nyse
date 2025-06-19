package com.example.ExchangeB_Nyse.Services;


import com.example.ExchangeB_Nyse.DTO.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.*;

//
//
//@Service
//public class NyseService {
//
//    @Autowired
//    private SimpMessagingTemplate template;
//
//    @Autowired
//    private TaskScheduler taskScheduler;
//
//    private final Random random = new Random();
//
//    @PostConstruct
//    public void startRandomSchedule() {
//        scheduleNextRun();
//    }
//
//    private void scheduleNextRun() {
//        long delay = 1000 + random.nextInt(5000); // 1s to 6s delay
//        taskScheduler.schedule(this::sendRandomNumber, Instant.now().plusMillis(delay));
//    }
//
//    private float roundToTwoDecimals(float value) {
//        return (float) (Math.round(value * 100.0) / 100.0);
//    }
//
//    public void sendRandomNumber() {
//        Symbol symbol=Symbol.fromCode(random.nextInt(1,8));
//        float basePrice;
//        float minFluctuation;
//        float maxFluctuation;
//        int minQty;
//        int maxQty;
//        String exchange="NYSE";
//
//        switch (symbol) {
//            case AAPL -> {
//                basePrice = 180.0f;
//                minFluctuation = -3.0f;
//                maxFluctuation = 5.0f;
//                minQty = 10;
//                maxQty = 200;
//            }
//            case TSLA -> {
//                basePrice = 190.0f;
//                minFluctuation = -8.0f;
//                maxFluctuation = 10.0f;
//                minQty = 5;
//                maxQty = 100;
//            }
//            case GOOGL -> {
//                basePrice = 135.0f;
//                minFluctuation = -2.0f;
//                maxFluctuation = 4.0f;
//                minQty = 15;
//                maxQty = 250;
//            }
//            case MSFT -> {
//                basePrice = 340.0f;
//                minFluctuation = -4.0f;
//                maxFluctuation = 6.0f;
//                minQty = 10;
//                maxQty = 150;
//            }
//            case AMZN -> {
//                basePrice = 125.0f;
//                minFluctuation = -3.0f;
//                maxFluctuation = 5.0f;
//                minQty = 20;
//                maxQty = 300;
//            }
//            case NVDA -> {
//                basePrice = 1100.0f;
//                minFluctuation = -20.0f;
//                maxFluctuation = 30.0f;
//                minQty = 2;
//                maxQty = 50;
//            }
//            case META -> {
//                basePrice = 460.0f;
//                minFluctuation = -10.0f;
//                maxFluctuation = 12.0f;
//                minQty = 8;
//                maxQty = 120;
//            }
//            default -> {
//                basePrice = 100.0f;
//                minFluctuation = -5.0f;
//                maxFluctuation = 5.0f;
//                minQty = 10;
//                maxQty = 100;
//            }
//        }
//
//        float price = roundToTwoDecimals(basePrice + minFluctuation + random.nextFloat() * (maxFluctuation - minFluctuation));
//        int qty = random.nextInt(minQty, maxQty + 1);
//
//        int year=2025,month=6,date=24,hours=15,minutes=30;
//        int filledQty = random.nextInt(0,qty+1);
//
//        int remainingQty=qty-filledQty;
//
//        int orderStatusCode =0;
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR, year);
//        calendar.set(Calendar.MONTH, month); // or 5
//        calendar.set(Calendar.DAY_OF_MONTH, date);
//        calendar.set(Calendar.HOUR_OF_DAY, hours);
//        calendar.set(Calendar.MINUTE, minutes);
//        calendar.set(Calendar.SECOND, 0);
//        Date specificTime = calendar.getTime();
//        Date timestamp = new Date();
//
//        int orderValidityCode = random.nextInt(1,6);
//
//
//        if(filledQty==0){
//            orderStatusCode=1;//,7;
//        } else if (filledQty>0&&filledQty<qty) {
//            if(timestamp.after(specificTime)&&orderValidityCode==1){
//                orderStatusCode=4;
//            }else{
//                orderStatusCode=2;
//            }
//
//        }else {
//            orderStatusCode=3;
//        }
//
//        Side side = Side.fromCode(random.nextInt(1,3));
//        OrderType orderType= OrderType.fromCode(random.nextInt(1,9));
//        OrderStatus orderStatus = OrderStatus.fromCode(orderStatusCode) ;
//        OrderValidity orderValidity = OrderValidity.fromCode(orderValidityCode);
//
//
//        int id = random.nextInt(0,10000);
//        Book book = new Book(id,symbol,side,price,qty,filledQty,remainingQty,orderType,orderStatus,orderValidity,timestamp,exchange);
//
//
//        template.convertAndSend("/topic/order", book);
////        System.out.println("Sending book: " + book);
//        scheduleNextRun();
//    }
//}




import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
//import java.util.*;
//
//@Service
//public class NyseService {
//
//    @Autowired
//    private SimpMessagingTemplate template;
//
//    @Autowired
//    private TaskScheduler taskScheduler;
//
//    private final Random random = new Random();
//    private final Map<Symbol, Float> lastTradedPrices = new EnumMap<>(Symbol.class);
//    private final float tickSize = 0.5f;
//
//    @PostConstruct
//    public void start() {
//        initPrices();
//        scheduleNextRun();
//    }
//
//    private void initPrices() {
//        lastTradedPrices.put(Symbol.AAPL, 180.0f);
//        lastTradedPrices.put(Symbol.TSLA, 190.0f);
//        lastTradedPrices.put(Symbol.GOOGL, 135.0f);
//        lastTradedPrices.put(Symbol.MSFT, 340.0f);
//        lastTradedPrices.put(Symbol.AMZN, 125.0f);
//        lastTradedPrices.put(Symbol.NVDA, 1100.0f);
//        lastTradedPrices.put(Symbol.META, 460.0f);
//    }
//
//    private void scheduleNextRun() {
//        long delay = 50 + random.nextInt(500); // 1s to 6s
//        taskScheduler.schedule(this::sendRandomBook, Instant.now().plusMillis(delay));
//    }
//
//    private float roundToTick(float value) {
//        return Math.round(value / tickSize) * tickSize;
//    }
//
//    public void sendRandomBook() {
//        Symbol symbol = Symbol.fromCode(random.nextInt(1, 8));
//        float lastPrice = lastTradedPrices.get(symbol);
//        Side side = Side.fromCode(random.nextInt(1, 3)); // 1: BUY, 2: SELL
//
//        float fluctuation = random.nextFloat() * (side == Side.BUY ? -1 : 1) * getFluctuationRange(symbol);
//        float price = roundToTick(lastPrice + fluctuation);
//
//        int qty = generateSkewedQty(symbol);
//        int filledQty = random.nextInt(0, qty + 1);
//        int remainingQty = qty - filledQty;
//
//        OrderType orderType = OrderType.fromCode(random.nextInt(1, 9));
//        OrderValidity orderValidity = OrderValidity.fromCode(random.nextInt(1, 6));
//        Date timestamp = new Date();
//
//        int orderStatusCode = computeOrderStatus(filledQty, qty, timestamp, orderValidity);
//        OrderStatus orderStatus = OrderStatus.fromCode(orderStatusCode);
//
//        Book book = new Book(
//                random.nextInt(10000),
//                symbol,
//                side,
//                price,
//                qty,
//                filledQty,
//                remainingQty,
//                orderType,
//                orderStatus,
//                orderValidity,
//                timestamp,
//                "NYSE"
//        );
//
//        // Update market price on filled order
//        if (filledQty > 0) {
//            lastTradedPrices.put(symbol, price);
//        }
//
//        template.convertAndSend("/topic/order", book);
//        System.out.println("Sent: " + book);
//        scheduleNextRun();
//    }
//
//    private float getFluctuationRange(Symbol symbol) {
//        return switch (symbol) {
//            case AAPL -> 3f;
//            case TSLA -> 6f;
//            case GOOGL -> 2f;
//            case MSFT -> 4f;
//            case AMZN -> 3f;
//            case NVDA -> 15f;
//            case META -> 6f;
//        };
//    }
//
//    private int generateSkewedQty(Symbol symbol) {
//        int min, max;
//        switch (symbol) {
//            case AAPL -> { min = 10; max = 200; }
//            case TSLA -> { min = 5; max = 100; }
//            case GOOGL -> { min = 15; max = 250; }
//            case MSFT -> { min = 10; max = 150; }
//            case AMZN -> { min = 20; max = 300; }
//            case NVDA -> { min = 2; max = 50; }
//            case META -> { min = 8; max = 120; }
//            default -> { min = 10; max = 100; }
//        }
//        // Skewed distribution: more small orders, fewer big ones
//        float gaussian = (float) Math.abs(random.nextGaussian());
//        return Math.min(max, Math.max(min, Math.round(gaussian * (max - min) / 2)));
//    }
//
//    private int computeOrderStatus(int filledQty, int qty, Date timestamp, OrderValidity validity) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2025, Calendar.JUNE, 24, 15, 30, 0);
//        Date expiry = calendar.getTime();
//
//        if (filledQty == 0) return 1; // Pending
//        else if (filledQty < qty) {
//            if (timestamp.after(expiry) && validity == OrderValidity.IOC) return 4; // Expired
//            else return 2; // Partially Filled
//        } else return 3; // Filled
//    }
//}



@Service
public class NyseService {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private TaskScheduler taskScheduler;

    private final Random random = new Random();
    private final Map<Symbol, Float> lastTradedPrices = new EnumMap<>(Symbol.class);
    private final float tickSize = 0.5f;

    @PostConstruct
    public void start() {
        initPrices();
        scheduleNextRun();
    }

    private void initPrices() {
        lastTradedPrices.put(Symbol.AAPL, 180.0f);
        lastTradedPrices.put(Symbol.TSLA, 190.0f);
        lastTradedPrices.put(Symbol.GOOGL, 135.0f);
        lastTradedPrices.put(Symbol.MSFT, 340.0f);
        lastTradedPrices.put(Symbol.AMZN, 125.0f);
        lastTradedPrices.put(Symbol.NVDA, 1100.0f);
        lastTradedPrices.put(Symbol.META, 460.0f);
    }

    private void scheduleNextRun() {
        long delay = 1000 + random.nextInt(5000); // 1s to 6s
        taskScheduler.schedule(this::sendRandomBook, Instant.now().plusMillis(delay));
    }

    private float roundToTick(float value) {
        return Math.round(value / tickSize) * tickSize;
    }

    public void sendRandomBook() {
        Symbol symbol = Symbol.fromCode(random.nextInt(1, 8));
        float lastPrice = lastTradedPrices.get(symbol);
        Side side = Side.fromCode(random.nextInt(1, 3)); // 1: BUY, 2: SELL

        float price = generateRealisticPrice(symbol, lastPrice, side);
        int qty = generateSkewedQty(symbol);
        int filledQty = random.nextInt(0, qty + 1);
        int remainingQty = qty - filledQty;

        OrderType orderType = OrderType.fromCode(random.nextInt(1, 9));
        OrderValidity orderValidity = OrderValidity.fromCode(random.nextInt(1, 6));
        Date timestamp = new Date();

        int orderStatusCode = computeOrderStatus(filledQty, qty, timestamp, orderValidity);
        OrderStatus orderStatus = OrderStatus.fromCode(orderStatusCode);

        Book book = new Book(
                random.nextInt(10000),
                symbol,
                side,
                price,
                qty,
                filledQty,
                remainingQty,
                orderType,
                orderStatus,
                orderValidity,
                timestamp,
                "Nyse"
        );

        // Update last traded price only if trade was partially or fully filled
        if (filledQty > 0) {
            lastTradedPrices.put(symbol, price);
        }

        template.convertAndSend("/topic/order", book);
        System.out.println("Sent: " + book);
        scheduleNextRun();
    }

    private float generateRealisticPrice(Symbol symbol, float lastPrice, Side side) {
        float fluctuationRange = getFluctuationRange(symbol);
        float randomFluctuation = random.nextFloat() * fluctuationRange;

        // Buy should generate lower price, sell higher
        float price = side == Side.BUY
                ? lastPrice - randomFluctuation
                : lastPrice + randomFluctuation;

        return roundToTick(price);
    }

    private float getFluctuationRange(Symbol symbol) {
        return switch (symbol) {
            case AAPL -> 3f;
            case TSLA -> 6f;
            case GOOGL -> 2f;
            case MSFT -> 4f;
            case AMZN -> 3f;
            case NVDA -> 15f;
            case META -> 6f;
        };
    }

    private int generateSkewedQty(Symbol symbol) {
        int min, max;
        switch (symbol) {
            case AAPL -> { min = 10; max = 200; }
            case TSLA -> { min = 5; max = 100; }
            case GOOGL -> { min = 15; max = 250; }
            case MSFT -> { min = 10; max = 150; }
            case AMZN -> { min = 20; max = 300; }
            case NVDA -> { min = 2; max = 50; }
            case META -> { min = 8; max = 120; }
            default -> { min = 10; max = 100; }
        }
        float gaussian = (float) Math.abs(random.nextGaussian());
        return Math.min(max, Math.max(min, Math.round(gaussian * (max - min) / 2)));
    }

    private int computeOrderStatus(int filledQty, int qty, Date timestamp, OrderValidity validity) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.JUNE, 24, 15, 30, 0);
        Date expiry = calendar.getTime();

        if (filledQty == 0) return 1; // Pending
        else if (filledQty < qty) {
            if (timestamp.after(expiry) && validity == OrderValidity.IOC) return 4; // Expired
            else return 2; // Partially Filled
        } else return 3; // Fully Filled
    }
}
