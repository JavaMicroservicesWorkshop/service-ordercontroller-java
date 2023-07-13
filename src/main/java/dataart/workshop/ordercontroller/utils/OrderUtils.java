package dataart.workshop.ordercontroller.utils;

import dataart.workshop.ordercontroller.dto.BookDto;

public class OrderUtils {

    public static String getBeautifiedBookName(BookDto bookDto) {
        return bookDto.getAuthor() + " - " + bookDto.getTitle();
    }
}
