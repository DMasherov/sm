package patterra.domain.repos;

import org.springframework.stereotype.Component;
import patterra.domain.Document;
import patterra.domain.Invention;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InventionRepositoryStub implements InventionRepository {
    public List<Invention> findAll() {
        return Arrays.asList(
                makeInvention(1, "123", new Date(), 1, 2, 3),
                makeInvention(2, "124", new Date(), 2, 3),
                makeInvention(3, "125", new Date(), 3)
        );
    }

    private Invention makeInvention(Integer id, String appNumber, Date date, Integer... documentIds) {
        Invention invention = new Invention();
        invention.setId(id);
        invention.setApplicationNumber(appNumber);
        invention.setFilingOfficeDate(date);
        invention.setDocuments(
                Arrays.asList(documentIds).stream()
                        .map(docId -> {
                            Document d = new Document();
                            d.setId(docId);
                            d.setName("doc number " + docId);
                            return d;
                        })
                        .collect(Collectors.toSet()));
        return invention;
    }
}
