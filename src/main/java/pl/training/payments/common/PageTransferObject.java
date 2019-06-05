package pl.training.payments.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageTransferObject<T> {

    private List<T> data;
    private int pageNumber;
    private int totalPages;

}
