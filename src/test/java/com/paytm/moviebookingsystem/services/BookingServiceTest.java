package com.paytm.moviebookingsystem.services;

import com.paytm.moviebookingsystem.model.Booking;
import com.paytm.moviebookingsystem.repository.BookingRepository;
import com.paytm.moviebookingsystem.repository.SeatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
public class BookingServiceTest {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Test
    public void testMultipleBookingsAtSameTime() throws InterruptedException, ExecutionException {
        int numThreads = 2;

        Integer showId = 3;
        List<Integer> seatIds = List.of(1, 2, 3);
        List<Integer> userIds = List.of(1,2);
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        List<Future<Void>> bookingFutures = new ArrayList<>();


        for (int i = 0; i < numThreads; i++) {
            Callable<Void> bookingTask1 = () -> {
                bookingService.bookSeats(userIds.get(0), showId, seatIds);
                return null;
            };
            Callable<Void> bookingTask2 = () -> {
                bookingService.bookSeats(userIds.get(1), showId, seatIds);
                return null;
            };
            Future<Void> bookingFuture1 = executorService.submit(bookingTask1);
            Future<Void> bookingFuture2 = executorService.submit(bookingTask2);
            bookingFutures.add(bookingFuture1);
            bookingFutures.add(bookingFuture2);
        }

        for (Future<Void> bookingFuture : bookingFutures) {
            try {
                bookingFuture.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Long actualTotalBookings = bookingService.count();
        assertEquals(seatIds.size(), actualTotalBookings);
    }

}
